package xyz.ndlr.service;

import org.springframework.stereotype.Service;
import xyz.ndlr.domain.entity.Provider;
import xyz.ndlr.repository.database_util.AbstractService;

@Service
public class ProviderService extends AbstractService<Provider> {
    public ProviderService() {
        super(Provider.class);
    }
}
