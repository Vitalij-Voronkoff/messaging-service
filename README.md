# Simple messaging service

A simple REST service for messaging between users.

## Requirements:

1. JDK 11
2. Docker

## How to run service:

1. `docker build --tag=messaging-service:latest .`
2. `docker-compose up`

## What can the service:

- Create new user:
```json
curl --location --request POST 'http://localhost:8080/v1/messages/users' \
--header 'Content-Type: application/json' \
--data-raw '{
    "nickname": "user 1"
}'
```

- Send message:
```json
curl --location --request POST 'http://localhost:8080/v1/messages' \
--header 'Content-Type: application/json' \
--data-raw '{
    "sender_id": 1,
    "recipient_id": 2,
    "message": "Hello world!"
}'
```

- View all received messages:
```json
curl --location --request GET 'http://localhost:8080/v1/messages/recipients/2'
```

- View all sent messages:
```json
curl --location --request GET 'http://localhost:8080/v1/messages/senders/1'
```

- View all received messages from the particular user:
```json
curl --location --request GET 'http://localhost:8080/v1/messages/recipients/2?senderId=1'
```

## What can be improved:

- Since service is super simplified interfaces were omitted
- Domain model
- Increase test coverage
- Can be added indexes to increase search speed
- Error handling