# Hive 优化

## 日常排查指令

```
ps -ef | grep processprocessname
```
## 日志记录
### 查看 SQL 执行时间和计算资源占用

一般的，我们通过查看 Hive Server的日志。

计算的主要耗时都是在MR上，在查看日志的时候通过记录 MR 所在的 stage 的 start 和 end 来看 SQL 执行的时长

在同样的位置，往往还有CPU计算资源时长的记录，如果发现计算资源的占用时间异常，需要通过排查 yarn 日志来看具体的信息