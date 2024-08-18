package gr.hua.dit.ds.group60.controller;

import gr.hua.dit.ds.group60.dao.CompanyDAO;
import gr.hua.dit.ds.group60.dao.LegalRepresentativeDAO;
import gr.hua.dit.ds.group60.dao.UserDAO;
import gr.hua.dit.ds.group60.entity.Company;
import gr.hua.dit.ds.group60.entity.LegalRepresentative;
import gr.hua.dit.ds.group60.entity.User;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.PersistenceContext;
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

    @PersistenceContext
    private EntityManager entityManager;
    @Autowired
    private LegalRepresentativeDAO legalRepresentativeDao;
    @Autowired
    private CompanyDAO companyDAO;

    @Autowired
    private UserDAO userDao;

//    @GetMapping("")
//    public String showLegalRepresentatives(Model model){
//
//
//        model.addAttribute("legal_representatives", legalrepresentativeDao.getLegalRepresentatives());
//        return "legal_representatives";
//    }
//
//    @GetMapping("/new")
//    public String addLegalRepresentative(Model model){
//        LegalRepresentative legal_representative = new LegalRepresentative();
//        model.addAttribute("legal_representative", legal_representative);
//        return "add_legal_representative";
//    }
//
//
//
//    @GetMapping("/{legal_representative_id}")
//    public String editLegalRepresentative(@PathVariable Integer legal_representative_id, Model model){
//        LegalRepresentative legalrepresentative = legalrepresentativeDao.getLegalRepresentative(legal_representative_id);
//        model.addAttribute("legal_representative", legalrepresentative);
//        return "add_legal_representative";
//
//    }
//
//    @PostMapping("/new")
//    public String saveLegalRepresentative(@Valid @ModelAttribute("legal_representative") LegalRepresentative legalRepresentative,
//                                          BindingResult result, Model model, Principal principal) {
//        if (result.hasErrors()) {
//            // Handle validation errors (e.g., return to the form with error messages)
//            return "add_legal_representative";
//        }
//
//        User currentUser = userDao.getUserByEmail(principal.getName());
//
//        // Validate the associated User entity
//        if (currentUser == null || !isValidUser(currentUser)) {
//            // Handle validation errors for the User entity
//            return "add_legal_representative";
//        }
//
//        legalRepresentative.setUser(currentUser);
//
//        // Check if the company already exists
//        Company existingCompany = null;
//        try {
//            existingCompany = companyDAO.getCompanyByName(legalRepresentative.getCompanyName());
//        } catch (jakarta.persistence.NoResultException | org.springframework.dao.EmptyResultDataAccessException e) {
//            // If no company is found, create a new one
//            existingCompany = new Company();
//            existingCompany.setName(legalRepresentative.getCompanyName());
//            // Save the company
//            companyDAO.saveCompany(existingCompany);
//        }
//
//        // Save or merge the legal representative
//        legalrepresentativeDao.saveLegalRepresentative(legalRepresentative);
//
//        // Fetch the managed legal representative
//        legalRepresentative = legalrepresentativeDao.getLegalRepresentativeById(legalRepresentative.getId());
//
//        if (existingCompany != null) {
//            // Set the legal representative for the existing company
//            existingCompany.setLegalRepresentative(legalRepresentative);
//            // Merge the company entity to make it managed
//            existingCompany = companyDAO.mergeCompany(existingCompany);
//            // Add the existing company to the set of companies for the legal representative
//            legalRepresentative.getCompanies().add(existingCompany);
//        }
//
//        model.addAttribute("legal_representatives", legalrepresentativeDao.getLegalRepresentatives());
//        return "legal_representatives";
//    }
//
//
//    private boolean isValidUser(User user) {
//        Validator validator = Validation.buildDefaultValidatorFactory().getValidator();
//        Set<ConstraintViolation<User>> violations = validator.validate(user);
//        return violations.isEmpty();
//    }
//
//
//    @DeleteMapping("/{legal_representative_id}")
//    public ResponseEntity<String> deleteLegalRepresentative(@PathVariable Integer legal_representative_id) {
//        legalrepresentativeDao.deleteLegalRepresentative(legal_representative_id);
//        return ResponseEntity.ok("Legal representative deleted successfully");
//    }
//
//


    @GetMapping("")
    public String showLegalRepresentatives(Model model) {
        model.addAttribute("legal_representatives", legalRepresentativeDao.getLegalRepresentatives());
        return "legal_representatives";
    }

    @GetMapping("/new")
    public String addLegalRepresentative(Model model) {
        model.addAttribute("legal_representative", new LegalRepresentative());
        return "add_legal_representative";
    }

    @GetMapping("/{legal_representative_id}")
    public String editLegalRepresentative(@PathVariable Integer legal_representative_id, Model model) {
        LegalRepresentative legalRepresentative = legalRepresentativeDao.getLegalRepresentative(legal_representative_id);
        if (legalRepresentative == null) {
            return "legal_representatives";
        }
        model.addAttribute("legal_representative", legalRepresentative);
        return "add_legal_representative";
    }

    @PostMapping("/new")
    public String saveLegalRepresentative(@Valid @ModelAttribute("legal_representative") LegalRepresentative legalRepresentative,
                                          BindingResult result, Model model, Principal principal) {
        if (result.hasErrors()) {
            return "add_legal_representative";
        }

        User currentUser = userDao.getUserByEmail(principal.getName());
        if (currentUser == null) {
            return "add_legal_representative";
        }

        // Set the user for the legal representative
        legalRepresentative.setUser(currentUser);

        // Check if the company exists or needs to be created
        Company existingCompany;
        try {
            existingCompany = companyDAO.getCompanyByName(legalRepresentative.getCompanyName());
            if (existingCompany == null) {
                // If no company is found, create and save a new one
                existingCompany = new Company();
                existingCompany.setName(legalRepresentative.getCompanyName());
                existingCompany.setLegalRepresentative(legalRepresentative);
                companyDAO.saveCompany(existingCompany);
            }
        } catch (NoResultException | org.springframework.dao.EmptyResultDataAccessException e) {
            // Handle the case where no company is found or an exception is thrown
            existingCompany = new Company();
            existingCompany.setName(legalRepresentative.getCompanyName());
            existingCompany.setLegalRepresentative(legalRepresentative);
            companyDAO.saveCompany(existingCompany);
        }

        // Save the legal representative
        legalRepresentativeDao.saveLegalRepresentative(legalRepresentative);

        // Fetch and update the legal representative after persistence to ensure relationships are managed
        legalRepresentative = legalRepresentativeDao.getLegalRepresentativeById(legalRepresentative.getId());
        legalRepresentative.getCompanies().add(existingCompany);
        existingCompany.setLegalRepresentative(legalRepresentative);

        // Persist the association
        legalRepresentativeDao.saveLegalRepresentative(legalRepresentative);
        companyDAO.mergeCompany(existingCompany);

        return "redirect:/legal_representatives";
    }

    @DeleteMapping("/{legal_representative_id}")
    public ResponseEntity<String> deleteLegalRepresentative(@PathVariable Integer legal_representative_id) {
        legalRepresentativeDao.deleteLegalRepresentative(legal_representative_id);
        return ResponseEntity.ok("Legal representative deleted successfully");
    }

    @GetMapping("/add_company/{legal_representative_id}")
    public String showAddCompanyForm(@PathVariable Integer legal_representative_id, Model model) {
        LegalRepresentative legalRepresentative = legalRepresentativeDao.getLegalRepresentative(legal_representative_id);
        if (legalRepresentative == null) {
            return "legal_representatives";
        }
        model.addAttribute("legal_representative", legalRepresentative);
        model.addAttribute("company", new Company());
        return "add_company";
    }

    @PostMapping("/add_company/{legal_representative_id}")
    public String addCompanyToLegalRepresentative(@PathVariable Integer legal_representative_id,
                                                  @Valid @ModelAttribute("company") Company company,
                                                  BindingResult result) {
        if (result.hasErrors()) {
            return "add_company";
        }

        LegalRepresentative legalRepresentative = legalRepresentativeDao.getLegalRepresentative(legal_representative_id);
        if (legalRepresentative == null) {
            return "legal_representatives";
        }

        // Check if the company already exists
        Company existingCompany = companyDAO.getCompanyByName(company.getName());
        if (existingCompany == null) {
            // If the company doesn't exist, create a new one
            existingCompany = new Company();
            existingCompany.setName(company.getName());
            existingCompany.setLegalRepresentative(legalRepresentative); // Set the legal representative
            companyDAO.saveCompany(existingCompany); // Save the new company
        }

        // Add the company to the legal representative's set of companies
        legalRepresentative.addCompany(existingCompany);
        legalRepresentativeDao.saveLegalRepresentative(legalRepresentative);

        return "legal_representatives";
    }

}
