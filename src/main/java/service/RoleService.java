package service;

import java.util.Optional;

import model.Role;

public interface RoleService {

	Optional<Role> findById(Integer id);

	Role create(Role role);

}
