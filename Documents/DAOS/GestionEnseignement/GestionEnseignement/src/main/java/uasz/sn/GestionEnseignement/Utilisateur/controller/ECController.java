package uasz.sn.GestionEnseignement.Utilisateur.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import uasz.sn.GestionEnseignement.Utilisateur.modele.EC;
import uasz.sn.GestionEnseignement.Utilisateur.modele.UE;
import uasz.sn.GestionEnseignement.Utilisateur.service.ECService;
import uasz.sn.GestionEnseignement.Utilisateur.service.UEService;

import java.util.List;
import java.util.Optional;

@Controller
public class ECController {

    @Autowired
    private ECService ecService;
    @Autowired
    private UEService ueService;


    @RequestMapping(value = "/ajouter_ec_ue", method = RequestMethod.POST)
    public String ajouter_ec_ue(Model model, EC ec) {
        // Validation ou initialisation de l'association UE <-> EC
        if (ec.getUe() != null && ec.getUe().getId() != null) {
            UE ue = ueService.getUEById(ec.getUe().getId()); // Service pour récupérer l'UE
            ec.setUe(ue); // Associer l'UE à l'EC
        }
        ecService.ajouterEC(ec); // Ajouter l'EC
        return "redirect:/details_ue?id=" + ec.getUe().getId(); // Rediriger vers les détails de l'UE
    }


    @RequestMapping(value = "/modifier_ec_ue", method = RequestMethod.POST)
    public String modifier_ec_ue(Model model, EC ec){
        ecService.modifierEC(ec);
        return "redirect:/details_ue?id="+ec.getUe().getId();
    }

    @RequestMapping(value = "/supprimer_ec_ue", method = RequestMethod.POST)
    public String supprimer_ec_ue(@RequestParam("id") Long ecId, Model model) {
        Optional<EC> optionalEC = ecService.getECById(ecId);
        if (optionalEC.isPresent()) {
            EC ec = optionalEC.get();
            if (ec.getUe() != null) {
                UE ueAssociee = ueService.getUEById(ec.getUe().getId());
                ecService.supprimerEC(ec);
                return "redirect:/details_ue?id=" + ueAssociee.getId(); // Rediriger vers la page de l'UE
            } else {
                model.addAttribute("message", "L'EC n'est pas associé à une UE valide !");
            }
        } else {
            model.addAttribute("message", "EC non trouvé !");
        }
        return "redirect:/ue"; // Si EC non trouvé, redirige vers la liste des UEs
    }

    @RequestMapping(value = "/archiver_ec", method = RequestMethod.POST)
    public String archiverEC(@RequestParam("id") Long ecId) {
        ecService.archiverEC(ecId);
        return "redirect:/details_ec?id=" + ecId; // Rediriger vers la page de l'EC
    }

    @RequestMapping(value = "/desarchiver_ec", method = RequestMethod.POST)
    public String desarchiverEC(@RequestParam("id") Long ecId) {
        ecService.desarchiverEC(ecId);
        return "redirect:/details_ec?id=" + ecId;
    }

    @RequestMapping(value = "/activer_ec", method = RequestMethod.POST)
    public String activerEC(@RequestParam("id") Long ecId) {
        ecService.activerEC(ecId);
        return "redirect:/details_ec?id=" + ecId;
    }

    @RequestMapping(value = "/desactiver_ec", method = RequestMethod.POST)
    public String desactiverEC(@RequestParam("id") Long ecId) {
        ecService.desactiverEC(ecId);
        return "redirect:/details_ec?id=" + ecId;
    }



}
