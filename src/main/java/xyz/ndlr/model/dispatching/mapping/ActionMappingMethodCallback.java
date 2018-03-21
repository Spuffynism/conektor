package xyz.ndlr.model.dispatching.mapping;

import org.apache.log4j.Logger;
import org.springframework.util.ReflectionUtils;
import xyz.ndlr.model.provider.ProviderResponse;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class ActionMappingMethodCallback implements ReflectionUtils.MethodCallback {
    private static final Logger logger = Logger.getLogger(ActionMappingMethodCallback.class);

    private ProviderActionRepository providerActionRepository;
    private Object bean;

    ActionMappingMethodCallback(ProviderActionRepository providerActionRepository, Object bean) {
        this.providerActionRepository = providerActionRepository;
        this.bean = bean;
    }

    @Override
    public void doWith(Method method) throws IllegalArgumentException {
        // provider mapping
        Class<?> methodDeclaringClass = method.getDeclaringClass();
        ProviderMapping providerMapping = methodDeclaringClass.getAnnotation(ProviderMapping.class);
        String provider = providerMapping.value();

        // action mapping
        ActionMapping actionMapping = method.getAnnotation(ActionMapping.class);
        String[] actions = actionMapping.value();

        String mappingMessage = String.format("Mapping provider '%s' (with mapping [%s]) action " +
                        "'%s' to [%s]",
                methodDeclaringClass.getSimpleName(), providerMapping.value(), method.getName(),
                String.join(", ", actions));

        // register actions
        for (String action : actions) {
            providerActionRepository.register(provider, action, (user, pipelinedMessage) -> {
                try {
                    return (ProviderResponse) method.invoke(bean, user, pipelinedMessage);
                } catch (IllegalAccessException | InvocationTargetException e) {
                    // Because there is no use in printing the InvocationTargetException's stack
                    // trace, we print the cause's stack trace.
                    e.getCause().printStackTrace();
                }
                return null;
            });
        }

        logger.info(mappingMessage);
    }
}