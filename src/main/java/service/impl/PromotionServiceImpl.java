package service.impl;

import java.util.List;
import java.util.Optional;

import javax.annotation.Resource;
import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import model.Formateur;
import model.Promotion;
import model.PromotionFormateur;
import repository.PromotionFormateurRepository;
import repository.PromotionRepository;
import service.PromotionService;

@Service
@Transactional
public class PromotionServiceImpl implements PromotionService {

	@Resource
	PromotionRepository promotionRepository;

	@Resource
	PromotionFormateurRepository promotionFormateurRepository;

	@Override
	public List<Promotion> promotions() {

		return (List<Promotion>) promotionRepository.findAll();
	}

	@Override
	public Optional<Promotion> findById(Integer id) {

		return promotionRepository.findById(id);
	}

	@Override
	public void save(Promotion s) {
		promotionRepository.save(s);

	}

	@Override
	public List<PromotionFormateur> findByPromotion(Promotion promotion) {

		return promotionFormateurRepository.findByPromotion(promotion);
	}

	@Override
	public List<PromotionFormateur> findByFormateur(Formateur formateur) {
		
		return promotionFormateurRepository.findByFormateur(formateur);
	}
	


}
