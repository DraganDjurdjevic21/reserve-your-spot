# Reserve your spot service

An example of a RESTful Web Server developed using Spring Boot.
This server acts a Location-Service - it will return all details needed to reserve you spot.

Requirements
The fully fledged server uses the following:

* SpringBoot 2.6.3
* Neo4j 4.4.3
* Dependencies
* There are a number of third-party dependencies used in the project. Browse the Maven pom.xml file for details of libraries and versions used.

Building the project
You will need:

* Java JDK 11 or higher
* Maven 3.2 or higher
* Docker
* Git
* Clone the project and use Maven to build the server

Run Neo4j:
* `docker pull neo4j:latest`
* `docker run --name=neo4j -p=7474:7474 -p=7687:7687  -d --volume=$HOME/neo4j/data:/data  --env NEO4J_AUTH=neo4j/test neo4j`
* `docker logs -f neo4j`


Run Spirng Boot with:
 * `mvn clean install spring-boot:run`

Swagger link
* `/swagger-ui.html#/