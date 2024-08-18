package gr.hua.dit.ds.group60.dao;

import gr.hua.dit.ds.group60.entity.Application;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class ApplicationDAOImpl implements ApplicationDAO {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    @Transactional
    public List<Application> getApplications() {
        TypedQuery<Application> query = entityManager.createQuery("from Application", Application.class);
        return query.getResultList();
    }

    @Override
    public Application getApplication(Integer application_id) {
        return entityManager.find(Application.class, application_id);
    }

    @Override
    @Transactional
    public void saveApplication(Application application) {
        if (application.getId() == null) {
            entityManager.persist(application);
        } else {
            entityManager.merge(application);
        }
    }

    @Override
    @Transactional
    public void deleteApplication(Integer application_id) {
        entityManager.remove(entityManager.find(Application.class, application_id));
    }

}
