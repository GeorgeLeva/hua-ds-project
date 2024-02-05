package gr.hua.dit.ds.group60.controller;

import gr.hua.dit.ds.group60.dao.LegalRepresentativeDAO;
import gr.hua.dit.ds.group60.dao.UserDAO;
import gr.hua.dit.ds.group60.entity.LegalRepresentative;
import gr.hua.dit.ds.group60.entity.User;
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
    public String saveLegalRepresentative(@Valid @ModelAttribute("legal_representative") LegalRepresentative legalrepresentative, BindingResult result, Model model, Principal principal) {
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

        legalrepresentative.setUser(currentUser);

        // Proceed with saving the LegalRepresentative
        legalrepresentativeDao.saveLegalRepresentative(legalrepresentative);
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
