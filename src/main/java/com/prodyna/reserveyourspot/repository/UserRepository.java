package com.prodyna.reserveyourspot.repository;

import com.prodyna.reserveyourspot.model.User;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.neo4j.repository.query.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * The implementation of Neo4jRepository for User object.
 *
 * @author Dragan Djurdjevic, PRODYNA SE
 * @see Neo4jRepository
 */
@Repository
public interface UserRepository extends Neo4jRepository<User, Long> {

    @Query("MATCH (u:User) " +
            "CALL { " +
            "WITH u " +
            "OPTIONAL MATCH (u)-[r:USER_RESERVATION]->(re:Reservation) " +
            "RETURN r,re }" +
            "RETURN u, collect(r), collect(ID(re)) as reservations")
    List<User> getAll();

    @Query("MATCH (u:User) where ID(u)=$userId " +
            "CALL { " +
            "WITH u " +
            "OPTIONAL MATCH (u)-[r:USER_RESERVATION]->(re:Reservation) " +
            "RETURN r,re }" +
            "RETURN u, collect(r), collect(ID(re)) as reservations")
    User getUserById(@Param("userId") Long userId);

    @Query("MATCH (u:User) where ID(u)=$userId " +
            "CALL { " +
            "WITH u " +
            "OPTIONAL MATCH (u)-[r:USER_RESERVATION]->(re:Reservation) " +
            "RETURN r,re }" +
            "set u.name=$name, u.email=$email, u.role=$role, u.password=$password " +
            "RETURN u, collect(r), collect(ID(re)) as reservations")
    User updateUser(@Param("userId") Long userId, @Param("name") String name,
                    @Param("email") String userName,
                    @Param("role") String role, @Param("password") String password);

    @Query("MATCH (u:User) where ID(u)=$userId set u.role=$role RETURN u")
    User updateRole(@Param("userId") Long userId, @Param("role") String role);

    @Query("MATCH(u:User{email:$email}) RETURN u")
    List<User> getUserByEmail(@Param("email") String userName);
}
