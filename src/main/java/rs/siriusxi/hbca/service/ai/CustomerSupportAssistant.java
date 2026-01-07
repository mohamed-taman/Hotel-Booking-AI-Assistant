package rs.siriusxi.hbca.service.ai;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.PromptChatMemoryAdvisor;
import org.springframework.ai.chat.client.advisor.vectorstore.QuestionAnswerAdvisor;
import org.springframework.ai.chat.memory.ChatMemory;
import org.springframework.ai.vectorstore.SearchRequest;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

import java.time.LocalDate;

import static org.springframework.ai.chat.memory.ChatMemory.CONVERSATION_ID;
import static rs.siriusxi.hbca.config.BookingAIToolsConfig.*;

/**
 * Service class responsible for assisting with customer support interactions
 * for the "Great Hotel Booking" application. This class facilitates online
 * chat-based communication with users.
 * <p>
 * Responsibilities:
 * - Configures a chat client with system prompts and advisors to handle chat interactions.
 * - Determines the context and intent of user messages to provide appropriate responses.
 * - Ensures compliance with booking-related processes, such as fetching details,
 * changing bookings, and cancellations, while adhering to set terms and conditions.
 * <p>
 * Chat Configuration:
 * - Assigns a default system prompt to establish agent behavior and the rules for user interactions.
 * - Uses advisors (e.g., PromptChatMemoryAdvisor, QuestionAnswerAdvisor) to manage conversation memory
 * and knowledge-based responses.
 * - Incorporates tools for specific operations like changing room types or canceling bookings.
 * <p>
 * Methods:
 * - {@code chat(String chatId, String userMessageContent)}: Accepts user messages and streams
 * responses in real time. Supports contextual handling based on conversation ID and the
 * current date.
 * <p>
 * Notes:
 * - Before providing information, changing bookings, or processing cancellations,
 * the agent retrieves the user's booking number, first name, and last name from
 * the chat history or explicitly requests it.
 * - Enforces user consent before applying charges for changes.
 * <p>
 * Dependencies:
 * - {@code ChatClient}: For managing chat sessions.
 * - {@code ChatMemory}: For storing and retrieving chat history.
 * - {@code VectorStore}: For performing knowledge-based searches.
 */
@Service
public class CustomerSupportAssistant {

    private final ChatClient chatClient;

    public CustomerSupportAssistant(ChatClient.Builder chatClientBuilder, ChatMemory chatMemory, VectorStore vectorStore) {
        // Configures a chat client with system prompt and advisors
        chatClient = chatClientBuilder
                .defaultSystem("""
                        	You are a customer chat support agent of an Hotel Booking named "Great Hotel Booking"."
                        	Respond in a friendly, helpful, and joyful manner.
                        	You are interacting with customers through an online chat system.
                        	Before providing information about a booking, cancelling a booking or changing a booking room type, you MUST always
                        	get the following information from the user as the following:
                        	1. Booking number to find the booking.
                        	2. Booking number, and customer first name and last name if customer wants to cancel or change room type for booking.
                        	Check the message history for this information before asking the user.
                        	Before changing a booking you MUST ensure it is permitted by the terms.
                        	If there is a charge for the change, you MUST ask the user to consent before proceeding.
                        	Use the provided functions to fetch booking details, change bookings, and cancel bookings.
                        	Use parallel function calling if required.
                        	Today is {current_date}.
                        """)
                .defaultAdvisors(PromptChatMemoryAdvisor
                                .builder(chatMemory)
                                .build(),
                        QuestionAnswerAdvisor.builder(vectorStore)
                                .searchRequest(SearchRequest.builder().build())
                                .build())
                .defaultToolNames(aiToolsNames())
                .build();
    }

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
