package ua.com.alus.medhosp.backend.domen.dao;

import org.apache.log4j.Logger;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.lang.reflect.ParameterizedType;

public abstract class JpaDAO<K, E> {
    private static Logger logger = Logger.getLogger(JpaDAO.class);

    private static final String ID = "id";

    protected Class<E> entityClass;

    @PersistenceContext(unitName = "MedHospUnit")
    protected EntityManager entityManager;

    @SuppressWarnings("unchecked")
    public JpaDAO() {
        ParameterizedType genericSuperclass = (ParameterizedType) getClass()
                .getGenericSuperclass();
        this.entityClass = (Class<E>) genericSuperclass
                .getActualTypeArguments()[1];
    }

    public void persist(E entity) {
        entityManager.persist(entity);
    }

    public void remove(E entity) {
        entityManager.remove(entity);
    }

    public E merge(E entity) {
        return entityManager.merge(entity);
    }

    public void refresh(E entity) {
        entityManager.refresh(entity);
    }

    public E findById(K id) {
        return entityManager.find(entityClass, id);
    }

    public E flush(E entity) {
        entityManager.flush();
        return entity;
    }

    @SuppressWarnings("unchecked")
    public Integer removeAll() {
        return entityManager.createQuery("DELETE FROM " +
                entityClass.getSimpleName() + " h").executeUpdate();
    }


}
