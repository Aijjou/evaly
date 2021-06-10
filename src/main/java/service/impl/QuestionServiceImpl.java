package service.impl;

import java.util.List;
import java.util.Optional;

import javax.annotation.Resource;
import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import model.Apprenant;
import model.Matiere;
import model.Question;
import model.Theme;
import repository.ApprenantRepository;
import repository.QuestionRepository;
import service.ApprenantService;
import service.QuestionService;



@Service
@Transactional
public class QuestionServiceImpl implements QuestionService{

	@Resource
	QuestionRepository questionRepository;
	
	
	@Override
	public List<Question> QuestionsByTheme(Theme theme) {
		return questionRepository.findByTheme(theme);
	}
	
	@Override
	public List<Question> QuestionsByMatiere(Matiere matiere) {
		return questionRepository.findByMatiere(matiere);
	}


	@Override
	public void save(Question q) {
		// TODO Auto-generated method stub
		questionRepository.save(q);
	}


	@Override
	public Optional<Question> findById(Integer id) {
		return questionRepository.findById(id);
	}


	@Override
	public List<Question> questions() {
		return (List<Question>) questionRepository.findAll();
	}

}
