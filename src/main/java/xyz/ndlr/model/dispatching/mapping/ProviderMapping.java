package xyz.ndlr.model.dispatching.mapping;

import org.springframework.core.annotation.AliasFor;
import org.springframework.stereotype.Service;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Service
@Retention(RetentionPolicy.RUNTIME)
public @interface ProviderMapping {

    @AliasFor(annotation = Service.class)
    String value() default "";
}
