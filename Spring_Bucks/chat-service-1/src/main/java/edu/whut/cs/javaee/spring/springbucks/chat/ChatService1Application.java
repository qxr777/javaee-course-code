package edu.whut.cs.javaee.spring.springbucks.chat;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ai.chat.memory.ChatMemory;
import org.springframework.ai.chat.memory.InMemoryChatMemory;
import org.springframework.ai.embedding.EmbeddingModel;
import org.springframework.ai.reader.TextReader;
import org.springframework.ai.transformer.splitter.TokenTextSplitter;
import org.springframework.ai.vectorstore.SimpleVectorStore;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.core.io.Resource;
import org.springframework.web.client.RestClient;


@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients
public class ChatService1Application {

	private static final Logger logger = LoggerFactory.getLogger(ChatService1Application.class);

	public static void main(String[] args) {
		new SpringApplicationBuilder(ChatService1Application.class).run(args);
	}

	// In the real world, ingesting documents would often happen separately, on a CI
	// server or similar.
@Bean
CommandLineRunner ingestTermOfServiceToVectorStore(EmbeddingModel embeddingModel, VectorStore vectorStore,
        @Value("classpath:rag/*.txt") Resource[] termsOfServiceDocs) {

    return args -> {
        for (Resource doc : termsOfServiceDocs) {
            // Ingest each document into the vector store
            vectorStore.write(new TokenTextSplitter().transform(new TextReader(doc).read()));
        }

        // Perform similarity search after all documents are ingested
        vectorStore.similaritySearch("Cancelling Bookings").forEach(doc -> {
            logger.info("Similar Document: {}", doc.getFormattedContent());
        });
    };
}


	@Bean
	public VectorStore vectorStore(EmbeddingModel embeddingModel) {
		return SimpleVectorStore.builder(embeddingModel).build();
//		return new SimpleVectorStore(embeddingModel);
	}

	@Bean
	public ChatMemory chatMemory() {
		return new InMemoryChatMemory();
	}

	@Bean
	@ConditionalOnMissingBean
	public RestClient.Builder restClientBuilder() {
		return RestClient.builder();
	}

}
