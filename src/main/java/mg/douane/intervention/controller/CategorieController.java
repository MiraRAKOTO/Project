package mg.douane.intervention.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import mg.douane.intervention.service.CategorieService;

@Controller
public class CategorieController {

    @Autowired
    CategorieService categorieService;

    @RequestMapping(value = "/problemeCateg")
    public String categoriePage(Model model) {
        model.addAttribute("categList", categorieService.getAllCategories());
        model.addAttribute("souscategList", categorieService.getAllSousCategories());
        model.addAttribute("soussouscategList", categorieService.getAllSousCategories());
        return "ListeCategorie";
    }
}
