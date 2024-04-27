package com.prodyna.reserveyourspot.repository;

import com.prodyna.reserveyourspot.model.OfficeSpace;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.neo4j.repository.query.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface OfficeSpaceRepository extends Neo4jRepository<OfficeSpace, Long> {

    @Query("MATCH(os:OfficeSpace) " +
            "CALL { " +
            "WITH os " +
            "OPTIONAL MATCH (os)<-[r:ROOM_OF]-(or:OfficeRoom) " +
            "RETURN r,or }" +
            "RETURN os, collect(ID(or)) as officeRoomReferences")
    List<OfficeSpace> getAll();

    @Query("MATCH(os:OfficeSpace) where ID(os) = $officeSpaceId " +
            "CALL { " +
            "WITH os " +
            "OPTIONAL MATCH (os)<-[r:ROOM_OF]-(or:OfficeRoom) " +
            "RETURN r,or }" +
            "RETURN os, collect(ID(or)) as officeRoomReferences")
    OfficeSpace getOfficeSpaceById(@Param("officeSpaceId") Long officeSpaceId);

    @Query("MATCH(os:OfficeSpace) where ID(os) = $officeSpaceId " +
            "OPTIONAL MATCH (os)<-[r:ROOM_OF]-(or:OfficeRoom) " +
            "set os.name=$name , os.description = $description " +
            "RETURN os, collect(ID(or)) as officeRoomReferences")
    OfficeSpace updateOfficeSpace(@Param("officeSpaceId") Long officeSpaceId, @Param("name") String name,
                                  @Param("description") String description);

    @Query("MATCH(os:OfficeSpace) where ID(os) = $officeSpaceId " +
            "OPTIONAL MATCH (os)<-[r:ROOM_OF]-(or:OfficeRoom) " +
            "OPTIONAL MATCH (or)<-[rw:WORK_STATION_OF]-(ws:WorkStation) " +
            "OPTIONAL MATCH (ws)<-[rr:RESERVATION_OF_WORK_STATION]-(re:Reservation) " +
            "DETACH DELETE re,ws,or,os")
    void delete(@Param("officeSpaceId") Long officeSpaceId);

    @Query("MATCH(os:OfficeSpace{name:$name}) RETURN os")
    OfficeSpace getOfficeSpaceByName(@Param("name") String name);
}
