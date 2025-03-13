package uasz.sn.GestionEnseignement.Utilisateur.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uasz.sn.GestionEnseignement.Utilisateur.modele.Permanent;

import java.util.Optional;

@Repository
public interface PermanentRepository extends JpaRepository<Permanent, Long> {

     Optional<Permanent> findByMatricule(String matricule) ;

}
