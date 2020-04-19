package personal.liyitong.search.service;

import com.alibaba.fastjson.JSONObject;
import personal.liyitong.search.entity.Neo4jLink;
import personal.liyitong.search.entity.Neo4jNode;
import personal.liyitong.search.repository.Neo4jLinkRepository;
import personal.liyitong.search.repository.Neo4jNodeRepository;

import java.util.Set;

public interface Neo4jService {

    JSONObject buildCommonGraph(JSONObject subject);

    JSONObject expandOneNode(String businessId);

    JSONObject convertToEcharts(Set<Neo4jNode> nodes, Set<Neo4jLink> links);

    Neo4jLinkRepository getLinkRepository();

    Neo4jNodeRepository getNodeRepository();
}
