package personal.liyitong.search.entity;

import com.alibaba.fastjson.JSONObject;

public class Echarts4Link implements Cloneable {

    private String source;

    private String target;

    private Integer category;

    private Integer symbolSize;

    private String value;

    private JSONObject lineStyle;

    public Echarts4Link() {
        category = 0;
        symbolSize = 10;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getTarget() {
        return target;
    }

    public void setTarget(String target) {
        this.target = target;
    }

    public Integer getCategory() {
        return category;
    }

    public void setCategory(Integer category) {
        this.category = category;
    }

    public Integer getSymbolSize() {
        return symbolSize;
    }

    public void setSymbolSize(Integer symbolSize) {
        this.symbolSize = symbolSize;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public JSONObject getLineStyle() {
        return lineStyle;
    }

    public void setLineStyle(JSONObject lineStyle) {
        this.lineStyle = lineStyle;
    }

    /**
     * 创建镜像关系，用于生成双向关系
     * @return
     */
    public Echarts4Link copyMirror() {
        Echarts4Link mirror = (Echarts4Link)this.clone();
        mirror.setTarget(this.source);
        mirror.setSource(this.target);
        return mirror;
    }

    @Override
    public Object clone() {
        Echarts4Link l = null;
        try {
            l = (Echarts4Link)super.clone();
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
        return l;
    }
}
