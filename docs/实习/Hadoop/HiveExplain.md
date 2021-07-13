## HiveExplain 

```SQL
0: jdbc:hive2://localhost:10000> explain
. . . . . . . . . . . . . . . .> insert overwrite table jointable
. . . . . . . . . . . . . . . .> select b.id, b.t, b.uid, b.keyword, b.url_rank, b.click_num, b.click_url
. . . . . . . . . . . . . . . .> from bigtable b
. . . . . . . . . . . . . . . .> join smalltable s
. . . . . . . . . . . . . . . .> on s.id = b.id;
INFO  : Compiling command(queryId=root_20210713105000_9462b0f1-4d8f-4a03-a56d-bba83087721b);USER_NAME:anonymous;IP:127.0.0.1;QUERY_STRING: explain
insert overwrite table jointable
select b.id, b.t, b.uid, b.keyword, b.url_rank, b.click_num, b.click_url
from bigtable b
join smalltable s
on s.id = b.id
INFO  : Semantic Analysis Completed
INFO  : Returning Hive schema: Schema(fieldSchemas:[FieldSchema(name:Explain, type:string, comment:null)], properties:null)
INFO  : Completed compiling command(queryId=root_20210713105000_9462b0f1-4d8f-4a03-a56d-bba83087721b);USER_NAME:anonymous;IP:127.0.0.1;Time taken: 0.418 seconds
INFO  : Concurrency mode is disabled, not creating a lock manager
INFO  : Executing command(queryId=root_20210713105000_9462b0f1-4d8f-4a03-a56d-bba83087721b); USER_NAME=anonymous;IP=127.0.0.1;QUERY_STRING:explain
insert overwrite table jointable
select b.id, b.t, b.uid, b.keyword, b.url_rank, b.click_num, b.click_url
from bigtable b
join smalltable s
on s.id = b.id
INFO  : Starting task [Stage-6:EXPLAIN] in serial mode
INFO  : Completed executing command(queryId=root_20210713105000_9462b0f1-4d8f-4a03-a56d-bba83087721b);USER_NAME=anonymous;IP=127.0.0.1;Time taken: 0.012 seconds
INFO  : OK
+--------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------+--+
|                                                                                       Explain                                                                                        |
+--------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------+--+
| STAGE DEPENDENCIES:                                                                                                                                                                  |
|   Stage-5 is a root stage                                                                                                                                                            |
|   Stage-4 depends on stages: Stage-5                                                                                                                                                 |
|   Stage-0 depends on stages: Stage-4                                                                                                                                                 |
|   Stage-2 depends on stages: Stage-0                                                                                                                                                 |
|                                                                                                                                                                                      |
| STAGE PLANS:                                                                                                                                                                         |
|   Stage: Stage-5                                                                                                                                                                     |
|     Map Reduce Local Work                                                                                                                                                            |
|       Alias -> Map Local Tables:                                                                                                                                                     |
|         $hdt$_1:s                                                                                                                                                                    |
|           Fetch Operator                                                                                                                                                             |
|             limit: -1                                                                                                                                                                |
|       Alias -> Map Local Operator Tree:                                                                                                                                              |
|         $hdt$_1:s                                                                                                                                                                    |
|           TableScan                                                                                                                                                                  |
|             alias: s                                                                                                                                                                 |
|             Statistics: Num rows: 3253771 Data size: 26030168 Basic stats: COMPLETE Column stats: NONE                                                                               |
|             Filter Operator                                                                                                                                                          |
|               predicate: id is not null (type: boolean)                                                                                                                              |
|               Statistics: Num rows: 3253771 Data size: 26030168 Basic stats: COMPLETE Column stats: NONE                                                                             |
|               Select Operator                                                                                                                                                        |
|                 expressions: id (type: bigint)                                                                                                                                       |
|                 outputColumnNames: _col0                                                                                                                                             |
|                 Statistics: Num rows: 3253771 Data size: 26030168 Basic stats: COMPLETE Column stats: NONE                                                                           |
|                 HashTable Sink Operator                                                                                                                                              |
|                   keys:                                                                                                                                                              |
|                     0 _col0 (type: bigint)                                                                                                                                           |
|                     1 _col0 (type: bigint)                                                                                                                                           |
|                                                                                                                                                                                      |
|   Stage: Stage-4                                                                                                                                                                     |
|     Map Reduce                                                                                                                                                                       |
|       Map Operator Tree:                                                                                                                                                             |
|           TableScan                                                                                                                                                                  |
|             alias: b                                                                                                                                                                 |
|             Statistics: Num rows: 797267 Data size: 258314656 Basic stats: COMPLETE Column stats: NONE                                                                               |
|             Filter Operator                                                                                                                                                          |
|               predicate: id is not null (type: boolean)                                                                                                                              |
|               Statistics: Num rows: 797267 Data size: 258314656 Basic stats: COMPLETE Column stats: NONE                                                                             |
|               Select Operator                                                                                                                                                        |
|                 expressions: id (type: bigint), t (type: bigint), uid (type: string), keyword (type: string), url_rank (type: int), click_num (type: int), click_url (type: string)  |
|                 outputColumnNames: _col0, _col1, _col2, _col3, _col4, _col5, _col6                                                                                                   |
|                 Statistics: Num rows: 797267 Data size: 258314656 Basic stats: COMPLETE Column stats: NONE                                                                           |
|                 Map Join Operator                                                                                                                                                    |
|                   condition map:                                                                                                                                                     |
|                        Inner Join 0 to 1                                                                                                                                             |
|                   keys:                                                                                                                                                              |
|                     0 _col0 (type: bigint)                                                                                                                                           |
|                     1 _col0 (type: bigint)                                                                                                                                           |
|                   outputColumnNames: _col0, _col1, _col2, _col3, _col4, _col5, _col6                                                                                                 |
|                   Statistics: Num rows: 3579148 Data size: 28633185 Basic stats: COMPLETE Column stats: NONE                                                                         |
|                   File Output Operator                                                                                                                                               |
|                     compressed: false                                                                                                                                                |
|                     Statistics: Num rows: 3579148 Data size: 28633185 Basic stats: COMPLETE Column stats: NONE                                                                       |
|                     table:                                                                                                                                                           |
|                         input format: org.apache.hadoop.mapred.TextInputFormat                                                                                                       |
|                         output format: org.apache.hadoop.hive.ql.io.HiveIgnoreKeyTextOutputFormat                                                                                    |
|                         serde: org.apache.hadoop.hive.serde2.lazy.LazySimpleSerDe                                                                                                    |
|                         name: default.jointable                                                                                                                                      |
|       Local Work:                                                                                                                                                                    |
|         Map Reduce Local Work                                                                                                                                                        |
|                                                                                                                                                                                      |
|   Stage: Stage-0                                                                                                                                                                     |
|     Move Operator                                                                                                                                                                    |
|       tables:                                                                                                                                                                        |
|           replace: true                                                                                                                                                              |
|           table:                                                                                                                                                                     |
|               input format: org.apache.hadoop.mapred.TextInputFormat                                                                                                                 |
|               output format: org.apache.hadoop.hive.ql.io.HiveIgnoreKeyTextOutputFormat                                                                                              |
|               serde: org.apache.hadoop.hive.serde2.lazy.LazySimpleSerDe                                                                                                              |
|               name: default.jointable                                                                                                                                                |
|                                                                                                                                                                                      |
|   Stage: Stage-2                                                                                                                                                                     |
|     Stats-Aggr Operator                                                                                                                                                              |
|                                                                                                                                                                                      |
+--------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------+--+
75 rows selected (0.507 seconds)
```



```SQL
0: jdbc:hive2://localhost:10000> explain extended
. . . . . . . . . . . . . . . .> insert overwrite table jointable
. . . . . . . . . . . . . . . .> select b.id, b.t, b.uid, b.keyword, b.url_rank, b.click_num, b.click_url
. . . . . . . . . . . . . . . .> from bigtable b
. . . . . . . . . . . . . . . .> join smalltable s
. . . . . . . . . . . . . . . .> on s.id = b.id;
INFO  : Compiling command(queryId=root_20210713105854_fc2c70e8-ad29-4025-ba4e-6979ab8c794e);USER_NAME:anonymous;IP:127.0.0.1;QUERY_STRING: explain extended
insert overwrite table jointable
select b.id, b.t, b.uid, b.keyword, b.url_rank, b.click_num, b.click_url
from bigtable b
join smalltable s
on s.id = b.id
INFO  : Semantic Analysis Completed
INFO  : Returning Hive schema: Schema(fieldSchemas:[FieldSchema(name:Explain, type:string, comment:null)], properties:null)
INFO  : Completed compiling command(queryId=root_20210713105854_fc2c70e8-ad29-4025-ba4e-6979ab8c794e);USER_NAME:anonymous;IP:127.0.0.1;Time taken: 0.402 seconds
INFO  : Concurrency mode is disabled, not creating a lock manager
INFO  : Executing command(queryId=root_20210713105854_fc2c70e8-ad29-4025-ba4e-6979ab8c794e); USER_NAME=anonymous;IP=127.0.0.1;QUERY_STRING:explain extended
insert overwrite table jointable
select b.id, b.t, b.uid, b.keyword, b.url_rank, b.click_num, b.click_url
from bigtable b
join smalltable s
on s.id = b.id
INFO  : Starting task [Stage-6:EXPLAIN] in serial mode
INFO  : Completed executing command(queryId=root_20210713105854_fc2c70e8-ad29-4025-ba4e-6979ab8c794e);USER_NAME=anonymous;IP=127.0.0.1;Time taken: 0.018 seconds
INFO  : OK
+--------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------+--+
|                                                                                       Explain                                                                                        |
+--------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------+--+
| STAGE DEPENDENCIES:                                                                                                                                                                  |
|   Stage-5 is a root stage                                                                                                                                                            |
|   Stage-4 depends on stages: Stage-5                                                                                                                                                 |
|   Stage-0 depends on stages: Stage-4                                                                                                                                                 |
|   Stage-2 depends on stages: Stage-0                                                                                                                                                 |
|                                                                                                                                                                                      |
| STAGE PLANS:                                                                                                                                                                         |
|   Stage: Stage-5                                                                                                                                                                     |
|     Map Reduce Local Work                                                                                                                                                            |
|       Alias -> Map Local Tables:                                                                                                                                                     |
|         $hdt$_1:s                                                                                                                                                                    |
|           Fetch Operator                                                                                                                                                             |
|             limit: -1                                                                                                                                                                |
|       Alias -> Map Local Operator Tree:                                                                                                                                              |
|         $hdt$_1:s                                                                                                                                                                    |
|           TableScan                                                                                                                                                                  |
|             alias: s                                                                                                                                                                 |
|             Statistics: Num rows: 3253771 Data size: 26030168 Basic stats: COMPLETE Column stats: NONE                                                                               |
|             GatherStats: false                                                                                                                                                       |
|             Filter Operator                                                                                                                                                          |
|               isSamplingPred: false                                                                                                                                                  |
|               predicate: id is not null (type: boolean)                                                                                                                              |
|               Statistics: Num rows: 3253771 Data size: 26030168 Basic stats: COMPLETE Column stats: NONE                                                                             |
|               Select Operator                                                                                                                                                        |
|                 expressions: id (type: bigint)                                                                                                                                       |
|                 outputColumnNames: _col0                                                                                                                                             |
|                 Statistics: Num rows: 3253771 Data size: 26030168 Basic stats: COMPLETE Column stats: NONE                                                                           |
|                 HashTable Sink Operator                                                                                                                                              |
|                   keys:                                                                                                                                                              |
|                     0 _col0 (type: bigint)                                                                                                                                           |
|                     1 _col0 (type: bigint)                                                                                                                                           |
|                   Position of Big Table: 0                                                                                                                                           |
|                                                                                                                                                                                      |
|   Stage: Stage-4                                                                                                                                                                     |
|     Map Reduce                                                                                                                                                                       |
|       Map Operator Tree:                                                                                                                                                             |
|           TableScan                                                                                                                                                                  |
|             alias: b                                                                                                                                                                 |
|             Statistics: Num rows: 797267 Data size: 258314656 Basic stats: COMPLETE Column stats: NONE                                                                               |
|             GatherStats: false                                                                                                                                                       |
|             Filter Operator                                                                                                                                                          |
|               isSamplingPred: false                                                                                                                                                  |
|               predicate: id is not null (type: boolean)                                                                                                                              |
|               Statistics: Num rows: 797267 Data size: 258314656 Basic stats: COMPLETE Column stats: NONE                                                                             |
|               Select Operator                                                                                                                                                        |
|                 expressions: id (type: bigint), t (type: bigint), uid (type: string), keyword (type: string), url_rank (type: int), click_num (type: int), click_url (type: string)  |
|                 outputColumnNames: _col0, _col1, _col2, _col3, _col4, _col5, _col6                                                                                                   |
|                 Statistics: Num rows: 797267 Data size: 258314656 Basic stats: COMPLETE Column stats: NONE                                                                           |
|                 Map Join Operator                                                                                                                                                    |
|                   condition map:                                                                                                                                                     |
|                        Inner Join 0 to 1                                                                                                                                             |
|                   keys:                                                                                                                                                              |
|                     0 _col0 (type: bigint)                                                                                                                                           |
|                     1 _col0 (type: bigint)                                                                                                                                           |
|                   outputColumnNames: _col0, _col1, _col2, _col3, _col4, _col5, _col6                                                                                                 |
|                   Position of Big Table: 0                                                                                                                                           |
|                   Statistics: Num rows: 3579148 Data size: 28633185 Basic stats: COMPLETE Column stats: NONE                                                                         |
|                   File Output Operator                                                                                                                                               |
|                     compressed: false                                                                                                                                                |
|                     GlobalTableId: 1                                                                                                                                                 |
|                     directory: hdfs://0.0.0.0:9000/user/hive/warehouse/jointable/.hive-staging_hive_2021-07-13_10-58-54_242_6667067201597482369-1/-ext-10000                         |
|                     NumFilesPerFileSink: 1                                                                                                                                           |
|                     Statistics: Num rows: 3579148 Data size: 28633185 Basic stats: COMPLETE Column stats: NONE                                                                       |
|                     Stats Publishing Key Prefix: hdfs://0.0.0.0:9000/user/hive/warehouse/jointable/.hive-staging_hive_2021-07-13_10-58-54_242_6667067201597482369-1/-ext-10000/      |
|                     table:                                                                                                                                                           |
|                         input format: org.apache.hadoop.mapred.TextInputFormat                                                                                                       |
|                         output format: org.apache.hadoop.hive.ql.io.HiveIgnoreKeyTextOutputFormat                                                                                    |
|                         properties:                                                                                                                                                  |
|                           COLUMN_STATS_ACCURATE {"BASIC_STATS":"true"}                                                                                                               |
|                           bucket_count -1                                                                                                                                            |
|                           columns id,t,uid,keyword,url_rank,click_num,click_url                                                                                                      |
|                           columns.comments                                                                                                                                           |
|                           columns.types bigint:bigint:string:string:int:int:string                                                                                                   |
|                           field.delim 	                                                                                                                                              |
|                           file.inputformat org.apache.hadoop.mapred.TextInputFormat                                                                                                  |
|                           file.outputformat org.apache.hadoop.hive.ql.io.HiveIgnoreKeyTextOutputFormat                                                                               |
|                           location hdfs://0.0.0.0:9000/user/hive/warehouse/jointable                                                                                                 |
|                           name default.jointable                                                                                                                                     |
|                           numFiles 2                                                                                                                                                 |
|                           numRows 2000040                                                                                                                                            |
|                           rawDataSize 242945374                                                                                                                                      |
|                           serialization.ddl struct jointable { i64 id, i64 t, string uid, string keyword, i32 url_rank, i32 click_num, string click_url}                             |
|                           serialization.format 	                                                                                                                                     |
|                           serialization.lib org.apache.hadoop.hive.serde2.lazy.LazySimpleSerDe                                                                                       |
|                           totalSize 244945414                                                                                                                                        |
|                           transient_lastDdlTime 1625037137                                                                                                                           |
|                         serde: org.apache.hadoop.hive.serde2.lazy.LazySimpleSerDe                                                                                                    |
|                         name: default.jointable                                                                                                                                      |
|                     TotalFiles: 1                                                                                                                                                    |
|                     GatherStats: true                                                                                                                                                |
|                     MultiFileSpray: false                                                                                                                                            |
|       Local Work:                                                                                                                                                                    |
|         Map Reduce Local Work                                                                                                                                                        |
|       Path -> Alias:                                                                                                                                                                 |
|         hdfs://0.0.0.0:9000/user/hive/warehouse/bigtable [$hdt$_0:b]                                                                                                                 |
|       Path -> Partition:                                                                                                                                                             |
|         hdfs://0.0.0.0:9000/user/hive/warehouse/bigtable                                                                                                                             |
|           Partition                                                                                                                                                                  |
|             base file name: bigtable                                                                                                                                                 |
|             input format: org.apache.hadoop.mapred.TextInputFormat                                                                                                                   |
+--------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------+--+
|                                                                                       Explain                                                                                        |
+--------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------+--+
|             output format: org.apache.hadoop.hive.ql.io.HiveIgnoreKeyTextOutputFormat                                                                                                |
|             properties:                                                                                                                                                              |
|               bucket_count -1                                                                                                                                                        |
|               columns id,t,uid,keyword,url_rank,click_num,click_url                                                                                                                  |
|               columns.comments                                                                                                                                                       |
|               columns.types bigint:bigint:string:string:int:int:string                                                                                                               |
|               field.delim 	                                                                                                                                                          |
|               file.inputformat org.apache.hadoop.mapred.TextInputFormat                                                                                                              |
|               file.outputformat org.apache.hadoop.hive.ql.io.HiveIgnoreKeyTextOutputFormat                                                                                           |
|               location hdfs://0.0.0.0:9000/user/hive/warehouse/bigtable                                                                                                              |
|               name default.bigtable                                                                                                                                                  |
|               numFiles 2                                                                                                                                                             |
|               numRows 0                                                                                                                                                              |
|               rawDataSize 0                                                                                                                                                          |
|               serialization.ddl struct bigtable { i64 id, i64 t, string uid, string keyword, i32 url_rank, i32 click_num, string click_url}                                          |
|               serialization.format 	                                                                                                                                                 |
|               serialization.lib org.apache.hadoop.hive.serde2.lazy.LazySimpleSerDe                                                                                                   |
|               totalSize 258314664                                                                                                                                                    |
|               transient_lastDdlTime 1625036312                                                                                                                                       |
|             serde: org.apache.hadoop.hive.serde2.lazy.LazySimpleSerDe                                                                                                                |
|                                                                                                                                                                                      |
|               input format: org.apache.hadoop.mapred.TextInputFormat                                                                                                                 |
|               output format: org.apache.hadoop.hive.ql.io.HiveIgnoreKeyTextOutputFormat                                                                                              |
|               properties:                                                                                                                                                            |
|                 bucket_count -1                                                                                                                                                      |
|                 columns id,t,uid,keyword,url_rank,click_num,click_url                                                                                                                |
|                 columns.comments                                                                                                                                                     |
|                 columns.types bigint:bigint:string:string:int:int:string                                                                                                             |
|                 field.delim 	                                                                                                                                                        |
|                 file.inputformat org.apache.hadoop.mapred.TextInputFormat                                                                                                            |
|                 file.outputformat org.apache.hadoop.hive.ql.io.HiveIgnoreKeyTextOutputFormat                                                                                         |
|                 location hdfs://0.0.0.0:9000/user/hive/warehouse/bigtable                                                                                                            |
|                 name default.bigtable                                                                                                                                                |
|                 numFiles 2                                                                                                                                                           |
|                 numRows 0                                                                                                                                                            |
|                 rawDataSize 0                                                                                                                                                        |
|                 serialization.ddl struct bigtable { i64 id, i64 t, string uid, string keyword, i32 url_rank, i32 click_num, string click_url}                                        |
|                 serialization.format 	                                                                                                                                               |
|                 serialization.lib org.apache.hadoop.hive.serde2.lazy.LazySimpleSerDe                                                                                                 |
|                 totalSize 258314664                                                                                                                                                  |
|                 transient_lastDdlTime 1625036312                                                                                                                                     |
|               serde: org.apache.hadoop.hive.serde2.lazy.LazySimpleSerDe                                                                                                              |
|               name: default.bigtable                                                                                                                                                 |
|             name: default.bigtable                                                                                                                                                   |
|         hdfs://0.0.0.0:9000/user/hive/warehouse/smalltable                                                                                                                           |
|           Partition                                                                                                                                                                  |
|             base file name: smalltable                                                                                                                                               |
|             input format: org.apache.hadoop.mapred.TextInputFormat                                                                                                                   |
|             output format: org.apache.hadoop.hive.ql.io.HiveIgnoreKeyTextOutputFormat                                                                                                |
|             properties:                                                                                                                                                              |
|               bucket_count -1                                                                                                                                                        |
|               columns id,t,uid,keyword,url_rank,click_num,click_url                                                                                                                  |
|               columns.comments                                                                                                                                                       |
|               columns.types bigint:bigint:string:string:int:int:string                                                                                                               |
|               field.delim 	                                                                                                                                                          |
|               file.inputformat org.apache.hadoop.mapred.TextInputFormat                                                                                                              |
|               file.outputformat org.apache.hadoop.hive.ql.io.HiveIgnoreKeyTextOutputFormat                                                                                           |
|               location hdfs://0.0.0.0:9000/user/hive/warehouse/smalltable                                                                                                            |
|               name default.smalltable                                                                                                                                                |
|               numFiles 2                                                                                                                                                             |
|               numRows 0                                                                                                                                                              |
|               rawDataSize 0                                                                                                                                                          |
|               serialization.ddl struct smalltable { i64 id, i64 t, string uid, string keyword, i32 url_rank, i32 click_num, string click_url}                                        |
|               serialization.format 	                                                                                                                                                 |
|               serialization.lib org.apache.hadoop.hive.serde2.lazy.LazySimpleSerDe                                                                                                   |
|               totalSize 26030168                                                                                                                                                     |
|               transient_lastDdlTime 1625036326                                                                                                                                       |
|             serde: org.apache.hadoop.hive.serde2.lazy.LazySimpleSerDe                                                                                                                |
|                                                                                                                                                                                      |
|               input format: org.apache.hadoop.mapred.TextInputFormat                                                                                                                 |
|               output format: org.apache.hadoop.hive.ql.io.HiveIgnoreKeyTextOutputFormat                                                                                              |
|               properties:                                                                                                                                                            |
|                 bucket_count -1                                                                                                                                                      |
|                 columns id,t,uid,keyword,url_rank,click_num,click_url                                                                                                                |
|                 columns.comments                                                                                                                                                     |
|                 columns.types bigint:bigint:string:string:int:int:string                                                                                                             |
|                 field.delim 	                                                                                                                                                        |
|                 file.inputformat org.apache.hadoop.mapred.TextInputFormat                                                                                                            |
|                 file.outputformat org.apache.hadoop.hive.ql.io.HiveIgnoreKeyTextOutputFormat                                                                                         |
|                 location hdfs://0.0.0.0:9000/user/hive/warehouse/smalltable                                                                                                          |
|                 name default.smalltable                                                                                                                                              |
|                 numFiles 2                                                                                                                                                           |
|                 numRows 0                                                                                                                                                            |
|                 rawDataSize 0                                                                                                                                                        |
|                 serialization.ddl struct smalltable { i64 id, i64 t, string uid, string keyword, i32 url_rank, i32 click_num, string click_url}                                      |
|                 serialization.format 	                                                                                                                                               |
|                 serialization.lib org.apache.hadoop.hive.serde2.lazy.LazySimpleSerDe                                                                                                 |
|                 totalSize 26030168                                                                                                                                                   |
|                 transient_lastDdlTime 1625036326                                                                                                                                     |
|               serde: org.apache.hadoop.hive.serde2.lazy.LazySimpleSerDe                                                                                                              |
|               name: default.smalltable                                                                                                                                               |
|             name: default.smalltable                                                                                                                                                 |
|       Truncated Path -> Alias:                                                                                                                                                       |
|         /bigtable [$hdt$_0:b]                                                                                                                                                        |
|                                                                                                                                                                                      |
|   Stage: Stage-0                                                                                                                                                                     |
|     Move Operator                                                                                                                                                                    |
|       tables:                                                                                                                                                                        |
|           replace: true                                                                                                                                                              |
|           source: hdfs://0.0.0.0:9000/user/hive/warehouse/jointable/.hive-staging_hive_2021-07-13_10-58-54_242_6667067201597482369-1/-ext-10000                                      |
+--------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------+--+
|                                                                                       Explain                                                                                        |
+--------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------+--+
|           table:                                                                                                                                                                     |
|               input format: org.apache.hadoop.mapred.TextInputFormat                                                                                                                 |
|               output format: org.apache.hadoop.hive.ql.io.HiveIgnoreKeyTextOutputFormat                                                                                              |
|               properties:                                                                                                                                                            |
|                 COLUMN_STATS_ACCURATE {"BASIC_STATS":"true"}                                                                                                                         |
|                 bucket_count -1                                                                                                                                                      |
|                 columns id,t,uid,keyword,url_rank,click_num,click_url                                                                                                                |
|                 columns.comments                                                                                                                                                     |
|                 columns.types bigint:bigint:string:string:int:int:string                                                                                                             |
|                 field.delim 	                                                                                                                                                        |
|                 file.inputformat org.apache.hadoop.mapred.TextInputFormat                                                                                                            |
|                 file.outputformat org.apache.hadoop.hive.ql.io.HiveIgnoreKeyTextOutputFormat                                                                                         |
|                 location hdfs://0.0.0.0:9000/user/hive/warehouse/jointable                                                                                                           |
|                 name default.jointable                                                                                                                                               |
|                 numFiles 2                                                                                                                                                           |
|                 numRows 2000040                                                                                                                                                      |
|                 rawDataSize 242945374                                                                                                                                                |
|                 serialization.ddl struct jointable { i64 id, i64 t, string uid, string keyword, i32 url_rank, i32 click_num, string click_url}                                       |
|                 serialization.format 	                                                                                                                                               |
|                 serialization.lib org.apache.hadoop.hive.serde2.lazy.LazySimpleSerDe                                                                                                 |
|                 totalSize 244945414                                                                                                                                                  |
|                 transient_lastDdlTime 1625037137                                                                                                                                     |
|               serde: org.apache.hadoop.hive.serde2.lazy.LazySimpleSerDe                                                                                                              |
|               name: default.jointable                                                                                                                                                |
|                                                                                                                                                                                      |
|   Stage: Stage-2                                                                                                                                                                     |
|     Stats-Aggr Operator                                                                                                                                                              |
|       Stats Aggregation Key Prefix: hdfs://0.0.0.0:9000/user/hive/warehouse/jointable/.hive-staging_hive_2021-07-13_10-58-54_242_6667067201597482369-1/-ext-10000/                   |
|                                                                                                                                                                                      |
+--------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------+--+
229 rows selected (0.521 seconds)
```