package service.impl;

import java.util.List;
import java.util.Optional;

import javax.annotation.Resource;
import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import model.Apprenant;
import model.Question;
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
	public List<Question> questions() {
		
		return (List<Question>) questionRepository.findAll();
	}


	@Override
	public void save(Question q) {
		// TODO Auto-generated method stub
		questionRepository.save(q);
	}


	@Override
	public Optional<Question> findById(Integer id) {
		// TODO Auto-generated method stub
		return questionRepository.findById(id);
	}

}
