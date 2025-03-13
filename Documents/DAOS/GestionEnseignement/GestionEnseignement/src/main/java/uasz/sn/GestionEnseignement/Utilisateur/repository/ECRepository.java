package uasz.sn.GestionEnseignement.Utilisateur.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import uasz.sn.GestionEnseignement.Utilisateur.modele.EC;
import uasz.sn.GestionEnseignement.Utilisateur.modele.UE;

import java.util.List;

public interface ECRepository extends JpaRepository<EC, Long> {
    List<EC> findByUe(UE ue);

    // Méthode utilisant le nom de convention
    List<EC> findByUeId(Long ueId);

    // Méthode utilisant une requête JPQL (facultatif, alternative)
    @Query("SELECT e FROM EC e WHERE e.ue.id = :ueId")
    List<EC> findECsByUEId(@Param("ueId") Long ueId);

    List<EC> findByUeIdIn(List<Long> ueIds);

    @Query("SELECT ec FROM EC ec JOIN ec.ue ue JOIN ue.classes c JOIN ue.semestre s WHERE c.id = :classeId AND s.id = :semestreId")
    List<EC> findByClasseIdAndSemestreId(@Param("classeId") Long classeId, @Param("semestreId") Long semestreId);

}
