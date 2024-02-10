package gr.hua.dit.ds.group60.dao;

import gr.hua.dit.ds.group60.entity.Company;
import gr.hua.dit.ds.group60.entity.LegalRepresentative;

import java.util.List;

public interface CompanyDAO {

    public List<Company> getCompanies();

    public Company getCompany(Integer company_id);

    public void saveCompany (Company Company);

    public void deleteCompany(Integer company_id);

    public Company getCompanyByName(String name);

    Company mergeCompany(Company company);


}
