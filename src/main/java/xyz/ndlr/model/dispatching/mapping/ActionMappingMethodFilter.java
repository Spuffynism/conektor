package xyz.ndlr.model.dispatching.mapping;

import org.apache.log4j.Logger;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.util.ReflectionUtils;
import xyz.ndlr.model.entity.User;
import xyz.ndlr.model.provider.ProviderResponse;
import xyz.ndlr.model.provider.facebook.PipelinedMessage;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

public class ActionMappingMethodFilter implements ReflectionUtils.MethodFilter {
    private static final Logger logger = Logger.getLogger(ActionMappingMethodFilter.class);

    private static final int PARAMETER_COUNT = 2;
    private static final Class<?> FIRST_PARAMETER_TYPE = User.class;
    private static final Class<?> SECOND_PARAMETER_TYPE = PipelinedMessage.class;
    private static final Class<?> RETURN_TYPE = ProviderResponse.class;

    @Override
    public boolean matches(Method method) {
        boolean isMapping = AnnotationUtils.findAnnotation(method, ActionMapping.class) != null;
        return isMapping
                && classIsValid(method)
                && methodSignatureIsValid(method);
    }

    private boolean classIsValid(Method method) {
        boolean hasAppropriateAnnotation =
                method.getDeclaringClass().isAnnotationPresent(ProviderMapping.class);
        if (!hasAppropriateAnnotation) {
            String errorMessage = String.format("A method with @%s must reside in a " +
                            "class with @%s. %s does not",
                    ActionMapping.class.getSimpleName(),
                    ProviderMapping.class.getSimpleName(),
                    method.getDeclaringClass().getName());
            logger.error(errorMessage);
        }

        return hasAppropriateAnnotation &&
                isNotInterfaceNorAbstractClass(method.getDeclaringClass());
    }

    private boolean isNotInterfaceNorAbstractClass(Class<?> clazz) {
        return !clazz.isInterface() && !Modifier.isAbstract(clazz.getModifiers());
    }

    private boolean methodSignatureIsValid(Method method) throws IllegalArgumentException {
        return hasAppropriateParameterCount(method)
                && hasAppropriateParameters(method)
                && hasAppropriateReturnType(method);
    }

    private boolean hasAppropriateParameterCount(Method method) {
        boolean hasAppropriateParameterCount = method.getParameterCount() == PARAMETER_COUNT;
        if (!hasAppropriateParameterCount) {
            String errorMessage = String.format("A method with @%s must take %d " +
                            "parameters: %s and %s. %s",
                    ActionMapping.class.getSimpleName(),
                    PARAMETER_COUNT,
                    FIRST_PARAMETER_TYPE.getName(),
                    SECOND_PARAMETER_TYPE.getName(),
                    doesNotMessage(method));
            logger.error(errorMessage);
        }

        return hasAppropriateParameterCount;
    }

    private boolean hasAppropriateParameters(Method method) {
        Class<?>[] parameterTypes = method.getParameterTypes();

        boolean hasAppropriateFirstParameter =
                FIRST_PARAMETER_TYPE.isAssignableFrom(parameterTypes[0]);
        if (!hasAppropriateFirstParameter) {
            String errorMessage = String.format("A method with @%s must have a %s as " +
                            "its first parameter. %s",
                    ActionMapping.class.getSimpleName(),
                    FIRST_PARAMETER_TYPE.getName(),
                    doesNotMessage(method));
            logger.error(errorMessage);
        }

        boolean hasAppropriateSecondParameter =
                SECOND_PARAMETER_TYPE.isAssignableFrom(parameterTypes[1]);
        if (!hasAppropriateSecondParameter) {
            String errorMessage = String.format("A method with @%s must have a %s as " +
                            "its second parameter. %s",
                    ActionMapping.class.getSimpleName(),
                    SECOND_PARAMETER_TYPE.getName(),
                    doesNotMessage(method));
            logger.error(errorMessage);
        }

        return hasAppropriateFirstParameter && hasAppropriateSecondParameter;
    }

    private boolean hasAppropriateReturnType(Method method) {
        boolean hasAppropriateReturnType = RETURN_TYPE.isAssignableFrom(method.getReturnType());
        if (!hasAppropriateReturnType) {
            String errorMessage = String.format("A method with @%s must have a %s " +
                            "return type. %s",
                    ActionMapping.class.getSimpleName(),
                    RETURN_TYPE.getName(),
                    doesNotMessage(method));
            logger.error(errorMessage);
        }

        return hasAppropriateReturnType;
    }

    private String doesNotMessage(Method method) {
        return String.format("%s.%s does not.",
                method.getDeclaringClass().getName(),
                method.getName());
    }
}
