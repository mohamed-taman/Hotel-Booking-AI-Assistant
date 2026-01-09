package rs.siriusxi.hbca.service.ai;

import lombok.RequiredArgsConstructor;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

import java.time.LocalDate;

import static org.springframework.ai.chat.memory.ChatMemory.CONVERSATION_ID;


/**
 * Service class responsible for handling customer support chat interactions
 * by leveraging AI-powered chat capabilities. This class integrates with
 * the {@link ChatClient} to facilitate message exchange between users and
 * support assistants in a reactive and non-blocking manner.
 *
 * Responsibilities:
 * - Provides a mechanism for initiating and managing chat sessions.
 * - Handles user input and generates appropriate AI-driven chat responses.
 * - Uses the {@link Flux} type to stream responses asynchronously.
 *
 * Key Features:
 * - Incorporates the current system date into the chat context using system parameters.
 * - Supports integration with Advisor modules by passing additional conversation parameters.
 * - Allows continuous conversation management through conversation IDs.
 *
 * Annotations:
 * - {@code @Service}: Marks this class as a Spring-managed service component.
 * - {@code @RequiredArgsConstructor}: Injects the required {@link ChatClient} dependency
 *   via constructor, ensuring immutability.
 *
 * Methods:
 * - {@link #chat(String, String)}: Processes user messages for a given chat session ID and
 *   streams AI-powered assistant responses.
 *
 * Dependencies:
 * - {@link ChatClient}: Provides the backend implementation for handling chat prompts,
 *   context, and response streaming logic.
 */
@Service
@RequiredArgsConstructor
public class CustomerSupportAssistant {

    private final ChatClient chatClient;

    public Flux<String> chat(String chatId, String userMessageContent) {
        // Streams chat responses with date and conversation ID
        return this.chatClient.prompt()
                .system(s -> s.param("current_date", LocalDate.now().toString()))
                .user(userMessageContent)
                .advisors(advisorSpec -> advisorSpec
                        .param(CONVERSATION_ID, chatId))
                .stream().content();
    }
}
