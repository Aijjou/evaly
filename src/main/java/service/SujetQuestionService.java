package service;

import java.util.List;

import model.Sujet;
import model.SujetQuestion;

public interface SujetQuestionService {
	
	void save(SujetQuestion s);
	List<SujetQuestion> findBySujet(Sujet sujet);
	void delete(SujetQuestion sq);

}
