1）智能代码生成：用户输入需求描述，AI 自动分析并选择合适的生成策略，通过工具调用生成代码文件，采用流式输出让用户实时看到 AI 的执行过程。


2）可视化编辑：生成的应用将实时展示，可以进入编辑模式，自由选择网页元素并且和 AI 对话来快速修改页面，直到满意为止。



3）一键部署分享：可以将生成的应用一键部署到云端并自动截取封面图，获得可访问的地址进行分享，同时支持完整项目源码下载。

# 需求TODO
- 接入文档/图片分析模型,上传需求文档/图片和所需接口
- 将BSS和MG的服务抽成先抽成tool供AI使用.实现任务编排
- 开发BSS和MG的MCP服务,供模型使用,自主选择实现


![img.png](img.png)

## 项目流程图

### 用户使用流程
```mermaid
flowchart TD
    A[用户访问首页] --> B[输入应用描述]
    B --> C{用户是否登录?}
    C -->|否| D[跳转登录页面]
    C -->|是| E[创建应用]
    D --> F[用户注册/登录]
    F --> E
    E --> G[AI智能路由分析]
    G --> H[选择代码生成类型]
    H --> I[AI代码生成]
    I --> J[流式输出代码]
    J --> K[保存代码文件]
    K --> L[进入应用对话页面]
    L --> M[用户与AI对话优化]
    M --> N[可视化编辑]
    N --> O[一键部署]
    O --> P[生成访问链接]
    P --> Q[分享应用]
```

### AI代码生成工作流
```mermaid
flowchart TD
    A[用户输入需求] --> B[图片素材收集]
    B --> C[提示词增强]
    C --> D[智能路由分析]
    D --> E{代码生成类型}
    E -->|HTML| F[HTML代码生成]
    E -->|Vue项目| G[Vue项目生成]
    E -->|Java项目| H[Java项目生成]
    E -->|多文件| I[多文件代码生成]
    F --> J[代码质量检查]
    G --> J
    H --> J
    I --> J
    J --> K{质量检查通过?}
    K -->|否| L[重新生成代码]
    K -->|是| M[项目构建]
    L --> J
    M --> N[保存到文件系统]
    N --> O[更新数据库记录]
    O --> P[返回生成结果]
```

## 系统架构图

### 整体架构
```mermaid
graph TB
    subgraph "前端层 (Vue3 + TypeScript)"
        A[用户界面] --> B[路由管理]
        B --> C[状态管理 Pinia]
        C --> D[API调用]
    end
    
    subgraph "后端层 (Spring Boot)"
        E[控制器层] --> F[服务层]
        F --> G[数据访问层]
        G --> H[AI服务层]
    end
    
    subgraph "AI服务层"
        I[LangChain4j] --> J[智能路由]
        J --> K[代码生成器]
        K --> L[质量检查]
        L --> M[项目构建器]
    end
    
    subgraph "工作流引擎"
        N[LangGraph4j] --> O[图片收集节点]
        O --> P[提示词增强节点]
        P --> Q[路由节点]
        Q --> R[代码生成节点]
        R --> S[质量检查节点]
        S --> T[项目构建节点]
    end
    
    subgraph "数据存储层"
        U[MySQL数据库] --> V[用户表]
        U --> W[应用表]
        U --> X[对话历史表]
        Y[Redis缓存] --> Z[Session存储]
        AA[文件系统] --> BB[代码文件]
        AA --> CC[部署文件]
    end
    
    subgraph "外部服务"
        DD[阿里云DashScope] --> EE[AI模型服务]
        FF[腾讯云COS] --> GG[对象存储]
        HH[Pexels API] --> II[图片搜索]
    end
    
    subgraph "监控系统"
        JJ[Prometheus] --> KK[指标收集]
        LL[Grafana] --> MM[可视化监控]
    end
    
    D --> E
    H --> I
    I --> N
    F --> U
    F --> Y
    F --> AA
    H --> DD
    H --> FF
    H --> HH
    E --> JJ
```

### 技术栈架构
```mermaid
graph LR
    subgraph "前端技术栈"
        A1[Vue 3] --> A2[TypeScript]
        A2 --> A3[Ant Design Vue]
        A3 --> A4[Vite]
        A4 --> A5[Pinia]
    end
    
    subgraph "后端技术栈"
        B1[Spring Boot 3] --> B2[Java 21]
        B2 --> B3[MyBatis-Flex]
        B3 --> B4[LangChain4j]
        B4 --> B5[LangGraph4j]
    end
    
    subgraph "数据存储"
        C1[MySQL] --> C2[Redis]
        C2 --> C3[文件系统]
    end
    
    subgraph "AI服务"
        D1[阿里云DashScope] --> D2[通义千问模型]
        D2 --> D3[图片生成API]
    end
    
    subgraph "部署监控"
        E1[Docker] --> E2[Prometheus]
        E2 --> E3[Grafana]
    end
```

### 生成代码 SSE 时序图
```mermaid
sequenceDiagram
    participant FE as 前端 AppChatPage.vue
    participant BE as 后端 AppController
    participant SVC as AppServiceImpl
    participant FAC as AiCodeGeneratorFacade
    participant AI as LangChain4j/模型
    participant FS as 文件系统
    participant DB as MySQL

    FE->>BE: GET /app/chat/gen/code?appId&message (SSE)
    BE->>SVC: chatToGenCode(appId, message, loginUser)
    SVC->>DB: 保存用户消息(chat_history)
    SVC->>FAC: generateAndSaveCodeStream(message, type, appId)
    FAC->>AI: 流式生成代码
    AI-->>FAC: token/代码片段流
    FAC-->>SVC: Flux<String>
    SVC-->>BE: Flux<String>
    loop 数据流
        BE-->>FE: SSE data: { d: "代码片段/工具信息" }
    end
    BE-->>FE: SSE event: done
    FAC->>FS: 解析并保存完整代码
    FAC->>DB: 记录完成状态
```

### 数据库 ER 图
```mermaid
erDiagram
    USER ||--o{ APP : owns
    USER ||--o{ CHAT_HISTORY : writes
    APP ||--o{ CHAT_HISTORY : contains

    USER {
        bigint id PK
        varchar userAccount
        varchar userPassword
        varchar userName
        varchar userAvatar
        varchar userProfile
        varchar userRole
        datetime createTime
        datetime updateTime
        tinyint isDelete
    }

    APP {
        bigint id PK
        varchar appName
        varchar cover
        text initPrompt
        varchar codeGenType
        varchar deployKey
        datetime deployedTime
        int priority
        bigint userId FK
        datetime createTime
        datetime updateTime
        tinyint isDelete
    }

    CHAT_HISTORY {
        bigint id PK
        text message
        varchar messageType
        bigint appId FK
        bigint userId FK
        datetime createTime
        datetime updateTime
        tinyint isDelete
    }
```

### 核心模块关系图
```mermaid
graph TD
    subgraph "用户管理模块"
        A1[用户注册] --> A2[用户登录]
        A2 --> A3[权限验证]
    end
    
    subgraph "应用管理模块"
        B1[创建应用] --> B2[应用列表]
        B2 --> B3[应用详情]
        B3 --> B4[应用编辑]
    end
    
    subgraph "AI代码生成模块"
        C1[智能路由] --> C2[代码生成器]
        C2 --> C3[质量检查]
        C3 --> C4[项目构建]
    end
    
    subgraph "对话管理模块"
        D1[对话历史] --> D2[消息存储]
        D2 --> D3[流式响应]
    end
    
    subgraph "部署分享模块"
        E1[应用部署] --> E2[文件复制]
        E2 --> E3[访问链接]
        E3 --> E4[截图生成]
    end
    
    A3 --> B1
    B4 --> C1
    C4 --> D1
    D3 --> E1
```


