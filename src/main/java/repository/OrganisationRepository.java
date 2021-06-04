package repository;

import org.springframework.data.repository.CrudRepository;

import model.Organisation;

public interface OrganisationRepository extends CrudRepository<Organisation, Integer> {

}
