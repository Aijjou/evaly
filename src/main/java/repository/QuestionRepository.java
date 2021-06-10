package repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import model.Matiere;
import model.Question;
import model.Theme;


public interface QuestionRepository extends CrudRepository<Question, Integer>{
	
	List<Question> findByTheme(Theme theme);
	Question findByIdQuestion(Integer id);
	List<Question> findByMatiere(Matiere matiere);

}
