package personal.liyitong.search.entity;

import org.neo4j.ogm.annotation.Id;
import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.Property;

import java.util.Objects;

@NodeEntity(label = "node")
public class Neo4jNode {

    @Id
    private String businessId;

    @Property
    private String name;

    @Property
    private String nodeType;

    @Property
    private String description;
    // 数据来源，如url或数据库链接地址
    @Property
    private String source;
    // 数据来源类型，互联网或数据库或其他
    @Property
    private String sourceType;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNodeType() {
        return nodeType;
    }

    public void setNodeType(String nodeType) {
        this.nodeType = nodeType;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getBusinessId() {
        return businessId;
    }

    public void setBusinessId(String businessId) {
        this.businessId = businessId;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getSourceType() {
        return sourceType;
    }

    public void setSourceType(String sourceType) {
        this.sourceType = sourceType;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Neo4jNode neo4jNode = (Neo4jNode) o;
        return businessId.equals(neo4jNode.businessId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(businessId);
    }

    public Echarts4Node convertToEchartsNode() {
        Echarts4Node node = new Echarts4Node();
        node.setId(this.businessId);
        node.setName(this.name);
        node.setNodeType(this.nodeType);
        node.setDescription(this.description);
        node.setDraggable(true);
        return node;
    }
}
