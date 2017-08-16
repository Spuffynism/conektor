package xyz.ndlr.service;

import xyz.ndlr.model.entity.Provider;
import xyz.ndlr.service.database_util.AbstractService;
import org.springframework.stereotype.Service;

@Service
public class ProviderService extends AbstractService<Provider> {
    public ProviderService() {
        super(Provider.class);
    }
}
