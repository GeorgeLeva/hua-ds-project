package gr.hua.dit.ds.group60.service;

import gr.hua.dit.ds.group60.dao.LegalRepresentativeDAO;
import gr.hua.dit.ds.group60.entity.Application;
import gr.hua.dit.ds.group60.entity.LegalRepresentative;
import gr.hua.dit.ds.group60.repository.ApplicationRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ApplicationService {
    @Autowired
    private ApplicationRepository applicationRepository;

    @Autowired
    private LegalRepresentativeDAO legalRepresentativeDAO;

    @Transactional
    public List<Application> getApplications(){return applicationRepository.findAll();
    }

    @Transactional
    public void saveApplication(Application application){applicationRepository.save(application);
    }

    @Transactional
    public void deleteApplication(Integer applicationId) {
        applicationRepository.deleteById(applicationId);
    }

    @Transactional
    public Application getApplication(Integer applicationId) {
        return applicationRepository.findById(applicationId).get();
    }

    public List<Application> getLegalRepsesentativeApplications(Integer legalRepresentativeId){
        LegalRepresentative legalRepresentative = legalRepresentativeDAO.getLegalRepresentative(legalRepresentativeId);
        return legalRepresentative.getApplications();
    }
}
