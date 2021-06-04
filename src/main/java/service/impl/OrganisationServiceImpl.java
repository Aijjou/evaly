package service.impl;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import model.Organisation;
import repository.OrganisationRepository;
import service.OrganisationService;

@Service
@Transactional
public class OrganisationServiceImpl implements OrganisationService {

	@Autowired
	OrganisationRepository organisationRepository;

	@Override
	public Organisation create(Organisation organisation) {

		return organisationRepository.save(organisation);
	}

}
