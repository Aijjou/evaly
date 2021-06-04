package service.impl;

import javax.annotation.Resource;
import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

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

}
