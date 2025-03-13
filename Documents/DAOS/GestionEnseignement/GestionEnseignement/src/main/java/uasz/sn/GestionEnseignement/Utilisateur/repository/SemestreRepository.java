package uasz.sn.GestionEnseignement.Utilisateur.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uasz.sn.GestionEnseignement.Utilisateur.modele.Semestre;
import uasz.sn.GestionEnseignement.Utilisateur.modele.UE;

import java.util.List;

public interface SemestreRepository extends JpaRepository<Semestre, Long> {
}
