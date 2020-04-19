package personal.liyitong.search.elasticsearch.param;

/**
 * 用于支撑rangeQuery过滤器，限制结果的取值范围
 */
public class Range {
    private String maxType;
    private String minType;
    private Object maxValue;
    private Object minValue;

    public String getMaxType() {
        return maxType;
    }

    public void setMaxType(String maxType) {
        this.maxType = maxType;
    }

    public String getMinType() {
        return minType;
    }

    public void setMinType(String minType) {
        this.minType = minType;
    }

    public Object getMaxValue() {
        return maxValue;
    }

    public void setMaxValue(Object maxValue) {
        this.maxValue = maxValue;
    }

    public Object getMinValue() {
        return minValue;
    }

    public void setMinValue(Object minValue) {
        this.minValue = minValue;
    }
}
