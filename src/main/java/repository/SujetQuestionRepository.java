package repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import model.Sujet;
import model.SujetQuestion;


public interface SujetQuestionRepository extends CrudRepository<SujetQuestion, Integer>{
	
	List<SujetQuestion> findBySujet(Sujet sujet);

}
