package uasz.sn.GestionEnseignement.Utilisateur.modele;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Enseignement {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private int totalHeures;

    @Enumerated(EnumType.STRING) // Correspond à ENUM dans la base de données
    @Column(nullable = true)
    private TypeEnseignement type;


    // Relation ManyToOne avec Maquette (chaque enseignement est lié à une maquette)
    @ManyToOne
    @JoinColumn(name = "maquette_id", nullable = false)
    private Maquette maquette;

    // Relation ManyToOne avec Classe (chaque enseignement est associé à une seule classe)
    @ManyToOne
    @JoinColumn(name = "classe_id", nullable = false)
    private Classe classe;

    // Relation ManyToOne avec Semestre (chaque enseignement est associé à un semestre)
    @ManyToOne
    @JoinColumn(name = "semestre_id", nullable = false)
    private Semestre semestre;

    @ManyToOne
    @JoinColumn(name = "ec_id", nullable = false)
    private EC ec;

   @OneToMany(mappedBy = "enseignement", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Repartition> repartitions;

    @OneToMany(mappedBy = "enseignement")
    private List<Seance> seances;


}
