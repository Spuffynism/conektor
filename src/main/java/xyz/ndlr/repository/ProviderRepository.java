package xyz.ndlr.repository;

import org.springframework.stereotype.Component;
import xyz.ndlr.domain.provider.IProviderRepository;
import xyz.ndlr.domain.provider.Provider;
import xyz.ndlr.repository.database_util.AbstractRepository;

@Component
public class ProviderRepository extends AbstractRepository<Provider> implements
        IProviderRepository {
    protected ProviderRepository() {
        super(Provider.class);
    }
}
