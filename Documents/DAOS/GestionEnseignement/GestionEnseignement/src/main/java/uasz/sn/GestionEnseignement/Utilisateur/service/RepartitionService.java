package uasz.sn.GestionEnseignement.Utilisateur.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uasz.sn.GestionEnseignement.Utilisateur.modele.*;
import uasz.sn.GestionEnseignement.Utilisateur.repository.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class RepartitionService {

    @Autowired
    private RepartitionRepository repartitionRepository;
    @Autowired
    private EnseignantRepository enseignantRepository;

    @Autowired
    private PermanentRepository permanentRepository;
    @Autowired
    private VacataireRepository vacataireRepository;
    @Autowired
    private EnseignementRepository enseignementRepository;



    // Ajouter une répartition
    public Repartition ajouterRepartition(Repartition repartition) {
        return repartitionRepository.save(repartition);
    }

    // Modifier une répartition
    public Repartition modifierRepartition(Long id, Repartition repartitionModifiee) {
        Repartition repartition = repartitionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Répartition non trouvée avec l'ID : " + id));

        repartition.setDateChoix(repartitionModifiee.getDateChoix());
        repartition.setEnseignant(repartitionModifiee.getEnseignant());
        repartition.setEnseignement(repartitionModifiee.getEnseignement());

        return repartitionRepository.save(repartition);
    }

    // Supprimer une répartition
    public void supprimerRepartition(Long id) {
        repartitionRepository.deleteById(id);
    }

    // Lister toutes les répartitions
    public List<Repartition> listerRepartitions() {
        return repartitionRepository.findAll();
    }

    // Rechercher une répartition par son ID
    public Optional<Repartition> getRepartitionById(Long id) {
        return repartitionRepository.findById(id);
    }

    // Rechercher les répartitions par enseignant
    public List<Repartition> getRepartitionsByEnseignant(Long enseignantId) {
        return repartitionRepository.findByEnseignantId(enseignantId);
    }

    // Rechercher les répartitions par enseignement
    public List<Repartition> getRepartitionsByEnseignement(Long enseignementId) {
        return repartitionRepository.findByEnseignementId(enseignementId);
    }

    public List<Repartition> getRepartitionsValidees() {
        // Assurez-vous que la méthode de récupération de vos répartitions validées est correcte
        return repartitionRepository.findByValideTrue();
    }


    // Service pour valider un choix définitif
    public Repartition validerRepartition(Long repartitionId, Long enseignantId) {
        Repartition repartition = repartitionRepository.findById(repartitionId)
                .orElseThrow(() -> new RuntimeException("Répartition non trouvée avec l'ID : " + repartitionId));

        Enseignant enseignant = enseignantRepository.findById(enseignantId)
                .orElseThrow(() -> new RuntimeException("Enseignant non trouvé avec l'ID : " + enseignantId));

        repartition.setEnseignant(enseignant); // Assigner définitivement l'enseignant choisi
        repartition.setValide(true);
        return repartitionRepository.save(repartition);
    }


    public List<Enseignement> getEnseignementsValides() {
        List<Repartition> repartitions = repartitionRepository.findEnseignementByValideTrue();
        return repartitions.stream()
                .map(Repartition::getEnseignement)
                .collect(Collectors.toList());
    }



    public Optional<Enseignant> getEnseignantParEnseignement(Long enseignementId) {
        return repartitionRepository.findByEnseignementIdAndValide(enseignementId, true)
                .map(Repartition::getEnseignant);  // Récupérer l'enseignant à partir de la répartition
    }


    public List<Repartition> rechercherRepartitionsParEnseignant(String matricule) {
        if (matricule != null) {
            // Recherche pour un enseignant permanent par matricule
            return repartitionRepository.findByEnseignantPermanentMatricule(matricule);
        }
        return Collections.emptyList();
    }


}



