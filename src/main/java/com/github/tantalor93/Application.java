package com.github.tantalor93;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.socialsignin.spring.data.dynamodb.repository.config.EnableDynamoDBRepositories;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBSaveExpression;
import com.amazonaws.services.dynamodbv2.document.BatchWriteItemOutcome;
import com.amazonaws.services.dynamodbv2.document.DynamoDB;
import com.amazonaws.services.dynamodbv2.model.ConditionalCheckFailedException;
import com.amazonaws.services.dynamodbv2.model.ExpectedAttributeValue;
import com.amazonaws.services.dynamodbv2.model.WriteRequest;
import com.github.tantalor93.entity.Dog;
import com.github.tantalor93.entity.User;
import com.github.tantalor93.repository.DogRepository;
import com.github.tantalor93.repository.UserRepository;

@SpringBootApplication
@EnableDynamoDBRepositories
public class Application implements CommandLineRunner {

	private static final Logger LOGGER = LoggerFactory.getLogger(Application.class);

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private DogRepository dogRepository;

	@Autowired
	private DynamoDBMapper dynamoDBMapper;

	@Autowired
	private AmazonDynamoDB amazonDynamoDB;

	public static void main(String[] args) {
		SpringApplication.run(Application.class);
	}

	@Override
	public void run(String... args) throws Exception {
		User user = userRepository.save(new User("1", "ondra", "benky", "mydata".getBytes()));
		LOGGER.info("Saved '{}'", user);

		Optional<User> byId = userRepository.findById("1");
		LOGGER.info("Found '{}'", byId.get());

		Optional<User> benky = userRepository.findByLastName("benky");
		LOGGER.info("Found by last name '{}'", benky.get());

		Dog berry = new Dog("Berry", "1");
		dogRepository.save(berry);

		Iterable<Dog> dogs = dogRepository.findAll();
		System.out.println(dogs);

		saveIfNotExists();

		batchWrite();
	}

	private void batchWrite() {
		User ulan = new User("2", "ulan", "batar", "anotherdata".getBytes());
		User opa = new User("3", "opa", "gangam", "anotherdata".getBytes());

		DynamoDB dynamoDB = new DynamoDB(amazonDynamoDB);
		List<DynamoDBMapper.FailedBatch> failedBatches = dynamoDBMapper.batchSave(ulan, opa);
		for (DynamoDBMapper.FailedBatch failedBatch : failedBatches) {
			Map<String, List<WriteRequest>> unprocessedItems = failedBatch.getUnprocessedItems();
			do {
				if (!unprocessedItems.isEmpty()) {
					BatchWriteItemOutcome batchWriteItemOutcome = dynamoDB
							.batchWriteItemUnprocessed(unprocessedItems);
					unprocessedItems = batchWriteItemOutcome.getUnprocessedItems();
				}
			} while (!unprocessedItems.isEmpty());
		}

		Iterable<User> all = userRepository.findAll();
		LOGGER.info("Found '{}' users", all);
	}

	private void saveIfNotExists() {
		User johny = new User("1", "johny", "praskac", "anotherdata".getBytes());
		DynamoDBSaveExpression dynamoDBSaveExpression = new DynamoDBSaveExpression();
		ExpectedAttributeValue expectedAttributeValue = new ExpectedAttributeValue(false);
		dynamoDBSaveExpression.setExpected(Map.of("id", expectedAttributeValue));
		try {
			dynamoDBMapper.save(johny, dynamoDBSaveExpression);
		} catch (ConditionalCheckFailedException ex) {
			LOGGER.info("ID is not unique!");
		}
	}
}
