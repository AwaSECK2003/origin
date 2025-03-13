package uasz.sn.GestionEnseignement.Utilisateur.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import uasz.sn.GestionEnseignement.Utilisateur.modele.EC;
import uasz.sn.GestionEnseignement.Utilisateur.modele.Formation;
import uasz.sn.GestionEnseignement.Utilisateur.modele.UE;
import uasz.sn.GestionEnseignement.Utilisateur.service.FormationService;
import uasz.sn.GestionEnseignement.Utilisateur.service.SemestreService;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/formation")
public class FormationController {

    @Autowired
    private FormationService formationService;
    @Autowired
    private SemestreService semestreService;

    @GetMapping
    public String afficherToutesFormations(Model model) {
        model.addAttribute("listeDesFormation", formationService.afficherToutesFormations());
        return "formation";
    }

    @PostMapping("/ajouter")
    public String ajouterFormation(@ModelAttribute Formation formation) {
        formationService.ajouterFormation(formation);
        return "redirect:/formation";
    }

    @PostMapping("/modifier")
    public String modifierFormation(@ModelAttribute Formation formation) {
        formationService.modifierFormation(formation);
        return "redirect:/formation";
    }

    @PostMapping("/archiver")
    public String archiverFormation(@RequestParam Long id) {
        formationService.archiverFormation(id);
        return "redirect:/formation";
    }

    @GetMapping("/{id}")
    public String afficherDetailsFormation(@PathVariable("id") Long id, Model model) {
        // Récupérer la formation par son ID
        Formation formation = formationService.findById(id);
        if (formation != null) {
            model.addAttribute("formation", formation);

            // Liste des semestres (1 à 10)
            List<List<UE>> listUEs = new ArrayList<>();
            for (long semestre = 1; semestre <= 10; semestre++) {
                listUEs.add(semestreService.getUEsBySemestre(semestre));
            }

            // Ajouter les UEs pour chaque semestre au modèle
            for (int i = 0; i < listUEs.size(); i++) {
                model.addAttribute("semestre" + (i + 1) + "UEs", listUEs.get(i));
            }

            // Récupérer et ajouter les ECs pour chaque semestre
            for (int i = 0; i < listUEs.size(); i++) {
                List<EC> semestreECs = new ArrayList<>();
                for (UE ue : listUEs.get(i)) {
                    semestreECs.addAll(semestreService.getECsByUE(ue.getId())); // Récupérer les ECs pour chaque UE
                }
                model.addAttribute("semestre" + (i + 1) + "ECs", semestreECs);
            }

            // Retourner la vue pour afficher les détails de la formation
            return "maquetteVrai";
        }

        // Si la formation n'est pas trouvée, rediriger vers la page de liste des formations
        return "redirect:/formation";
    }


    // Pour Afficher les UES et ECS dans la maquette

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
