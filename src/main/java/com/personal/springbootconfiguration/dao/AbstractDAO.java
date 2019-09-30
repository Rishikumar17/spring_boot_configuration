/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.personal.springbootconfiguration.dao;

/**
 *
 * @author 1460344
 */
import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Example;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@SuppressWarnings("unchecked")
@PropertySource(value = {"classpath:query.properties"})
public abstract class AbstractDAO<E> {

    @Autowired
    Environment environment;

    @Autowired
    SessionFactory sessionFactory;
    private final Class<E> entityClass;

    public AbstractDAO() {
        this.entityClass = (Class<E>) ((ParameterizedType) this.getClass().getGenericSuperclass())
                .getActualTypeArguments()[0];
    }

    protected Session getSession() {
        return this.sessionFactory.getCurrentSession();
    }

    public E findById(final Serializable id) {
        return (E) getSession().get(this.entityClass, id);
    }

    public Serializable save(E entity) {
        return getSession().save(entity);
    }

    public Object[] getParametersObjectArray(Object... args) {
        return args;
    }

    public E saveOrUpdate(E entity) {
        getSession().saveOrUpdate(entity);
        return entity;

    }

    public void delete(E entity) {
        getSession().delete(entity);
    }

    public void deleteAll() {
        List<E> entities = findAll();
        for (E entity : entities) {
            getSession().delete(entity);
        }
    }

    public List<E> findAll() {
        return getSession().createCriteria(this.entityClass).list();
    }

    public List<E> findAllByExample(E entity) {
        Example example = Example.create(entity).ignoreCase().enableLike().excludeZeroes();
        return getSession().createCriteria(this.entityClass).add(example).list();
    }

    public void clear() {
        getSession().clear();

    }

    public void flush() {
        getSession().flush();

    }

    public List<E> find(final String queryString) {
        Query query = getSession().getNamedQuery(queryString);
        return query.list();
    }

    public List<E> findByQuery(final String queryString, final Object... values) {

        Query query = getSession().getNamedQuery(queryString);
        if (values != null) {
            for (int i = 0; i < values.length; i++) {
                query.setParameter(i, values[i]);
            }
        }

        return query.list();

    }

    public List findBySqlQuery(final String queryString, final Object... values) {

        Query query = getSession().createSQLQuery(environment.getRequiredProperty(queryString));
        if (values != null) {
            for (int i = 0; i < values.length; i++) {
                query.setParameter(i, values[i]);
            }
        }

        return query.list();

    }

    public Object[] findBySqlQueryUnique(final String queryString, final Object... values) {

        Query query = getSession().createSQLQuery(environment.getRequiredProperty(queryString));
        if (values != null) {
            for (int i = 0; i < values.length; i++) {
                query.setParameter(i, values[i]);
            }
        }

        return (Object[]) query.uniqueResult();

    }

    public E findBySqlQueryEntityUnique(final String queryString, final Object... values) {
        try {

            Query query = getSession().createSQLQuery(environment.getRequiredProperty(queryString)).addEntity("tbl", entityClass);
            if (values != null) {
                for (int i = 0; i < values.length; i++) {
                    query.setParameter(i, values[i]);
                }
            }

            return (E) query.uniqueResult();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

    }

    public List<E> findBySqlQueryEntity(final String queryString, final Object... values) {
        try {

            Query query = getSession().createSQLQuery(environment.getRequiredProperty(queryString)).addEntity("tbl", entityClass);
            if (values != null) {
                for (int i = 0; i < values.length; i++) {
                    query.setParameter(i, values[i]);
                }
            }

            return query.list();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

    }

    public Map findBySqlMap(final String queryString, final Object... values) {

        Map m = new HashMap();
        m.put("key", Arrays.asList(environment.getRequiredProperty(queryString + "_key").split(",")));

        Query query = getSession().createSQLQuery(environment.getRequiredProperty(queryString));
        if (values != null) {
            for (int i = 0; i < values.length; i++) {
                query.setParameter(i, values[i]);
            }
        }
        m.put("responses", query.list());
        return m;

    }

    public E findByQueryUnique(final String queryString, final Object... values) {

        Query query = getSession().getNamedQuery(queryString);
        if (values != null) {
            for (int i = 0; i < values.length; i++) {
                query.setParameter(i, values[i]);
            }
        }
        return (E) query.uniqueResult();

    }
   /**
     *
     * @param queryString
     * @return one object based on the query string
     * @throws org.hibernate.HibernateException
     */
    public E findOne(String queryString) {
        return findOne(queryString, (Object[]) null);
    }

    /**
     *
     * @param queryString
     * @param value
     * @return one object based on the value
     * @throws org.hibernate.HibernateException
     */
    public E findOne(String queryString, Object value) {
        return findOne(queryString, new Object[]{value});
    }

    /**
     *
     * @param queryString
     * @param values
     * @return one object based on the set of values
     * @throws org.hibernate.HibernateException
     */
    public E findOne(final String queryString, final Object[] values) {
        Query query = getSession().getNamedQuery(queryString);
        if (values != null) {
            for (int i = 0; i < values.length; i++) {
                query.setParameter(i, values[i]);
            }
        }
        return (E) query.uniqueResult();
    }

   
}
