package mg.douane.intervention.service;

import mg.douane.intervention.data.domaine.Quartier;

import java.util.List;

public interface QuartierService {
    List<Quartier> getQuartierByVille(Long idVille);
}
