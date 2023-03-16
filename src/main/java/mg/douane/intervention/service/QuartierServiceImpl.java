package mg.douane.intervention.service;


import mg.douane.intervention.data.domaine.Quartier;
import mg.douane.intervention.data.domaine.Ville;
import mg.douane.intervention.repository.QuartierRepository;
import mg.douane.intervention.repository.VilleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class QuartierServiceImpl implements QuartierService{
    @Autowired
    QuartierRepository quartierRepository;

    @Autowired
    VilleRepository villeRepository;

    @Override
    public List<Quartier> getQuartierByVille(Long idVille) {
        Optional<Ville> ville = villeRepository.findById(idVille);
        if(ville.isPresent())
            return quartierRepository.findByVille(ville.get());
        return null;
    }
}
