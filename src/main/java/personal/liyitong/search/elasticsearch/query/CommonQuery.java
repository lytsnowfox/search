package personal.liyitong.search.elasticsearch.query;

import com.alibaba.fastjson.JSONArray;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.search.SearchType;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.core.CountRequest;
import org.elasticsearch.client.core.CountResponse;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.sort.SortOrder;
import org.springframework.beans.factory.annotation.Autowired;
import personal.liyitong.search.elasticsearch.param.ESQuery;

import java.io.IOException;
import java.util.Map;

/**
 * 通用ElasticSearch查询，采用模板方法模式
 * 使用时支持Spring注入或运行时自定义内部类继承，如需自定义，请自行为highLevelClient赋值
 */
public abstract class CommonQuery {

    @Autowired
    protected RestHighLevelClient highLevelClient;

    public CommonQuery(){}

    public CommonQuery(RestHighLevelClient highLevelClient) {
        this.highLevelClient = highLevelClient;
    }

    public void setQueryParam(ESQuery param, SearchSourceBuilder builder) {
        // 设置分页
        if (param.getPage() != null) {
            Integer pageSize = param.getPageSize();
            Integer pageIndex = param.getPageIndex();
            builder.from((pageIndex - 1) * pageSize).size(pageSize);
        }
        // 设置排序
        if (param.getOrder() != null) {
            for (String key: param.getOrder().keySet()) {
                String order = param.getOrder().get(key);
                if (ESQuery.ASC.equals(order)) {
                    builder.sort(key, SortOrder.ASC);
                } else if (ESQuery.DESC.equals(order)) {
                    builder.sort(key, SortOrder.DESC);
                } else {
                    continue;
                }
            }
        }
        // 设置过滤器
        if (!param.getRange().isEmpty()) {
            param.buildRange(builder);
        }
        if (!param.getTerms().isEmpty()) {
            param.buildTerms(builder);
        }
    }

    public JSONArray commonQueryData(ESQuery param) {
        SearchRequest request = new SearchRequest();
        SearchSourceBuilder builder = new SearchSourceBuilder();
        // 子类实现，确定检索类型
        initQueryBuilder(builder, param);
        setQueryParam(param, builder);
        // 设置分片
        if (param.getIndices() != null) {
            request.indices(param.getIndices());
        }
        request.source(builder);
        request.searchType(SearchType.DFS_QUERY_THEN_FETCH);
        SearchResponse searchResponse = null;
        try {
            searchResponse = highLevelClient.search(request, RequestOptions.DEFAULT);
        } catch (IOException e) {
            e.printStackTrace();
        }
        JSONArray result = new JSONArray();
        SearchHits searchHits = searchResponse.getHits();
        SearchHit[] hits = searchHits.getHits();
        for (SearchHit hit: hits) {
            Map<String, Object> map = hit.getSourceAsMap();
            map.put("_id", hit.getId());
            map.put("_index", hit.getIndex());
            result.add(map);
        }
        return result;
    }

    public long commonQueryCount(ESQuery param) {
        CountRequest countRequest = new CountRequest();
        SearchSourceBuilder builder = new SearchSourceBuilder();
        // 子类实现，确定检索类型
        initQueryBuilder(builder, param);
        // 设置分片
        if (param.getIndices() != null) {
            countRequest.indices(param.getIndices());
        }
        countRequest.source(builder);
        CountResponse response = null;
        try {
            response = highLevelClient.count(countRequest, RequestOptions.DEFAULT);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return response.getCount();
    }

    protected abstract void initQueryBuilder(SearchSourceBuilder builder, ESQuery param);
}
