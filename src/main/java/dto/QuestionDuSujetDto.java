package dto;

import java.util.List;

import model.Question;

public class QuestionDuSujetDto {
	
	List<Question> questionsFromSujet;

	public List<Question> getQuestionsFromSujet() {
		return questionsFromSujet;
	}

	public void setQuestionsFromSujet(List<Question> questionsFromSujet) {
		this.questionsFromSujet = questionsFromSujet;
	}

}
