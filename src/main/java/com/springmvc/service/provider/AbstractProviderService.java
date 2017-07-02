package com.springmvc.service.provider;

import com.springmvc.model.entity.User;
import com.springmvc.service.database_util.AbstractService;

public abstract class AbstractProviderService extends AbstractService<User> {

    protected AbstractProviderService() {
        super(User.class);
    }

    public User getByUserAccountDetailsIdAndProviderId() {
        return null;
    }
}
