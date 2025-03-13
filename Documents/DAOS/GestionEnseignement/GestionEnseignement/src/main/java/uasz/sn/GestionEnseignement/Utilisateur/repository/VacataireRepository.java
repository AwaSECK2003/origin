package uasz.sn.GestionEnseignement.Utilisateur.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uasz.sn.GestionEnseignement.Utilisateur.modele.Permanent;
import uasz.sn.GestionEnseignement.Utilisateur.modele.Vacataire;

import java.util.Optional;

public interface VacataireRepository extends JpaRepository<Vacataire, Long> {

    Optional<Vacataire> findByNiveau(String niveau) ;
}
