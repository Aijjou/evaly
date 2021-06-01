package service.impl;

import java.util.List;

import javax.annotation.Resource;
import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import model.Matiere;
import model.Theme;
import repository.ThemeRepository;
import service.ThemeService;



@Service
@Transactional
public class ThemeServiceImpl implements ThemeService{

	@Resource
	ThemeRepository themeRepository;

	@Override
	public List<Theme> findThemesByMat(Matiere matiere) {
		return themeRepository.findByMatiere(matiere);
	}

	@Override
	public List<Theme> findAllThemes() {
		return (List<Theme>) themeRepository.findAll();
	}

	@Override
	public Theme findThemesById(Integer id) {
		return themeRepository.findByIdTheme(id);
	}

}
