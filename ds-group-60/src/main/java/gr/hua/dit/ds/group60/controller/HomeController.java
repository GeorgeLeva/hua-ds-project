package gr.hua.dit.ds.group60.controller;

import gr.hua.dit.ds.group60.dao.LegalRepresentativeDAO;
import gr.hua.dit.ds.group60.dao.UserDAO;
import gr.hua.dit.ds.group60.entity.User;
import gr.hua.dit.ds.group60.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")
public class HomeController {

    @Autowired
    private UserDAO userDao;

    @GetMapping("")
    public String home(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentPrincipalName = authentication.getName();

        User currentUser = userDao.getUserByEmail(currentPrincipalName);

        model.addAttribute("currentUser", currentUser);
        return "home";
    }
}
