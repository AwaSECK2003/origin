package uasz.sn.GestionEnseignement.Utilisateur.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uasz.sn.GestionEnseignement.Utilisateur.modele.Salle;

@Repository
public interface SalleRepository extends JpaRepository<Salle, Long> {
}
