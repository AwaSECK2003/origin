package uasz.sn.GestionEnseignement.Utilisateur.controller;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import uasz.sn.GestionEnseignement.Utilisateur.modele.*;
import uasz.sn.GestionEnseignement.Utilisateur.repository.ClasseRepository;
import uasz.sn.GestionEnseignement.Utilisateur.service.*;

import java.util.List;
import java.util.Optional;

@Controller
@AllArgsConstructor
public class UEController {

    @Autowired
    private final UEService ueService;

    @Autowired
    private final MaquetteService maquetteService;

    @Autowired
    private final SemestreService semestreService;

    @Autowired
    private final ClasseService classeService;
    @Autowired
    private final ECService ecService;
    @Autowired
    private final ClasseRepository classeRepository;



    @RequestMapping(value = "/ue", method = RequestMethod.GET)
    public String listerUE(Model model) {
        List<UE> ueList = ueService.afficherToutUE();
        model.addAttribute("listeDesUE", ueList);
        /*List<Maquette> listeDesMaquette = maquetteService.obtenirMaquettesActives();
        model.addAttribute("listeDesMaquette", listeDesMaquette);
         */
        List<Semestre> semestres=semestreService.getAllSemestres();
        model.addAttribute("semestres", semestres);
        List<Classe> classes =classeService.afficherClassesNonArchivees();
        model.addAttribute("listeDesClasses",classes);
        // Créer un objet maquette vide pour le formulaire
        model.addAttribute("ue", new UE());

        return "ue";
    }


    @RequestMapping(value = "/ajouter_ue", method = RequestMethod.POST)
    public String ajouterUE(@ModelAttribute UE ue, @RequestParam Long semestreId, @RequestParam List<Long> classeIds, Model model) {
        Optional<Semestre> semestreOpt = semestreService.getSemestreById(semestreId);
        if (semestreOpt.isPresent()) {
            Semestre semestre = semestreOpt.get();
            ue.setSemestre(semestre); // Assurez-vous que vous associez bien le semestre
            List<Classe> classes = classeRepository.findAllById(classeIds);
            ue.setClasses(classes);
            ueService.ajouterUE(ue);
            model.addAttribute("message", "UE ajoutée avec succès");
        } else {
            model.addAttribute("message", "Semestre associé non trouvé !");
        }
        return "redirect:/ue";
    }

    @RequestMapping(value = "/modifier_ue", method = RequestMethod.POST)
    public String modifierUE(@ModelAttribute UE ue, @RequestParam Long semestreId, @RequestParam List<Long> classeIds, Model model) {
        Optional<Semestre> semestreOpt = semestreService.getSemestreById(semestreId);
        if (semestreOpt.isPresent()) {
            Semestre semestre = semestreOpt.get();
            ue.setSemestre(semestre);
            List<Classe> classes = classeRepository.findAllById(classeIds);
            ue.setClasses(classes);
            ueService.modifierUE(ue);
            model.addAttribute("message", "UE modifiée avec succès");
        } else {
            model.addAttribute("message", "Semestre associée non trouvée !");
        }
        return "redirect:/ue";
    }

    @RequestMapping(value = "/supprimer_ue", method = RequestMethod.POST)
    public String supprimerUE(@RequestParam Long id, Model model) {
        UE ue = ueService.getUEById(id);
        if (ue != null) {
            // Récupérer la liste des EC associés à cette UE
            List<EC> ecList = ueService.afficherLesEC(ue);
            // Supprimer les EC associés avant de supprimer l'UE
            if (ecList != null && !ecList.isEmpty()) {
                for (EC ec : ecList) {
                    // Supprimer l'EC de la base de données
                    ecService.supprimerEC(ec);
                }
            }
            // Supprimer l'UE
            ueService.supprimerUE(ue);
            model.addAttribute("message", "UE et ses EC supprimés avec succès");

        } else {
            model.addAttribute("message", "UE non trouvée !");
        }

        return "redirect:/ue";
    }

    @RequestMapping(value = "/details_ue", method = RequestMethod.GET)
    public String detailsUE(Model model, @RequestParam("id") Long id) {
        UE ue = ueService.afficherUE(id);

        if (ue != null && (ue.getEcs() == null || ue.getEcs().isEmpty())) {
            List<EC> ecList = ueService.afficherLesEC(ue); // Récupère les EC associés
            ue.setEcs(ecList); // Associe la liste des EC à l'UE
        }
        model.addAttribute("ue", ue); // Passe l'objet UE au modèle
        model.addAttribute("listeDesEC", ue.getEcs()); // Passe la liste des EC
        return "ue_details"; // Retourne la vue
    }


    @RequestMapping(value = "/archiver_ue", method = RequestMethod.POST)
    public String archiverUE(@RequestParam Long id, Model model) {
        UE ue = ueService.getUEById(id);
        if (ue != null) {
            ue.setEstArchivee(true);
            ueService.modifierUE(ue);
            model.addAttribute("message", "UE archivée avec succès");
        } else {
            model.addAttribute("message", "UE non trouvée !");
        }
        return "redirect:/ue";
    }

    @RequestMapping(value = "/desarchiver_ue", method = RequestMethod.POST)
    public String desarchiverUE(@RequestParam Long id, Model model) {
        UE ue = ueService.getUEById(id);
        if (ue != null) {
            ue.setEstArchivee(false);
            ueService.modifierUE(ue);
            model.addAttribute("message", "UE désarchivée avec succès");
        } else {
            model.addAttribute("message", "UE non trouvée !");
        }
        return "redirect:/ue";
    }

    @RequestMapping(value = "/activer_ue", method = RequestMethod.POST)
    public String activerUE(@RequestParam Long id, Model model) {
        UE ue = ueService.getUEById(id);
        if (ue != null) {
            ue.setActive(true);
            ueService.modifierUE(ue);
            model.addAttribute("message", "UE activée avec succès");
        } else {
            model.addAttribute("message", "UE non trouvée !");
        }
        return "redirect:/ue";
    }

    @RequestMapping(value = "/desactiver_ue", method = RequestMethod.POST)
    public String desactiverUE(@RequestParam Long id, Model model) {
        UE ue = ueService.getUEById(id);
        if (ue != null) {
            ue.setActive(false);
            ueService.modifierUE(ue);
            model.addAttribute("message", "UE désactivée avec succès");
        } else {
            model.addAttribute("message", "UE non trouvée !");
        }
        return "redirect:/ue";
    }

}
