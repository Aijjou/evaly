package service.impl;

import java.util.List;

import javax.annotation.Resource;
import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import model.Sujet;
import model.SujetQuestion;
import repository.SujetQuestionRepository;
import service.SujetQuestionService;



@Service
@Transactional
public class SujetQuestionServiceImpl implements SujetQuestionService{

	@Resource
	SujetQuestionRepository sujetQuestionRepository;


	@Override
	public void save(SujetQuestion s) {
		sujetQuestionRepository.save(s);
	}


	@Override
	public List<SujetQuestion> findBySujet(Sujet sujet) {
		return sujetQuestionRepository.findBySujet(sujet);
	}


	@Override
	public void delete(SujetQuestion sq) {
		sujetQuestionRepository.delete(sq);
	}

}
