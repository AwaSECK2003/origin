package uasz.sn.GestionEnseignement.Utilisateur.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import uasz.sn.GestionEnseignement.Authentification.modele.Utilisateur;
import uasz.sn.GestionEnseignement.Authentification.repository.UtilisateurRepository;
import uasz.sn.GestionEnseignement.Authentification.service.UtilisateurService;
import uasz.sn.GestionEnseignement.Utilisateur.modele.*;
import uasz.sn.GestionEnseignement.Utilisateur.repository.EnseignementRepository;
import uasz.sn.GestionEnseignement.Utilisateur.repository.PermanentRepository;
import uasz.sn.GestionEnseignement.Utilisateur.repository.VacataireRepository;
import uasz.sn.GestionEnseignement.Utilisateur.service.*;

import java.security.Principal;
import java.util.*;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/repartitions")
public class RepartitionController {

    @Autowired
    private RepartitionService repartitionService;

    @Autowired
    private EnseignantService enseignantService;

    @Autowired
    private EnseignementService enseignementService;
    @Autowired
    private UtilisateurService utilisateurService;
    @Autowired
    private UtilisateurRepository utilisateurRepository;

    @Autowired
    private EnseignementRepository enseignementRepository;
    @Autowired
    private PermanentRepository permanentRepository;
    @Autowired
    private VacataireRepository vacataireRepository;
    @Autowired
    private ClasseService classeService;
    @Autowired
    private SemestreService semestreService;


    @GetMapping
    public String listerRepartitions(
            @RequestParam(required = false) Long classeId,
            @RequestParam(required = false) Long semestreId,
            Model model) {
        List<Repartition> repartitions = repartitionService.listerRepartitions();
        model.addAttribute("repartitions", repartitions);

        // Récupérer la liste des enseignants
        List<Enseignant> enseignants = enseignantService.lister();
        model.addAttribute("enseignants", enseignants);

        // Récupérer la liste des enseignements filtrés par classe et semestre si spécifiés
        List<Enseignement> enseignements;
        if (classeId != null && semestreId != null) {
            enseignements = enseignementService.getEnseignementsByClasseAndSemestre(classeId, semestreId);
        } else {
            enseignements = enseignementService.getAllEnseignements();
        }
        model.addAttribute("enseignements", enseignements);

        return "repartition";
    }

    @GetMapping("/selection-enseignement")
    public String afficherSelection(Model model) {
        // Récupérer la liste des classes et des semestres
        List<Classe> classes = classeService.getAllClasses();
        List<Semestre> semestres = semestreService.getAllSemestres();

        // Ajouter les listes au modèle
        model.addAttribute("classes", classes);
        model.addAttribute("semestres", semestres);

        return "selection_enseignement";
    }




    // Ajouter une répartition
    @PostMapping("/ajouter")
    public String ajouterRepartition(@ModelAttribute Repartition repartition, Model model) {
        try {
            repartitionService.ajouterRepartition(repartition);
            model.addAttribute("success", "Répartition ajoutée avec succès !");
        } catch (Exception e) {
            model.addAttribute("error", "Erreur lors de l'ajout de la répartition : " + e.getMessage());
        }
        return "redirect:/repartitions"; // Rediriger vers la liste des répartitions
    }


    // Modifier une répartition
    @PostMapping("/modifier")
    public String modifierRepartition(@ModelAttribute Repartition repartition, Model model) {
        try {
            repartitionService.modifierRepartition(repartition.getId(), repartition);
            model.addAttribute("success", "Répartition modifiée avec succès !");
        } catch (Exception e) {
            model.addAttribute("error", "Erreur lors de la modification de la répartition : " + e.getMessage());
        }
        return "redirect:/repartitions"; // Rediriger vers la liste des répartitions
    }

    // Supprimer une répartition
    @PostMapping("/supprimer")
    public String supprimerRepartition(@RequestParam("id") Long id, Model model) {
        try {
            repartitionService.supprimerRepartition(id);
            model.addAttribute("success", "Répartition supprimée avec succès !");
        } catch (Exception e) {
            model.addAttribute("error", "Erreur lors de la suppression de la répartition : " + e.getMessage());
        }
        return "redirect:/repartitions"; // Rediriger vers la liste des répartitions
    }

    // Rechercher une répartition par son ID
    @GetMapping("/rechercher")
    public String rechercherRepartition(@RequestParam("id") Long id, Model model) {
        Optional<Repartition> repartition = repartitionService.getRepartitionById(id);
        if (repartition.isPresent()) {
            model.addAttribute("repartition", repartition.get());
            return "repartition_details"; // Vue pour afficher les détails de la répartition
        } else {
            model.addAttribute("message", "Répartition non trouvée");
            return "repartition"; // Rediriger vers la liste des répartitions avec un message d'erreur
        }
    }

    // Afficher les répartitions par enseignant
    @GetMapping("/enseignant/{enseignantId}")
    public String getRepartitionsByEnseignant(@PathVariable Long enseignantId, Model model) {
        List<Repartition> repartitions = repartitionService.getRepartitionsByEnseignant(enseignantId);
        model.addAttribute("repartitions", repartitions);
        return "repartition"; // Vue pour afficher les répartitions par enseignant
    }

    // Afficher les répartitions par enseignement
    @GetMapping("/enseignement/{enseignementId}")
    public String getRepartitionsByEnseignement(@PathVariable Long enseignementId, Model model) {
        List<Repartition> repartitions = repartitionService.getRepartitionsByEnseignement(enseignementId);
        model.addAttribute("repartitions", repartitions);
        return "repartition"; // Vue pour afficher les répartitions par enseignement
    }

    @GetMapping("/valider")
    public String afficherRepartitionsAValider(Model model) {
        List<Repartition> repartitions = repartitionService.listerRepartitions();
        model.addAttribute("repartitions", repartitions);
        return "repartition_valider";
    }
/*

    @GetMapping("/liste_definitive")
    public String afficherListeChoix(
            @RequestParam(required = false) Long classeId,
            @RequestParam(required = false) Long semestreId,
            Model model) {

        // Récupérer les répartitions validées
        List<Repartition> repartitionsValidees = repartitionService.getRepartitionsValidees();

        // Filtrer les répartitions par classe et semestre si les paramètres sont fournis
        if (classeId != null && semestreId != null) {
            repartitionsValidees = repartitionsValidees.stream()
                    .filter(repartition -> repartition.getEnseignement().getClasse().getId().equals(classeId)
                            && repartition.getEnseignement().getSemestre().getId().equals(semestreId))
                    .collect(Collectors.toList());
        }

        // Créer une Map pour stocker la durée totale par enseignement (clé : ec_id + classe_id)
        Map<String, Integer> dureeTotaleParEnseignement = new HashMap<>();

        // Calculer la durée totale pour chaque enseignement (CM + TD + TP)
        for (Repartition repartition : repartitionsValidees) {
            Enseignement enseignement = repartition.getEnseignement();
            String key = enseignement.getEc().getId() + "_" + enseignement.getClasse().getId();

            // Ajouter les heures totales en fonction du type d'enseignement
            dureeTotaleParEnseignement.merge(key, enseignement.getTotalHeures(), Integer::sum);
        }

        // Associer chaque enseignant à son rôle
        Map<Long, String> rolesUtilisateurs = new HashMap<>();
        for (Repartition repartition : repartitionsValidees) {
            Long enseignantId = repartition.getEnseignant().getId();
            rolesUtilisateurs.put(enseignantId, utilisateurService.obtenirRoleUtilisateur(enseignantId));
        }

        // Récupérer les listes de classes et de semestres
        List<Classe> classes = classeService.getAllClasses(); // Assurez-vous que cette méthode existe
        List<Semestre> semestres = semestreService.getAllSemestres(); // Assurez-vous que cette méthode existe

        // Ajouter les répartitions filtrées, les rôles, les classes et les semestres au modèle
        model.addAttribute("repartitionsValidees", repartitionsValidees);
        model.addAttribute("rolesUtilisateurs", rolesUtilisateurs);
        model.addAttribute("dureeTotaleParEnseignement", dureeTotaleParEnseignement); // Ajouter la durée totale
        model.addAttribute("classes", classes);
        model.addAttribute("semestres", semestres);

        return "liste_choix_definitifs"; // Nom de la vue Thymeleaf
    }

 */


    @GetMapping("/liste_definitive")
    public String afficherListeChoix(
            @RequestParam(required = false) Long classeId,
            @RequestParam(required = false) Long semestreId,
            Model model) {

        // Récupérer les répartitions validées
        List<Repartition> repartitionsValidees = repartitionService.getRepartitionsValidees();

        // Filtrer les répartitions par classe et semestre si les paramètres sont fournis
        if (classeId != null && semestreId != null) {
            repartitionsValidees = repartitionsValidees.stream()
                    .filter(repartition ->
                            repartition.getEnseignement().getClasse().getId() != null &&
                                    repartition.getEnseignement().getClasse().getId().equals(classeId) &&
                                    repartition.getEnseignement().getSemestre().getId() != null &&
                                    repartition.getEnseignement().getSemestre().getId().equals(semestreId)
                    )
                    .collect(Collectors.toList());
        }

        // Créer une liste pour stocker les données du tableau
        List<Object[]> tableau = new ArrayList<>();

        // Parcourir les répartitions validées pour construire le tableau
        for (Repartition repartition : repartitionsValidees) {
            Enseignement enseignement = repartition.getEnseignement();
            EC ec = enseignement.getEc();
            UE ue = ec.getUe();
            Classe classe = enseignement.getClasse();
            Semestre semestre = enseignement.getSemestre();

            // Initialiser les colonnes pour chaque type d'enseignement (CM, TD, TP)
            String enseignantCM = "";
            int dureeCM = 0;
            String enseignantTD = "";
            int dureeTD = 0;
            String enseignantTP = "";
            int dureeTP = 0;

            // Remplir les colonnes en fonction du type d'enseignement
            switch (enseignement.getType()) {
                case CM:
                    enseignantCM = repartition.getEnseignant().getNom() + " " + repartition.getEnseignant().getPrenom();
                    dureeCM = enseignement.getTotalHeures();
                    break;
                case TD:
                    enseignantTD = repartition.getEnseignant().getNom() + " " + repartition.getEnseignant().getPrenom();
                    dureeTD = enseignement.getTotalHeures();
                    break;
                case TP:
                    enseignantTP = repartition.getEnseignant().getNom() + " " + repartition.getEnseignant().getPrenom();
                    dureeTP = enseignement.getTotalHeures();
                    break;
            }


            // Ajouter une ligne au tableau
            Object[] ligne = {
                    classe.getNom(), // Colonne 1 : Nom de la classe
                    semestre.getIntitule(), // Colonne 2 : Semestre de l'UE
                    ue.getLibelle(), // Colonne 3 : Libellé de l'UE
                    ue.getCoefficient(), // Colonne 4 : Coefficient de l'UE
                    enseignantCM, // Colonne 5 : Enseignant CM
                    dureeCM, // Colonne 6 : Durée CM
                    enseignantTD, // Colonne 7 : Enseignant TD
                    dureeTD, // Colonne 8 : Durée TD
                    enseignantTP, // Colonne 9 : Enseignant TP
                    dureeTP // Colonne 10 : Durée TP
            };

            tableau.add(ligne);
        }

        // Récupérer les listes de classes et de semestres pour les filtres
        List<Classe> classes = classeService.getAllClasses();
        List<Semestre> semestres = semestreService.getAllSemestres();

        // Ajouter les données au modèle
        model.addAttribute("tableau", tableau);
        model.addAttribute("classes", classes);
        model.addAttribute("semestres", semestres);

        return "tableau_repartitions"; // Nom de la vue Thymeleaf
    }



    @PostMapping("/valider")
    public String validerRepartition(
            @RequestParam("repartitionId") Long repartitionId,
            @RequestParam("enseignantId") Long enseignantId,
            Model model) {
        try {
            if (enseignantId == null) {
                throw new IllegalArgumentException("L'ID de l'enseignant est requis.");
            }
            Repartition repartitionValidee = repartitionService.validerRepartition(repartitionId, enseignantId);
            model.addAttribute("repartition", repartitionValidee);
            return "repartition_valider_liste";
        } catch (Exception e) {
            model.addAttribute("error", e.getMessage());
            return "repartition_valider"; // Retour avec un message d'erreur
        }
    }

    @GetMapping("/rechercherRepartitions")
    public String rechercherRepartitions(@RequestParam(required = false) String matricule,
                                         Model model) {
        // Si des paramètres de recherche sont fournis, effectuer la recherche
        if (matricule != null ) {
            List<Repartition> repartitions = repartitionService.rechercherRepartitionsParEnseignant(matricule);
            model.addAttribute("repartitions", repartitions);
        } else {
            // Sinon, afficher le formulaire de recherche sans résultats
            model.addAttribute("repartitions", Collections.emptyList());
        }
        return "repartition_matricule_ou_niveau"; // Nom de la vue Thymeleaf
    }

    @GetMapping("/selection-classe-semestre")
    public String selectionClasseSemestre(Model model) {
        // Récupérer la liste des classes et des semestres
        List<Classe> classes = classeService.getAllClasses();
        List<Semestre> semestres = semestreService.getAllSemestres();

        // Ajouter les listes au modèle
        model.addAttribute("classes", classes);
        model.addAttribute("semestres", semestres);

        return "selection_repartition";
    }
 /*

    @GetMapping("/tableau-repartition")
    public String afficherTableauEnseignements(Model model) {
        // Appeler la méthode du service pour récupérer les données
        List<Object[]> tableau = repartitionService.genererTableauEnseignementsValides();
        // Passer les données à la vue
        model.addAttribute("tableau", tableau);
        return "tableau_repartitions";
    }

  */


}