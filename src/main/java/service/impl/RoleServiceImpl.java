package service.impl;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import model.Role;
import repository.RoleRepository;
import service.RoleService;



@Service
public class RoleServiceImpl implements RoleService{

	
	@Autowired
	RoleRepository roleRepository;
	
	
	@Override
	public Optional<Role> findById(Integer id) {
		
		return roleRepository.findById(id);
	}

	@Override
	public Role create(Role role) {
		
		return roleRepository.save(role);
	}



}
