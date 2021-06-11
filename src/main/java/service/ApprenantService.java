package service;

import java.util.List;
import java.util.Optional;

import javax.mail.MessagingException;
import javax.validation.Valid;

import dto.ApprenantDto;
import dto.ApprenantDtoFinal;
import model.Apprenant;
import model.Promotion;

public interface ApprenantService {

	public boolean delete(Integer isDelete);

	public List<Apprenant> apprenants();

	public List<Apprenant> apprenantsByPromotion(Promotion promotion);

	public Optional<Apprenant> findById(Integer id);

	void save(Apprenant t);

	Apprenant createApprenantParFormateur(ApprenantDto apprenantDto);

}
