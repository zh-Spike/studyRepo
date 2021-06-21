# Hive3
## Hive 版本升级

HDP 换到 3.1.4 后存在配置beeline新参数的问题
```xml
hive.metastore.event.db.notification.api.auth=false
```

### Tez 引擎的编译和测试

Tez 0.92 编译不支持 HDP3.3.0 protobuf 版本不是 2.5.0

换成 HDP3.1.4 成功编译 

注意 Guava 在 Hive、Tez、HDP 三个lib中的版本都不一样，会导致确实依赖，统一换成HDP的`27-jre`版本

