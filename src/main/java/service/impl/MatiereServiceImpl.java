package service.impl;

import java.util.List;
import java.util.Optional;

import javax.annotation.Resource;
import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import model.Matiere;
import repository.MatiereRepository;
import service.MatiereService;



@Service
@Transactional
public class MatiereServiceImpl implements MatiereService{

	@Resource
	MatiereRepository matiereRepository;
	
	
	@Override
	public Optional<Matiere> findById(Integer id){
		
		return matiereRepository.findById(id);
	}

	@Override
	public List<Matiere> matieres() {
		return (List<Matiere>) matiereRepository.findAll();
	}
	
	@Override
	public void save(Matiere m) {
		matiereRepository.save(m);
		
	}


}
