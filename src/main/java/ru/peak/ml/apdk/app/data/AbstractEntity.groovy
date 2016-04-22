package ru.peak.ml.apdk.app.data

import javax.persistence.GeneratedValue
import javax.persistence.Id
import javax.persistence.MappedSuperclass

/**
 *
 */
@MappedSuperclass
class AbstractEntity {

    @Id
    @GeneratedValue
    long id;

}
