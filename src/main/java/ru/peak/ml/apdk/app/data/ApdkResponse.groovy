package ru.peak.ml.apdk.app.data

import javax.persistence.*
/**
 * Ответ от приложения лояльность
 */
@Entity
@Table(name = "ApdkResponse")
public class ApdkResponse extends AbstractEntity {

    @Basic
    @Column
    @Temporal(TemporalType.TIMESTAMP)
    Date sendDate;

    @Basic
    @Column
    String title;

    /**
     * Тип операции
     */
    @Basic
    @Column
    String operationType;

    /**
     * Тип ответа
     */
    @Basic
    @Column
    String resultType;

    @ManyToOne(cascade=CascadeType.ALL)
    @JoinColumn(name = "responseOriginalDataId")
    ResponseOriginalData responseOriginalData;
}
