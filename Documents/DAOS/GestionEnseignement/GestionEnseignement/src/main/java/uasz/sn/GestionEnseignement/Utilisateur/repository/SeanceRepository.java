package uasz.sn.GestionEnseignement.Utilisateur.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import uasz.sn.GestionEnseignement.Utilisateur.modele.Seance;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Repository
public interface SeanceRepository extends JpaRepository<Seance, Long> {

    List<Seance> findByClasseId(Long classeId);

    List<Seance> findByJourBetween(LocalDate debut, LocalDate fin);



    @Query("SELECT s FROM Seance s " + "JOIN s.enseignement e " + "WHERE e.classe.id = :classeId AND e.semestre.id = :semestreId")
     List<Seance> findSeancesByClasseAndSemestre(@Param("classeId") Long classeId, @Param("semestreId") Long semestreId);


    @Query("SELECT s FROM Seance s " +
            "JOIN s.enseignement e " +
            "WHERE s.classe.id = :classeId " +
            "AND e.semestre.id = :semestreId " +
            "AND s.jour BETWEEN :debutSemaine AND :finSemaine")
    List<Seance> findByClasseIdAndSemestreIdAndJourBetween(
            @Param("classeId") Long classeId,
            @Param("semestreId") Long semestreId,
            @Param("debutSemaine") LocalDate debutSemaine,
            @Param("finSemaine") LocalDate finSemaine
    );


        @Query("SELECT s FROM Seance s WHERE s.jour = :jour AND " +
                "((s.heureDebut <= :heureFin AND s.heureFin >= :heureDebut))")
        List<Seance> findByJourAndHeureDebutLessThanEqualAndHeureFinGreaterThanEqual(
                @Param("jour") LocalDate jour,
                @Param("heureFin") LocalTime heureFin,
                @Param("heureDebut") LocalTime heureDebut);





}

