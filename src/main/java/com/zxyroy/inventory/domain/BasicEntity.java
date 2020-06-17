package com.zxyroy.inventory.domain;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;

@MappedSuperclass
public abstract class BasicEntity implements Serializable {
    @Id
    @GeneratedValue(generator = "native")
    @GenericGenerator(name = "native", strategy = "native")
    @Getter
    private Long id;

    @Getter
    @Setter
    private boolean deleted = false;

    @Getter
    private final Timestamp createdAt = new Timestamp(System.currentTimeMillis());

    @Getter
    private Timestamp updatedAt = new Timestamp(System.currentTimeMillis());

    @PreUpdate
    public void setLastUpdate() {
        this.updatedAt = new Timestamp(System.currentTimeMillis());
    }

}