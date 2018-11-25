package xyz.ndlr.domain.dispatching.mapping;

import java.lang.annotation.*;

@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@Documented
public @interface ActionMapping {
    String[] value();

    String DEFAULT_ACTION = "";
}
