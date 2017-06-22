package com.springmvc.model.achat;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.springmvc.model.jambon.Jambon;
import com.springmvc.model.user.User;

import javax.persistence.*;
import java.text.SimpleDateFormat;
import java.util.Date;

@Entity
@Table(name = "achat_ach")
@AssociationOverrides({
        @AssociationOverride(name = "user",
                joinColumns = @JoinColumn(name = "ach_usr_id")),
        @AssociationOverride(name = "jambon",
                joinColumns = @JoinColumn(name = "ach_jam_id"))
})
public class Achat {
    private int id;
    private Date dateAchat;
    private String dateHumaine;
    private String commentaire;
    private User user;
    private Jambon jambon;

    public Achat() {
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ach_id")
    public int getId() {
        return id;
    }

    public Achat setId(int id) {
        this.id = id;
        return this;
    }

    @JsonIgnore
    @Temporal(TemporalType.TIMESTAMP)
    @Basic
    @Column(name = "ach_date_achat")
    public Date getDateAchat() {
        return dateAchat;
    }

    public Achat setDateAchat(Date dateAchat) {
        this.dateAchat = dateAchat;
        this.setDateHumaine(dateAchat);

        return this;
    }

    @Transient
    public String getDateHumaine() {
        return dateHumaine;
    }

    public Achat setDateHumaine(String dateHumaine) {
        this.dateHumaine = dateHumaine;
        return this;
    }

    public Achat setDateHumaine(Date dateAchat) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
        this.dateHumaine = simpleDateFormat.format(dateAchat);

        return this;
    }

    @Basic
    @Column(name = "ach_commentaire")
    public String getCommentaire() {
        return commentaire;
    }

    public Achat setCommentaire(String commentaire) {
        this.commentaire = commentaire;
        return this;
    }

    @JsonIgnore
    @ManyToOne
    public User getUser() {
        return user;
    }

    public Achat setUser(User user) {
        this.user = user;
        return this;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    public Jambon getJambon() {
        return jambon;
    }

    public Achat setJambon(Jambon jambon) {
        this.jambon = jambon;
        return this;
    }
}