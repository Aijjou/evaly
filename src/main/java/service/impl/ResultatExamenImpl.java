package service.impl;

import java.util.List;
import java.util.Optional;

import javax.annotation.Resource;
import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import model.ResultatExamen;
import repository.ResultatExamenRepository;
import service.ResultatExamenService;



@Service
@Transactional
public class ResultatExamenImpl implements ResultatExamenService{

	@Resource
	ResultatExamenRepository resultatExamenRepository;
	
	
	@Override
	public List<ResultatExamen> resultatsExamens() {
		
		
		
		return (List<ResultatExamen>) resultatExamenRepository.findAll();
	}


	@Override
	public Optional<ResultatExamen> findById(Integer id) {
		return resultatExamenRepository.findById(id);
	}
	
	@Override
	public void save(ResultatExamen s) {
		resultatExamenRepository.save(s);

	}

}
