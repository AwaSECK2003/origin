package uasz.sn.GestionEnseignement.Utilisateur.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import uasz.sn.GestionEnseignement.Utilisateur.modele.Enseignement;

import java.util.List;

public interface EnseignementRepository extends JpaRepository<Enseignement, Long> {

    Enseignement findById(long id);

    List<Enseignement> findByClasseIdAndSemestreId(Long classeId, Long semestreId);


}
