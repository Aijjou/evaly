package repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import model.Apprenant;
import model.Promotion;

public interface ApprenantRepository extends CrudRepository<Apprenant, Integer> {

	List<Apprenant> findByPromotion(Promotion promotion);


}
