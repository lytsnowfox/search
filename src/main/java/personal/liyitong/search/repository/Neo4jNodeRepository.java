package personal.liyitong.search.repository;

import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import personal.liyitong.search.entity.Neo4jNode;

import java.util.List;

@Repository
public interface Neo4jNodeRepository extends Neo4jRepository<Neo4jNode, Long> {

    @Query("match (n:node)-[r:relation]->(m:node) where n.businessId = {businessId} return m")
    List<Neo4jNode> getOutterNodes(@Param("businessId") String businessId);

    @Query("match (m:node)-[r:relation]->(n:node) where n.businessId = {businessId} return m")
    List<Neo4jNode> getInnerNodes(@Param("businessId") String businessId);

    @Query("match (n:node)-[r:relation]->(m:node) where n.businessId = {businessId} and m.nodeType = {type} return m")
    List<Neo4jNode> getOutterNodesByType(@Param("businessId") String businessId, @Param("type") String type);

    @Query("match (m:node)-[r:relation]->(n:node) where n.businessId = {businessId} and m.nodeType = {type} return m")
    List<Neo4jNode> getInnerNodesByType(@Param("businessId") String businessId, @Param("type") String type);

    Neo4jNode findByBusinessId(String businessId);
}