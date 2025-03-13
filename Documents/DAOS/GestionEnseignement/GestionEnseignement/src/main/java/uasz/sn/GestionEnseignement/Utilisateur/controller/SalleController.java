package uasz.sn.GestionEnseignement.Utilisateur.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import uasz.sn.GestionEnseignement.Utilisateur.modele.Batiment;
import uasz.sn.GestionEnseignement.Utilisateur.modele.Salle;
import uasz.sn.GestionEnseignement.Utilisateur.service.BatimentService;
import uasz.sn.GestionEnseignement.Utilisateur.service.SalleService;

import java.util.List;

@Controller
@RequestMapping("/salles")
public class SalleController {
     @Autowired
     private SalleService salleService;

     @Autowired
     private BatimentService batimentService;

     @GetMapping
     public String afficherToutesLesSalles(Model model) {
         model.addAttribute("listeDesSalles", salleService.afficherToutesLesSalles());

         List<Batiment> batiments =batimentService.afficherTousLesBâtiments();
         model.addAttribute("batiments", batiments);
         return "salles"; // Nom de la vue Thymeleaf
     }

    @PostMapping("/ajouter")
    public String ajouterSalle(@ModelAttribute Salle salle) {
        salleService.ajouterSalle(salle);
        return "redirect:/salles";
    }

    @PostMapping("/modifier")
    public String modifierSalle(@ModelAttribute Salle salle) {
         salleService.modifierSalle(salle.getId(), salle);
         return "redirect:/salles";
     }
    @PostMapping("/supprimer")
    public String supprimerSalle(@RequestParam Long id) {
         salleService.supprimerSalle(id);
         return "redirect:/salles";
    }

}
