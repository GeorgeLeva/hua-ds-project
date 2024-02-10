package gr.hua.dit.ds.group60.dao;

import gr.hua.dit.ds.group60.entity.Application;

import java.util.List;

public interface ApplicationDAO {

    public List<Application> getApplications();

    public Application getApplication(Integer application_id);

    public void saveApplication(Application application);

    public void deleteApplication(Integer application_id);
}
