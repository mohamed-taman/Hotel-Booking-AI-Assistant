package rs.siriusxi.hbca.ui;

import com.vaadin.flow.server.auth.AnonymousAllowed;
import com.vaadin.hilla.BrowserCallable;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Flux;
import rs.siriusxi.hbca.service.ai.CustomerSupportAssistant;

@BrowserCallable
@AnonymousAllowed
@RequiredArgsConstructor
public class AssistantUIService {
    private final CustomerSupportAssistant agent;

    public Flux<String> chat(String chatId, String userMessage) {
        return agent.chat(chatId, userMessage);
    }
}
