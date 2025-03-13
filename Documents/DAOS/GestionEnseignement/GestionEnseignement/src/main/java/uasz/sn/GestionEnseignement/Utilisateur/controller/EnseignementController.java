package uasz.sn.GestionEnseignement.Utilisateur.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import uasz.sn.GestionEnseignement.Utilisateur.modele.*;
import uasz.sn.GestionEnseignement.Utilisateur.repository.MaquetteRepository;
import uasz.sn.GestionEnseignement.Utilisateur.service.*;

import java.util.List;
import java.util.Optional;

@Controller
public class EnseignementController {

    @Autowired
    private EnseignementService enseignementService;

    @Autowired
    private EnseignantService enseignantsService;

    @Autowired
    private UEService ueService;

    @Autowired
    private ECService ecService;

    @Autowired
    private ClasseService classeService;

    @Autowired
    private SemestreService semestreService;

    @Autowired
    private MaquetteService maquetteService;

    @Autowired
    private MaquetteRepository maquetteRepository;

    // Afficher la liste de tous les enseignements
    @GetMapping("/enseignements")
    public String listerEnseignements(Model model) {
        List<Enseignement> enseignements = enseignementService.getAllEnseignements();
        model.addAttribute("enseignements", enseignements);

        List<Maquette> maquettes = maquetteService.obtenirMaquettesNonArchivees();
        model.addAttribute("maquettes", maquettes);

        List<Semestre> semestres = semestreService.getAllSemestres();
        model.addAttribute("semestres", semestres);

        List<Classe> classes = classeService.afficherClassesNonArchivees();
        model.addAttribute("classes", classes);

        List<EC> ecs = ecService.afficherToutEC();
        model.addAttribute("ecs", ecs);

        // Ajouter les types d'enseignement pour la sélection dans la vue
        model.addAttribute("typesEnseignement", TypeEnseignement.values());

        return "enseignement_maquette";
    }

    // Ajouter un nouvel enseignement
    @PostMapping("/enseignements/ajouter")
    public String ajouterEnseignement(@ModelAttribute Enseignement enseignement) {
        enseignementService.ajouterEnseignement(enseignement);
        return "redirect:/enseignements";
    }

    // Modifier un enseignement
    @PostMapping("/enseignements/modifier")
    public String modifierEnseignement(@ModelAttribute Enseignement enseignement) {
        enseignementService.modifierEnseignement(enseignement.getId(), enseignement);
        return "redirect:/enseignements";
    }

    // Supprimer un enseignement
    @PostMapping("/enseignements/supprimer")
    public String supprimerEnseignement(@RequestParam("id") Long id) {
        enseignementService.supprimerEnseignement(id);
        return "redirect:/enseignements"; // Rediriger vers la liste des enseignements après suppression
    }

    // Rechercher un enseignement par son ID
    @GetMapping("/enseignements/rechercher")
    public String rechercherEnseignement(@RequestParam("id") Long id, Model model) {
        Optional<Enseignement> enseignement = enseignementService.getEnseignementById(id);
        if (enseignement.isPresent()) {
            model.addAttribute("enseignement", enseignement.get());
            return "enseignements"; // Vue pour afficher l'enseignement trouvé
        } else {
            model.addAttribute("message", "Enseignement non trouvé");
            return "enseignements"; // Vue avec message si enseignement non trouvé
        }
    }
}