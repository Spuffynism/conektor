package xyz.ndlr.model;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import xyz.ndlr.model.provider.facebook.shared.AttachmentDeserializer;
import xyz.ndlr.model.provider.facebook.shared.Attachment;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ObjectMapperConfiguration {

    @Bean
    public ObjectMapper objectMapper() {
        SimpleModule module = new SimpleModule();
        module.addDeserializer(Attachment.class, new AttachmentDeserializer());

        return new ObjectMapper().registerModules(module);
    }
}
