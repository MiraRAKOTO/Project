package mg.douane.intervention.service;

import mg.douane.intervention.data.domaine.Categorie;
import mg.douane.intervention.repository.CategorieRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CategorieServiceImpl implements CategorieService {
    @Autowired
    CategorieRepository categoriRepository;

    @Override
    public Iterable<Categorie> getAllSousCategories() {
        return categoriRepository.findAllSousCategories();
    }

    @Override
    public Iterable<Categorie> getAllSousSousCategories() {
        // return sousCategoriRepository.findAllSousSousCategories();
        return null;
    }

    @Override
    public List<Categorie> getAllSousCategoriesByCat(Long id) {
        Optional<Categorie> categorie = categoriRepository.findById(id);

        if (categorie.isPresent()) {
            List<Categorie> cat = categoriRepository.findAllByCat(categorie.get());

            return cat;
        }
        return null;
    }

    @Override
    public List<Categorie> getAllSousSousCategoriesBySous(Long id) {
        Optional<Categorie> sousCategorie = categoriRepository.findById(id);
        if (sousCategorie.isPresent())
            return categoriRepository.findAllByScats(sousCategorie.get());
        return null;
    }

    @Override
    public Iterable<Categorie> getAllCategories() {

        return categoriRepository.findAllCategories();
    }
}
