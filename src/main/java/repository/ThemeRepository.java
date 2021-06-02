package repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import model.Matiere;
import model.Theme;


public interface ThemeRepository extends CrudRepository<Theme, Integer>{
	
	List<Theme> findByMatiere(Matiere matiere);
	Theme findByIdTheme(Integer id);

}
