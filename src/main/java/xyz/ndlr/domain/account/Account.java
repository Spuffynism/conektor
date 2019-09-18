package xyz.ndlr.domain.account;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import xyz.ndlr.domain.provider.Provider;
import xyz.ndlr.domain.provider.ProviderId;
import xyz.ndlr.domain.user.User;
import xyz.ndlr.domain.user.UserId;

import javax.persistence.*;

@Entity
@Table(name = "account_acc")
public class Account {
    private AccountId id;
    private AccountToken token;
    @JsonProperty("user_id")
    private UserId userId;
    @JsonIgnore
    private User user;
    @JsonProperty("provider_id")
    private ProviderId providerId;
    @JsonIgnore
    private Provider provider;

    public Account() {
    }

    @Id
    @Column(name = "acc_id")
    public AccountId getId() {
        return id;
    }

    public void setId(AccountId id) {
        this.id = id;
    }

    @Basic
    @Column(name = "acc_token")
    public AccountToken getToken() {
        return token;
    }

    public void setToken(AccountToken token) {
        this.token = token;
    }

    @Basic
    @Column(name = "acc_user_id")
    public UserId getUserId() {
        return userId;
    }

    public void setUserId(UserId userId) {
        this.userId = userId;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "acc_user_id", insertable = false, updatable = false)
    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Basic
    @Column(name = "acc_provider_id")
    public ProviderId getProviderId() {
        return providerId;
    }

    public void setProviderId(ProviderId providerId) {
        this.providerId = providerId;
    }

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "acc_provider_id", insertable = false, updatable = false)
    public Provider getProvider() {
        return provider;
    }

    public void setProvider(Provider provider) {
        this.provider = provider;
    }
}
