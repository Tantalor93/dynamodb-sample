package com.github.tantalor93.entity;

import org.springframework.data.annotation.Id;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBRangeKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable;

@DynamoDBTable(tableName = "dogs")
public class Dog {

	@Id
	private DogId dogId;

	public Dog() {
	}

	public Dog(String name, String ownerId) {
		dogId = new DogId();
		dogId.setName(name);
		dogId.setOwnerId(ownerId);
	}

	@DynamoDBHashKey(attributeName = "name")
	public String getName() {
		return dogId == null ? null : dogId.getName();
	}

	@DynamoDBRangeKey(attributeName = "ownerId")
	public String getOwnerId() {
		return dogId == null ? null : dogId.getOwnerId();
	}

	public void setOwnerId(String ownerId) {
		if (dogId == null) {
			dogId = new DogId();
		}
		dogId.setOwnerId(ownerId);
	}

	public void setName(String name) {
		if (dogId == null) {
			dogId = new DogId();
		}
		dogId.setName(name);
	}

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;

		Dog dog = (Dog) o;

		return dogId != null ? dogId.equals(dog.dogId) : dog.dogId == null;
	}

	@Override
	public int hashCode() {
		return dogId != null ? dogId.hashCode() : 0;
	}

	@Override
	public String toString() {
		return "Dog{" +
				"dogId=" + dogId +
				'}';
	}
}
