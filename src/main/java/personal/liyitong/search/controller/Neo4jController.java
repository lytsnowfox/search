package personal.liyitong.search.controller;

import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import personal.liyitong.search.service.Neo4jService;

@RestController
@RequestMapping("/neo4j")
public class Neo4jController {

    @Autowired
    private Neo4jService neo4jService;

    @RequestMapping("/buildCommonGraph")
    public JSONObject buildCommonGraph(@RequestBody JSONObject subject) {
        return neo4jService.buildCommonGraph(subject);
    }

    @RequestMapping("/expandOneNode")
    public JSONObject expandOneNode(String businessId) {
        return neo4jService.expandOneNode(businessId);
    }
}
