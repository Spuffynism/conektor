package xyz.ndlr.model.dispatching.mapping;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.stereotype.Component;
import org.springframework.util.ReflectionUtils;

@Component
public class ActionMappingAnnotationProcessor implements BeanPostProcessor {

    private ProviderActionRepository providerActionRepository;
    private ConfigurableListableBeanFactory configurableListableBeanFactory;

    @Autowired
    public ActionMappingAnnotationProcessor(ConfigurableListableBeanFactory
                                                    configurableListableBeanFactory,
                                            ProviderActionRepository providerActionRepository) {
        this.configurableListableBeanFactory = configurableListableBeanFactory;
        this.providerActionRepository = providerActionRepository;
    }

    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws
            BeansException {
        Class<?> managedBeanClass = bean.getClass();
        ReflectionUtils.MethodCallback methodCallback =
                new ActionMappingMethodCallback(providerActionRepository, bean);

        ReflectionUtils.doWithMethods(managedBeanClass, methodCallback);

        return bean;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws
            BeansException {
        return bean;
    }
}
