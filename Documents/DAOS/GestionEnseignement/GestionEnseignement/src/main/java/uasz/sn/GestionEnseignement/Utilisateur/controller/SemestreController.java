package uasz.sn.GestionEnseignement.Utilisateur.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import uasz.sn.GestionEnseignement.Utilisateur.modele.EC;
import uasz.sn.GestionEnseignement.Utilisateur.modele.Semestre;
import uasz.sn.GestionEnseignement.Utilisateur.modele.UE;
import uasz.sn.GestionEnseignement.Utilisateur.service.SemestreService;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/semestres")
public class SemestreController {

    @Autowired
    private SemestreService semestreService;

    // Afficher tous les semestres
    @GetMapping
    public String getAllSemestres(Model model) {
        List<Semestre> semestres = semestreService.getAllSemestres();
        model.addAttribute("semestres", semestres);
        return "semestres"; // Vue pour afficher la liste des semestres
    }

    // Obtenir un semestre par ID
    @GetMapping("/{id}")
    public String getSemestreById(@PathVariable Long id, Model model) {
        Optional<Semestre> semestre = semestreService.getSemestreById(id);
        if (semestre.isPresent()) {
            model.addAttribute("semestre", semestre.get());
            return "semestreDetails"; // Vue pour afficher les détails d'un semestre
        } else {
            model.addAttribute("error", "Semestre introuvable");
            return "error"; // Vue pour afficher une erreur
        }
    }

    // Ajouter un nouveau semestre
    @PostMapping("/ajouter")
    public String ajouterSemestre(@ModelAttribute Semestre semestre, Model model) {
        Semestre nouveauSemestre = semestreService.ajouterSemestre(semestre);
        model.addAttribute("semestre", nouveauSemestre);
        return "redirect:/semestres"; // Redirection vers la liste des semestres
    }

    // Modifier un semestre
    @PostMapping("/modifier/{id}")
    public String modifierSemestre(@ModelAttribute Semestre semestre, Model model) {
        try {
            // Appel à la méthode de service pour mettre à jour le semestre
            semestreService.modifierSemestre(semestre);
            return "redirect:/semestres";
        } catch (Exception e) {
            model.addAttribute("error", "Erreur lors de la modification du semestre");
            return "error";
        }
    }


    // Supprimer un semestre
    @GetMapping("/supprimer/{id}")
    public String supprimerSemestre(@PathVariable Long id, Model model) {
        try {
            semestreService.supprimerSemestre(id);
            return "redirect:/semestres"; // Redirection vers la liste des semestres après suppression
        } catch (Exception e) {
            model.addAttribute("error", "Impossible de supprimer le semestre");
            return "error"; // Vue pour afficher une erreur
        }
    }

    // Obtenir les UE associées à un semestre
    @GetMapping("/{id}/ues")
    public String getUEsBySemestre(@PathVariable Long id, Model model) {
        List<UE> ues = semestreService.getUEsBySemestre(id);
        model.addAttribute("ues", ues);
        model.addAttribute("semestreId", id);
        return "ues"; // Vue pour afficher les UE associées
    }


    // Méthode pour obtenir les ECs d'une UE
    @GetMapping("/{semestreId}/ues/{ueId}/ecs")
    public String getECsByUE(@PathVariable String semestreId, @PathVariable Long ueId, Model model) {
        // Récupérer les ECs pour l'UE spécifiée
        List<EC> ecs = semestreService.getECsByUE(ueId);

        // Ajouter les ECs au modèle
        model.addAttribute("ecs", ecs);
        model.addAttribute("ueId", ueId);
        model.addAttribute("semestreId", semestreId);

        // Retourner la vue pour afficher les ECs
        return "ecs"; // Vue pour afficher les ECs de l'UE
    }


}
