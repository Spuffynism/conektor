package com.springmvc.model.achat;

import com.springmvc.model.database_util.AbstractManager;
import com.springmvc.model.database_util.QueryExecutor;
import com.springmvc.model.jambon.JambonService;
import com.springmvc.model.user.UserService;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;

@Component
public class AchatService extends AbstractManager<Achat> {

    private UserService userService;
    private JambonService jambonService;

    @Autowired
    public AchatService(UserService userService, JambonService jambonService) {
        super(Achat.class);

        this.userService = userService;
        this.jambonService = jambonService;
    }

    public Achat acheter(Achat achat) {
        Achat newAchat = new Achat()
                .setJambon(jambonService.get(achat.getJambon().getId()))
                .setUser(userService.getAuthenticatedUser())
                .setCommentaire(achat.getCommentaire())
                .setDateAchat(new Date());

        return ajouter(newAchat);
    }

    @SuppressWarnings(value = "unchecked")
    public List<Achat> getByUser(int userId) {
            return new QueryExecutor<>(session -> {
                List<Achat> achats = (List<Achat>) session.createCriteria(Achat.class)
                        .add(Restrictions.eq("userId", userId))
                        .uniqueResult();

                session.getTransaction().commit();

                return achats;
            }).execute();
    }
}
