### 接入包说明
本接入包提供了dify接口的接入方法。

* 引入依赖包
```xml
<dependency>
    <groupId>cn.jzyunqi</groupId>
    <artifactId>yqfw-common-third-dify</artifactId>
    <version>${yqfw.version}</version>
</dependency>
```
* 引入配置
```java
@Import({DifyConfig.class})
```
* 配置自己的Dify信息
```java
@Bean
public DifyAuthRepository difyAuthRepository() {
    return () -> List.of(
            new DifyAuth("1", "https://api.dify.ai/v1", "app-xxxx"),
            new DifyAuth("2", "https://api.dify.ai/v1", "app-yyyy")
    );
}
```