package gr.hua.dit.ds.group60.controller;

import gr.hua.dit.ds.group60.dao.LegalRepresentativeDAO;
import gr.hua.dit.ds.group60.entity.LegalRepresentative;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("legalrepresentative")
public class LegalRepresentativeController {

    @Autowired
    private LegalRepresentativeDAO legalrepresentativeDao;

    @GetMapping("")
    public String showLegalRepresentatives(Model model){


        model.addAttribute("legalrepresentatives", legalrepresentativeDao.getLegalRepresentatives());
        return "legalrepresentatives";
    }

    @GetMapping("/new")
    public String addLegalRepresentative(Model model){
        LegalRepresentative legalrepresentative = new LegalRepresentative();
        model.addAttribute("legalrepresentative",legalrepresentative);

        return "add_legalrepresentative";

    }

    @GetMapping("{legalrepresentative_id}")
    public String editLegalRepresentative(@PathVariable Integer legalrepresentative_id, Model model){
        LegalRepresentative legalrepresentative = legalrepresentativeDao.getLegalRepresentative(legalrepresentative_id);
        model.addAttribute("legalrepresentative", legalrepresentative);
        return "add_legalrepresentative";

    }

    @PostMapping("/new")
    public String saveLegalRepresentative(@ModelAttribute("legalrepresentative") LegalRepresentative legalrepresentative, Model model) {
        legalrepresentativeDao.saveLegalRepresentative(legalrepresentative);
        model.addAttribute("legalrepresentatives", legalrepresentativeDao.getLegalRepresentatives());
        return "legalrepresentatives";
    }

    @DeleteMapping("{legalrepresentative_id}")
    public String deleteLegalRepresentative(@PathVariable Integer legalrepresentative_id){
        legalrepresentativeDao.deleteLegalRepresentative(legalrepresentative_id);
        return "legalrepresentatives";
    }


}
