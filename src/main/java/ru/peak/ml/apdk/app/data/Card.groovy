package ru.peak.ml.apdk.app.data

import javax.persistence.Basic
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.Table

@Entity
@Table(name = "Card")
public class Card extends AbstractEntity{

    @Basic
    @Column
    String number;

    @Basic
    @Column
    String expireDate;

    @Basic
    @Column
    String hash;
}
