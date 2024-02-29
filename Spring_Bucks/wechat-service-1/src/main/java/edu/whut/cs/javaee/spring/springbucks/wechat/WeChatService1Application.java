package edu.whut.cs.javaee.spring.springbucks.wechat;

import dev.langchain4j.data.document.Document;
import dev.langchain4j.data.document.DocumentSplitter;
import dev.langchain4j.data.document.parser.TextDocumentParser;
import dev.langchain4j.data.document.splitter.DocumentSplitters;
import dev.langchain4j.data.segment.TextSegment;
import dev.langchain4j.memory.chat.MessageWindowChatMemory;
import dev.langchain4j.model.chat.ChatLanguageModel;
import dev.langchain4j.model.embedding.AllMiniLmL6V2EmbeddingModel;
import dev.langchain4j.model.embedding.EmbeddingModel;
import dev.langchain4j.model.openai.OpenAiTokenizer;
import dev.langchain4j.retriever.EmbeddingStoreRetriever;
import dev.langchain4j.retriever.Retriever;
import dev.langchain4j.service.AiServices;
import dev.langchain4j.store.embedding.EmbeddingStore;
import dev.langchain4j.store.embedding.EmbeddingStoreIngestor;
import dev.langchain4j.store.embedding.inmemory.InMemoryEmbeddingStore;
import edu.whut.cs.javaee.spring.springbucks.wechat.llm.BookingTools;
import edu.whut.cs.javaee.spring.springbucks.wechat.llm.CustomerSupportAgent;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.context.annotation.Bean;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;

import java.io.IOException;

import static dev.langchain4j.data.document.loader.FileSystemDocumentLoader.loadDocument;
import static dev.langchain4j.model.openai.OpenAiModelName.GPT_3_5_TURBO;

@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients
public class WeChatService1Application {

    @Bean
    CustomerSupportAgent customerSupportAgent(ChatLanguageModel chatLanguageModel,
                                              BookingTools bookingTools,
                                              Retriever<TextSegment> retriever) {
        return AiServices.builder(CustomerSupportAgent.class)
                .chatLanguageModel(chatLanguageModel)
//                .chatMemory(MessageWindowChatMemory.withMaxMessages(20))
                .chatMemory(MessageWindowChatMemory.withMaxMessages(5))
                .tools(bookingTools)
                .retriever(retriever)
                .build();
    }

    @Bean
    Retriever<TextSegment> retriever(EmbeddingStore<TextSegment> embeddingStore, EmbeddingModel embeddingModel) {

        // You will need to adjust these parameters to find the optimal setting, which will depend on two main factors:
        // - The nature of your data
        // - The embedding model you are using
//        int maxResultsRetrieved = 1;
//        double minScore = 0.6;
        int maxResultsRetrieved = 3;
        double minScore = 0.6;

        return EmbeddingStoreRetriever.from(embeddingStore, embeddingModel, maxResultsRetrieved, minScore);
    }

    @Bean
    EmbeddingModel embeddingModel() {
        return new AllMiniLmL6V2EmbeddingModel();
    }

    @Bean
    EmbeddingStore<TextSegment> embeddingStore(EmbeddingModel embeddingModel, ResourceLoader resourceLoader) throws IOException {

        // Normally, you would already have your embedding store filled with your data.
        // However, for the purpose of this demonstration, we will:

        // 1. Create an in-memory embedding store
        EmbeddingStore<TextSegment> embeddingStore = new InMemoryEmbeddingStore<>();

        // 2. Load an example document ("Miles of Smiles" terms of use)
//        Resource resource = resourceLoader.getResource("classpath:miles-of-smiles-terms-of-use.txt");
//        Document document = loadDocument(resource.getFile().toPath(), new TextDocumentParser());
        Resource resource = resourceLoader.getResource("classpath:SpringBucks咖啡服务条款.txt");
        Document document = loadDocument(resource.getFile().toPath(), new TextDocumentParser());
        Resource resource2 = resourceLoader.getResource("classpath:SpringBucks价格表.txt");
        Document document2 = loadDocument(resource2.getFile().toPath(), new TextDocumentParser());


        // 3. Split the document into segments 100 tokens each
        // 4. Convert segments into embeddings
        // 5. Store embeddings into embedding store
        // All this can be done manually, but we will use EmbeddingStoreIngestor to automate this:
//        DocumentSplitter documentSplitter = DocumentSplitters.recursive(100, 0, new OpenAiTokenizer(GPT_3_5_TURBO));
        DocumentSplitter documentSplitter = DocumentSplitters.recursive(300, 0, new OpenAiTokenizer(GPT_3_5_TURBO));
        EmbeddingStoreIngestor ingestor = EmbeddingStoreIngestor.builder()
                .documentSplitter(documentSplitter)
                .embeddingModel(embeddingModel)
                .embeddingStore(embeddingStore)
                .build();
//        ingestor.ingest(document);
        ingestor.ingest(document2);
        ingestor.ingest(document);

        return embeddingStore;
    }

    public static void main(String[] args) {
//        System.setProperty("proxyHost", "127.0.0.1");
//        System.setProperty("proxyPort", "7890");
        SpringApplication.run(WeChatService1Application.class, args);
    }

}
