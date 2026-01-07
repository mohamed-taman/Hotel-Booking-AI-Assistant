package rs.siriusxi.hbca.config;

import org.springframework.ai.chat.memory.ChatMemory;
import org.springframework.ai.chat.memory.MessageWindowChatMemory;
import org.springframework.ai.embedding.EmbeddingModel;
import org.springframework.ai.vectorstore.SimpleVectorStore;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Spring configuration class responsible for defining application-specific beans.
 *
 * This configuration class provides the setup for core components related to
 * vector storage and chat memory functionality using Spring's dependency injection
 * mechanism. It facilitates the creation and management of the following beans:
 *
 * - {@code VectorStore}: A vector storage mechanism built with a specific embedding model
 *   for managing vectorized data efficiently.
 * - {@code ChatMemory}: A memory storage implementation for managing message contexts
 *   in a chat application with a defined message window size.
 *
 * Annotations:
 * - {@code @Configuration}: Indicates this class is a source of bean definitions for
 *   the Spring application context.
 * - {@code @Bean}: Marks methods within this class as bean providers to be managed
 *   by the Spring container.
 */
@Configuration
public class AppConfig {

    @Bean
    public VectorStore vectorStore(EmbeddingModel embeddingModel) {
        return SimpleVectorStore.builder(embeddingModel).build();
    }

    @Bean
    public ChatMemory chatMemory() {
        return MessageWindowChatMemory.
                builder().
                maxMessages(100)
                .build();
    }
}
