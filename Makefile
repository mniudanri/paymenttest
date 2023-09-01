network:
	docker network create testenv

app_dependencies:
	mvn clean install -DskipTests

app_build:
	rm -rf target
	mvn clean package -DskipTests
	docker-compose build --no-cache

app_run:
	java -jar .\target\paymenttest-0.0.1-SNAPSHOT.jar

app_build_package:
	mvn clean package -DskipTests

clear_build_app:
	docker builder prune

refresh_db_data:
	sh ./refresh_sb.sh

createdb:
	docker exec -it posgresdb createdb --username=root --owner=root postgres

dropdb:
	docker exec -it posgresdb dropdb postgres

.PHONY: network app_dependencies run_app app_build