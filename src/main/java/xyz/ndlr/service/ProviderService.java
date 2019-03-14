package xyz.ndlr.service;

import org.springframework.stereotype.Service;
import xyz.ndlr.domain.provider.IProviderRepository;

@Service
public class ProviderService {
    private final IProviderRepository providerRepository;

    public ProviderService(IProviderRepository providerRepository) {
        this.providerRepository = providerRepository;
    }
}
