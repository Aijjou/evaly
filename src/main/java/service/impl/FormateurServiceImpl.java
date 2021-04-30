package service.impl;

import java.util.List;
import javax.annotation.Resource;
import javax.transaction.Transactional;
import org.springframework.stereotype.Service;
import model.Formateur;
import repository.FormateurRepository;
import service.FormateurService;

@Service
@Transactional
public class FormateurServiceImpl implements FormateurService {

	@Resource
	FormateurRepository formateurRepository;

	@Override
	public List<Formateur> formateurs() {

		return (List<Formateur>) formateurRepository.findAll();
	}

}
