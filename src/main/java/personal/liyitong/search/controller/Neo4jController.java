package personal.liyitong.search.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import personal.liyitong.search.entity.Person;
import personal.liyitong.search.neo4j.PersonRepository;

@RestController
@RequestMapping("/neo4j")
public class Neo4jController {

    @Autowired
    private PersonRepository personRepository;

    @RequestMapping("/addPerson")
    public Person addPerson() {
        Person person = new Person();
        person.setName("张小三");
        person.setBorn(1992);
        return personRepository.save(person);
    }
}
