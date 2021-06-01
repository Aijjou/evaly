package service;

import java.util.List;
import java.util.Optional;

import model.Theme;

public interface ThemeService {
	

	public List<Theme> themes();

	void save(Theme t);

	public Optional<Theme> findById(Integer id);

}
