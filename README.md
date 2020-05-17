create tables
```
aws dynamodb create-table --endpoint-url http://localhost:8000 --cli-input-json file://create-table-users.json
aws dynamodb create-table --endpoint-url http://localhost:8000 --cli-input-json file://create-table-dogs.json
```