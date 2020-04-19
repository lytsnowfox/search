package personal.liyitong.search.elasticsearch.param;

import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.query.RangeQueryBuilder;
import org.elasticsearch.index.query.TermsQueryBuilder;
import org.elasticsearch.search.builder.SearchSourceBuilder;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 用于存储ES传递参数
 */
public class ESQuery {
    public static final String ASC = "asc";
    public static final String DESC = "desc";
    public static final String GT = "gt";
    public static final String GTE = "gte";
    public static final String LT = "lt";
    public static final String LTE = "lte";
    public static final String EQ = "eq";
    // 关键词，用户的输入
    private String keyword;
    // 当前登录用户账户
    private String account;
    // 待检索的字段
    private String[] keys;
    // 确定检索范围
    private String[] indices;
    // 分页信息
    private Map<String, Integer> page;
    // 排序信息，key: asc/desc
    private Map<String, String> order;
    // 构建terms参数
    private List<TermsParam> terms;
    // 构建过滤参数,key是字段名称
    private Map<String, Range> range;

    private BoolQueryBuilder boolBuilder;

    public ESQuery() {
        terms = new ArrayList<>();
        order = new HashMap<>();
        range = new HashMap<>();
        page = new HashMap<>();
    }

    //=================工具方法=================
    // 获取分页页码
    public Integer getPageIndex() {
        return page.get("pageIndex");
    }
    // 获取页面大小
    public Integer getPageSize() {
        return page.get("pageSize");
    }
    // 操作TermsQuery相关参数
    public ESQuery addTerm(TermsParam t) {
        terms.add(t);
        return this;
    }

    // 操作排序参数
    public ESQuery addOrder(String key, String sort) {
        if (ASC.equals(sort) || DESC.equals(sort)) {
            order.put(key, sort);
        }
        return this;
    }

    public ESQuery addRange(String field, String type, Object value) {
        if (range.get(field) == null) {
            range.put(field, new Range());
        }
        Range c = range.get(field);
        if (GT.equals(type) || GTE.equals(type) || EQ.equals(type)) {
            c.setMinType(type);
            c.setMinValue(value);
        } else if (LT.equals(type) || LTE.equals(type)) {
            c.setMaxType(type);
            c.setMaxValue(value);
        }
        return this;
    }
    /**
     * 使用boolQuery定义过滤器，目前使用must连接各个条件，后续视情拓展
     * @param builder
     */
    public void buildRange(SearchSourceBuilder builder) {
        if (!range.isEmpty()) {
            BoolQueryBuilder boolBuilder = getBoolBuilder();
            for (String field : range.keySet()) {
                RangeQueryBuilder rangeBuilder = QueryBuilders.rangeQuery(field);
                Range c = range.get(field);
                if (EQ.equals(c.getMinType())) {
                    rangeBuilder.gte(c.getMinValue()).lte(c.getMinValue());
                } else {
                    if (LT.equals(c.getMaxType())) {
                        rangeBuilder.lt(c.getMaxValue());
                    }
                    if (LTE.equals(c.getMaxType())) {
                        rangeBuilder.lte(c.getMaxValue());
                    }
                    if (GT.equals(c.getMinType())) {
                        rangeBuilder.gt(c.getMinValue());
                    }
                    if (GTE.equals(c.getMinType())) {
                        rangeBuilder.gte(c.getMinValue());
                    }
                }
                boolBuilder.must(rangeBuilder);
            }
            builder.postFilter(boolBuilder);
        }
    }

    public void buildTerms(SearchSourceBuilder builder) {
        if (!terms.isEmpty()) {
            BoolQueryBuilder boolBuilder = getBoolBuilder();
            for (TermsParam term : terms) {
                TermsQueryBuilder termsQueryBuilder = QueryBuilders.termsQuery(term.getField(), term.getValues());
                boolBuilder.must(termsQueryBuilder);
            }
            builder.postFilter(boolBuilder);
        }
    }
    //=================Getter/Setter=================
    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String[] getKeys() {
        return keys;
    }

    public void setKeys(String[] keys) {
        this.keys = keys;
    }

    public String[] getIndices() {
        return indices;
    }

    public void setIndices(String[] indices) {
        this.indices = indices;
    }

    public Map<String, Integer> getPage() {
        return page;
    }

    public void setPage(Map<String, Integer> page) {
        this.page = page;
    }

    public Map<String, String> getOrder() {
        return order;
    }

    public void setOrder(Map<String, String> order) {
        this.order = order;
    }

    public List<TermsParam> getTerms() {
        return terms;
    }

    public void setTerms(List<TermsParam> terms) {
        this.terms = terms;
    }

    public Map<String, Range> getRange() {
        return range;
    }

    public void setRange(Map<String, Range> range) {
        this.range = range;
    }

    public synchronized BoolQueryBuilder getBoolBuilder() {
        if (boolBuilder == null) {
            boolBuilder = QueryBuilders.boolQuery();
        }
        return boolBuilder;
    }
}
