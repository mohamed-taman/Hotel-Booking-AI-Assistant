package rs.siriusxi.hbca;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
class HCSAApplicationTest {

    @Test
    void contextLoads(ApplicationContext context) {
        assertNotNull(context);
    }
}