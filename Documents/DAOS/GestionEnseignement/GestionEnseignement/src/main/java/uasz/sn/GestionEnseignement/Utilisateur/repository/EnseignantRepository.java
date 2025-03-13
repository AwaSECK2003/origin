package uasz.sn.GestionEnseignement.Utilisateur.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import uasz.sn.GestionEnseignement.Utilisateur.modele.Enseignant;
import uasz.sn.GestionEnseignement.Utilisateur.modele.Permanent;
import uasz.sn.GestionEnseignement.Utilisateur.modele.Repartition;
import uasz.sn.GestionEnseignement.Utilisateur.modele.Vacataire;

import java.util.List;
import java.util.Optional;

public interface EnseignantRepository extends JpaRepository<Enseignant, Long> {
    boolean existsByUsername(String username);


    // Rechercher un permanent par matricule
    @Query("SELECT e FROM Permanent e WHERE e.matricule = :matricule")
    Optional<Permanent> findPermanentByMatricule(@Param("matricule") String matricule);

    // Rechercher un vacataire par niveau
    @Query("SELECT e FROM Vacataire e WHERE e.niveau = :niveau")
    Optional<Vacataire> findVacataireByNiveau(@Param("niveau") String niveau);


    Optional<Permanent> findByMatricule(String matricule);

    // Rechercher un enseignant par username et niveau
    Optional<Vacataire> findByUsernameAndNiveau(String username, String niveau);

    Optional<Enseignant> findByUsername(String username);
}


