package service.impl;

import java.util.List;
import java.util.Optional;

import javax.annotation.Resource;
import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import model.Apprenant;
import model.Examen;
import model.ReponseApprenantExamen;
import repository.ReponseApprenantExamenRepository;
import service.ReponseApprenantExamenService;



@Service
@Transactional
public class ReponseApprenantExamenImpl implements ReponseApprenantExamenService{

	@Resource
	ReponseApprenantExamenRepository reponseApprenantExamenRepository;
	
	
	@Override
	public List<ReponseApprenantExamen> reponseApprenantExamens() {
		return (List<ReponseApprenantExamen>) reponseApprenantExamenRepository.findAll();
	}


	@Override
	public Optional<ReponseApprenantExamen> findById(Integer id) {
		return reponseApprenantExamenRepository.findById(id);
	}


	@Override
	public void save(ReponseApprenantExamen t) {
		reponseApprenantExamenRepository.save(t);
		
	}


	@Override
	public List<ReponseApprenantExamen> findByApprenantAndExamen(Apprenant a, Examen e) {
		return reponseApprenantExamenRepository.findByApprenantAndExamen(a,e);
	}

}
