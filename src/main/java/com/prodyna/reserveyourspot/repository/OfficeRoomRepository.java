package com.prodyna.reserveyourspot.repository;

import com.prodyna.reserveyourspot.model.OfficeRoom;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.neo4j.repository.query.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface OfficeRoomRepository extends Neo4jRepository<OfficeRoom, Long> {

    @Query("MATCH(or:OfficeRoom) " +
            "CALL { " +
            "WITH or " +
            "OPTIONAL MATCH (or)<-[r:WORK_STATION_OF]-(ws:WorkStation) " +
            "RETURN r,ws }" +
            "RETURN or,collect(ID(ws)) as workStationReferences")
    List<OfficeRoom> getAll();

    @Query("MATCH(or:OfficeRoom) where ID(or) = $officeRoomId " +
            "CALL { " +
            "WITH or " +
            "OPTIONAL MATCH (or)<-[r:WORK_STATION_OF]-(ws:WorkStation) " +
            "RETURN r,ws }" +
            "RETURN or, collect(ID(ws)) as workStationReferences")
    OfficeRoom getOfficeRoomById(@Param("officeRoomId") Long officeRoomId);


    @Query("MATCH (os:OfficeSpace) where ID(os)=$officeSpaceId " +
            "MATCH (os) <-[r:ROOM_OF]-(or:OfficeRoom) " +
            "OPTIONAL MATCH (or)<-[rw:WORK_STATION_OF]-(ws:WorkStation) " +
            "WITH or, ws " +
            "RETURN or, collect(ID(ws)) as workStationReferences")
    List<OfficeRoom> getOfficeRoomsByOfficeSpaceId(@Param("officeSpaceId") Long officeSpaceId);

    @Query("MATCH (os:OfficeSpace) where ID(os)=$officeSpaceId WITH os " +
            "CREATE(or:OfficeRoom{name:$name, uniqueCode:$uniqueCode}) " +
            "CREATE (os)<-[r:ROOM_OF]-(or) " +
            "RETURN or")
    OfficeRoom createByOfficeSpaceId(@Param("officeSpaceId") Long officeSpaceId, @Param("name") String name,
                                     @Param("uniqueCode") Long uniqueCode);

    @Query("MATCH (or:OfficeRoom) where ID(or)=$officeRoomId " +
            "OPTIONAL MATCH (or)<-[r:WORK_STATION_OF]-(ws:WorkStation) " +
            "set or.name=$name " +
            "RETURN or,collect(r),collect(ID(ws)) as workStationReferences")
    OfficeRoom updateOfficeRoomNameById(@Param("officeRoomId") Long officeRoomId, @Param("name") String name);

    @Query("MATCH (or:OfficeRoom) where ID(or)=$officeRoomId " +
            "OPTIONAL MATCH (or)<-[rw:WORK_STATION_OF]-(ws:WorkStation) " +
            "OPTIONAL MATCH (ws)<-[rr:RESERVATION_OF_WORK_STATION]-(re:Reservation) " +
            "DETACH DELETE or,ws,re")
    void delete(@Param("officeRoomId") Long officeRoomId);
}
