package personal.liyitong.search.elasticsearch.param;

public class TermsParam {
    //要限制的字段名称
    private String field;
    // 限制字段的取值范围
    private String[] values;

    public String getField() {
        return field;
    }

    public void setField(String field) {
        this.field = field;
    }

    public String[] getValues() {
        return values;
    }

    public void setValues(String[] values) {
        this.values = values;
    }
}
