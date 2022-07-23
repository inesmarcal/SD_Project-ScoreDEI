 Para que a plataforma arranque é necessário ter uma uma base de dados com as seguintes configurações:
	
	url=jdbc:postgresql://localhost:5432/scoredei  --> url onde a base de dados está a correr, sendo o localhost:5432/ o local onde o postgresSQL está a correr e o scoredei a base de dados
	username=postgres
	password=postgres

Ou em alternativa alterar o ficheiro

	./projeto/src/main/resources/application.properties

A plataforma está disponivel no endereço:
	
	http://localhost:8080/

Através do endpoint / será possivel criar um administrador com as seguintes credenciais:
	
	Email:admin@gmail.com
	Pass:admin

Através do endpoint /generate-data será possivel adicionar 5 equipas à base de dados. Estas já se encontram definidas no código.

Execução através do scoreDei.war: java -jar scoreDei.war