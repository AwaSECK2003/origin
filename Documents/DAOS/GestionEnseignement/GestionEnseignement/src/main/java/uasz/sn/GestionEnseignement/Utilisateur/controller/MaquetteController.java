package uasz.sn.GestionEnseignement.Utilisateur.controller;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import uasz.sn.GestionEnseignement.Utilisateur.modele.*;
import uasz.sn.GestionEnseignement.Utilisateur.repository.FormationRepository;
import uasz.sn.GestionEnseignement.Utilisateur.repository.SemestreRepository;
import uasz.sn.GestionEnseignement.Utilisateur.service.*;

import java.util.*;

@Controller
@RequestMapping("/maquette")
public class MaquetteController {

    @Autowired
    private MaquetteService maquetteService;

    @Autowired
    private ClasseService classeService;

    @Autowired
    SemestreRepository semestreRepository;
    @Autowired
    private FormationService formationService;

    @Autowired
    FormationRepository formationRepository;
    @Autowired
    private UEService ueService;
    @Autowired
    private SemestreService semestreService;
    @Autowired
    private ECService ecService;



    @GetMapping
    public String afficherToutesLesMaquettes(Model model) {
        List<Maquette> maquettes = maquetteService.obtenirMaquettesNonArchivees();
        model.addAttribute("maquettes", maquettes);
        List<Classe> listeDesClasse = classeService.afficherClassesNonArchivees();
        model.addAttribute("listeDesClasse", listeDesClasse);
        List<Semestre> semestres =semestreService.getAllSemestres();
        model.addAttribute("semestres", semestres);

        // Créer un objet maquette vide pour le formulaire
        model.addAttribute("maquette", new Maquette());

        return "maquette";
    }



    @RequestMapping(value = "/ajouter", method = RequestMethod.POST)
    public String ajouterMaquette(@ModelAttribute Maquette maquette, @RequestParam Long classeId, @RequestParam Long semestreId, Model model) {
        // Récupérer la classe
        Classe classe = classeService.getClasseById(classeId);
        if (classe == null) {
            model.addAttribute("message", "Classe non trouvée !");
            return "redirect:/maquette"; // Redirige avec un message d'erreur
        }
        // Récupérer le semestre
        Semestre semestre = semestreService.getSemestreById(semestreId)
                .orElseThrow(() -> new RuntimeException("Semestre non trouvé"));
        // Associer la classe et le semestre à la maquette
        maquette.setClasse(classe);
        maquette.setSemestre(semestre);
        // Ajouter la maquette
        maquetteService.ajouterMaquette(maquette);
        return "redirect:/maquette";
    }

    @PostMapping("/modifier")
    public String modifierMaquette(@ModelAttribute Maquette maquette,@RequestParam Long classeId, @RequestParam Long semestreId, Model model) {
        Classe classe = classeService.getClasseById(classeId);
        if (classe == null) {
            model.addAttribute("message", "Classe non trouvée !");
            return "redirect:/maquette"; // Redirige avec un message d'erreur
        }

        Semestre semestre = semestreService.getSemestreById(semestreId)
                .orElseThrow(() -> new RuntimeException("Semestre non trouvé"));

        maquette.setClasse(classe);
        maquette.setSemestre(semestre);
        maquetteService.modifierMaquette(maquette.getId(), maquette);
        return "redirect:/maquette";
    }


    // Archiver une maquette
    @PostMapping("/archiver")
    public String archiverMaquette(@RequestParam Long id, Model model) {
        maquetteService.archiverMaquette(id);
        model.addAttribute("message", "Maquette archivée avec succès");
        return "redirect:/maquette";
    }

    // Désarchiver une maquette
    @PostMapping("/desarchiver")
    public String desarchiverMaquette(@RequestParam Long id, Model model) {
        maquetteService.desarchiverMaquette(id);
        model.addAttribute("message", "Maquette désarchivée avec succès");
        return "redirect:/maquette";
    }

    // Activer une maquette
    @PostMapping("/activer")
    public String activerMaquette(@RequestParam Long id, Model model) {
        maquetteService.activerMaquette(id);
        model.addAttribute("message", "Maquette activée avec succès");
        return "redirect:/maquette";
    }

    // Désactiver une maquette
    @PostMapping("/desactiver")
    public String desactiverMaquette(@RequestParam Long id, Model model) {
        maquetteService.desactiverMaquette(id);
        model.addAttribute("message", "Maquette désactivée avec succès");
        return "redirect:/maquette";
    }

    @GetMapping(value = "/details_maquette")
    public String afficherUesParClasseEtSemestre(@RequestParam Long classeId, @RequestParam Long semestreId, Model model) {
        // Récupérer la classe par son ID
        Optional<Classe> classeOpt = classeService.rechercherClasseParId(classeId);
        if (classeOpt.isEmpty()) {
            model.addAttribute("message", "Classe non trouvée !");
            return "error"; // Afficher une erreur si la classe n'est pas trouvée
        }
        Classe classe = classeOpt.get();

        // Récupérer le semestre par son ID
        Optional<Semestre> semestreOpt = semestreService.getSemestreById(semestreId);
        if (semestreOpt.isEmpty()) {
            model.addAttribute("message", "Semestre non trouvé !");
            return "error"; // Afficher une erreur si le semestre n'est pas trouvé
        }
        Semestre semestre = semestreOpt.get();

        // Récupérer les UEs associées à la classe et au semestre
        List<UE> ues = ueService.afficherUesParClasseEtSemestre(classe, semestre);
        model.addAttribute("listeDesUEs", ues);
        model.addAttribute("classe", classe);
        model.addAttribute("semestre", semestre);

        return "maquettes_ues";
    }

    @GetMapping("/ues/{ueId}/ecs")
    public String afficherEcsParUe(@PathVariable Long ueId, Model model) {
        UE ue = ueService.getUEById(ueId);
        if (ue == null) {
            model.addAttribute("message", "UE non trouvée !");
            return "error"; // Affiche une erreur si l'UE n'est pas trouvée
        }
        List<EC> ecs = ueService.afficherLesECsParUE(ue);
        model.addAttribute("ecs", ecs);
        model.addAttribute("ue", ue);
        return "maquettes_ecs";
    }

    @GetMapping("/details_ues_ecs")
    public String afficherUesEtEcsParClasseEtSemestre(@RequestParam Long classeId,
                                                      @RequestParam Long semestreId,
                                                      Model model) {
        // Vérifier si la classe et le semestre existent
        Optional<Classe> classeOpt = classeService.rechercherClasseParId(classeId);
        Optional<Semestre> semestreOpt = semestreService.getSemestreById(semestreId);


        if (classeOpt.isEmpty() || semestreOpt.isEmpty()) {
            model.addAttribute("message", "Classe ou semestre non trouvé !");
            return "error"; // Affichage d'une page d'erreur si la classe ou le semestre n'existe pas
        }

        // Récupérer les UEs associées à la classe et au semestre
        UeEcWrapper ueEcWrapper = maquetteService.getUesAndEcsForClasseAndSemestre(classeId, semestreId);


        if (ueEcWrapper.getUes().isEmpty()) {
            // Si aucune UE n'est trouvée, rediriger vers la vue 'aucune_maquette'
            model.addAttribute("classe", classeOpt.get());
            model.addAttribute("semestre", semestreOpt.get());
            return "aucune_maquette";
        }

        // Ajouter les UEs et ECs associées au modèle
        model.addAttribute("listeDesUEs", ueEcWrapper.getUes());
        model.addAttribute("listeDesECs", ueEcWrapper.getEcs()); // getEcs() génère automatiquement la liste des ECs
        model.addAttribute("classe", classeOpt.get());
        model.addAttribute("semestre", semestreOpt.get());

        // Retourner la vue correspondante
        return "maquettes_ues_ecs";
    }


}

