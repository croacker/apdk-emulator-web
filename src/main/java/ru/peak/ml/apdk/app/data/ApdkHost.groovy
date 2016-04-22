package ru.peak.ml.apdk.app.data

import javax.persistence.Basic
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.Table

@Entity
@Table(name = "ApdkHost")
public class ApdkHost extends AbstractEntity {

    @Basic
    @Column
    String name;

    @Basic
    @Column
    String address;

    @Basic
    @Column
    String port;
}
