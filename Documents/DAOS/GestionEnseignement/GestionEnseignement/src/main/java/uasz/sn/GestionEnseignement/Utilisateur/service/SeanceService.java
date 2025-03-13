package uasz.sn.GestionEnseignement.Utilisateur.service;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import uasz.sn.GestionEnseignement.Utilisateur.modele.*;
import uasz.sn.GestionEnseignement.Utilisateur.repository.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@Transactional
public class SeanceService {
    @Autowired
    private SeanceRepository seanceRepository;
    @Autowired
    private EnseignantRepository enseignantRepository;
    @Autowired
    private EnseignementRepository enseignementRepository;
    @Autowired
    private ClasseRepository classeRepository;
    @Autowired
    private SalleRepository  salleRepository;
    @Autowired
    private SalleService salleService;


    public List<Seance> getAllSeances() {
        return seanceRepository.findAll();
    }

    public Seance ajouterSeance(Seance seance) {
        return seanceRepository.save(seance);
    }

    public Seance modifierSeance(Long id, Seance seanceDetails) {
        Seance seance = seanceRepository.findById(id).orElseThrow(() -> new RuntimeException("Séance non trouvée pour cet id :: " + id));

        seance.setJour(seanceDetails.getJour());
        seance.setHeureDebut(seanceDetails.getHeureDebut());
        seance.setHeureFin(seanceDetails.getHeureFin());
        //seance.setEnseignant(seanceDetails.getEnseignant());
        seance.setEnseignement(seanceDetails.getEnseignement());
        seance.setClasse(seanceDetails.getClasse());
        seance.setSalle(seanceDetails.getSalle());

        return seanceRepository.save(seance);
    }


    public Optional<Seance> getSeanceById(Long id) {
        return seanceRepository.findById(id);
    }


    public List<Seance> getSeancesByClasseId(Long classeId) {
        return seanceRepository.findByClasseId(classeId);
    }

    public List<Seance> getSeancesBetweenDates(LocalDate debut, LocalDate fin) {
        return seanceRepository.findByJourBetween(debut, fin);
    }

    public void supprimerSeance(Long id) {
        seanceRepository.deleteById(id);
    }

    public Enseignant getEnseignantById(Long id) {
        return enseignantRepository.findById(id).orElse(null);
    }
    public Enseignement getEnseignementById(Long id) {
        return enseignementRepository.findById(id).orElse(null);
    }
    public Classe getClasseById(Long id) {
        return classeRepository.findById(id).orElse(null); // Retrieves the Classe by ID
    }
    public Salle getSalleById(Long id) {
        return salleRepository.findById(id).orElse(null); // Retrieves Salle by ID
    }


    public List<Seance> getSeancesByClasseAndSemestre(Long classeId, Long semestreId) {
        return seanceRepository.findSeancesByClasseAndSemestre(classeId, semestreId);
    }

    public List<Seance> getSeancesByClasseAndSemestreAndWeek(Long classeId, Long semestreId, LocalDate debutSemaine, LocalDate finSemaine) {
        return seanceRepository.findByClasseIdAndSemestreIdAndJourBetween(classeId, semestreId, debutSemaine, finSemaine);
    }

    public List<Salle> getSallesDisponibles(LocalDate jour, LocalTime heureDebut, LocalTime heureFin) {
        // Récupérer toutes les salles
        List<Salle> toutesLesSalles = salleRepository.findAll();

        // Récupérer les séances qui chevauchent la plage horaire spécifiée
        List<Seance> seancesConflict = seanceRepository.findByJourAndHeureDebutLessThanEqualAndHeureFinGreaterThanEqual(
                jour, heureFin, heureDebut);

        // Récupérer les IDs des salles déjà réservées
        Set<Long> sallesOccupees = seancesConflict.stream()
                .map(seance -> seance.getSalle().getId())
                .collect(Collectors.toSet());

        // Filtrer les salles disponibles
        return toutesLesSalles.stream()
                .filter(salle -> !sallesOccupees.contains(salle.getId()))
                .collect(Collectors.toList());
    }


}
