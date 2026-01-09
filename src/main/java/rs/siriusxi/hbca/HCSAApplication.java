package rs.siriusxi.hbca;

import com.vaadin.flow.component.dependency.StyleSheet;
import com.vaadin.flow.component.page.AppShellConfigurator;
import com.vaadin.flow.theme.lumo.Lumo;
import lombok.extern.log4j.Log4j2;
import org.springframework.ai.document.Document;
import org.springframework.ai.reader.TextReader;
import org.springframework.ai.transformer.splitter.TokenTextSplitter;
import org.springframework.ai.vectorstore.SimpleVectorStore;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.core.io.Resource;

import java.io.File;
import java.nio.file.Path;
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
    public CommandLineRunner commandLineRunner(SimpleVectorStore simpleVectorStore,
                                               @Value("classpath:booking-terms.txt")
                                               Resource termsOfServiceDocs) {
        return args -> {
            // Reads documents, splits, and ingests into a vector store.
            File fileVectorStore = Path.of(".", "store", "rag", "booking-terms.json").toFile();
            // Loads or creates a file-backed vector store if needed.
            if (fileVectorStore.exists()) {
                log.info("Vector store file '{}' exists, loading the data...",
                        fileVectorStore.getName());
                // Load the vector store file.
                simpleVectorStore.load(fileVectorStore);
            } else {
                log.warn("Vector store file '{}' does not exist, creating a new one.",
                        fileVectorStore.getName());
                // Ensure parent directories exist before creating the file
                File parentDir = fileVectorStore.getParentFile();
                if (parentDir != null && !parentDir.exists()) {
                    log.debug("Parent directories created: {}",
                            parentDir.mkdirs() ? "Yes" : "No");
                }
                log.debug("Is vector store file created? {}",
                        fileVectorStore.createNewFile() ? "Yes" : "No");

                // Read the Magna products file and create a vector store file.
                TextReader reader = new TextReader(termsOfServiceDocs);
                // Set vector store file name metadata.
                reader.getCustomMetadata().put("filename", termsOfServiceDocs.getFilename());
                // Read the Magna products file into a list of documents.
                List<Document> documents = reader.read();
                log.info("documents size: {}", documents.size());
                //Split the documents into smaller vectors.
                var splitDocuments = new TokenTextSplitter().split(documents);
                // Add the split documents to the vector store.
                simpleVectorStore.add(splitDocuments);
                // Save the split documents to a vector store file.
                simpleVectorStore.save(fileVectorStore);
            }
        };
    }
}
