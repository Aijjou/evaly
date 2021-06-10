package service.impl;

import java.util.List;
import java.util.Optional;

import javax.annotation.Resource;
import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import model.Reponse;
import repository.ReponseRepository;
import service.ReponseService;



@Service
@Transactional
public class ReponseServiceImpl implements ReponseService{

	@Resource
	ReponseRepository reponseRepository;
	
	
	@Override
	public List<Reponse> reponses() {
		
		return (List<Reponse>) reponseRepository.findAll();
	}
	
	@Override
	public Optional<Reponse> findById(Integer id){
		
		return reponseRepository.findById(id);
	}

	@Override
	public void save(Reponse t) {
		reponseRepository.save(t);
		
	}
	
	@Override
	public void deleteById(Integer id) {
		reponseRepository.deleteById(id);
		
	}

	@Override
	public void delete(Reponse r) {
		reponseRepository.delete(r);
		
	}

}
