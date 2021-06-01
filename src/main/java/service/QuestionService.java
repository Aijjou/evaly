package service;

import java.util.List;

import model.Question;
import model.Theme;

public interface QuestionService {
	
	public List<Question> QuestionsByTheme(Theme theme);

	public Question findQuestionsById(Integer id);
	
}
