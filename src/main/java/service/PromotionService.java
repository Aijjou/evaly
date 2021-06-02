package service;

import java.util.List;
import java.util.Optional;

import model.Promotion;
import model.Theme;

public interface PromotionService {
	

	public List<Promotion> promotions();

	void save(Promotion t);

	public Optional<Promotion> findById(Integer id);

}
