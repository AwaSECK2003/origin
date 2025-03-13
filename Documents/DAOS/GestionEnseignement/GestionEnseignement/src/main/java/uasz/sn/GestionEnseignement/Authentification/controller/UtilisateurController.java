package uasz.sn.GestionEnseignement.Authentification.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import uasz.sn.GestionEnseignement.Authentification.modele.Role;
import uasz.sn.GestionEnseignement.Authentification.modele.Utilisateur;
import uasz.sn.GestionEnseignement.Authentification.service.UtilisateurService;
import uasz.sn.GestionEnseignement.Utilisateur.modele.Permanent;

import java.security.Principal;

@Controller
public class UtilisateurController {

    @Autowired
    private UtilisateurService utilisateurService;

    @RequestMapping(value = "/login")
    public String index() { return "login"; }


    @RequestMapping("/")
            public String login(Principal principal) {
                String url="login";
                Utilisateur utilisateur = utilisateurService.rechercher_Utilisateur(principal.getName());

                if (utilisateur.getRoles().get(0).getRole().equals("Permanent")) {
                    url = "redirect:/Permanent/Accueil";
                } else if (utilisateur.getRoles().get(0).getRole().equals("Vacataire")) {
                    url = "redirect:/Vacataire/Accueil";
                } else if (utilisateur.getRoles().get(0).getRole().equals("ChefDepartement")) {
                    url = "redirect:/ChefDepartement/Accueil";
                } else if (utilisateur.getRoles().get(0).getRole().equals("Etudiant")) {
                    url = "redirect:/Etudiant/Accueil";
                } else if (utilisateur.getRoles().get(0).getRole().equals("ResponsableClasse")) {
                    url = "redirect:/ResponsableClasse/Accueil";
                }
                else if (utilisateur.getRoles().get(0).getRole().equals("ResponsableMasters")) {
                    url = "redirect:/ResponsableMasters/Accueil"; // Vue spécifique pour responsable masters
                } else if (utilisateur.getRoles().get(0).getRole().equals("CoordonnateurLicences")) {
                    url = "redirect:/CoordonnateurLicences/Accueil"; // Vue spécifique pour coordonnateur licences
                }
                return url;
            }

    @RequestMapping(value = "/logout")
    public String LogOutAndRedirectToLoginPage(Authentication authentication,
                                               HttpServletRequest request,
                                               HttpServletResponse response) {
        if (authentication != null) {
            new SecurityContextLogoutHandler().logout(request, response, authentication);
        }
        return "redirect:/login?logout=true";
    }
    @RequestMapping(value = "/profil", method = RequestMethod.GET)
    public String profil_Utilisateur(Model model, Principal principal){
        Utilisateur utilisateur=utilisateurService.rechercher_Utilisateur(principal.getName());
        model.addAttribute("utilisateur",utilisateur);
        model.addAttribute("nom",utilisateur.getNom());
        model.addAttribute("prenom",utilisateur.getPrenom().charAt(0));
        return "profil";
    }


}