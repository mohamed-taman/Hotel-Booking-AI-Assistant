package rs.siriusxi.hbca.config;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.PromptChatMemoryAdvisor;
import org.springframework.ai.chat.client.advisor.SimpleLoggerAdvisor;
import org.springframework.ai.chat.client.advisor.vectorstore.QuestionAnswerAdvisor;
import org.springframework.ai.chat.memory.ChatMemory;
import org.springframework.ai.chat.memory.MessageWindowChatMemory;
import org.springframework.ai.chat.memory.repository.jdbc.JdbcChatMemoryRepository;
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
 * Configuration class responsible for defining application-level beans
 * required for embedding models, chat memory, vector storage, and chat clients.
 * This class is annotated with {@code @Configuration} to indicate that it
 * declares beans for the Spring application context.
 * <p>
 * Beans Defined:
 * <p>
 * - {@link SimpleVectorStore}:
 *   Configures a vector storage system using an embedding model for
 *   managing vectorized data used in AI-related applications.
 * <p>
 * - {@link ChatMemory}:
 *   Defines a chat memory instance for storing and providing context to
 *   conversational agents. The memory limits the context to the last
 *   100 messages, aiding large language models in generating context-aware responses.
 * <p>
 * - {@link ChatClient}:
 *   Creates a chat client configured with a default system prompt, a set of
 *   advisors to manage chat interactions and memory, and AI tools for task handling.
 *   The client is the central interface for managing chat-based workflows.
 * <p>
 * Dependencies:
 * <p>
 * - {@link EmbeddingModel}: Provides embedding model logic for vector generation
 *   in {@link SimpleVectorStore}.
 * <p>
 * - {@link JdbcChatMemoryRepository}: Underpins the chat memory system, storing chat messages
 *   and retrieving stored history to provide a coherent conversation context.
 * <p>
 * - {@link ChatClient.Builder}: Assists in building an instance of {@link ChatClient}
 *   with configurable properties such as system prompts, advisors, and tools.
 * <p>
 * Advisors Added to the Chat Client:
 * <p>
 * - {@link SimpleLoggerAdvisor}: Logs chat interactions for debugging and monitoring purposes.
 * <p>
 * - {@link PromptChatMemoryAdvisor}: Leverages ChatMemory to provide historical context to the conversation.
 * <p>
 * - {@link QuestionAnswerAdvisor}: Aims to process user queries by utilizing vector-based storage
 *   and executing search requests.
 * <p>
 * Configuration Notes:
 * <p>
 * - The system prompt is loaded from an external resource file, enhancing configurability.
 * - AI tools used by the chat client are pre-defined as an array of names provided by
 *   the method `aiToolsNames`, which fetches values from the sibling configuration class
 *   {@link BookingToolsConfig}.
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
    ChatMemory chatMemory(JdbcChatMemoryRepository jdbcChatMemoryRepository) {
        return MessageWindowChatMemory.builder()
                .chatMemoryRepository(jdbcChatMemoryRepository)
                /*
                "maxMessages" parameter tells the chat memory to send LLM the last 100 messages as context.
                */
                .maxMessages(100)
                .build();
    }

    @Bean
    ChatClient chatClient(ChatClient.Builder chatClientBuilder, ChatMemory chatMemory, VectorStore vectorStore) {
        // Configures a chat client with system prompt and advisors
        return chatClientBuilder
                .defaultSystem(systemPrompt)
                .defaultAdvisors(
                        // This advisor logs chat interactions
                        new SimpleLoggerAdvisor(-1),
                        PromptChatMemoryAdvisor
                                .builder(chatMemory)
                                .build(),
                        QuestionAnswerAdvisor.builder(vectorStore)
                                .searchRequest(SearchRequest.builder().build())
                                .build())
                .defaultToolNames(aiToolsNames())
                .build();
    }
}
