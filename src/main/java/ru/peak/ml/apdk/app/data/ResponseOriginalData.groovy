package ru.peak.ml.apdk.app.data

import javax.persistence.Basic
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.Table
/**
 * Исходные данные ответа
 */
@Entity
@Table(name = "ResponseOriginalData")
public class ResponseOriginalData extends AbstractEntity {

    @Basic
    @Column(length = 2048)
    String originalData;

}
