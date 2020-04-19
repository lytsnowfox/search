package personal.liyitong.search.repository;

import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.stereotype.Repository;
import personal.liyitong.search.entity.Neo4jLink;

import java.util.List;

@Repository
public interface Neo4jLinkRepository extends Neo4jRepository<Neo4jLink, Long> {

    List<Neo4jLink> findByStartId(String startId);

    List<Neo4jLink> findByEndId(String endId);

    List<Neo4jLink> findByStartIdAndLinkType(String startId, String linkType);

    List<Neo4jLink> findByEndIdAndLinkType(String endId, String linkType);
}