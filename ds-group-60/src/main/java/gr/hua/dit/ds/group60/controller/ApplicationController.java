package gr.hua.dit.ds.group60.controller;

import gr.hua.dit.ds.group60.entity.Application;
import gr.hua.dit.ds.group60.service.ApplicationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;


@Controller
@RequestMapping("/applications")
public class ApplicationController {

    @Autowired
    private ApplicationService applicationService;

    @GetMapping("")
    public String showApplications(Model model){
        model.addAttribute("applications", applicationService.getApplications());
        return "applications";
    }

//    @Secured("ROLE_LEGAL_REPRESENTATIVE")
    @GetMapping("/new")
    public String addApplication(Model model){
        Application application = new Application();
        model.addAttribute("application", application);
        return "add_application";
    }

    @GetMapping("/{application_id}")
    public String editApplication(@PathVariable Integer application_id, Model model){
        Application application = applicationService.getApplication(application_id);
        model.addAttribute("application", application);
        return "add_application";
    }

    @GetMapping("/legal_representatives/{legal_representative_id}")
    public String getLegalRepresentativeApplications(@PathVariable Integer legal_representative_id, Model model){
        model.addAttribute("applications", applicationService.getLegalRepsesentativeApplications(legal_representative_id));
        return "applications";
    }

//    @Secured("ROLE_LEGAL_REPRESENTATIVE")
    @PostMapping("/new")
    public String saveApplications(Application application, Model model){
        applicationService.saveApplication(application);
        model.addAttribute("applications", applicationService.getApplications());
        return "applications";
    }


    @DeleteMapping("/{application_id}")
    public String deleteApplication(@PathVariable Integer application_id){
        applicationService.deleteApplication(application_id);
        return "applications";
    }

}
