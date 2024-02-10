package gr.hua.dit.ds.group60.controller;

import gr.hua.dit.ds.group60.dao.CompanyDAO;
import gr.hua.dit.ds.group60.dao.LegalRepresentativeDAO;
import gr.hua.dit.ds.group60.dao.UserDAO;
import gr.hua.dit.ds.group60.entity.Application;
import gr.hua.dit.ds.group60.entity.Company;
import gr.hua.dit.ds.group60.entity.LegalRepresentative;
import gr.hua.dit.ds.group60.entity.User;
import gr.hua.dit.ds.group60.service.ApplicationService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;


@Controller
@RequestMapping("/applications")
public class ApplicationController {

    @Autowired
    private ApplicationService applicationService;

    @Autowired
    private UserDAO userDao;
    @Autowired
    private CompanyDAO companyDAO;

    @Autowired
    private LegalRepresentativeDAO legalRepresentativeDAO;

    @GetMapping("")
    public String showApplications(Model model) {
        model.addAttribute("applications", applicationService.getApplications());
        return "applications";
    }

    //    @Secured("ROLE_LEGAL_REPRESENTATIVE")
    @GetMapping("/new")
    public String addApplication(Model model, Principal principal) {
        // Retrieve the currently logged-in user
        User currentUser = userDao.getUserByEmail(principal.getName());

        // Retrieve legal representatives associated with the current user
        List<LegalRepresentative> legalRepresentatives = legalRepresentativeDAO.getLegalRepresentativesByUser(currentUser);

        // Create a new application object
        Application application = new Application();

        // Add legal representatives and other necessary attributes to the model
        model.addAttribute("application", application);
        model.addAttribute("legalRepresentatives", legalRepresentatives);

        return "add_application";
    }


    @GetMapping("/{application_id}")
    public String editApplication(@PathVariable Integer application_id, Model model) {
        Application application = applicationService.getApplication(application_id);
        model.addAttribute("application", application);
        return "add_application";
    }

    @GetMapping("/legal_representatives/{legal_representative_id}")
    public String getLegalRepresentativeApplications(@PathVariable Integer legal_representative_id, Model model) {
        model.addAttribute("applications", applicationService.getLegalRepsesentativeApplications(legal_representative_id));
        return "applications";
    }

    //    @Secured("ROLE_LEGAL_REPRESENTATIVE")
    @PostMapping("/new")
    public String saveApplication(@Valid @ModelAttribute("application") Application application, BindingResult result, Model model, Principal principal) {
        if (result.hasErrors()) {
            // Handle validation errors (e.g., return to the form with error messages)
            return "add_application";
        }

        // Retrieve currently logged-in user's details
        User currentUser = userDao.getUserByEmail(principal.getName());

        // Retrieve currently logged-in legal representative's details
        LegalRepresentative currentLegalRepresentative = legalRepresentativeDAO.getLegalRepresentativeById(currentUser.getId());

        // Proceed with saving the Application
        application.setLegalRepresentative(currentLegalRepresentative);
        applicationService.saveApplication(application);
        model.addAttribute("applications", applicationService.getApplications());
        return "applications";
    }
}