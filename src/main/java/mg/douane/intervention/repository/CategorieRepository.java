package mg.douane.intervention.repository;

import mg.douane.intervention.data.domaine.Categorie;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CategorieRepository extends CrudRepository<Categorie, Long> {

    @Query("SELECT c FROM Categorie c WHERE c.cat IS NULL and c.dateFinCat is null")
    List<Categorie> findAllCategories();

    @Query("SELECT c FROM Categorie c WHERE c.cat IS NOT NULL and c.dateFinCat is null ")
    List<Categorie> findAllSousCategories();

    List<Categorie> findAllByCat(Categorie categorie);

    List<Categorie> findAllByScats(Categorie sousCategorie);

    @Query("SELECT c FROM Categorie c WHERE lower(c.libelleCat) LIKE %:libelle%")
    List<Categorie> findByLibelleCat(@Param("libelle") String libelle);
}
