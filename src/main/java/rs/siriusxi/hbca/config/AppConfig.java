package rs.siriusxi.hbca.config;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.PromptChatMemoryAdvisor;
import org.springframework.ai.chat.client.advisor.vectorstore.QuestionAnswerAdvisor;
import org.springframework.ai.chat.memory.ChatMemory;
import org.springframework.ai.chat.memory.MessageWindowChatMemory;
import org.springframework.ai.embedding.EmbeddingModel;
import org.springframework.ai.vectorstore.SearchRequest;
import org.springframework.ai.vectorstore.SimpleVectorStore;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;

import static rs.siriusxi.hbca.config.BookingToolsConfig.aiToolsNames;


/**
 * Application configuration class for setting up AI-related components.
 * <p>
 * The class is annotated with {@code @Configuration} indicating that it declares
 * Spring beans and configuration for the application context. It provides the
 * following bean definitions:
 * <p>
 * 1. {@link SimpleVectorStore}: An implementation of a vector store
 * used for handling embeddings with compatibility for an {@link EmbeddingModel}.
 * <p>
 * 2. {@link ChatMemory}: A memory structure to store and manage chat messages,
 * supporting a sliding window of up to 100 messages.
 * <p>
 * 3. {@link ChatClient}: The main chat client configuration that binds various
 * components, including chat memory, vector store, advisors, and system prompt,
 * into a single instance for AI-enabled chat processing.
 */
@Configuration
public class AppConfig {

    @Value("classpath:SystemMessage.st")
    private Resource systemPrompt;

    @Bean
    SimpleVectorStore vectorStore(EmbeddingModel embeddingModel) {
        return SimpleVectorStore.builder(embeddingModel).build();
    }

    @Bean
    ChatMemory chatMemory() {
        return MessageWindowChatMemory.
                builder().
                maxMessages(100)
                .build();
    }

    @Bean
    ChatClient chatClient(ChatClient.Builder chatClientBuilder, ChatMemory chatMemory, VectorStore vectorStore) {
        // Configures a chat client with system prompt and advisors
        return chatClientBuilder
                .defaultSystem(systemPrompt)
                .defaultAdvisors(PromptChatMemoryAdvisor
                                .builder(chatMemory)
                                .build(),
                        QuestionAnswerAdvisor.builder(vectorStore)
                                .searchRequest(SearchRequest.builder().build())
                                .build())
                .defaultToolNames(aiToolsNames())
                .build();
    }
}
