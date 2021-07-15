# GC问题学习

CMS 收集器，会发生 STW（Stop the World），一般是 Mutator 线程达到了 SafePoint.

![](../pics/GGGGGCCCCCC.png)

1. 未发生 GC，但 STW 很久。
   
    进入safePoint时间过长

2. CMS GC

    1. Young GC时间过长
    2. CMS GC 次数过多
       1. 参数XX:CMSInitiatingOccupancyFraction是否设置太小
       2. 观察`jstat`，确认进入老生代对象的规律。如果是老生代周期性地进入大量对象，需要做两个事情，其一是确认新生代是否设置太小，或者survivor区设置太小，很多对象没有经过充分淘汰就进入老生代。其二是确认是否代码层面有周期性的加载数据的操作，此时需要使用jmap + MAT确认，或者如果对业务熟悉的话，可以猜想具体原因。

    3. CMS GC InitMark/Remark阶段耗时长

        1. 如果remark阶段过长是rescan耗时过长，可以开启-XX:+CMSScavengeBeforeRemark选项，强制remark之前开始一次minor gc，减少remark的暂停时间，但是在remark之后也将立即开始又一次minor gc。
        2. 如果remark阶段过长是因为weak refs processing 处理时间太长，可以加上参数-XX:+PrintReferenceGC打印各种Reference的个数和处理时间。这种场景下可以采取的优化主要有两个：其一是开启并行remark: -XX:+CMSParallelRemarkEnabled。另一个是使用MAT分析堆内存中reference较多的根本原因。

    4. CMS Full GC比较频繁，FGC问题分析从下面两个维度进行考虑
       1. 维度一：查看gc日志，确认是哪种Full GC。不同的FGC，调整策略不同。
            ```
            concurrent mode failure：调整参数-XX:CMSInitiatingOccupancyFraction
            promotion mode failure：调整为G1GC
            ```
        除过上述两个之外：考虑system.gc() 。
        
        2. 使用jstat观察或者根据gc日志观察。观察的依据是gc日志中ygc的年龄结构是不是只有age=1或者age=2的对象，或者jstat发现很多对象没有经过Survivor直接晋升到老生代。
            - Young区设置太小/Survivor区设置太小，导致很多对象没有充分在Young区淘汰进入了Old区。
            - 业务/代码原因短时间内申请了大量对象，这些对象直接进入Old区，导致Old区可用空间不足
            - 业务/代码原因产生了大对象，不经过Young区直接进入Old区

### JVM G1GC相关问题
 1. 存活对象占比直接影响Xmx以及Live阈值参数
 2. G1GC Scan rs耗时太长问题
 3. 并发线程数量调整
 4. 单次Mixed GC时间太长：可以分散到多次Mixed GC

### 其他特殊案例

real时间严重大于user时间+sys时间，一般遇到这种问题，可以从两个方面分析：

1. 在对应的时间点是否有内存swap，导致磁盘IO。当物理内存不够时，为了顺利执行程序，会把内存中暂时不用的数据挪到一块特殊的硬盘区域中，这个过程就是swap，发生swap时，real time可能变大。
2. gclog是否被阻塞

## 内存碎片&收集器退化
1. 现象
并发的 CMS GC 算法，退化为 Foreground 单线程串行 GC 模式，STW 时间超长，有时会长达十几秒。其中 CMS 收集器退化后单线程串行 GC 算法有两种：

   1. 带压缩动作的算法，称为 MSC，上面我们介绍过，使用标记-清理-压缩，单线程全暂停的方式，对整个堆进行垃圾收集，也就是真正意义上的 Full GC，暂停时间要长于普通 CMS。
   2. 不带压缩动作的算法，收集 Old 区，和普通的 CMS 算法比较相似，暂停时间相对 MSC 算法短一些。

2. 原因

   1. 晋升失败（Promotion Failed）
      1. YGC 失败 内存碎片化
   2. 增量收集担保失败
      1. 直接除法 FGC
   3. 并发模式失败（Concurrent Mode Failure）
      1. 也是发生概率较高的一种，在 GC 日志中经常能看到 Concurrent Mode Failure 关键字。这种是由于并发 Background CMS GC 正在执行，同时又有 Young GC 晋升的对象要放入到了 Old 区中，而此时 Old 区空间不足造成的。
      2. 浮动垃圾
      3. 为什么 CMS GC 正在执行还会导致收集器退化呢？主要是由于 CMS 无法处理浮动垃圾（Floating Garbage）引起的。CMS 的并发清理阶段，Mutator 还在运行，因此不断有新的垃圾产生，而这些垃圾不在这次清理标记的范畴里，无法在本次 GC 被清除掉，这些就是浮动垃圾，除此之外在 Remark 之前那些断开引用脱离了读写屏障控制的对象也算浮动垃圾。所以 Old 区回收的阈值不能太高，否则预留的内存空间很可能不够，从而导致 Concurrent Mode Failure 发生。

3. 解决方案

- 内存碎片： 通过配置 -XX:UseCMSCompactAtFullCollection=true 来控制 Full GC的过程中是否进行空间的整理（默认开启，注意是Full GC，不是普通CMS GC），以及 -XX: CMSFullGCsBeforeCompaction=n 来控制多少次 Full GC 后进行一次压缩。

- 增量收集： 降低触发 CMS GC 的阈值，即参数 -XX:CMSInitiatingOccupancyFraction 的值，让 CMS GC 尽早执行，以保证有足够的连续空间，也减少 Old 区空间的使用大小，另外需要使用 -XX:+UseCMSInitiatingOccupancyOnly 来配合使用，不然 JVM 仅在第一次使用设定值，后续则自动调整。

- 浮动垃圾： 视情况控制每次晋升对象的大小，或者缩短每次 CMS GC 的时间，必要时可调节 NewRatio 的值。另外就是使用 -XX:+CMSScavengeBeforeRemark 在过程中提前触发一次 Young GC，防止后续晋升过多对象。
---
学习自组长范欣欣、美团技术博客