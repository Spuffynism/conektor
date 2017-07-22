package com.springmvc.model.dispatching;

import com.springmvc.model.provider.ProviderResponse;

import java.util.List;

abstract class AbstractSubDispatcher {
    List<ProviderResponse> responses;

    AbstractSubDispatcher(List<ProviderResponse> responses) {
        this.responses = responses;
    }

}
