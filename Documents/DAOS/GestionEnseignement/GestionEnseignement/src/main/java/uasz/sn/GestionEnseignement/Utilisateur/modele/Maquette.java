package uasz.sn.GestionEnseignement.Utilisateur.modele;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Maquette {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nom;
    private String description;
    private boolean estArchivee = false;  // Pour l'archivage de la maquette
    private boolean estActive = true;

    // Relation ManyToOne avec Classe (chaque maquette est associée à une seule classe)
    @ManyToOne
    @JoinColumn(name = "classe_id", nullable = false)
    private Classe classe;

    @ManyToOne
    @JoinColumn(name = "semestre_id", nullable = false)
    private Semestre semestre;

    @OneToMany(mappedBy = "maquette", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Enseignement> enseignements;


}
