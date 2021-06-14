package repository;

import java.util.List;

import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;

import model.Formateur;
import model.Promotion;
import model.PromotionFormateur;

public interface PromotionFormateurRepository extends CrudRepository<PromotionFormateur, Integer> {

	public List<PromotionFormateur> findByPromotion(Promotion promotion);

	public List<PromotionFormateur> findByFormateur(Formateur formateur);

	

}
