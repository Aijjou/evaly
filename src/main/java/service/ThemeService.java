package service;

import java.util.List;

import model.Matiere;
import model.Theme;

public interface ThemeService {
	
	public List<Theme> findAllThemes();

	public List<Theme> findThemesByMat(Matiere matiere);
	
	public Theme findThemesById(Integer id);

}
