package com.prodyna.reserveyourspot;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.neo4j.harness.ServerControls;
import org.neo4j.harness.TestServerBuilders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.neo4j.DataNeo4jTest;
import org.springframework.data.neo4j.core.Neo4jClient;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataNeo4jTest
class Neo4jRepositoryTest {

    private static ServerControls embeddedDatabaseServer;

    @BeforeAll
    static void initializeNeo4j() {

        embeddedDatabaseServer = TestServerBuilders.newInProcessBuilder()
                .newServer();
    }

    @AfterAll
    static void stopNeo4j() {

        embeddedDatabaseServer.close();
    }

    @DynamicPropertySource
    static void neo4jProperties(DynamicPropertyRegistry registry) {

        registry.add("spring.neo4j.uri", embeddedDatabaseServer::boltURI);
        registry.add("spring.neo4j.authentication.username", () -> "neo4");
        registry.add("spring.neo4j.authentication.password", () -> "test");
    }

    @Test
    public void findSomethingShouldWork(@Autowired Neo4jClient client) {
        Optional<Long> result = client.query("MATCH (n) RETURN COUNT(n)")
                .fetchAs(Long.class)
                .one();
        assertThat(result).hasValue(0L);
    }
}
