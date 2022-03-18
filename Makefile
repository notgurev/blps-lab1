.PHONY: build helios run clean dev prod build

all:
	@echo hi

helios:
	ssh -p2222 -Y -L 5432:pg:5432 s286528@se.ifmo.ru

dev: target/blps.jar
	./mvnw clean package -Pdev

prod: target/blps.jar
	./mvnw clean package -Pprod

build: dev

clean:
	rm -rf target

run:
	java -jar target/blps.jar