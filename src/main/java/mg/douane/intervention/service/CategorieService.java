package mg.douane.intervention.service;

import mg.douane.intervention.data.domaine.Categorie;

import java.util.List;

public interface CategorieService {
    public Iterable<Categorie> getAllCategories();

    public Iterable<Categorie> getAllSousCategories();

    public Iterable<Categorie> getAllSousSousCategories();

    public List<Categorie> getAllSousCategoriesByCat(Long id);

    public List<Categorie> getAllSousSousCategoriesBySous(Long id);

}
