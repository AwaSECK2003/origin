package uasz.sn.GestionEnseignement.Utilisateur.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import uasz.sn.GestionEnseignement.Utilisateur.modele.*;
import uasz.sn.GestionEnseignement.Utilisateur.service.*;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.TextStyle;
import java.util.*;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/seances")
public class SeanceController {

    @Autowired
    private SeanceService seanceService;
    @Autowired
    private ClasseService classeService;
    @Autowired
    private EnseignantService enseignantService;
    @Autowired
    private EnseignementService enseignementService;
    @Autowired
    private SalleService salleService;
    @Autowired
    private RepartitionService repartitionService;
    @Autowired
    private SemestreService semestreService;

    @GetMapping
    public String afficherToutesSeances(Model model) {
        // Récupérer toutes les séances
        List<Seance> seances = seanceService.getAllSeances();

        // Créer une carte pour lier chaque enseignement à son enseignant
        Map<Long, Enseignant> enseignantsParEnseignement = new HashMap<>();

        // Pour chaque séance, récupérer l'enseignant associé
        for (Seance seance : seances) {
            Long enseignementId = seance.getEnseignement().getId();
            repartitionService.getEnseignantParEnseignement(enseignementId)
                    .ifPresent(enseignant -> enseignantsParEnseignement.put(enseignementId, enseignant));
        }

        // Ajouter les informations dans le modèle
        model.addAttribute("seances", seances);
        model.addAttribute("enseignantsParEnseignement", enseignantsParEnseignement);

        // Récupérer et ajouter d'autres informations au modèle si nécessaire
        List<Enseignement> enseignements = repartitionService.getEnseignementsValides();
        model.addAttribute("enseignements", enseignements);

        List<Classe> classes = classeService.afficherClassesNonArchivees();
        model.addAttribute("classes", classes);


        List<Salle> salles = salleService.afficherToutesLesSalles();
        model.addAttribute("salles", salles);

        return "seances";
    }

    @PostMapping("/ajouter")
    public String ajouterSeance(@ModelAttribute("seance") Seance seance) {
        seanceService.ajouterSeance(seance);
        return "redirect:/seances";
    }

    @PostMapping("/modifier")
    public String modifierSeance(@ModelAttribute Seance seanceDetails) {
        seanceService.modifierSeance(seanceDetails.getId(), seanceDetails);
        return "redirect:/seances";
    }

    @PostMapping("/supprimer")
    public String supprimerSeance(@RequestParam("id") Long id) {
        seanceService.supprimerSeance(id);
        return "redirect:/seances";
    }

    @GetMapping("/selection")
    public String afficherSelection(Model model) {
        // Récupérer la liste des classes et des semestres
        List<Classe> classes = classeService.getAllClasses();
        List<Semestre> semestres = semestreService.getAllSemestres();

        // Ajouter les listes au modèle
        model.addAttribute("classes", classes);
        model.addAttribute("semestres", semestres);

        return "selection"; // Nom de la vue Thymeleaf
    }



    @GetMapping("/tableau")
    public String afficherTableau(
            @RequestParam Long classeId,
            @RequestParam Long semestreId,
            Model model
    ) {
        // Récupérer les séances pour la classe et le semestre spécifiés
        List<Seance> seances = seanceService.getSeancesByClasseAndSemestre(classeId, semestreId);

        // Trouver la date de la première séance
        LocalDate debutSemestre = seances.stream()
                .map(Seance::getJour) // Récupérer la date de chaque séance
                .min(LocalDate::compareTo) // Trouver la date la plus ancienne
                .orElseThrow(() -> new RuntimeException("Aucune séance trouvée pour ce semestre et cette classe"));

        // Calculer la date de fin du semestre (10 semaines après la date de début)
        LocalDate finSemestre = debutSemestre.plusWeeks(10);

        // Créer une carte pour lier chaque enseignement à son enseignant
        Map<Long, Enseignant> enseignantsParEnseignement = new HashMap<>();

        // Grouper les séances par jour de la semaine
        Map<String, List<SeanceDetails>> emploiDuTemps = new LinkedHashMap<>();

        // Initialiser les jours de la semaine
        String[] joursSemaine = {"LUNDI", "MARDI", "MERCREDI", "JEUDI", "VENDREDI", "SAMEDI"};
        for (String jour : joursSemaine) {
            emploiDuTemps.put(jour, new ArrayList<>());
        }

        // Remplir la carte avec les séances
        for (Seance seance : seances) {
            Long enseignementId = seance.getEnseignement().getId();
            repartitionService.getEnseignantParEnseignement(enseignementId)
                    .ifPresent(enseignant -> enseignantsParEnseignement.put(enseignementId, enseignant));

            String jourSemaine = seance.getJour().getDayOfWeek()
                    .getDisplayName(TextStyle.FULL, Locale.FRENCH).toUpperCase();

            // Récupérer le libellé de l'EC et le nom complet de l'enseignant
            String libelleEC = seance.getEnseignement().getEc().getLibelle();
            Enseignant enseignant = enseignantsParEnseignement.get(seance.getEnseignement().getId());
            String nomCompletEnseignant = (enseignant != null) ? enseignant.getPrenom() + " " + enseignant.getNom() : "Non attribué";

            // Convertir le type de l'enseignement en String
            String typeEnseignement = seance.getEnseignement().getType().toString();

            // Construire les détails de la séance
            SeanceDetails details = new SeanceDetails(
                    libelleEC,
                    typeEnseignement,
                    seance.getSalle().getNom(),
                    nomCompletEnseignant,
                    seance.getHeureDebut().toString() + " - " + seance.getHeureFin().toString()
            );

            // Ajouter les détails à la liste des séances pour ce jour
            emploiDuTemps.get(jourSemaine).add(details);
        }

        // Récupérer la classe et le semestre concernés
        Classe classe = classeService.getClassesById(classeId)
                .orElseThrow(() -> new RuntimeException("Classe non trouvée"));
        Semestre semestre = semestreService.getSemestreById(semestreId)
                .orElseThrow(() -> new RuntimeException("Semestre non trouvé"));

        // Récupérer toutes les plages horaires possibles
        Set<String> plagesHoraires = new TreeSet<>();
        for (List<SeanceDetails> detailsList : emploiDuTemps.values()) {
            for (SeanceDetails details : detailsList) {
                plagesHoraires.add(details.getPlageHoraire());
            }
        }

        // Ajouter les informations dans le modèle
        model.addAttribute("plagesHoraires", plagesHoraires);
        model.addAttribute("emploiDuTemps", emploiDuTemps);
        model.addAttribute("classe", classe);
        model.addAttribute("semestre", semestre);
        model.addAttribute("debutSemestre", debutSemestre);
        model.addAttribute("finSemestre", finSemestre);

        return "emploi_du_temps";
    }

    @GetMapping("/selection-semaine")
    public String afficherSelectionSemaine(Model model) {
        // Récupérer la liste des classes et des semestres
        List<Classe> classes = classeService.getAllClasses();
        List<Semestre> semestres = semestreService.getAllSemestres();

        // Ajouter les listes au modèle
        model.addAttribute("classes", classes);
        model.addAttribute("semestres", semestres);

        return "selection_semaine";
    }


    @GetMapping("/semaine")
    public String afficherTableauSemaine(
            @RequestParam Long classeId,
            @RequestParam Long semestreId,
            @RequestParam String dateDebutSemaine, // Date de début de la semaine au format "yyyy-MM-dd"
            Model model
    ) {
        // Convertir la date de début de la semaine en LocalDate
        LocalDate dateSelectionnee = LocalDate.parse(dateDebutSemaine);
        LocalDate debutSemaine = dateSelectionnee.with(DayOfWeek.MONDAY); // Début de la semaine (lundi)
        LocalDate finSemaine = debutSemaine.plusDays(5); // Fin de la semaine (samedi)

        // Récupérer les séances pour la classe, le semestre et la semaine spécifiés
        List<Seance> seances = seanceService.getSeancesByClasseAndSemestreAndWeek(classeId, semestreId, debutSemaine, finSemaine);

        // Créer une carte pour lier chaque enseignement à son enseignant
        Map<Long, Enseignant> enseignantsParEnseignement = new HashMap<>();

        // Grouper les séances par jour de la semaine
        Map<String, List<SeanceDetails>> emploiDuTemps = new LinkedHashMap<>();

        // Initialiser les jours de la semaine
        String[] joursSemaine = {"LUNDI", "MARDI", "MERCREDI", "JEUDI", "VENDREDI", "SAMEDI"};
        for (String jour : joursSemaine) {
            emploiDuTemps.put(jour, new ArrayList<>());
        }

        // Remplir la carte avec les séances
        for (Seance seance : seances) {
            Long enseignementId = seance.getEnseignement().getId();
            repartitionService.getEnseignantParEnseignement(enseignementId)
                    .ifPresent(enseignant -> enseignantsParEnseignement.put(enseignementId, enseignant));

            String jourSemaine = seance.getJour().getDayOfWeek()
                    .getDisplayName(TextStyle.FULL, Locale.FRENCH).toUpperCase();

            // Récupérer le libellé de l'EC et le nom complet de l'enseignant
            String libelleEC = seance.getEnseignement().getEc().getLibelle();
            Enseignant enseignant = enseignantsParEnseignement.get(seance.getEnseignement().getId());
            String nomCompletEnseignant = (enseignant != null) ? enseignant.getPrenom() + " " + enseignant.getNom() : "Non attribué";

            // Convertir le type de l'enseignement en String
            String typeEnseignement = seance.getEnseignement().getType().toString();

            // Construire les détails de la séance
            SeanceDetails details = new SeanceDetails(
                    libelleEC,
                    typeEnseignement,
                    seance.getSalle().getNom(),
                    nomCompletEnseignant,
                    seance.getHeureDebut().toString() + " - " + seance.getHeureFin().toString()
            );

            // Ajouter les détails à la liste des séances pour ce jour
            emploiDuTemps.get(jourSemaine).add(details);
        }

        // Récupérer la classe et le semestre concernés
        Classe classe = classeService.getClassesById(classeId)
                .orElseThrow(() -> new RuntimeException("Classe non trouvée"));
        Semestre semestre = semestreService.getSemestreById(semestreId)
                .orElseThrow(() -> new RuntimeException("Semestre non trouvé"));

        // Récupérer toutes les plages horaires possibles
        Set<String> plagesHoraires = new TreeSet<>();
        for (List<SeanceDetails> detailsList : emploiDuTemps.values()) {
            for (SeanceDetails details : detailsList) {
                plagesHoraires.add(details.getPlageHoraire());
            }
        }

        model.addAttribute("plagesHoraires", plagesHoraires);
        model.addAttribute("emploiDuTemps", emploiDuTemps);
        model.addAttribute("classe", classe);
        model.addAttribute("semestre", semestre);
        model.addAttribute("debutSemaine", debutSemaine);
        model.addAttribute("finSemaine", finSemaine);

        return "emploi_du_temps_semaine";
    }

    @GetMapping("/selection-salle")
    public String selectionSalle() {
        return "selection_salles";
    }


    @GetMapping("/salles-disponibles")
    public String afficherSallesDisponibles(
            @RequestParam LocalDate jour,
            @RequestParam LocalTime heureDebut,
            @RequestParam LocalTime heureFin,
            Model model) {

        // Récupérer les salles disponibles
        List<Salle> sallesDisponibles = seanceService.getSallesDisponibles(jour, heureDebut, heureFin);

        // Ajouter les salles disponibles au modèle
        model.addAttribute("sallesDisponibles", sallesDisponibles);
        model.addAttribute("jour", jour);
        model.addAttribute("heureDebut", heureDebut);
        model.addAttribute("heureFin", heureFin);

        return "salles_disponibles"; // Nom de la vue Thymeleaf
    }

}
