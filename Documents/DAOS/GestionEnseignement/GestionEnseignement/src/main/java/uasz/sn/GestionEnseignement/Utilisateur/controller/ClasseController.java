package uasz.sn.GestionEnseignement.Utilisateur.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import uasz.sn.GestionEnseignement.Utilisateur.modele.Batiment;
import uasz.sn.GestionEnseignement.Utilisateur.modele.Classe;
import uasz.sn.GestionEnseignement.Utilisateur.modele.Formation;
import uasz.sn.GestionEnseignement.Utilisateur.service.BatimentService;
import uasz.sn.GestionEnseignement.Utilisateur.service.ClasseService;
import uasz.sn.GestionEnseignement.Utilisateur.service.FormationService;

import java.util.List;

@Controller
@RequestMapping("/classe")
public class ClasseController {

    @Autowired
    private ClasseService classeService;

    @Autowired
    private FormationService formationService;
    @Autowired
    private BatimentService batimentService;

    // Afficher toutes les classes
    @GetMapping
    public String afficherToutesClasses(Model model) {
        // Récupérer toutes les classes
        List<Classe> classes = classeService.afficherClassesNonArchivees();
        model.addAttribute("listeDesClasses", classes);
        // Récupérer la liste des formations pour le formulaire d'ajout
        List<Formation> listeDesFormation = formationService.findAll();
        model.addAttribute("listeDesFormation", listeDesFormation);

        // Créer un objet classe vide pour le formulaire
        model.addAttribute("classe", new Classe());

        return "classe"; // Nom de la vue (JSP ou Thymeleaf)
    }



    @PostMapping("/ajouter")
    public String ajouterClasse(@RequestParam String formationCode,@ModelAttribute Classe classe) {
        // Récupérer la formation par son code
        Formation formation = formationService.rechercherFormationParCode(formationCode);
        if (formation != null) {
            // Associer la formation et le bâtiment à la classe
            classe.setFormation(formation);
            classeService.ajouterClasse(formation.getId(), classe);
        }

        return "redirect:/classe";
    }

    @PostMapping("/modifier")
    public String modifierClasse(@ModelAttribute Classe classe) {
        try {
            classeService.modifierClasse(classe.getId(), classe);
        } catch (RuntimeException e) {
            return "redirect:/classe?erreur=ClasseNonTrouvee";
        }
        return "redirect:/classe";
    }


    // Archiver une classe
    @PostMapping("/archiver")
    public String archiverClasse(@RequestParam Long id) {
        classeService.archiverClasse(id);
        return "redirect:/classe"; // Redirige vers la page principale des classes
    }

    // Rechercher une classe par son nom
    @GetMapping("/rechercher")
    public String rechercherClasse(@RequestParam String nom, Model model) {
        Classe classe = classeService.rechercherClasseParNom(nom);
        model.addAttribute("classe", classe);
        return "classe"; // La même vue affichant la classe recherchée
    }

    @PostMapping("/supprimer")
    public String supprimerClasse(@RequestParam Long id) {
        classeService.supprimerClasse(id);
        return "redirect:/classe";
    }
}
