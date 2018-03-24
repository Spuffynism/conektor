package xyz.ndlr.model.dispatching.mapping;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.stereotype.Component;
import org.springframework.util.ReflectionUtils;

@Component
public class ActionMappingAnnotationProcessor implements BeanPostProcessor {

    private ActionRepository actionRepository;

    @Autowired
    public ActionMappingAnnotationProcessor(ActionRepository actionRepository) {
        this.actionRepository = actionRepository;
    }

    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws
            BeansException {
        Class<?> managedBeanClass = bean.getClass();
        ReflectionUtils.MethodCallback methodCallback =
                new ActionMappingMethodCallback(actionRepository, bean);
        ReflectionUtils.MethodFilter methodFilter = new ActionMappingMethodFilter();

        ReflectionUtils.doWithMethods(managedBeanClass, methodCallback, methodFilter);

        return bean;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws
            BeansException {
        return bean;
    }
}
