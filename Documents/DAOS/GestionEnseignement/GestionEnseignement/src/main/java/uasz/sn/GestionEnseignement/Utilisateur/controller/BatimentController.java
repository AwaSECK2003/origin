package uasz.sn.GestionEnseignement.Utilisateur.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import uasz.sn.GestionEnseignement.Utilisateur.modele.Batiment;
import uasz.sn.GestionEnseignement.Utilisateur.modele.EC;
import uasz.sn.GestionEnseignement.Utilisateur.service.BatimentService;

import java.nio.file.Path;
import java.util.List;

@Controller
@RequestMapping("/batiment")
public class BatimentController {

    @Autowired
    private BatimentService batimentService;

    // Afficher la liste de tous les bâtiments
    @GetMapping
    public String afficherTousLesBatiments(Model model) {
        List<Batiment> batiments = batimentService.afficherTousLesBâtiments();
        model.addAttribute("batiments", batiments);
        return "batiment"; // Redirige vers la vue de la liste des bâtiments
    }

    // Ajouter un nouveau bâtiment
    @PostMapping("/ajouter")
    public String ajouterBatiment(@ModelAttribute Batiment batiment) {
        batimentService.ajouterBatiment(batiment);
        return "redirect:/batiment";
    }

    // Modifier un bâtiment
    @PostMapping("/modifier/{id}")
    public String modifierBatiment(@PathVariable("id") Long id, @ModelAttribute Batiment batiment) {
        batimentService.modifierBatiment(id, batiment);
        return "redirect:/batiment";
    }

    // Supprimer un bâtiment
    @PostMapping("/supprimer/{id}")
    public String supprimerBatiment(@PathVariable("id") Long id) {
        batimentService.supprimerBatiment(id);
        return "redirect:/batiment";
    }

    // Rechercher un bâtiment par son ID
    @GetMapping("/rechercher/{id}")
    public String rechercherBatiment(@PathVariable("id") Long id, Model model) {
        Batiment batiment = batimentService.obtenirBatimentParId(id);
        model.addAttribute("batiment", batiment);
        return "batiment/details";
    }
}
