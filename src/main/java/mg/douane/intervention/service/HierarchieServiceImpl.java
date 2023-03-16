package mg.douane.intervention.service;

import mg.douane.intervention.data.domaine.Hierarchie;
import mg.douane.intervention.data.domaine.TypeHierarchie;
import mg.douane.intervention.repository.HierarchieRepository;
import mg.douane.intervention.repository.TypeHierarchieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class HierarchieServiceImpl implements HierarchieService{
    @Autowired
    TypeHierarchieRepository typeHierarchieRepository;

    @Autowired
    HierarchieRepository hierarchieRepository;

    @Override
    public List<Hierarchie> getHierarchieByTypeHierarchie(Long idType) {
        Optional<TypeHierarchie> typeHierarchie = typeHierarchieRepository.findById(idType);
        if(typeHierarchie.isPresent())
            return hierarchieRepository.findByType(typeHierarchie.get());
        return null;
    }
}
