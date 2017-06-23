package com.springmvc.service;

import com.springmvc.model.Provider;
import com.springmvc.service.database_util.AbstractService;
import org.springframework.stereotype.Component;

@Component
public class ProviderService extends AbstractService<Provider> {
    public ProviderService() {
        super(Provider.class);
    }
}
