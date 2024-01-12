package gr.hua.dit.ds.group60.dao;

import gr.hua.dit.ds.group60.entity.Application;
import gr.hua.dit.ds.group60.entity.LegalRepresentative;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import jakarta.transaction.Transactional;
import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class LegalRepresentativeDAOImpl implements LegalRepresentativeDAO {

    @PersistenceContext
    private EntityManager entityManager;


    @Override
    @Transactional
    public List<LegalRepresentative> getLegalRepresentatives() {
        TypedQuery<LegalRepresentative> query = entityManager.createQuery("from LegalRepresentative", LegalRepresentative.class);
        return query.getResultList();
    }

    @Override
    public LegalRepresentative getLegalRepresentative(Integer legalrepresentative_id) {
        return entityManager.find(LegalRepresentative.class, legalrepresentative_id);
    }

    @Override
    @Transactional
    public void saveLegalRepresentative(LegalRepresentative legalrepresentative) {
        System.out.println("legalrepresentative"+ legalrepresentative.getLegalRepresentativeId());
        if (legalrepresentative.getLegalRepresentativeId() == null) {
            entityManager.persist(legalrepresentative);
        } else {
            entityManager.merge(legalrepresentative);
        }
    }

    @Override
    @Transactional
    public void deleteLegalRepresentative(Integer legalrepresentative_id) {
        System.out.println("Deleting legal representative with id: " + legalrepresentative_id);
        entityManager.remove(entityManager.find(LegalRepresentative.class, legalrepresentative_id));
    }

    @Override
    @Transactional
    public List<Application> getApplications(Integer legalrepresentative_id) {
        LegalRepresentative legalrepresentative = entityManager.find(LegalRepresentative.class, legalrepresentative_id);
        return legalrepresentative.getApplications();
    }
}
