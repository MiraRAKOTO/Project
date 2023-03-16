package mg.douane.intervention.data.dto;

import lombok.Getter;
import lombok.Setter;
import java.util.Date;

@Getter
@Setter
public class HierarchieDto {
    private Long idHier;
    private String libelleHier;
    private Long idShier;
    private Long idType;
    private Date dateFinHier;
}
