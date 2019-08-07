package personal.liyitong.search.neo4j;

import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.stereotype.Repository;
import personal.liyitong.search.entity.Person;

@Repository
public interface PersonRepository extends Neo4jRepository<Person, Long> {
}
