package personal.liyitong.search.service.impl;

import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import personal.liyitong.search.entity.Echarts4Link;
import personal.liyitong.search.entity.Echarts4Node;
import personal.liyitong.search.entity.Neo4jLink;
import personal.liyitong.search.entity.Neo4jNode;
import personal.liyitong.search.entity.Collection;
import personal.liyitong.search.repository.Neo4jLinkRepository;
import personal.liyitong.search.repository.Neo4jNodeRepository;
import personal.liyitong.search.service.Neo4jService;

import java.util.*;

@Service
public class Neo4jServiceImpl implements Neo4jService {

    @Autowired
    private Neo4jLinkRepository linkRepository;

    @Autowired
    private Neo4jNodeRepository nodeRepository;

    @Override
    public JSONObject buildCommonGraph(JSONObject subject) {
        JSONObject data = subject.getJSONObject("subject");
        List<Collection> collections = subject.getJSONArray("metadata").toJavaList(Collection.class);
        Set<Neo4jLink> links = new HashSet<>();
        Map<String, Neo4jNode> nodeMap = new HashMap<>();
        for (Collection c : collections) {
            if ("nodes".equals(c.getName())) {
                List<Neo4jNode> nodes = data.getJSONArray(c.getId()).toJavaList(Neo4jNode.class);
                for (Neo4jNode node : nodes) {
                    nodeMap.put(node.getBusinessId(), node);
                }
            } else if ("links".equals(c.getName())) {
                // link依赖于node
                links.addAll(data.getJSONArray(c.getId()).toJavaList(Neo4jLink.class));
            }
        }
        Set<Neo4jLink> insertLinks = new HashSet<>();
        for (Neo4jLink link : links) {
            Neo4jNode start = nodeMap.get(link.getStartId());
            Neo4jNode end = nodeMap.get(link.getEndId());
            if (start != null && end != null) {
                link.setStart(start);
                link.setEnd(end);
                if (link.getLinkId() == null) {
                    link.setLinkId(String.valueOf(link.hashCode()));
                }
                // 只插入有效的关系
                insertLinks.add(link);
            }
        }
        linkRepository.saveAll(insertLinks);
        JSONObject result = new JSONObject();
        result.put("status", "success");
        return result;
    }

    @Override
    public JSONObject expandOneNode(String businessId) {
        Set<Neo4jNode> nodes = new HashSet<>();
        Neo4jNode root = nodeRepository.findByBusinessId(businessId);
        if (root == null) {
            return null;
        }
        nodes.add(root);
        nodes.addAll(nodeRepository.getInnerNodes(businessId));
        nodes.addAll(nodeRepository.getOutterNodes(businessId));
        Set<Neo4jLink> links = new HashSet<>();
        links.addAll(linkRepository.findByStartId(businessId));
        links.addAll(linkRepository.findByEndId(businessId));
        return convertToEcharts(nodes, links);
    }
    /**
     * echarts4的前台图谱包括nodes和links两部分，且不允许有重复id的数据。
     * 对于节点之间的关系，source和target之间的id建议使用字符串，如果使用
     * 数字则代表节点在数组中的索引号，并不是真实值，不了解这一点很可能导致
     * 节点之间的连线无法显示
     */
    @Override
    public JSONObject convertToEcharts(Set<Neo4jNode> nodes, Set<Neo4jLink> links) {
        Set<Echarts4Node> echarts4Nodes = new HashSet<>();
        Set<Echarts4Link> echarts4Links = new HashSet<>();
        for (Neo4jNode node : nodes) {
            echarts4Nodes.add(node.convertToEchartsNode());
        }
        for (Neo4jLink link : links) {
            echarts4Links.add(link.convertToEchartsLink());
        }
        JSONObject data = new JSONObject();
        data.put("nodes", echarts4Nodes);
        data.put("links", echarts4Links);
        return data;
    }

    @Override
    public Neo4jLinkRepository getLinkRepository() {
        return linkRepository;
    }

    @Override
    public Neo4jNodeRepository getNodeRepository() {
        return nodeRepository;
    }
}
