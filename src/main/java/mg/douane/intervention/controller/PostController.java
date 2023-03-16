package mg.douane.intervention.controller;

import mg.douane.intervention.data.domaine.Agent;
import mg.douane.intervention.data.domaine.FichePoste;
import mg.douane.intervention.data.domaine.Poste;
import mg.douane.intervention.repository.HierarchieRepository;
import mg.douane.intervention.repository.PorteRepository;
import mg.douane.intervention.repository.PosteRepository;
import mg.douane.intervention.repository.QuartierRepository;
import mg.douane.intervention.repository.TypeHierarchieRepository;
import mg.douane.intervention.repository.VilleRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;
import java.util.Date;
import java.util.Optional;

@Controller
public class PostController {

    @Autowired
    PorteRepository porteRepository;

    @Autowired
    HierarchieRepository hierarchieRepository;

    @Autowired
    QuartierRepository quartierRepository;

    @Autowired
    PosteRepository posteRepository;

    @Autowired
    TypeHierarchieRepository typeHierarchieRepository;

    @Autowired
    VilleRepository villeRepository;

    @RequestMapping(value = "/post")
    public String posteForm(Model model) {
        model.addAttribute("posteForm", new Poste());
        model.addAttribute("posteListe", posteRepository.findAll());
        model.addAttribute("porteListe", porteRepository.findAll());
        model.addAttribute("hierarchieListe", hierarchieRepository.findAll());
        model.addAttribute("quartierListe", quartierRepository.findAll());

        model.addAttribute("typeListe", typeHierarchieRepository.findAll());
        model.addAttribute("villeListe", villeRepository.findAll());
        return "Admin/GererPoste";
    }

    @PostMapping("/posteForm")
    public String createPoste(@Valid @ModelAttribute("posteForm") Poste poste, BindingResult result, ModelMap model) {
        if (result.hasErrors()) {
            model.addAttribute("posteForm", poste);
            model.addAttribute("formTab", "active");
        } else {
            try {
                posteRepository.save(poste);
                model.addAttribute("posteForm", new Poste());

            } catch (Exception e) {
                model.addAttribute("formErrorMessage", e.getMessage());
                model.addAttribute("posteForm", poste);
                model.addAttribute("formTab", "active");
            }
        }

        model.addAttribute("posteListe", posteRepository.findAll());
        model.addAttribute("porteListe", porteRepository.findAll());
        model.addAttribute("hierarchieListe", hierarchieRepository.findAll());
        model.addAttribute("quartierListe", quartierRepository.findAll());
        // return "agent-form/agent-view";
        return "redirect:/post";
    }
}
