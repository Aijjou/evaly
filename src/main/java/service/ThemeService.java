package service;

import java.util.List;

import model.Matiere;
import java.util.Optional;

import model.Theme;

public interface ThemeService {
	
	public List<Theme> themes();

	public List<Theme> findThemesByMat(Matiere matiere);

	void save(Theme t);

	public Optional<Theme> findById(Integer id);

}
