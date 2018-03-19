package xyz.ndlr.model.dispatching.mapping;

import org.apache.log4j.Logger;
import org.springframework.util.ReflectionUtils;
import xyz.ndlr.model.entity.User;
import xyz.ndlr.model.provider.ProviderResponse;
import xyz.ndlr.model.provider.facebook.PipelinedMessage;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Type;

public class ActionMappingMethodCallback implements ReflectionUtils.MethodCallback {
    private static final Logger logger = Logger.getLogger(ActionMappingMethodCallback.class);

    private static final int ACTION_MAPPING_METHOD_PARAMETER_COUNT = 2;
    private static final Class<?> ACTION_MAPPING_FIRST_PARAMETER_TYPE = User.class;
    private static final Class<?> ACTION_MAPPING_SECOND_PARAMETER_TYPE = PipelinedMessage.class;
    private static final Class<?> ACTION_MAPPING_RETURN_TYPE = ProviderResponse.class;

    private ProviderActionRepository providerActionRepository;
    private Object bean;

    ActionMappingMethodCallback(ProviderActionRepository providerActionRepository, Object bean) {
        this.providerActionRepository = providerActionRepository;
        this.bean = bean;
    }

    @Override
    public void doWith(Method method) throws IllegalArgumentException {
        if (!method.isAnnotationPresent(ActionMapping.class))
            return;

        checkDeclaringClassValidity(method);
        checkMethodParametersValidity(method);

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

    private void checkDeclaringClassValidity(Method method) {
        if (!method.getDeclaringClass().isAnnotationPresent(ProviderMapping.class)) {
            String errorMessage = "A method with @ActionMapping must reside in a class with " +
                    "@ProviderMapping. %s does not";
            throw new IllegalArgumentException(String.format(errorMessage,
                    method.getDeclaringClass().getName()));
        }
    }

    private void checkMethodParametersValidity(Method method) throws IllegalArgumentException {
        if (!ACTION_MAPPING_RETURN_TYPE.isAssignableFrom(method.getReturnType())) {
            String errorMessage = String.format("A method with @ActionMapping must have a %s " +
                            "return type",
                    ACTION_MAPPING_RETURN_TYPE.getName());
            throw new IllegalArgumentException(errorMessage);
        }

        if (method.getParameterCount() != ACTION_MAPPING_METHOD_PARAMETER_COUNT) {
            String errorMessage = String.format("A method with @ActionMapping must take %d " +
                            "parameters: %s and %s",
                    ACTION_MAPPING_METHOD_PARAMETER_COUNT,
                    ACTION_MAPPING_FIRST_PARAMETER_TYPE.getName(),
                    ACTION_MAPPING_SECOND_PARAMETER_TYPE.getName());
            throw new IllegalArgumentException(errorMessage);
        }

        Type[] parameterTypes = method.getParameterTypes();

        if (ACTION_MAPPING_FIRST_PARAMETER_TYPE.isAssignableFrom(parameterTypes[0].getClass())) {
            String errorMessage = String.format("A method with @ActionMapping must have a %s as " +
                    "its first parameter", ACTION_MAPPING_FIRST_PARAMETER_TYPE.getName());
            throw new IllegalArgumentException(errorMessage);
        }

        if (ACTION_MAPPING_SECOND_PARAMETER_TYPE.isAssignableFrom(parameterTypes[0].getClass())) {
            String errorMessage = String.format("A method with @ActionMapping must have a %s as " +
                    "its second parameter", ACTION_MAPPING_SECOND_PARAMETER_TYPE.getName());
            throw new IllegalArgumentException(errorMessage);
        }
    }
}