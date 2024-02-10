package gr.hua.dit.ds.group60.controller;

import gr.hua.dit.ds.group60.dao.CompanyDAO;
import gr.hua.dit.ds.group60.dao.LegalRepresentativeDAO;
import gr.hua.dit.ds.group60.dao.UserDAO;
import gr.hua.dit.ds.group60.entity.Company;
import gr.hua.dit.ds.group60.entity.LegalRepresentative;
import gr.hua.dit.ds.group60.entity.User;
import jakarta.persistence.NoResultException;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Valid;
import jakarta.validation.Validation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Validator;

import java.security.Principal;
import java.util.Set;


@Controller
@RequestMapping("/legal_representatives")
public class LegalRepresentativeController {

    @Autowired
    private LegalRepresentativeDAO legalrepresentativeDao;
    @Autowired
    private CompanyDAO companyDAO;

    @Autowired
    private UserDAO userDao;

    @GetMapping("")
    public String showLegalRepresentatives(Model model){


        model.addAttribute("legal_representatives", legalrepresentativeDao.getLegalRepresentatives());
        return "legal_representatives";
    }

    @GetMapping("/new")
    public String addLegalRepresentative(Model model){
        LegalRepresentative legal_representative = new LegalRepresentative();
        model.addAttribute("legal_representative", legal_representative);
        return "add_legal_representative";
    }



    @GetMapping("/{legal_representative_id}")
    public String editLegalRepresentative(@PathVariable Integer legal_representative_id, Model model){
        LegalRepresentative legalrepresentative = legalrepresentativeDao.getLegalRepresentative(legal_representative_id);
        model.addAttribute("legal_representative", legalrepresentative);
        return "add_legal_representative";

    }

    @PostMapping("/new")
    public String saveLegalRepresentative(@Valid @ModelAttribute("legal_representative") LegalRepresentative legalRepresentative,
                                          BindingResult result, Model model, Principal principal) {
        if (result.hasErrors()) {
            // Handle validation errors (e.g., return to the form with error messages)
            return "add_legal_representative";
        }

        User currentUser = userDao.getUserByEmail(principal.getName());

        // Validate the associated User entity
        if (currentUser == null || !isValidUser(currentUser)) {
            // Handle validation errors for the User entity
            return "add_legal_representative";
        }

        legalRepresentative.setUser(currentUser);

        // Check if the company already exists
        Company company;
        try {
            company = companyDAO.getCompanyByName(legalRepresentative.getCompanyName());
        } catch (jakarta.persistence.NoResultException | org.springframework.dao.EmptyResultDataAccessException e) {
            // If no company is found, create a new one
            company = new Company();
            company.setName(legalRepresentative.getCompanyName());
            // Save the company
            companyDAO.saveCompany(company);
        }

        // Save or merge the legal representative
        legalrepresentativeDao.saveLegalRepresentative(legalRepresentative);

        // Fetch the managed legal representative
        legalRepresentative = legalrepresentativeDao.getLegalRepresentativeById(legalRepresentative.getId());

        // Set the legal representative for the company
        company.setLegalRepresentative(legalRepresentative);

        // Add the company to the set of companies for the legal representative
        legalRepresentative.getCompanies().add(company);

        model.addAttribute("legal_representatives", legalrepresentativeDao.getLegalRepresentatives());
        return "legal_representatives";
    }



    private boolean isValidUser(User user) {
        Validator validator = Validation.buildDefaultValidatorFactory().getValidator();
        Set<ConstraintViolation<User>> violations = validator.validate(user);
        return violations.isEmpty();
    }


    @DeleteMapping("/{legal_representative_id}")
    public ResponseEntity<String> deleteLegalRepresentative(@PathVariable Integer legal_representative_id) {
        legalrepresentativeDao.deleteLegalRepresentative(legal_representative_id);
        return ResponseEntity.ok("Legal representative deleted successfully");
    }

}
