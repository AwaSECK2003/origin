package uasz.sn.GestionEnseignement.Utilisateur.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import uasz.sn.GestionEnseignement.Utilisateur.modele.Formation;

import java.util.List;

public interface FormationRepository extends JpaRepository<Formation, Long> {

    @Query("SELECT f FROM Formation f WHERE LOWER(f.nom) LIKE LOWER(CONCAT('%', :nom, '%')) AND f.archive = false")
    List<Formation> rechercherFormationParNom(@Param("nom") String nom);

    @Query("SELECT f FROM Formation f WHERE LOWER(f.code) LIKE LOWER(CONCAT('%', :code, '%')) AND f.archive = false")
    List<Formation> rechercherFormationParCode(@Param("code") String code);


    @Query("SELECT f FROM Formation f WHERE f.archive = false")
    List<Formation> findAllActives();

    @Query("SELECT f FROM Formation f WHERE f.niveau = :niveau AND f.archive = false")
    List<Formation> findByNiveau(@Param("niveau") int niveau);


}
