# Spring AI 示例项目集合

本目录包含了一系列使用 Spring AI 框架的示例项目，展示了各种 AI 应用场景和实现方式。

## 项目结构

### 基础组件
* **[agentic-patterns](./agentic-patterns)** - 智能代理模式的实现示例
  * 展示如何在 Spring 应用中实现和使用 AI 代理模式
* **[agents](./agents)** - AI 代理系统示例
  * 包含基础的 AI 代理实现和使用方法
* **[prompts-and-output-parsers](./prompts-and-output-parsers)** - 提示词工程和输出解析
  * 演示如何编写高效的提示词
  * 展示不同类型的输出解析器使用方法
* **[spring-ai-functions](./spring-ai-functions)** - Spring AI 函数示例
  * 基础函数使用方法
  * 函数链和组合示例

### RAG (检索增强生成) 应用
* **[spring-ai-html-rag](./spring-ai-html-rag)** - 基于 HTML 的 RAG 实现
  * 展示如何处理和分析 HTML 内容
* **[spring-ai-rag-chat](./spring-ai-rag-chat)** - RAG 聊天应用
  * 实现基于知识库的智能对话系统
* **[spring-ai-rag-wiki](./spring-ai-rag-wiki)** - 维基百科 RAG 示例
  * 使用维基百科数据的知识检索和生成系统

### 多模态应用
* **[spring-ai-multimodal](./spring-ai-multimodal)** - 多模态 AI 应用
  * 处理文本、图像等多种形式的输入
* **[spring-ai-multimodal-audio](./spring-ai-multimodal-audio)** - 音频处理示例
  * 音频识别和处理功能展示
* **[spring-ai-image-gen](./spring-ai-image-gen)** - 图像生成应用
  * 使用 AI 模型生成图像的示例

### 特定场景应用
* **[playground-flight-booking](./playground-flight-booking)** - 航班预订系统
  * 展示 AI 在实际业务场景中的应用
* **[spring-ai-sql](./spring-ai-sql)** - SQL AI 助手
  * AI 辅助 SQL 查询生成和优化
* **[spring-ai-summarizer](./spring-ai-summarizer)** - 文本摘要生成器
  * 自动生成文本摘要的应用示例

## 快速开始

1. 克隆仓库
```bash
git clone https://github.com/qxr777/javaee-course-code.git
```

2. 进入项目目录
```bash
cd javaee-course-code/Spring_AI
```

3. 选择感兴趣的项目目录
```bash
cd <project-name>
```

4. 运行示例
```bash
./mvnw spring-boot:run
```

## 环境要求

* JDK 17 或更高版本
* Maven 3.6+ 或使用包含的 Maven Wrapper
* Spring Boot 3.x
* Spring AI 依赖

## 注意事项

* 部分项目可能需要配置特定的 API 密钥或其他凭证
* 请查看各子项目中的 README 文件获取具体配置信息
* 示例代码仅供学习参考，生产环境使用需要进行适当的调整和优化

## 许可证

本项目使用 [MIT 许可证](LICENSE)。

## 贡献

欢迎提交 Issue 和 Pull Request 来帮助改进项目。