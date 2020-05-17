package com.github.tantalor93.entity;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBRangeKey;

public class DogId {

	private String name;
	private String ownerId;

	public DogId(String name, String ownerId) {
		this.name = name;
		this.ownerId = ownerId;
	}

	public DogId() {
	}

	@DynamoDBHashKey(attributeName = "name")
	public String getName() {
		return name;
	}

	@DynamoDBRangeKey(attributeName = "ownerId")
	public String getOwnerId() {
		return ownerId;
	}

	public void setOwnerId(String ownerId) {
		this.ownerId = ownerId;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;

		DogId dogId = (DogId) o;

		if (ownerId != null ? !ownerId.equals(dogId.ownerId) : dogId.ownerId != null)
			return false;
		return name != null ? name.equals(dogId.name) : dogId.name == null;
	}

	@Override
	public int hashCode() {
		int result = ownerId != null ? ownerId.hashCode() : 0;
		result = 31 * result + (name != null ? name.hashCode() : 0);
		return result;
	}

	@Override
	public String toString() {
		return "DogId{" +
				"ownerId='" + ownerId + '\'' +
				", name='" + name + '\'' +
				'}';
	}
}
