network:
	docker network create testenv

app_dependencies:
	mvn clean install -DskipTests

app_build:
	mvn clean package -DskipTests
	docker-compose build --no-cache

app_run:
	docker-compose up

app_build_package:
	mvn clean package -DskipTests

clear_build_app:
	docker builder prune

.PHONY: network app_dependencies run_app run_build