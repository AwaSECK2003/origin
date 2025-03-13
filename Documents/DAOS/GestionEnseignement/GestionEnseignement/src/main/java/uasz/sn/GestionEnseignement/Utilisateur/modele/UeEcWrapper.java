package uasz.sn.GestionEnseignement.Utilisateur.modele;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@Data
@NoArgsConstructor
public class UeEcWrapper {
    private List<UE> ues;

    public List<EC> getEcs() {
        List<EC> ecsList = new ArrayList<>();
        if (ues != null) {
            for (UE ue : ues) {
                if (ue.getEcs() != null) {
                    ecsList.addAll(ue.getEcs());
                }
            }
        }
        return ecsList;
    }
}
