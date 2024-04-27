package com.prodyna.reserveyourspot.repository;

import com.prodyna.reserveyourspot.model.Reservation;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.neo4j.repository.query.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface ReservationRepository extends Neo4jRepository<Reservation, Long> {

    @Query("MATCH(re:Reservation) " +
            "CALL { " +
            "WITH re " +
            "MATCH (re) -[rws:RESERVATION_OF_WORK_STATION]-> (ws:WorkStation) " +
            "MATCH (ws) -[ror:WORK_STATION_OF]-> (or:OfficeRoom) " +
            "MATCH (or) -[ros:ROOM_OF]-> (os:OfficeSpace) " +
            "RETURN ws,or,os,rws }" +
            "RETURN re, ws.uniqueCode as workStation,ID(or) as officeRoomId,ID(os) as officeSpaceId")
    List<Reservation> getAll();

    @Query("MATCH(re:Reservation) where ID(re)=$reservationId " +
            "CALL { " +
            "WITH re " +
            "MATCH (re) -[rws:RESERVATION_OF_WORK_STATION]-> (ws:WorkStation) " +
            "MATCH (ws) -[ror:WORK_STATION_OF]-> (or:OfficeRoom) " +
            "MATCH (or) -[ros:ROOM_OF]-> (os:OfficeSpace) " +
            "RETURN ws,or,os,rws }" +
            "RETURN re, ws.uniqueCode as workStation,ID(or) as officeRoomId,ID(os) as officeSpaceId")
    Reservation getById(@Param("reservationId") Long reservationId);

    @Query("MATCH (ws:WorkStation{uniqueCode:$workStationCode}) " +
            "MATCH (ws) -[ror:WORK_STATION_OF]-> (or:OfficeRoom) " +
            "MATCH (or) -[ros:ROOM_OF]-> (os:OfficeSpace) " +
            "MATCH (u:User{email:$userEmail}) " +
            "CREATE (rz:Reservation{userEmail:u.email, userName:u.name, workStationCode:ws.uniqueCode, date:$date}) " +
            "CREATE (ws)<-[rws:RESERVATION_OF_WORK_STATION]-(rz) " +
            "CREATE (u)-[ru:USER_RESERVATION]->(rz) " +
            "RETURN rz, ws.uniqueCode as workStation,ID(or) as officeRoomId,ID(os) as officeSpaceId")
    Reservation createByWorkStation(@Param("workStationCode") String workStationCode, @Param("userEmail") String userEmail,
                       @Param("date") LocalDate date);

    @Query("MATCH(r:Reservation{workStationCode:$workStationCode, date:$date}) return r")
    List<Reservation> isWorkStationAvailableForDate(@Param("workStationCode") String workStationCode, @Param("date") LocalDate date);
}
