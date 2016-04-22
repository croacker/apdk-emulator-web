package ru.peak.ml.apdk.app.data.dao;

import ru.peak.ml.apdk.app.data.AbstractEntity;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import javax.transaction.Transactional;
import java.util.List;

/**
 *
 */
abstract class CommonDao<T extends AbstractEntity> {

    @PersistenceContext
    def EntityManager entityManager;

    protected Query getQuery(String query) {
        entityManager.createQuery(query);
    }

    public List<T> getAll(Class<T> clazz) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<T> query = criteriaBuilder.createQuery(clazz);
        Root<T> root = query.from(clazz);
        query.select(root);
        return entityManager.createQuery(query).getResultList();
    }

    public T findById(Class<T> clazz, Long id) {
        entityManager.find(clazz, id);
    }

    @Transactional
    public T merge(T entity){
        entityManager.merge(entity);
    }

    @Transactional
    public void persist(T entity){
        entityManager.persist(entity);
    }

    @Transactional
    public void remove(T entity) {
        entityManager.remove(entityManager.contains(entity) ? entity : entityManager.merge(entity));
    }
}
