package mg.douane.intervention.data.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FichePosteDto {
    private Long idFich;
    private String nameAgent;
    private String hierarchie;
    private String fonction;
    private String numMatr;

}
