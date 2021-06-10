package service;

import java.util.List;
import java.util.Optional;

import model.Matiere;
import model.Question;
import model.Theme;

public interface QuestionService {
	
	public List<Question> QuestionsByTheme(Theme theme);
	
	public List<Question> QuestionsByMatiere(Matiere matiere);
	
	public List<Question> questions();
	
	public void save (Question q);
	
	public Optional<Question> findById(Integer id);

}
