# Hive 

## Hadoop && Hive

### Hadoop 2.7.7 编译

最开始用Ubuntu20 编译的,问题很多

1. protoBuf版本不对
2. findbugs 未安装
3. CmakeList.txt 文件中需要在cmake_minimum_required(VERSION 2.6 FATAL_ERROR)增加一行
    ```
    project(ProjectName)
    ```
4. openssl版本太高（1.1.1->1.0.2）
   ```
   make VERBOSE=1
   ```
### Hadoop 2.10.1 的部署

后来想了下，编译个锤子，直接编译好的包

先搞个 本地模式试试看

解压
```
tar -zxvf hadoop-2.10.1.tar.gz
```
配下profile
```
sudo vim /etc/profile
```
配置 HADOOP_HOME 变量,将 hadoop 的 bin 目录加入到 PATH 变量中
```shell
export HADOOP_HOME=/home/spike/hadoop-2.10.1
PATH=$PATH:$HADOOP_HOME/bin
```


#### run demo 

搞一个 words.txt 内容如下
```
java hadoop spark
python scala hive
hive hbase scala spark
java php spark hadoop
```
运行 hadoop 的 wordcount mapreduce 示例
```shell
hadoop jar $HADOOP_HOME/share/hadoop/mapreduce/hadoop-mapreduce-examples-2.10.1.jar wordcount words output
```

查看output 目录下生成了两个文件 

`part-r-00000` 和 `_SUCCESS`

`_SUCCESS` 说明运行成功了,我们查看 `part-r-00000` 的输出结果
```shell
hadoop	2
hbase	1
hive	2
java	2
php	1
python	1
scala	2
spark	3
```

### Hive 2.1.1 编译和部署

## Hive 基础理论和语法学习

