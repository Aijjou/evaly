package service;

import java.util.List;
import java.util.Optional;

import model.Formateur;
import model.Promotion;
import model.PromotionFormateur;
import model.Theme;

public interface PromotionService {

	public List<Promotion> promotions();

	void save(Promotion t);

	public Optional<Promotion> findById(Integer id);

	public List<PromotionFormateur> findByPromotion(Promotion promotion);

	public List<PromotionFormateur> findByFormateur(Formateur formateur);

}
