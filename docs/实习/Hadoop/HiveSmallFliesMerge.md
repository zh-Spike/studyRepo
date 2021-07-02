## Merge Hive Small File 合并小文件
### Hive Small File 产生

1. 在 MR 中的 reduce 阶段，会产生文件 reduce 的越多 小文件越多
2. 分区表，在进行动态分区表的操作时产生的动态分区的小文件
3. 数据表本来就有很多小文件

### Hive Small File 过多影响

1. Hive 的数据还是存在 HDFS 上的，小文件太多，容易在文件存储端造成瓶颈，给 NN 带来压力从而影响处理效率

2. 要对这些小文件也进行 MR，效率低
   
### Hive 自带解决方案
输入端设置
```SQL
-- 每个Map最大输入大小(这个值决定了合并后文件的数量)
set mapred.max.split.size=256000000;  
-- 一个节点上split的至少的大小(这个值决定了多个DataNode上的文件是否需要合并)
set mapred.min.split.size.per.node=100000000;
-- 一个交换机下split的至少的大小(这个值决定了多个交换机上的文件是否需要合并)  
set mapred.min.split.size.per.rack=100000000;
-- 执行Map前进行小文件合并
set hive.input.format=org.apache.hadoop.hive.ql.io.CombineHiveInputFormat; 
```

输出端设置
```SQL
-- 如果hive的程序，只有maptask，将MapTask产生的所有小文件进行合并
set hive.merge.mapfiles=true;
-- 如果hive的程序，有Map和ReduceTask,将ReduceTask产生的所有小文件进行合并
set hive.merge.mapredfiles=true;
-- 每一个合并的文件的大小
set hive.merge.size.per.task=256000000;
-- 平均每个文件的大小，如果小于这个值就会进行合并
set hive.merge.smallfiles.avgsize=16000000;
```

### 数据归档

#### 数据归档解释

HDP 内置一个 hadoop archive，可以把文件归并成较大的文件，归档后创建一个 data.har, 里面放索引和数据。索引记录归并前文件在归并后的位置。

#### 操作
```SQL
-- 用来控制归档是否可用
set hive.archive.enabled=true;
-- 通知Hive在创建归档时是否可以设置父目录
set hive.archive.har.parentdir.settable=true;
-- 控制需要归档文件的大小
set har.partfile.size=1099511627776;

-- 使用以下命令进行归档
ALTER TABLE A ARCHIVE PARTITION(dt='2020-12-24', hr='12');

-- 对已归档的分区恢复为原文件
ALTER TABLE A UNARCHIVE PARTITION(dt='2020-12-24', hr='12');
```
注意:
- 归档的分区可以查看不能 insert overwrite，必须先 unarchive

### 存储文件来处理

默认的 textfile 并不是高效的文件存储方式。

ORC 可以通过 `concatenate` 合并的
```SQL
ALTER TABLE table_name [PARTITION (partition_key = 'partition_value')] CONCATENATE;
```

Parquet文件合并

作为Apache Parquet项目的一部分，有一组基于Java的命令行工具，称为parquet-tools。最新的parquet-tools版本包括一个merge命令，该命令可以将较小的parquet文件逻辑地追加到较大的parquet文件中。该命令以二进制形式将parquet文件块串联在一起，而无需序列化/反序列化、合并页脚、修改路径和偏移量元数据。

要在HDFS中运行parquet-tools merge命令：
```shell
hadoop jar parquet-tools-1.9.0.jar merge <input> <output>
```
其中，input是源parquet文件或目录，而output是合并原始内容的目标parquet文件，此合并命令不会删除或覆盖原始文件。因此，它需要手动创建一个临时目录，并用压缩后的文件替换原始的小文件，以使Big SQL或Apache Hive知道该文件。

另外，不管文件的存储格式如何，要考虑的解决方案是重新创建表并通过执行INSERT…SELECT进行压缩。

### 使用INSERT…SELECT合并文件

通过使用`INSERT…SELECT`语法直接创建一个新表作为原始表的副本来压缩效率低下的拆分数据，此过程将根据插入的并行度将数据重新组织为相对少量的较大文件。

以下是一个如何创建新表，然后在Big SQL中插入旧表中的数据的示例：
```SQL
CREATE TABLE new_table LIKE old_table;
INSERT INTO new_table select * from old_table;
```
该解决方案还允许通过将数据分区复制到新表中，删除原始分区并插入新的压缩分区来合并单个分区中的文件。