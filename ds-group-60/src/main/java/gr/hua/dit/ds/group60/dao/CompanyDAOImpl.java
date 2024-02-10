package gr.hua.dit.ds.group60.dao;

import gr.hua.dit.ds.group60.entity.Company;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class CompanyDAOImpl implements CompanyDAO {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    @Transactional
    public List<Company> getCompanies() {
        return entityManager.createQuery("from Company", Company.class).getResultList();
    }

    @Override
    @Transactional
    public Company getCompany(Integer company_id) {
        return entityManager.find(Company.class, company_id);
    }

    @Override
    @Transactional
    public void saveCompany(Company company) {
        entityManager.merge(company);
    }

    @Override
    @Transactional
    public void deleteCompany(Integer company_id) {
        Company company = entityManager.find(Company.class, company_id);
        entityManager.remove(company);
    }

    @Override
    public Company getCompanyByName(String companyName) {
        // Implement the method to get a company by its name
        return entityManager.createQuery("SELECT c FROM Company c WHERE c.name = :companyName", Company.class)
                .setParameter("companyName", companyName)
                .getSingleResult();
    }

    @Override
    @Transactional
    public Company mergeCompany(Company company) {
        Company existingCompany = getCompanyByName(company.getName());
        if (existingCompany != null) {
            // Merge the properties of the passed company to the existing one
            existingCompany.setLegalRepresentative(company.getLegalRepresentative());

            // Return the merged company
            return entityManager.merge(existingCompany);
        } else {
            // If the company doesn't exist, persist the passed company
            entityManager.persist(company);
            return company;
        }
    }
}

