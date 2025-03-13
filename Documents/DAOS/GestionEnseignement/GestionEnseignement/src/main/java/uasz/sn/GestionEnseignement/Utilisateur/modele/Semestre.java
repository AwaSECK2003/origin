package uasz.sn.GestionEnseignement.Utilisateur.modele;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Semestre {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private int annee;
    @Column(nullable = false, unique = true)
    private String code; // Exemple : "S1", "S2", etc.

    @Column(nullable = false)
    private String intitule; // Exemple : "Semestre 1", "Semestre 2", etc.

    @OneToMany(mappedBy = "semestre", cascade = CascadeType.ALL)
    private List<UE> ues;

    @OneToMany(mappedBy = "semestre", cascade = CascadeType.ALL)
    private List<Maquette> maquettes;

    @OneToMany(mappedBy = "semestre", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Enseignement> enseignements;



}
