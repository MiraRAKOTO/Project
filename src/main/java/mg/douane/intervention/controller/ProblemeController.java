package mg.douane.intervention.controller;

import mg.douane.intervention.data.domaine.*;
import mg.douane.intervention.data.dto.*;
import mg.douane.intervention.repository.*;
import mg.douane.intervention.service.AgentService;
import mg.douane.intervention.service.CategorieService;
import mg.douane.intervention.service.ProblemeService;
import mg.douane.intervention.service.ReponseService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;

@Controller
public class ProblemeController {

    @Autowired
    ProblemeService problemeService;

    @Autowired
    CategorieRepository categorieRepository;

    @Autowired
    CategorieService categorieService;

    @Autowired
    PrioriterRepository prioriterRepository;

    @Autowired
    ProblemeRepository problemeRepository;

    @Autowired
    AgentRepository agentRepository;

    @Autowired
    Statusrepository statusrepository;

    @Autowired
    ReponseRepository reponseRepository;

    @Autowired
    AgentService agentService;

    @Autowired
    FichePosteRepository fichePosteRepository;

    @Autowired
    IntervenantRepository intervenantRepository;

    @Autowired
    ReponseService reponseService;

    @Autowired
    TypeHierarchieRepository typeHierarchieRepository;

    @Autowired
    VilleRepository villeRepository;

    @RequestMapping(value = "/problemeList/{filter}")
    public String problemePage(Model model, @PathVariable(name = "filter") String filter) {
        if (filter.equals("all"))
            model.addAttribute("problemList", problemeService.getAllProblemes());
        else if (filter.equals("resolu"))
            model.addAttribute("problemList", problemeService.getAllProblemesFilter("resolu"));
        else
            model.addAttribute("problemList", problemeService.getAllProblemesFilter("notresolu"));
        return "ListeToutProbleme";
    }

    @RequestMapping(value = "/problemView/{userName}")
    public String problemeView(Model model, @PathVariable(name = "userName") String userName) {
        model.addAttribute("problemList", problemeService.getAllProblemesByAgent(userName));
        model.addAttribute("problemtForm", new Probleme());
        model.addAttribute("categori", categorieRepository.findAllCategories());
        model.addAttribute("prioriter", prioriterRepository.findAll());
        return "SignalerProbleme";
    }

    @RequestMapping(value = "/problemRecep/{userName}/{filter}")
    public String problemRecep(Model model, @PathVariable(name = "userName") String userName,
            @PathVariable(name = "filter") String filter) {
        model.addAttribute("problemList", problemeService.getAllProblemesByDest(userName));
        model.addAttribute("agentList", agentRepository.findAll());
        model.addAttribute("categori", categorieRepository.findAllCategories());
        model.addAttribute("prioriter", prioriterRepository.findAll());
        model.addAttribute("problemtForm", new Probleme());
        model.addAttribute("problemListNews", problemeService.getAllProblemesByDestNews(userName, filter));
        model.addAttribute("problemListEnAttente", problemeService.getAllProblemesByDestEnAttente(userName, filter));
        model.addAttribute("problemListResolu", problemeService.getAllProblemesByDestResolu(userName, filter));
        return "Dispatch/ReceptionProblemeDispatch";
    }

    @RequestMapping(value = "/problemRepons/{userName}")
    public String problemRepons(Model model, @PathVariable(name = "userName") String userName) {
        model.addAttribute("reponseList", problemeService.getAllReponseByDest(userName));
        model.addAttribute("agentList", agentRepository.findAll());
        model.addAttribute("categori", categorieRepository.findAllCategories());
        model.addAttribute("prioriter", prioriterRepository.findAll());
        model.addAttribute("problemtForm", new Probleme());
        return "Dispatch/ReceptionReponseDispatch";
    }

    @GetMapping("/sousCategorieListe/{id}")
    @ResponseBody
    public List<CategorieDto> getSousCat(@PathVariable Long id) {
        List<Categorie> sousCategories = categorieService.getAllSousCategoriesByCat(id);
        return getSousCategorieDtos(sousCategories);
    }

    @GetMapping("/getSousCategorieListe/{id}")
    @ResponseBody
    public List<CategorieDto> getSousCategorie(@PathVariable Long id) {
        List<Categorie> sousCategories = categorieService.getAllSousCategoriesByCat(id);
        List<CategorieDto> categorieDtos = new ArrayList<>();
        for (int i = 0; i < sousCategories.size(); i++) {
            CategorieDto categorieDto = new CategorieDto();
            categorieDto.setIdCat(sousCategories.get(i).getIdCat());
            categorieDto.setLibelleCat(sousCategories.get(i).getLibelleCat());
            categorieDtos.add(categorieDto);
        }
        return categorieDtos;
    }

    @GetMapping("/getAllCategorieListe/{find}")
    @ResponseBody
    public List<CategorieDto> getAllCategorieListe(@PathVariable String find) {
        List<Categorie> categories = new ArrayList<>();
        if (find.equals("All")) {
            categories = categorieRepository.findAllCategories();
        } else {
            categories = categorieRepository.findByLibelleCat(find.toLowerCase());
        }
        List<CategorieDto> categorieDtos = new ArrayList<>();
        CategorieDto categorieDto = new CategorieDto();
        for (int i = 0; i < categories.size(); i++) {
            if (categories.get(i).getScats().isEmpty()) {
                categorieDto = new CategorieDto();
                categorieDto.setIdCat(categories.get(i).getIdCat());
                categorieDto.setLibelleCat(categories.get(i).getLibelleCat());
                categorieDto.setIdSCat((long) 0);
                categorieDtos.add(categorieDto);
            } else {
                categorieDto = new CategorieDto();
                categorieDto.setIdCat(categories.get(i).getIdCat());
                categorieDto.setLibelleCat(categories.get(i).getLibelleCat());
                categorieDto.setIdSCat((long) 0);
                categorieDtos.add(categorieDto);

                Set<Categorie> cat = categories.get(i).getScats();
                List<Categorie> catListe = new ArrayList<Categorie>(cat);
                for (int j = 0; j < catListe.size(); j++) {
                    if (catListe.get(j).getScats().isEmpty()) {
                        categorieDto = new CategorieDto();
                        categorieDto.setIdCat(catListe.get(j).getIdCat());
                        categorieDto.setLibelleCat(catListe.get(j).getLibelleCat());
                        categorieDto.setIdSCat(categories.get(i).getIdCat());
                        categorieDtos.add(categorieDto);
                    } else {
                        categorieDto = new CategorieDto();
                        categorieDto.setIdCat(catListe.get(j).getIdCat());
                        categorieDto.setLibelleCat(catListe.get(j).getLibelleCat());
                        categorieDto.setIdSCat(categories.get(i).getIdCat());
                        categorieDtos.add(categorieDto);
                        try {
                            Set<Categorie> sousCat = catListe.get(i).getScats();
                            List<Categorie> sousCatListe = new ArrayList<Categorie>(sousCat);
                            for (int k = 0; k < sousCatListe.size(); k++) {
                                categorieDto = new CategorieDto();
                                categorieDto.setIdCat(sousCatListe.get(k).getIdCat());
                                categorieDto.setLibelleCat(sousCatListe.get(k).getLibelleCat());
                                categorieDto.setIdSCat(catListe.get(j).getIdCat());
                                categorieDtos.add(categorieDto);
                            }
                        } catch (Exception e) {
                        }
                    }
                }
            }
        }
        return categorieDtos;
    }

    @GetMapping("/addSCat/{name}/{idScat}")
    @ResponseBody
    public String addSCat(@PathVariable String name, @PathVariable Long idScat) {
        if (idScat == 0) {
            Categorie categorie = new Categorie();
            categorie.setLibelleCat(name);
            categorie.setDateDebCat(new Date());
            categorieRepository.save(categorie);
        } else {
            Categorie categorie = new Categorie();
            categorie.setLibelleCat(name);
            Optional<Categorie> categorieOptional = categorieRepository.findById(idScat);
            categorie.setCat(categorieOptional.get());
            categorie.setDateDebCat(new Date());
            categorieRepository.save(categorie);
        }
        return null;
    }

    @GetMapping("/updateCat/{idCat}/{name}")
    @ResponseBody
    public String updateCat(@PathVariable Long idCat, @PathVariable String name) {
        Optional<Categorie> categorieOptional = categorieRepository.findById(idCat);
        if (categorieOptional.isPresent()) {
            categorieOptional.get().setLibelleCat(name);
            categorieRepository.save(categorieOptional.get());
        }
        return null;
    }

    @GetMapping("/deleteCat/{idCat}")
    @ResponseBody
    public String deleteCat(@PathVariable Long idCat) {
        Optional<Categorie> categorieOptional = categorieRepository.findById(idCat);
        if (categorieOptional.isPresent()) {
            categorieRepository.delete(categorieOptional.get());
        }
        return null;
    }

    @GetMapping("/sousCategorie2Liste/{id}")
    @ResponseBody
    public List<CategorieDto> getSousCats(@PathVariable Long id) {
        List<Categorie> sousCategories = categorieService.getAllSousSousCategoriesBySous(id);
        return getSousCategorieDtos(sousCategories);
    }

    private List<CategorieDto> getSousCategorieDtos(List<Categorie> sousCategories) {
        List<CategorieDto> categorieDtos = new ArrayList<>();
        for (int i = 0; i < sousCategories.size(); i++) {
            Set<Categorie> ct = sousCategories.get(i).getScats();
            List<Categorie> lc = new ArrayList<Categorie>(ct);
            if (lc.size() > 0) {
                CategorieDto cdt = new CategorieDto();
                cdt.setIdCat(sousCategories.get(i).getIdCat());
                cdt.setLibelleCat(sousCategories.get(i).getLibelleCat());
                cdt.setIdSCat((long) 0);
                categorieDtos.add(cdt);
                for (int j = 0; j < lc.size(); j++) {
                    CategorieDto sousCategorieDto = new CategorieDto();
                    sousCategorieDto.setIdSCat(sousCategories.get(i).getIdCat());
                    sousCategorieDto.setLibelleCat(lc.get(j).getLibelleCat());
                    try {
                        sousCategorieDto.setIdCat(lc.get(j).getIdCat());
                    } catch (Exception e) {
                        sousCategorieDto.setIdCat((long) 0);
                    }
                    categorieDtos.add(sousCategorieDto);
                    Set<Categorie> ctt = lc.get(j).getScats();
                    List<Categorie> lct = new ArrayList<Categorie>(ctt);
                    if (lct.size() > 0) {
                        for (int n = 0; n < lct.size(); n++) {
                            sousCategorieDto = new CategorieDto();
                            sousCategorieDto.setIdSCat(lc.get(j).getIdCat());
                            sousCategorieDto.setLibelleCat(lct.get(n).getLibelleCat());
                            try {
                                sousCategorieDto.setIdCat(lct.get(n).getIdCat());
                            } catch (Exception e) {
                                sousCategorieDto.setIdCat((long) 0);
                            }
                            categorieDtos.add(sousCategorieDto);
                        }
                    }
                }

            } else {
                CategorieDto sousCategorieDto = new CategorieDto();
                sousCategorieDto.setIdCat(sousCategories.get(i).getIdCat());
                sousCategorieDto.setLibelleCat(sousCategories.get(i).getLibelleCat());
                sousCategorieDto.setIdSCat((long) 0);
                categorieDtos.add(sousCategorieDto);

            }

        }
        categorieDtos.sort(Comparator.comparing(CategorieDto::getIdCat));
        return categorieDtos;
    }

    @GetMapping("/getIntervenant/{id}")
    @ResponseBody
    public List<AgentDto> getIntervenant(@PathVariable long id) {
        Optional<Categorie> categorie = categorieRepository.findById(id);
        List<FichePoste> fichePostes = fichePosteRepository.findByCatFich(categorie.get());
        List<AgentDto> agentDtos = new ArrayList<>();
        for (int i = 0; i < fichePostes.size(); i++) {
            AgentDto agentDto = new AgentDto();
            agentDto.setIdAgent(fichePostes.get(i).getAgentFich().getIdAgent());
            agentDto.setNumMatAgent(fichePostes.get(i).getAgentFich().getNumMatAgent());
            agentDto.setNomAgent(fichePostes.get(i).getAgentFich().getNomAgent());
            agentDto.setPrenomAgent(fichePostes.get(i).getAgentFich().getPrenomAgent());
            agentDtos.add(agentDto);
        }
        return agentDtos;
    }

    @PostMapping("/addPrblm")
    public String createPrblm(@Valid @ModelAttribute("problemtForm") Probleme probleme,
            @Valid @ModelAttribute("intervenant") long intervenant,
            @Valid @ModelAttribute("idProbMer") long idProbMer, BindingResult result,
            ModelMap model, @RequestParam("file") MultipartFile file) {
        SecurityContext context = SecurityContextHolder.getContext();
        Authentication authentication = context.getAuthentication();
        if (result.hasErrors()) {
            model.addAttribute("problemtForm", probleme);
        } else {
            Probleme problemeAdd = probleme;
            if (idProbMer == 0) {
                try {
                    problemeAdd = probleme;
                    problemeAdd.setDateEnvProb(new Date());
                    Optional<Agent> agent = agentRepository
                            .findByUsername(((UserDetails) authentication.getPrincipal()).getUsername());
                    problemeAdd.setAgentProb(agent.get());
                    Optional<Statut> statut = statusrepository.findById((long) 1);
                    problemeAdd.setStatut(statut.get());

                    model.addAttribute("problemtForm", new Probleme());
                } catch (Exception e) {
                    model.addAttribute("formErrorMessage", e.getMessage());
                }
            } else {
                Optional<Probleme> problemeOptional = problemeRepository.findById(idProbMer);
                problemeAdd = probleme;
                problemeAdd.setPriorite(problemeOptional.get().getPriorite());
                problemeAdd.setAgentProb(problemeOptional.get().getAgentProb());
                problemeAdd.setDateEnvProb(new Date());
                problemeAdd.setConfidentialiteProb(problemeOptional.get().isConfidentialiteProb());
                Optional<Statut> statut = statusrepository.findById((long) 1);
                problemeAdd.setStatut(statut.get());
                problemeAdd.setDescriptionProb(problemeOptional.get().getDescriptionProb());
                problemeAdd.setProb(problemeOptional.get());
                problemeAdd.setLibelleProb("Probleme transferet : " + problemeOptional.get().getLibelleProb());
                problemeAdd.setPieceJointeProb(problemeOptional.get().getPieceJointeProb());
                problemeAdd.setProbCat(problemeOptional.get().getProbCat());

                Optional<Statut> statutTransfer = statusrepository.findById((long) 4);
                problemeOptional.get().setStatut(statutTransfer.get());
                problemeRepository.save(problemeOptional.get());

            }
            Probleme saveProb = problemeRepository.save(problemeAdd);

            try {
                problemeService.uploadImage(file, saveProb);
            } catch (IOException e) {
                System.out.println(e.toString());
            }
            Intervenant interv = new Intervenant();
            Optional<Agent> agentOptional = agentRepository.findById(intervenant);
            interv.setAgentInt(agentOptional.get());
            interv.setProbInt(saveProb);
            intervenantRepository.save(interv);
        }

        return "redirect:/problemView/" + ((UserDetails) authentication.getPrincipal()).getUsername();
    }

    @RequestMapping(value = "/viewPblm/{id}")
    public String getViewPrblmForm(Model model, @PathVariable(name = "id") long id) {
        Optional<Probleme> problemToView = problemeRepository.findById(id);
        try {
            if (problemToView.get().getStatut().getIdStatut() == 1) {
                Optional<Statut> statut = statusrepository.findById((long) 2);
                problemToView.get().setStatut(statut.get());
                problemeRepository.save(problemToView.get());
            }
        } catch (Exception e) {
        }
        List<Reponse> reponseView = reponseRepository.findByProblemeRep(problemToView.get());

        model.addAttribute("pblmView", problemToView.get());
        model.addAttribute("response", reponseView);
        model.addAttribute("orgPrblm", problemToView.get().getAgentProb());
        model.addAttribute("reponseForm", new Reponse());
        try {
            model.addAttribute("lasteResponse", reponseView.get(reponseView.size() - 1));
        } catch (Exception e) {
            model.addAttribute("lasteResponse", new Reponse());
        }
        return "Dispatch/DetailMessageDispatch";
    }

    @PostMapping("/addRep")
    public String addRep(@Valid @ModelAttribute("reponseForm") Reponse reponse,
            @Valid @ModelAttribute("idProb") long idProb, BindingResult result,
            ModelMap mode, @RequestParam("file") MultipartFile file) {

        SecurityContext context = SecurityContextHolder.getContext();
        Authentication authentication = context.getAuthentication();
        Optional<Agent> agent = agentRepository
                .findByUsername(((UserDetails) authentication.getPrincipal()).getUsername());
        Optional<Probleme> prb = problemeRepository.findById(idProb);

        reponse.setDateEnvRep(new Date());
        reponse.setProblemeRep(prb.get());
        reponse.setAgentRep(agent.get());
        Reponse reponseSave = reponseRepository.save(reponse);

        try {
            reponseService.uploadImage(file, reponseSave);
        } catch (IOException e) {
            System.out.println("--------------------------------------------------------");
            System.out.println(e.toString());
            System.out.println("--------------------------------------------------------");
        }

        return "redirect:/viewPblm/" + prb.get().getIdProb();
    }

    @RequestMapping("/download/{idReponse}")
    // public ResponseEntity<byte[]> downloadImage(@PathVariable long idReponse) {
    public ResponseEntity<String> downloadImage(@PathVariable long idReponse) {
        Optional<Reponse> reponse = reponseRepository.findById(idReponse);
        if (!StringUtils.isEmpty(reponse.get().getFileName())) {
            /*
             * byte[] image = reponseService.downloadImage(reponse.get().getFileName());
             * return
             * ResponseEntity.status(HttpStatus.OK).contentType(MediaType.valueOf(reponse.
             * get().getFileType()))
             * .body(image);
             */
            return ResponseEntity.ok(reponse.get().getFileName());
        }
        return null;
    }

    @RequestMapping("/resolveprbm/{idProb}")
    public String addRep(@PathVariable long idProb) {
        Optional<Probleme> prb = problemeRepository.findById(idProb);
        Optional<Statut> statut = statusrepository.findById((long) 3);
        Probleme probleme = prb.get();
        probleme.setStatut(statut.get());
        try {
            probleme.getProb().setStatut(statut.get());
        } catch (Exception e) {
        }
        Probleme p = problemeRepository.save(probleme);
        return "redirect:/viewPblm/" + idProb;
    }

    @RequestMapping("/tranferer/{idAgent}/{idPrb}")
    public String tranferer(@PathVariable long idAgent, @PathVariable long idPrb) {
        SecurityContext context = SecurityContextHolder.getContext();
        Authentication authentication = context.getAuthentication();
        Optional<Agent> agentOrg = agentRepository
                .findByUsername(((UserDetails) authentication.getPrincipal()).getUsername());
        Optional<Probleme> probleme = problemeRepository.findById(idPrb);
        try {
            // String interTemp = probleme.get().getIntervenant();
            // String[] interSplit = interTemp.split(",");
            // if (interSplit.length > 0) {
            // String temp = "";
            // for (int i = 0; i < interSplit.length; i++) {
            // Optional<Agent> agentOptional = agentRepository.findById(interSplit[i]);
            // if (agentOrg.get() == agentOptional.get()) {
            // temp = temp + idAgent;
            // } else {
            // temp = temp + agentOptional.get().getNumMatAgent();
            // }
            // temp = temp + ",";
            // }
            // probleme.get().setIntervenant(temp.substring(0, temp.length() - 1));
            // problemeRepository.save(probleme.get());
            // }
        } catch (Exception e) {
        }
        return "redirect:/problemRecep/" + ((UserDetails) authentication.getPrincipal()).getUsername();
    }

    @RequestMapping(value = "/fichPoste")
    public String fichePoste(Model model) {
        model.addAttribute("agentList", agentService.getAllAgentsWithFiche());
        model.addAttribute("categList", categorieRepository.findAllCategories());
        model.addAttribute("ficheList", fichePosteRepository.findAll());
        return "Dispatch/GererFichePosteDispatch";
    }

    @RequestMapping("/getAgent/{idAgent}")
    public ResponseEntity<FichePosteDto> getAgent(@PathVariable long idAgent) {
        Optional<Agent> agentOrg = agentRepository.findById(idAgent);
        FichePoste fichePoste = fichePosteRepository.findByAgentFich(agentOrg.get());
        FichePosteDto fichePosteDto = new FichePosteDto();
        try {
            fichePosteDto.setIdFich(fichePoste.getIdFich());
        } catch (Exception e) {
        }
        fichePosteDto.setNameAgent(agentOrg.get().getNomAgent() + ' ' + agentOrg.get().getPrenomAgent());
        try {
            fichePosteDto.setFonction(fichePoste.getPosteFich().getFonctionPoste());
        } catch (Exception e) {
        }
        try {
            fichePosteDto.setHierarchie(fichePoste.getPosteFich().getHierarchiePoste().getLibelleHier());
        } catch (Exception e) {
        }
        try {
            fichePosteDto.setNumMatr(agentOrg.get().getNumMatAgent());
        } catch (Exception e) {
        }
        return ResponseEntity.ok(fichePosteDto);
    }

    @RequestMapping(value = "/saveFiche/{idCat}/{idFiche}")
    public String saveFiche(@PathVariable long idCat, @PathVariable long idFiche) {
        Optional<Categorie> categorie = categorieRepository.findById(idCat);
        Optional<FichePoste> fichePoste = fichePosteRepository.findById(idFiche);
        fichePoste.get().setCatFich(categorie.get());
        fichePosteRepository.save(fichePoste.get());
        return "redirect:/fichPoste";
    }

    @RequestMapping(value = "/getPrblm/{idPrb}")
    public ResponseEntity<ProblemeDto> getPrblm(@PathVariable long idPrb) {
        SimpleDateFormat formater = new SimpleDateFormat("dd/MM/yyyy 'à' hh:mm:ss");
        Optional<Probleme> problemeOptional = problemeRepository.findById(idPrb);
        Intervenant intervenant = intervenantRepository.findByProbInt(problemeOptional.get());
        List<Reponse> reponseView = reponseRepository.findByProblemeRep(problemeOptional.get());
        List<ReponseDto> reponseDtos = new ArrayList<>();
        for (int i = 0; i < reponseView.size(); i++) {
            ReponseDto reponseDto = new ReponseDto();
            reponseDto.setDateEnvRep(formater.format(reponseView.get(i).getDateEnvRep()));
            reponseDto.setDescriptionRep(reponseView.get(i).getDescriptionRep());
            reponseDto.setExpediteur(reponseView.get(i).getAgentRep().getNomAgent() + " "
                    + reponseView.get(i).getAgentRep().getPrenomAgent());
            reponseDtos.add(reponseDto);
        }

        ProblemeDto problemeDto = new ProblemeDto();
        problemeDto.setDateEnvProb(formater.format(problemeOptional.get().getDateEnvProb()));
        problemeDto.setStatut(problemeOptional.get().getStatut().getLibelleStatut());
        problemeDto.setLibelleProb(problemeOptional.get().getLibelleProb());
        problemeDto.setIntervenant(
                intervenant.getAgentInt().getNomAgent() + " " + intervenant.getAgentInt().getPrenomAgent());
        problemeDto.setDescriptionProb(problemeOptional.get().getDescriptionProb());
        problemeDto.setReponseDtos(reponseDtos);

        return ResponseEntity.ok(problemeDto);
    }

}
