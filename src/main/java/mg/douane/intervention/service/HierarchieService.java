package mg.douane.intervention.service;

import mg.douane.intervention.data.domaine.Hierarchie;

import java.util.List;

public interface HierarchieService {

    List<Hierarchie> getHierarchieByTypeHierarchie(Long idType);
}
