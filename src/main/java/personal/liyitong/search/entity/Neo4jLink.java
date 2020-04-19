package personal.liyitong.search.entity;

import org.neo4j.ogm.annotation.*;

import java.util.Objects;

@RelationshipEntity(type = "relation")
public class Neo4jLink {

    @Id
    private String linkId;

    @StartNode
    private Neo4jNode start;

    @EndNode
    private Neo4jNode end;

    @Property
    private String name;

    @Property
    private String linkType;

    @Property
    private String description;

    @Property
    private String startId;

    @Property
    private String endId;

    public String getLinkId() {
        return linkId;
    }

    public void setLinkId(String linkId) {
        this.linkId = linkId;
    }

    public Neo4jNode getStart() {
        return start;
    }

    public void setStart(Neo4jNode start) {
        this.start = start;
    }

    public Neo4jNode getEnd() {
        return end;
    }

    public void setEnd(Neo4jNode end) {
        this.end = end;
    }

    public String getName() {
        return name;
    }

    public String getLinkType() {
        return linkType;
    }

    public void setLinkType(String linkType) {
        this.linkType = linkType;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getStartId() {
        return startId;
    }

    public void setStartId(String startId) {
        this.startId = startId;
    }

    public String getEndId() {
        return endId;
    }

    public void setEndId(String endId) {
        this.endId = endId;
    }

    public RelationShip getRelation() {
        RelationShip relation = new RelationShip();
        relation.setName(this.name);
        relation.setStartId(this.startId);
        relation.setStart(this.start.getName());
        relation.setStartDes(this.start.getDescription());
        relation.setEndId(this.endId);
        relation.setEnd(this.end.getName());
        relation.setEndDes(this.end.getDescription());
        return relation;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Neo4jLink neo4jLink = (Neo4jLink) o;
        return name.equals(neo4jLink.name) &&
                startId.equals(neo4jLink.startId) &&
                endId.equals(neo4jLink.endId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, startId, endId);
    }

    public Echarts4Link convertToEchartsLink() {
        Echarts4Link link = new Echarts4Link();
        link.setSource(this.startId);
        link.setTarget(this.endId);
        link.setValue(this.name);
        return link;
    }
}
