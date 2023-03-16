package mg.douane.intervention.data.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Setter
@Getter
public class ReponseDto {
    private String descriptionRep;
    private String dateEnvRep;
    private String expediteur;
}
