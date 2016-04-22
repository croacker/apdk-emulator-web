package ru.peak.ml.apdk.app.data.dao;

import org.springframework.stereotype.Repository;
import ru.peak.ml.apdk.app.data.AppParameter;

import javax.persistence.Query;
import java.util.List;

/**
 *
 */
@Repository
class AppParameterDao extends CommonDao<AppParameter> {

    public AppParameter findByName(String name) {
        AppParameter result = null;
        Query query = entityManager.createQuery("select a from AppParameter a where a.name = :name");
        query.setParameter("name", name);
        List<AppParameter> list = query.getResultList();
        if (!list.isEmpty()) {
            result = list.get(0);
        }
        return result;
    }

}
