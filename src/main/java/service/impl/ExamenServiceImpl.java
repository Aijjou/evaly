package service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import model.Examen;
import repository.ExamenRepository;
import service.ExamenService;


@Service
@Transactional
public class ExamenServiceImpl implements ExamenService{

	@Resource
	private ExamenRepository examenRepository;
	
	@Override
	public List<Examen> getListExamen() {
		
		return (List<Examen>) examenRepository.findAll();
	}
	
	
	
	
	

}
