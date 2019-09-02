package personal.liyitong.search.entity;

public abstract class NodeBaseEntity extends AllBaseEntity {

    /**
     * 在图数据库中每个节点展示的名字
     */
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
