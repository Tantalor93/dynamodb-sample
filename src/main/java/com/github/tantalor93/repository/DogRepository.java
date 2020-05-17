package com.github.tantalor93.repository;

import org.socialsignin.spring.data.dynamodb.repository.DynamoDBCrudRepository;
import org.socialsignin.spring.data.dynamodb.repository.EnableScan;

import com.github.tantalor93.entity.Dog;
import com.github.tantalor93.entity.DogId;

@EnableScan
public interface DogRepository extends DynamoDBCrudRepository<Dog, DogId> {
}
