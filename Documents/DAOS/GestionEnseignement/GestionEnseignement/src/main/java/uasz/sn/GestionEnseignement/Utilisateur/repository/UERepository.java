package uasz.sn.GestionEnseignement.Utilisateur.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import uasz.sn.GestionEnseignement.Utilisateur.modele.Classe;
import uasz.sn.GestionEnseignement.Utilisateur.modele.EC;
import uasz.sn.GestionEnseignement.Utilisateur.modele.Semestre;
import uasz.sn.GestionEnseignement.Utilisateur.modele.UE;

import java.util.List;

public interface UERepository extends JpaRepository<UE, Long> {

    @Query("SELECT e FROM EC e WHERE e.ue = ?1")
    List<EC> findByUE(UE ue);
    List<UE> findBySemestreId(Long semestreId);

    List<UE> findByClassesAndSemestre(Classe classe, Semestre semestre);

    @Query("SELECT ue FROM UE ue JOIN ue.classes c JOIN ue.semestre s WHERE c.id = :classeId AND s.id = :semestreId")
    List<UE> findByClasseIdAndSemestreId(@Param("classeId") Long classeId, @Param("semestreId") Long semestreId);


}
