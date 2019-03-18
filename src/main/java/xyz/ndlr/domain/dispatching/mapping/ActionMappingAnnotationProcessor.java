package xyz.ndlr.domain.dispatching.mapping;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.stereotype.Component;
import org.springframework.util.ReflectionUtils;
import xyz.ndlr.domain.provider.ImageService;

@Component
public class ActionMappingAnnotationProcessor implements BeanPostProcessor {

    private final IActionRepository actionRepository;

    @Autowired
    public ActionMappingAnnotationProcessor(IActionRepository actionRepository) {
        this.actionRepository = actionRepository;
    }

    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws
            BeansException {
        conditionallyPopulateHumanNames(bean);

        Class<?> managedBeanClass = bean.getClass();
        ReflectionUtils.MethodCallback methodCallback =
                new ActionMappingMethodCallback(actionRepository, bean);
        ReflectionUtils.MethodFilter methodFilter = new ActionMappingMethodFilter();

        ReflectionUtils.doWithMethods(managedBeanClass, methodCallback, methodFilter);

        return bean;
    }

    /**
     * Populates the IActionRepository's image services human names.
     *
     * @param bean the bean that is maybe an ImageService
     */
    private void conditionallyPopulateHumanNames(Object bean) {
        boolean isImageService = ImageService.class.isAssignableFrom(bean.getClass());

        if (!isImageService)
            return;

        Class<?> clazz = bean.getClass();
        ProviderMapping providerMapping = clazz.getAnnotation(ProviderMapping.class);
        String provider = providerMapping.value();
        String humanName = providerMapping.humanName();

        actionRepository.registerImageProviderHumanName(provider, humanName);
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws
            BeansException {
        return bean;
    }
}
