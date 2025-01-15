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
* 配置自己的微信公众号信息
```java
@Bean
public DifyClientConfig difyClientConfig() {
    return new DifyClientConfig(
            "xxxx",
            "xxxx",
            "xxxx",
            "xxx",
            "xxx"
    );
}
```