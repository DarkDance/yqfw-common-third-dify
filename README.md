### 一、项目介绍
yqfw-common-third-dify是云祺框架的Dify AI服务接入模块,提供了Dify对话接口、知识库管理、工作流执行等功能的统一封装。Dify是一个开源的LLM应用开发平台，该模块帮助开发者快速集成Dify的AI能力，支持阻塞式和流式两种对话模式。

### 二、项目结构
本模块遵循统一的类命名规范：
- `DifyAuth.java` —— 认证信息（API Key等）
- `DifyAuthHelper.java` —— 认证助手（由业务侧实现，提供认证信息）
- `DifyClient.java` —— 客户端（对外暴露服务调用入口）
- `DifyConfig.java` —— 配置类（注册Bean到Spring容器）

```
yqfw-common-third-dify
└── src/main/java
    └── cn.jzyunqi.common.third.dify
        └── api
            ├── enums
            └── model
                ├── chat
                └── doc
```

### 三、使用说明

#### 1. 安装依赖
运行mvn clean install命令安装当前包，然后在个人项目中引入如下依赖：
```xml
<dependency>
    <groupId>cn.jzyunqi</groupId>
    <artifactId>yqfw-common-third-dify</artifactId>
    <version>${yqfw.version}</version>
</dependency>
```

#### 2. 配置Dify服务
在个人项目中引入Dify配置：
```java
@Import({DifyConfig.class})
```

配置Dify认证信息（支持多个Dify应用）：
```java
@Bean
public DifyAuthRepository difyAuthRepository() {
    return () -> List.of(
            new DifyAuth("app-1", "https://api.dify.ai/v1", "app-xxxx"),
            new DifyAuth("app-2", "https://api.dify.ai/v1", "app-yyyy")
    );
}
```

#### 3. 使用Dify客户端进行对话
注入DifyClient并调用对话接口：
```java
@Resource
private DifyClient difyClient;

public void chat() {
    // 阻塞式对话
    ChatMsgParam chatParam = new ChatMsgParam();
    chatParam.setQuery("你好，请介绍一下自己");
    chatParam.setUser("user-123");
    chatParam.setResponseMode(ResponseMode.BLOCKING);
    
    BlockingChatData response = difyClient.block.chatMessages("app-1", chatParam);
    System.out.println("AI回复: " + response.getAnswer());
    
    // 流式对话
    chatParam.setResponseMode(ResponseMode.STREAMING);
    Flux<StreamingData> streamResponse = difyClient.stream.streamChatMessages("app-1", chatParam);
    streamResponse.subscribe(data -> {
        if (data.getEventType() == EventType.MESSAGE) {
            System.out.print(data.getAnswer());
        }
    });
}
```

#### 4. 管理知识库
使用DifyClient管理知识库文档：
```java
@Resource
private DifyClient difyClient;

public void manageDataset() {
    // 创建数据集
    DatasetParam datasetParam = new DatasetParam();
    datasetParam.setName("我的知识库");
    datasetParam.setIndexingTechnique(IndexingTechnique.HIGH_QUALITY);
    
    DatasetData dataset = difyClient.dataset.createDataset("app-1", datasetParam);
    
    // 上传文档
    DocParam docParam = new DocParam();
    docParam.setName("文档名称");
    docParam.setText("文档内容...");
    
    DocRsp docResponse = difyClient.dataset.createDocumentByText(
            "app-1", 
            dataset.getId(), 
            docParam
    );
    
    System.out.println("文档ID: " + docResponse.getDocument().getId());
}
```

#### 5. 执行工作流
使用DifyClient执行工作流：
```java
@Resource
private DifyClient difyClient;

public void runWorkflow() {
    Map<String, Object> inputs = new HashMap<>();
    inputs.put("input_field", "input_value");
    
    // 阻塞式执行工作流
    BlockingWorkflowData workflowResult = difyClient.block.runWorkflow(
            "app-1", 
            inputs, 
            "user-123", 
            ResponseMode.BLOCKING
    );
    
    System.out.println("工作流输出: " + workflowResult.getOutputs());
}
```

#### 6. 文件上传
上传文件到Dify：
```java
@Resource
private DifyClient difyClient;

public void uploadFile() {
    FileUploadData fileData = difyClient.block.uploadFile(
            "app-1",
            file,  // MultipartFile
            "user-123"
    );
    
    System.out.println("文件ID: " + fileData.getId());
}
```