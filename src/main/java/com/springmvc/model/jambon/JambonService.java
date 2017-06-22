package com.springmvc.model.jambon;

import com.springmvc.model.database_util.AbstractManager;
import org.springframework.stereotype.Component;

@Component
public class JambonService extends AbstractManager<Jambon> {

    public JambonService() {
        super(Jambon.class);
    }
}
