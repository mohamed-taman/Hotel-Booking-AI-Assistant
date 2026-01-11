package rs.siriusxi.hbca.ui;

import com.vaadin.flow.server.auth.AnonymousAllowed;
import com.vaadin.hilla.BrowserCallable;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import reactor.core.publisher.Flux;
import rs.siriusxi.hbca.service.ai.CustomerSupportAssistant;

/**
 * A UI service class that facilitates chat-based interactions between users
 * and the {@link CustomerSupportAssistant} service. This class serves as a
 * bridge between the frontend UI layer and the backend customer support logic.
 *
 * Responsibilities:
 * - Exposes a method for initiating and managing online chat sessions.
 * - Delegates chat processing to the {@link CustomerSupportAssistant},
 *   including message handling, intent recognition, and response generation.
 *
 * Key Features:
 * - Allows browser-based clients to invoke chat interactions using the
 *   {@code chat} method.
 * - Supports anonymous access and is callable directly from the browser,
 *   as indicated by the {@code @AnonymousAllowed} and {@code @BrowserCallable}
 *   annotations.
 * - Provides streaming chat responses using reactive programming constructs
 *   via the {@link Flux} return type.
 *
 * Annotations:
 * - {@code @BrowserCallable}: Marks the class as callable from a browser client.
 * - {@code @AnonymousAllowed}: Permits access by unauthenticated users.
 * - {@code @RequiredArgsConstructor}: Automatically injects the required
 *   {@link CustomerSupportAssistant} dependency via constructor.
 *
 * Methods:
 * - {@link #chat(String, String)}: Accepts a chat session ID and user message,
 *   and streams asynchronous responses from the backend support assistant.
 */

@Log4j2
@BrowserCallable
@AnonymousAllowed
@RequiredArgsConstructor
public class AssistantUIService {
    private final CustomerSupportAssistant agent;

    public Flux<String> chat(String chatId, String userMessage) {
        log.info("Chat initiated with chatId: {} and userMessage: {}", chatId, userMessage);
        return agent.chat(chatId, userMessage);
    }
}
