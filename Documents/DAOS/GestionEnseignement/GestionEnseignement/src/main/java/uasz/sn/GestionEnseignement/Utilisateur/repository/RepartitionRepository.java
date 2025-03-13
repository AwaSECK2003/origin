package uasz.sn.GestionEnseignement.Utilisateur.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import uasz.sn.GestionEnseignement.Utilisateur.modele.Enseignant;
import uasz.sn.GestionEnseignement.Utilisateur.modele.Enseignement;
import uasz.sn.GestionEnseignement.Utilisateur.modele.Repartition;

import java.util.List;
import java.util.Optional;

@Repository
public interface RepartitionRepository extends JpaRepository<Repartition, Long> {

    List<Repartition> findByEnseignantId(Long enseignantId);

    // Rechercher les répartitions par enseignement
    List<Repartition> findByEnseignementId(Long enseignementId);

    List<Repartition> findByEnseignementIdAndValideFalse(Long enseignementId);

    List<Repartition> findByValideTrue();

    List<Repartition> findEnseignementByValideTrue();


    Optional<Repartition> findByEnseignementIdAndValide(Long enseignementId, boolean valide);


    // Rechercher les répartitions par matricule d'un enseignant permanent
    @Query("SELECT r FROM Repartition r JOIN r.enseignant e JOIN Permanent p ON e.id = p.id WHERE p.matricule = :matricule")
    List<Repartition> findByEnseignantPermanentMatricule(@Param("matricule") String matricule);

}


