package com.restaurante.repository;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.query.Query;
import java.util.List;
import java.util.Optional;

public abstract class AbstractRepository<T> {
    private final Class<T> entityClass;
    private static SessionFactory sessionFactory;

    static {
        try {
            sessionFactory = new Configuration()
                .configure("hibernate.cfg.xml")
                .buildSessionFactory();
        } catch (Exception e) {
            System.err.println("Failed to create SessionFactory: " + e);
            throw new ExceptionInInitializerError(e);
        }
    }

    public AbstractRepository(Class<T> entityClass) {
        this.entityClass = entityClass;
    }

    protected Session openSession() {
        return sessionFactory.openSession();
    }

    public void save(T entity) {
        try (Session session = openSession()) {
            Transaction tx = session.beginTransaction();
            session.persist(entity);
            tx.commit();
        }
    }

    public Optional<T> findById(Long id) {
        try (Session session = openSession()) {
            return Optional.ofNullable(session.get(entityClass, id));
        }
    }

    public List<T> findAll() {
        try (Session session = openSession()) {
            Query<T> query = session.createQuery(
                "FROM " + entityClass.getSimpleName(), entityClass);
            return query.getResultList();
        }
    }

    public void update(T entity) {
        try (Session session = openSession()) {
            Transaction tx = session.beginTransaction();
            session.merge(entity);
            tx.commit();
        }
    }

    public void delete(T entity) {
        try (Session session = openSession()) {
            Transaction tx = session.beginTransaction();
            session.remove(entity);
            tx.commit();
        }
    }

    public static void shutdown() {
        if (sessionFactory != null) {
            sessionFactory.close();
        }
    }
}