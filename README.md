### 接入包说明
本接入包提供了微信公众号、微信支付、微信小程序、微信开放平台等微信接口的接入，并提供了微信消息、支付回调接口的处理方法。

* 引入依赖包
```xml
<dependency>
    <groupId>cn.jzyunqi</groupId>
    <artifactId>yqfw-common-third-weixin</artifactId>
    <version>${yqfw.version}</version>
</dependency>
```
* 引入配置
```java
@Import({WxMpConfig.class})
```
* 配置自己的微信公众号信息
```java
@Bean
public WxMpClientConfig wxMpClientConfig() {
    return new WxMpClientConfig(
            "xxxx",
            "xxxx",
            "xxxx",
            "xxx",
            "xxx"
    );
}
```


### 接入微信消息回调接口方法
1. 新增一个controller并继承AWeixinMsgCbController，在类(非方法)上加上注解`@RequestMapping("/....")`作为微信公众号的回调地址
```java
@Controller
@RequestMapping("/weixin/callback")
public class WeixinMsgCbController extends AWxMpMsgCbController {
  @Override
  protected ReplyMsgData processSubscribeEvent(EventMsgData eventMsgData) throws BusinessException {
      // 用户关注了
  }
}
```
2. 重写processXXXMsg方法来处理关注的事件
3. 在微信公众号后台配置这个完整回调地址

### 接入微信支付回调接口方法
1. 新增一个controller并继承AWeixinPayCbController，在类(非方法)上加上注解`@RequestMapping("/....")`作为微信支付的回调地址
```java
@Controller
@RequestMapping("/api/callback/pmall/wxpay/callback")
public class WeixinPayCbController extends AWxPayCbController {
    @Override
    public void paySuccess(PayCallbackDto payCallbackDto) throws BusinessException {
        //支付回调校验完成，且支付成功了
    }
}
```
2. 实现paySuccess方法来处理支付成功后的业务逻辑
3. 在微信商户后台配置这个完整回调地址

### 微信微信公众平台配置
1. 设置与开发->基本配置
- 可获取公众号的AppID、AppSecret、
- 配置IP白名单
- 配置服务器回调地址和消息加解密相关信息
- 绑定开放平台账号用来获取unionId
2. 如果需要开发微信内访问的H5页面，设置与开发->公众号设置->功能设置
- 配置业务域名，自己开发的H5在微信内部访问时不会出现风险提示
- 配置JS接口安全域名，如果自己开发的H5页面需要用到JSSDK，需要配置这个域名
- 配置网页授权域名，在这个域名下的URL可以通过微信oauth2的地址转发来获取用户授权code
    * 微信授权URL https://open.weixin.qq.com/connect/oauth2/authorize?appid=APPID&redirect_uri=REDIRECT_URI&response_type=code&scope=SCOPE&state=STATE#wechat_redirect
    * 自己开发的H5页面会收到 REDIRECT_URI?code=CODE&state=STATE 的请求
    * 注意：只有当scope为"snsapi_userinfo"时服务端使用code换取信息才会返回unionid
    * 注意：REDIRECT_URI必须要encodeURI编码

### 微信支付配置（商户平台）
1. TODO

### 微信开放平台
1. 网站开发(不在微信内打开)
- 方式一：网页授权方式
  * 微信授权二维码 https://open.weixin.qq.com/connect/qrconnect?appid=APPID&redirect_uri=REDIRECT_URI&response_type=code&scope=snsapi_login&state=STATE#wechat_redirect
  * 自己开发的H5页面会收到 REDIRECT_URI?code=CODE&state=STATE 的请求
- 方式二：二维码授权方式
  * 引入weixinJS文件http://res.wx.qq.com/connect/zh_CN/htmledition/js/wxLogin.js
  * 使用weixinJS文件提供的对象方法生成二维码var obj = new WxLogin({...});
