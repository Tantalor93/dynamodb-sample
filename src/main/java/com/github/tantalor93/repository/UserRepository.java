package com.github.tantalor93.repository;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import com.github.tantalor93.entity.User;

public interface UserRepository extends CrudRepository<User, String> {

	Optional<User> findByLastName(String lastName);
}
