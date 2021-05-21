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

用到的版本就是 2.1.1 而且一般都是要通过 JDBC 链接 MySQL 的

Caution：MySQL 巨坑

换了 Ubuntu 不同版本的MySQL都是不同的
```
我之前用的是 Ubuntu 20 默认是 8.0 坑大的一b
迁移到 16,有手就行
```
安装MySQL
```shell
sudo apt-get install -y mysql-server-5.7
```
配置对应JDBC驱动 丢到lib下
```shell
cd /home/spike/hive-2.1.1/lib
wget https://maven.aliyun.com/repository/public/mysql/mysql-connector-java/8.0.20/mysql-connector-java-8.0.20.jar
```

配置 profile
```shell
export HIVE_HOME=/home/spike/hive-2.1.1
PATH=$PATH:$HIVE_HOME/bin
```
```shell
source /etc/profile
```

配置 hive 的 xml
```shell
mv conf/hive-env.sh.template conf/hive-env.sh # 重命名环境文件
mv conf/hive-log4j2.properties.template conf/hive-log4j2.properties # 重命名日志文件
cp conf/hive-default.xml.template conf/hive-site.xml # 拷贝生成 xml 文件
```
配置 xml 直接查找 
```xml
<!-- hive元数据地址，默认是/user/hive/warehouse -->
<property>
    <name>hive.metastore.warehouse.dir</name>
    <value>/user/hive/warehouse</value>
</property>
<!-- hive查询时输出列名 -->
<property>
    <name>hive.cli.print.header</name>
    <value>true</value>
</property>
<!-- 显示当前数据库名 -->
<property>
    <name>hive.cli.print.current.db</name>
    <value>true</value>
</property>
<!-- 开启本地模式，默认是false -->
<property>
    <name>hive.exec.mode.local.auto</name>
    <value>true</value>
</property>

<property>
    <name>javax.jdo.option.ConnectionUserName</name>
    <value>root</value>
    <description>Username to use against metastore database</description>
</property>
<property>
    <name>javax.jdo.option.ConnectionPassword</name>
    <value>root</value>
    <description>password to use against metastore database</description>
</property>
<!-- URL用于连接远程元数据 -->
<property>
    <name>hive.metastore.uris</name>
    <value>thrift://localhost:9083</value>
</property>
<!-- 元数据使用mysql数据库, amp用来配置时区的-->
<property>
    <name>javax.jdo.option.ConnectionURL</name>
    <value>jdbc:mysql://localhost:3306/hivedb?createDatabaseIfNotExist=true&amp;userSSL=false</value>
</property>
<property>
    <name>javax.jdo.option.ConnectionDriverName</name>
    <value>com.mysql.cj.jdbc.Driver</value>
    <description>Driver class name for a JDBC metastore</description>
</property>
```
error 1
```shell
Caused by: java.net.URISyntaxException: Relative path in absolute URI: ${system:java.io.tmpdir%7D/$%7Bsystem:user.name%7D
```
add this in xml
```xml
  <property>
    <name>system:java.io.tmpdir</name>
    <value>/tmp/hive/java</value>
  </property>
  <property>
    <name>system:user.name</name>
    <value>${user.name}</value>
  </property>
```

error2
```shell
java.lang.IllegalArgumentException:java.net.URISyntaxException: Relative path in absolute URI:{system:java.io.tmpdir%7D/{system:java.io.tmpdir%7D/%7Bsystem:user.name%7D
```
缺少 tmp 文件
```
在hive安装目录下创建临时io的tmp文件夹：
mkdir iotmp
然后，将这个iotmp的绝对路径替换hive-site.xml文件中的
${system:java.io.tmpdir}。
再执行hive，启动成功！
在进行hive测试时也会有IllegalArgumentException问题，这时也只需要将相对路径改为绝对路径！
```
error3
```shell
Error: Duplicate key name 'PCS_STATS_IDX' (state=42000,code=1061)
```
一看 deplicate 数据库重了

这个错误是因为之前你的数据库名字或者表的名字已经存在啦，需要你把之前的删了或者在重新创建一个新的，直接在/conf/hive-site.xml配置文件下面修改，如下图所示，重新定义了一个数据库的名字就可以了
![](../pics/797f2f91a89744b6ddd857141b6a1d99.png)

给 MySQL 提权
```SQL
mysql> update mysql.user set host='%' where user='root';
mysql> flush privileges;
```

初始化hive
```
schematool -dbType mysql -initSchema
```
开始元数据
```shell
nohup hive --service metastore & 
```
启动hive
```shell
hive
```
hive 里查看 hdfs 文件系统
```shell
hive(default)>dfs -ls /;
```
## Hive 基础理论和语法学习

