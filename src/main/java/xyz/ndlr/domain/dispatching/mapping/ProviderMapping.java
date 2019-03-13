package xyz.ndlr.domain.dispatching.mapping;

import org.springframework.core.annotation.AliasFor;
import org.springframework.stereotype.Service;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Represents a provider mapping.
 * <p>
 * Even though services with this annotation won't be injected in any other classes, Spring still
 * needs to discover them. Thus, the @Service annotation is needed.
 */
@Service
@Retention(RetentionPolicy.RUNTIME)
public @interface ProviderMapping {

    @AliasFor(annotation = Service.class)
    String value();

    /**
     * Used for quick-reply titles when the user is asked what we should do with an image they sent.
     */
    String humanName() default DEFAULT_HUMAN_NAME;

    String DEFAULT_HUMAN_NAME = "";
}
