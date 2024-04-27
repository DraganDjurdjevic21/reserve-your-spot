package com.prodyna.reserveyourspot.repository;

import com.prodyna.reserveyourspot.model.WorkStation;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.neo4j.repository.query.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface WorkStationRepository extends Neo4jRepository<WorkStation, Long> {

    @Query("MATCH (ws:WorkStation) " +
            "CALL { " +
            "WITH ws " +
            "OPTIONAL MATCH (ws)<-[r:RESERVATION_OF_WORK_STATION]-(re:Reservation) " +
            "RETURN r,re } " +
            "RETURN ws,collect(ID(re)) as reservationReferences")
    List<WorkStation> getAll();

    @Query("MATCH (ws:WorkStation) where ID(ws) = $workStationId " +
            "CALL { " +
            "WITH ws " +
            "OPTIONAL MATCH (ws)<-[r:RESERVATION_OF_WORK_STATION]-(re:Reservation) " +
            "RETURN r,re } " +
            "RETURN ws,collect(ID(re)) as reservationReferences")
    WorkStation getWorkStationById(@Param("workStationId") Long workStationId);

    @Query("MATCH (ws:WorkStation)-[r:WORK_STATION_OF]->(or:OfficeRoom) where ID(or) = $officeRoomId  " +
            "CALL { " +
            "WITH ws " +
            "OPTIONAL MATCH (ws)<-[rw:RESERVATION_OF_WORK_STATION]-(re:Reservation) " +
            "RETURN rw,re } " +
            "RETURN ws,rw, collect(ID(re)) as reservationReferences")
    List<WorkStation> getWorkStationByOfficeRoom(@Param("officeRoomId") Long officeRoomId);


    @Query("Match (or:OfficeRoom) where ID(or)=$officeRoomId WITH or " +
            "CREATE(ws:WorkStation{description:$description, uniqueCode:$uniqueCode, equipment:$equipment}) " +
            "CREATE (or)<-[r:WORK_STATION_OF]-(ws) RETURN or,r,ws")
    WorkStation createWorkStationByOfficeRoom(@Param("officeRoomId") Long officeRoomId, @Param("description") String description,
                                              @Param("uniqueCode") String uniqueCode, @Param("equipment") String equipment);

    @Query("MATCH (ws:WorkStation)-[r:WORK_STATION_OF]->(or:OfficeRoom) where ID(ws) = $workStationId  " +
            "OPTIONAL MATCH (ws)<-[r:RESERVATION_OF_WORK_STATION]-(re:Reservation) " +
            "set ws.description = $description, ws.equipment = $equipment " +
            "RETURN ws,r, collect(ID(re)) as reservationReferences")
    WorkStation updateWorkStation(@Param("workStationId") Long workStationId, @Param("description") String description,
                                  @Param("equipment") String equipment);

    @Query("MATCH (ws:WorkStation{uniqueCode:$uniqueCode})" +
            "OPTIONAL MATCH (ws)<-[r:RESERVATION_OF_WORK_STATION]-(re:Reservation) " +
            "RETURN ws, collect(ID(re)) as reservationReferences")
    List<WorkStation> getWorkStationByUniqueCode(@Param("uniqueCode") String uniqueCode);

    @Query("MATCH (ws:WorkStation) where ID(ws)=$workStationId " +
            "OPTIONAL MATCH (ws)<-[rr:RESERVATION_OF_WORK_STATION]-(re:Reservation) " +
            "DETACH DELETE ws,re")
    void delete(@Param("workStationId") Long workStationId);
}
