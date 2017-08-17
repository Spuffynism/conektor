package xyz.ndlr.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.*;

@Entity
@Table(name = "account_acc")
public class Account {
    private int id;
    @JsonProperty("details")
    private String details;
    private String token;
    @JsonProperty("user_id")
    private int userId;
    @JsonIgnoreProperties("accounts")
    private User user;
    @JsonProperty("provider_id")
    private int providerId;
    @JsonIgnore
    private Provider provider;

    public Account() {
    }

    //<editor-fold> Default getters and setters
    @Id
    @Column(name = "acc_id")
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Basic
    @Column(name = "acc_details")
    public String getDetails() {
        return details;
    }

    public void setDetails(String jsonDetails) {
        this.details = jsonDetails;
    }

    @Basic
    @Column(name = "acc_token")
    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    @Basic
    @Column(name = "acc_user_id")
    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
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
    public int getProviderId() {
        return providerId;
    }

    public void setProviderId(int providerId) {
        this.providerId = providerId;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "acc_provider_id", insertable = false, updatable = false)
    public Provider getProvider() {
        return provider;
    }

    public void setProvider(Provider provider) {
        this.provider = provider;
    }

    //</editor-fold>
}
