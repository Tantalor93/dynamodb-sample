package com.github.tantalor93.entity;

import java.util.Arrays;
import java.util.Objects;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBIndexHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBIndexRangeKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapperFieldModel;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTyped;

@DynamoDBTable(tableName = "users")
public class User {

	private String id;
	private String firstName;
	private String lastName;
	private byte[] data;

	public User() {
	}

	public User(String id, String firstName, String lastName, byte[] data) {
		this.id = id;
		this.firstName = firstName;
		this.lastName = lastName;
		this.data = data;
	}

	@DynamoDBHashKey(attributeName = "id")
	public String getId() {
		return id;
	}

	@DynamoDBAttribute(attributeName = "firstName")
	@DynamoDBIndexRangeKey(attributeName = "firstName", globalSecondaryIndexName = "LastNameIndex")
	public String getFirstName() {
		return firstName;
	}

	@DynamoDBAttribute(attributeName = "lastName")
	@DynamoDBIndexHashKey(attributeName = "lastName", globalSecondaryIndexName = "LastNameIndex")
	public String getLastName() {
		return lastName;
	}

	@DynamoDBAttribute(attributeName = "data")
	@DynamoDBTyped(DynamoDBMapperFieldModel.DynamoDBAttributeType.B)
	public byte[] getData() {
		return data;
	}

	public void setId(String id) {
		this.id = id;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public void setData(byte[] data) {
		this.data = data;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null || getClass() != obj.getClass()) {
			return false;
		}
		User other = (User) obj;
		return Objects.equals(id, other.id)
				&& Objects.equals(firstName, other.firstName)
				&& Objects.equals(lastName, other.lastName)
				&& Arrays.equals(data, other.data);
	}

	@Override
	public int hashCode() {
		return Objects.hash(id, firstName, lastName, data);
	}

	@Override
	public String toString() {
		return "User{" +
				"id='" + id + '\'' +
				", firstName='" + firstName + '\'' +
				", lastName='" + lastName + '\'' +
				", data=" + Arrays.toString(data) +
				'}';
	}
}
