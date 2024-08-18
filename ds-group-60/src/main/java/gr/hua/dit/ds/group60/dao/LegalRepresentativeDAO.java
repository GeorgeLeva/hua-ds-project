package gr.hua.dit.ds.group60.dao;

import gr.hua.dit.ds.group60.entity.Application;
import gr.hua.dit.ds.group60.entity.LegalRepresentative;
import gr.hua.dit.ds.group60.entity.User;

import java.util.List;

public interface LegalRepresentativeDAO {

    public List<LegalRepresentative> getLegalRepresentatives();
    public LegalRepresentative getLegalRepresentative(Integer legalrepresentative_id);
    public void saveLegalRepresentative(LegalRepresentative legalrepresentative);
    public void deleteLegalRepresentative(Integer legalrepresentative_id);

    List<Application> getApplications(Integer legalrepresentative_id);
    List<LegalRepresentative> getLegalRepresentativesByUser(User user);

    public LegalRepresentative getLegalRepresentativeById(Integer id);
}
