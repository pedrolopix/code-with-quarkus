.PHONY: help
.DEFAULT_GOAL := help

help: ## Prints this help message and exits.
	@grep -E '^[a-zA-Z_-]+:.*?## .*$$' $(MAKEFILE_LIST) | sort | awk 'BEGIN {FS = ":.*?## "}; {printf "\033[36m%-30s\033[0m %s\n", $$1, $$2}'


dev: ## Starts the chat-service in develop mode
	mvn compile -pl :chat-service quarkus:dev

dev-injector: ## Starts the injector in develop mode
	mvn compile -pl :chat-injector quarkus:dev -Ddebug=5006

build: ## Cleans and builds the project
	mvn clean package

start: ## Starts docker containers.
	docker-compose up --build -d

stop-chat: ## Stop chat-service container.
	docker stop chat-service -t 300

logs: ## Shows docker container logs.
	docker-compose logs -f

build-docker: ## Build the chat-service docker image.
	docker build -f Dockerfile .

inject-dev: ## Inject users and messages to chat-service running on dev mode
	java -jar chat-injector/target/chat-injector-*-runner.jar --url=ws://localhost:8080/chat

inject: ## Inject 5 users and 100 messages to chat-service running on docker
	java -jar chat-injector/target/chat-injector-*-runner.jar --url=ws://localhost:8088/chat -m=100 --users=5
