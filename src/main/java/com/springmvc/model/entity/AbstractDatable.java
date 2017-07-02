package com.springmvc.model.entity;

import javax.persistence.Basic;
import javax.persistence.Column;
import java.util.Date;

public abstract class AbstractDatable {
    Date dateCreated;
    Date dateModified;

    /**
     * Needs to be overridden because the name of the database column referring to the creation date
     * must be specified with a hibernate @Column annotation.
     *
     * @return the creation date
     */
    @Basic
    @Column(updatable = false, insertable = false)
    public abstract Date getDateCreated();

    public void setDateCreated(Date dateCreated) {
        this.dateCreated = dateCreated;
    }

    /**
     * Needs to be overridden because the name of the database column referring to the last
     * modification date must be specified with a hibernate @Column annotation.
     *
     * @return the creation date
     */
    @Basic
    @Column(updatable = false, insertable = false)
    public abstract Date getDateModified();

    public void setDateModified(Date dateModified) {
        this.dateModified = dateModified;
    }
}
