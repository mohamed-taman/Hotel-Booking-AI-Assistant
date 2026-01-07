package rs.siriusxi.hbca;

import com.vaadin.flow.component.dependency.StyleSheet;
import com.vaadin.flow.component.page.AppShellConfigurator;
import com.vaadin.flow.theme.lumo.Lumo;
import lombok.extern.log4j.Log4j2;
import org.springframework.ai.document.Document;
import org.springframework.ai.reader.TextReader;
import org.springframework.ai.transformer.splitter.TextSplitter;
import org.springframework.ai.transformer.splitter.TokenTextSplitter;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.core.io.Resource;

import java.util.List;

@SpringBootApplication
@StyleSheet(Lumo.STYLESHEET) // Use Aura.STYLESHEET to use Aura instead
@StyleSheet(Lumo.UTILITY_STYLESHEET)
@StyleSheet("styles.css") // Your custom styles
@Log4j2
public class HCSAApplication implements AppShellConfigurator {

    static void main(String[] args) {
        SpringApplication.run(HCSAApplication.class, args);
    }

    /**
     * Creates bean to ingest documents into a vector store
     */
    @Bean
    public CommandLineRunner commandLineRunner(VectorStore vectorStore,
                                               @Value("classpath:booking-terms.txt")
                                               Resource termsOfServiceDocs) {
        return args -> {
            List<Document> documents = new TextReader(termsOfServiceDocs).read();
            log.info("documents size: {}", documents.size());

            TextSplitter textSplitter = new TokenTextSplitter();

            for (Document document : documents) {
                List<Document> splitDocuments = textSplitter.split(document);
                vectorStore.write(splitDocuments);
                log.info("Ingested document: {}", document.getText());
            }
        };
    }

}
