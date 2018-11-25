package xyz.ndlr.domain.dispatching.mapping;

import org.apache.log4j.Logger;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.util.ReflectionUtils;
import xyz.ndlr.domain.provider.ProviderResponse;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class ActionMappingMethodCallback implements ReflectionUtils.MethodCallback {
    private static final Logger logger = Logger.getLogger(ActionMappingMethodCallback.class);

    private final ActionRepository actionRepository;
    private final Object bean;

    ActionMappingMethodCallback(ActionRepository actionRepository, Object bean) {
        this.actionRepository = actionRepository;
        this.bean = bean;
    }

    @Override
    public void doWith(Method method) throws IllegalArgumentException {
        Class<?> methodDeclaringClass = method.getDeclaringClass();
        ProviderMapping providerMapping = methodDeclaringClass.getAnnotation(ProviderMapping.class);
        String provider = providerMapping.value();

        registerProviderHumanName(providerMapping);

        ActionMapping actionMapping = AnnotationUtils.findAnnotation(method, ActionMapping.class);
        String[] actions = actionMapping.value();

        for (String action : actions) {
            registerAction(method, provider, action);
        }

        logMappingMessage(methodDeclaringClass.getSimpleName(), provider, method.getName(),
                actions);
    }

    private void registerAction(Method method, String provider, String action) {
        actionRepository.register(provider, action, (user, pipelinedMessage) -> {
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

    private void registerProviderHumanName(ProviderMapping providerMapping) {
        String humanName = providerMapping.humanName();
        boolean useFallbackname = ProviderMapping.DEFAULT_HUMAN_NAME.equals(humanName);

        actionRepository.registerHumanName(providerMapping.value(),
                useFallbackname ? providerMapping.value() : humanName);
    }

    private void logMappingMessage(String methodDeclaringClass, String provider, String methodName,
                                   String[] actions) {
        String mappingMessage;
        boolean isDefaultAction = actions[0].equals(ActionMapping.DEFAULT_ACTION);
        if (isDefaultAction) {
            mappingMessage = String.format("Mapped provider '%s' (with mapping [%s]) default " +
                    "action.", methodDeclaringClass, provider);
        } else {
            mappingMessage = String.format("Mapped provider '%s' (with mapping [%s]) " +
                            "action '%s' to [%s]",
                    methodDeclaringClass, provider, methodName,
                    String.join(", ", actions));
        }

        logger.info(mappingMessage);
    }
}