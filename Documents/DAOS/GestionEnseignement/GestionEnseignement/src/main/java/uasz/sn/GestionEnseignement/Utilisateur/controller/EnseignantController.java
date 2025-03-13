package uasz.sn.GestionEnseignement.Utilisateur.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import uasz.sn.GestionEnseignement.Authentification.modele.Utilisateur;
import uasz.sn.GestionEnseignement.Authentification.service.UtilisateurService;
import uasz.sn.GestionEnseignement.Utilisateur.modele.Etudiant;
import uasz.sn.GestionEnseignement.Utilisateur.modele.Permanent;
import uasz.sn.GestionEnseignement.Utilisateur.modele.Vacataire;
import uasz.sn.GestionEnseignement.Utilisateur.service.EtudiantService;
import uasz.sn.GestionEnseignement.Utilisateur.service.PermanentService;
import uasz.sn.GestionEnseignement.Utilisateur.service.VacataireService;

import java.security.Principal;
import java.util.List;
@Controller
public class EnseignantController {
    @Autowired
    private UtilisateurService utilisateurService;
    @Autowired
    private PermanentService permanentService;
    @Autowired
    VacataireService vacataireService;
    @Autowired
    private EtudiantService etudiantService;

    @RequestMapping(value = "/ChefDepartement/Enseignant", method = RequestMethod.GET)
    public  String chefDepartement_Enseignant(Model model, Principal principal){
        List<Permanent> permanents=permanentService.lister();
        model.addAttribute("permanents",permanents);
        List<Vacataire> vacataires=vacataireService.lister();
        model.addAttribute("vacataires",vacataires);
        List<Etudiant> etudiants=etudiantService.lister();
        model.addAttribute("etudiants",etudiants);
        Utilisateur utilisateur=utilisateurService.rechercher_Utilisateur(principal.getName());
        model.addAttribute("nom",utilisateur.getNom());
        model.addAttribute("prenom",utilisateur.getPrenom().charAt(0));
        return "chefDepartement_Enseignant";
    }

}

