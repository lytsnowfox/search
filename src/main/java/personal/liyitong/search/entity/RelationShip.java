package personal.liyitong.search.entity;

import java.util.Objects;

/**
 * 用于描述知识图谱的关系，由知识图谱的节点实体类生成
 */
public class RelationShip {

    private String name;

    private String start;

    private String startId;

    private String startDes;

    private String end;

    private String endId;

    private String endDes;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStart() {
        return start;
    }

    public void setStart(String start) {
        this.start = start;
    }

    public String getStartId() {
        return startId;
    }

    public void setStartId(String startId) {
        this.startId = startId;
    }

    public String getStartDes() {
        return startDes;
    }

    public void setStartDes(String startDes) {
        this.startDes = startDes;
    }

    public String getEnd() {
        return end;
    }

    public void setEnd(String end) {
        this.end = end;
    }

    public String getEndId() {
        return endId;
    }

    public void setEndId(String endId) {
        this.endId = endId;
    }

    public String getEndDes() {
        return endDes;
    }

    public void setEndDes(String endDes) {
        this.endDes = endDes;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RelationShip that = (RelationShip) o;
        return name.equals(that.name) &&
                startId.equals(that.startId) &&
                endId.equals(that.endId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, startId, endId);
    }
}
