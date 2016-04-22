package ru.peak.ml.apdk.app.data.dao;

import org.springframework.stereotype.Repository;
import ru.peak.ml.apdk.app.data.ApdkResponse;
import ru.peak.ml.apdk.app.data.AppParameter;

import javax.persistence.Query;
import java.util.List;

/**
 *
 */
@Repository
class ApdkResponseDao extends CommonDao<ApdkResponse> {

    public List<ApdkResponse> getAll() {
        super.getAll(ApdkResponse.class);
    }

    public List<ApdkResponse> getAllDescending() {
            AppParameter result = null;
            Query query = entityManager.createQuery("select a from ApdkResponse a order by a.sendDate desc");
            return query.getResultList();
    }

}
