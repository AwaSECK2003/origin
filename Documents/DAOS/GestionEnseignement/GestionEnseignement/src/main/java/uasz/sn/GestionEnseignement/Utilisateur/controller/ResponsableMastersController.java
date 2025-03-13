package uasz.sn.GestionEnseignement.Utilisateur.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import uasz.sn.GestionEnseignement.Authentification.modele.Utilisateur;
import uasz.sn.GestionEnseignement.Authentification.service.UtilisateurService;

import java.security.Principal;

@Controller
@RequestMapping("/ResponsableMasters")
public class ResponsableMastersController {

    @Autowired
    private UtilisateurService utilisateurService;
    @Autowired
    PasswordEncoder passwordEncoder;

    @GetMapping("/Accueil")
    public String accueilResponsableMasters(Model model, Principal principal) {
        // Ajouter les informations spécifiques pour le responsable des masters
        // model.addAttribute("message", "Bienvenue Responsable Masters !");//
        Utilisateur utilisateur=utilisateurService.rechercher_Utilisateur(principal.getName());
        model.addAttribute("nom",utilisateur.getNom());
        model.addAttribute("prenom",utilisateur.getPrenom().charAt(0));
        return "template_ResponsableMasters";  // Vue pour le responsable des masters
    }

}
