## Hadoop 
### 1.x -> 2.x -> 3.x 组成原理
1.x 
    
    HDFS + MapReduce
    
    HDFS存储数据 MapReduce计算和调度资源
2.x 和 3.x 在具体组成上没啥区别
    
    HDFS + MapReduce + Yarn 

    比较于1.x,把 MapReduce 的调度部分剥离出来给 Yarn

### HDFS 概述

NameNode 存储数据的位置信息 (我的理解是 磁盘存储的索引)

NameNode（nn）：存储文件的元数据，如文件名，文件目录结构，文件属性（生成时间、副本数、文件权限），以及每个文件的块列表和块所在的DataNode等。

DataNode 存储具体的内容(类比 b+ Tree 的 叶子节点下的挂的data)

DataNode(dn)：在本地文件系统存储文件块数据，以及块数据的校验和。

分布式，要对NameNode持悲观态度，存在一个`秘书节点 2NN`来作为 NameNode 的备份

Secondary NameNode(2nn)：每隔一段时间对NameNode元数据备份。

### Yarn 概述

![](../pics/Screenshot%202021-05-19%20112607.png)

1. ResourceManager（RM）：整个集群资源（内存、CPU等）的老大
2. ApplicationMaster（AM）：单个任务运行的老大
3. NodeManager（N M）：单个节点服务器资源老大
4. Container：容器，相当一台独立的服务器，里面封装了任务运行所需要的资源，如内存、CPU、磁盘、网络等。

类似于linkedlist，单个服务的Master可以挂载在后面的不同的串行任务

### MapReduce 概述

MapReduce 本质就是给影射+并行处理+归并的过程

MapReduce 将计算过程分为两个阶段：Map 和 Reduce

1. Map 阶段并行处理输入数据
2. Reduce 阶段对 Map 结果进行汇总


### 流程图

![](../pics/Screenshot%202021-05-19%20114053.png)

### Hadoop 部署的坑

#### 绝对路径 和 $ 的坑 
hadoop in my Ubuntu as a single node cluster. This is my JAVA_HOME in my hadoop_env.sh

Please use `Absolute Path` in hadoop_env.sh 

It is define `JAVA_HOME` like this, so it can be wrong
```sh
export JAVA_HOME=${JAVA_HOME}
```
if you hard-code the path to your JVM installation it works
```sh
export JAVA_HOME=/usr/lib/jvm/java...
```
this resolution by environmental variable as is seems to fail. Hard-coding fixed the problem for me.

#### 伪分布式存在着22端口和SSH服务没开
实际上就是要配置一下 SSH 服务
```sh
apt install openssh-server
```
然后 设个空密码 

#### HDFS namenode is not starting up
停掉 yarn 和 HDFS
```sh
./sbin/stop-all.sh
```

DFS needs to be formatted. Just issue the following command after stopping all and then restart.

format 一下 NN 节点，再跑`start-DFS.sh`和`start-yarn.sh` 
```
hadoop namenode -format
```

FORMAT command will check or create path/dfs/name, and initialize or reinitalize it. then running start-dfs.sh would run namenode, datanode, then namesecondary. when namenode check not exist path/dfs/name or not initialize, it occurs a fatal error, then exit. that's why namenode not start up.

more details you can check HADOOP_COMMON/logs/XXX.namenode.log