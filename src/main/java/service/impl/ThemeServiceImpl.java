package service.impl;

import java.util.List;
import java.util.Optional;

import javax.annotation.Resource;
import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import model.Apprenant;
import model.Question;
import model.Theme;
import repository.ApprenantRepository;
import repository.QuestionRepository;
import repository.ThemeRepository;
import service.ApprenantService;
import service.QuestionService;
import service.ThemeService;



@Service
@Transactional
public class ThemeServiceImpl implements ThemeService{

	@Resource
	ThemeRepository themeRepository;
	
	
	@Override
	public List<Theme> themes() {
		
		return (List<Theme>) themeRepository.findAll();
	}

}
