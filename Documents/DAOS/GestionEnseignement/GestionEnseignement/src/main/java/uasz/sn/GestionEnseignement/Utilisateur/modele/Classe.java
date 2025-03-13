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
public class Classe {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nom;
    private String description;
    @Column(columnDefinition = "boolean default false")
    private boolean estArchivee = false;
    // Relation ManyToOne avec Formation (une formation peut avoir plusieurs classes)
    @ManyToOne
    @JoinColumn(name = "formation_id", nullable = false)
    private Formation formation;

    // Relation OneToMany avec Maquette (une classe peut avoir plusieurs maquettes)
    @OneToMany(mappedBy = "classe")
    private List<Maquette> maquettes;

    @ManyToMany(mappedBy = "classes")
    private List<UE> ues;

    @OneToMany(mappedBy = "classe")
    private List<Enseignement> enseignements;

    @OneToMany(mappedBy = "classe")
    private List<Seance> seances;

}
