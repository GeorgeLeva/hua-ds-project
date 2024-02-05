package gr.hua.dit.ds.group60.dao;

import gr.hua.dit.ds.group60.entity.LegalRepresentative;
import gr.hua.dit.ds.group60.entity.User;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.hibernate.query.Query;
import jakarta.persistence.TypedQuery;
import org.springframework.stereotype.Repository;

@Repository
public class UserDAOImpl implements UserDAO {
    @PersistenceContext
    private EntityManager entityManager;

    public User getUserByEmail(String email) {
        TypedQuery<User> query = entityManager.createQuery("from User where email=:email", User.class);
        query.setParameter("email", email);
        return query.getSingleResult();
    }
}
