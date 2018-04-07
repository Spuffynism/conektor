package xyz.ndlr.model.dispatching.mapping;

import java.lang.annotation.*;

@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@Documented
public @interface ActionMapping {
    String[] value();

    public static final String DEFAULT_ACTION = "";
}
