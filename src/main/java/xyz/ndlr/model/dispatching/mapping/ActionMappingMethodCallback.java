package xyz.ndlr.model.dispatching.mapping;

import org.apache.log4j.Logger;
import org.springframework.util.ReflectionUtils;
import xyz.ndlr.model.provider.ProviderResponse;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class ActionMappingMethodCallback implements ReflectionUtils.MethodCallback {
    private static final Logger logger = Logger.getLogger(ActionMappingMethodCallback.class);

    private ActionRepository actionRepository;
    private Object bean;

    ActionMappingMethodCallback(ActionRepository actionRepository, Object bean) {
        this.actionRepository = actionRepository;
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

        // register actions
        for (String action : actions) {
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

        logMappingMessage(methodDeclaringClass.getSimpleName(), provider, method.getName(),
                actions);
    }

    private void logMappingMessage(String methodDeclaringClass, String provider, String methodName,
                                   String[] actions) {
        String mappingMessage = "";
        boolean isDefaultAction = actions[0].equals("");
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