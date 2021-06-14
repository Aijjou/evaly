package service;

import java.util.Optional;

import model.Organisation;

public interface OrganisationService {

	public Organisation create(Organisation organisation);
	public Optional<Organisation> findOrganisation(Integer idAdmin);
	
	
}
