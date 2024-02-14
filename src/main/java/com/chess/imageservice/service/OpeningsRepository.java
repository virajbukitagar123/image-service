package com.chess.imageservice.service;

import com.chess.backend.dto.Position;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.neo4j.repository.query.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OpeningsRepository extends Neo4jRepository<Position, Long> {

    @Query("MATCH (root:Position {type: \"start\"}) " +
            "WITH root " +
            "MATCH path = (root)-[:MOVED_TO*]->(leaf:Position) " +
            "WHERE ()-[:MOVED_TO]->(leaf) AND $opening IN leaf.openings " +
            "RETURN nodes(path) AS nodes_in_order " +
            "ORDER BY length(path) DESC " +
            "LIMIT 1")
    List<Position> findPositionsByOpening(String opening);

}
