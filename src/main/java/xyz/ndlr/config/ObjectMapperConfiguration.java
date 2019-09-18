package xyz.ndlr.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import xyz.ndlr.infrastructure.provider.facebook.shared.Attachment;
import xyz.ndlr.infrastructure.provider.facebook.shared.AttachmentDeserializer;

@Configuration
public class ObjectMapperConfiguration {

    @Bean
    public ObjectMapper objectMapper() {
        SimpleModule module = new SimpleModule();
        module.addDeserializer(Attachment.class, new AttachmentDeserializer());

        return new ObjectMapper().registerModules(module);
    }
}
