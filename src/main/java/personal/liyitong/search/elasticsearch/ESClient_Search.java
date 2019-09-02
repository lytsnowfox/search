package personal.liyitong.search.elasticsearch;

import org.elasticsearch.action.bulk.BulkRequest;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.search.SearchType;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.aggregations.AggregationBuilder;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.aggregations.bucket.terms.Terms;
import org.elasticsearch.search.aggregations.bucket.terms.TermsAggregationBuilder;
import org.elasticsearch.search.aggregations.metrics.Sum;
import org.elasticsearch.search.aggregations.metrics.SumAggregationBuilder;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.sort.SortOrder;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class ESClient_Search extends ESClient {

    public void insertRandomData() {
        BulkRequest request = new BulkRequest();
        for (int i=1; i<=5; i++) {
            Map<String, Object> jObj = new HashMap<>();
            jObj.put("name", "liyitong");
            jObj.put("course", "科目" + i);
            jObj.put("score", 90 + i);
            request.add(new IndexRequest("student").id(String.valueOf(i+5))
                    .source(jObj));
        }
        try {
            client.bulk(request, RequestOptions.DEFAULT);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                client.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void testSearch(String keyword) {
        SearchRequest request = new SearchRequest();
        SearchSourceBuilder builder = new SearchSourceBuilder();
        builder.query(QueryBuilders.matchQuery("info", keyword))
                .from(0).size(5)
                .sort("age", SortOrder.DESC)
                .postFilter(QueryBuilders.rangeQuery("age").from(21).to(23))
                .explain(true);
        request.indices("user");
        request.source(builder);
        request.searchType(SearchType.DFS_QUERY_THEN_FETCH);
        SearchResponse searchResponse = null;
        try {
            searchResponse = client.search(request, RequestOptions.DEFAULT);
        } catch (IOException e) {
            e.printStackTrace();
        }
        SearchHits searchHits = searchResponse.getHits();
        SearchHit[] hits = searchHits.getHits();
        for (SearchHit hit: hits) {
            System.out.println(hit);
        }
    }

    public Map<String, Object> multiIndexSearch() {
        Map<String, Object> result = new HashMap<>();
        SearchRequest request = new SearchRequest();
        SearchSourceBuilder builder = new SearchSourceBuilder();
        builder.query(QueryBuilders.matchAllQuery());
        request.indices("posts", "posts2");
        //request.indices("posts*");
        request.source(builder);
        SearchResponse searchResponse = null;
        try {
            searchResponse = client.search(request, RequestOptions.DEFAULT);
        } catch (IOException e) {
            e.printStackTrace();
        }
        SearchHits searchHits = searchResponse.getHits();
        SearchHit[] hits = searchHits.getHits();
        for (SearchHit hit: hits) {
            result.put(hit.getId(), hit.getSourceAsString());
        }
        return result;
    }

    public Map<Object, Long> testAggregation() {
        SearchRequest request = new SearchRequest();
        SearchSourceBuilder builder = new SearchSourceBuilder();
        // 定义一个聚合，包括名称和聚合字段
        TermsAggregationBuilder aggregation = AggregationBuilders.terms("by_age").field("age");
        builder.query(QueryBuilders.matchAllQuery()).aggregation(aggregation);
        request.indices("user");
        request.source(builder);
        SearchResponse searchResponse = null;
        try {
            searchResponse = client.search(request, RequestOptions.DEFAULT);
        } catch (IOException e) {
            e.printStackTrace();
        }
        Terms terms = searchResponse.getAggregations().get("by_age");
        Map<Object, Long> result = new HashMap<>();
        for (Terms.Bucket entry : terms.getBuckets()) {
            result.put(entry.getKey(), entry.getDocCount());
        }
        return result;
    }


    public Map<Object,Double> testComplexAgr() {
        SearchRequest request = new SearchRequest();
        SearchSourceBuilder builder = new SearchSourceBuilder();
        // 定义一个聚合，包括名称和聚合字段
        /* name.keyword解決以下异常：
            Elasticsearch exception [type=illegal_argument_exception, reason=Fielddata is disabled on text fields by default.
            Set fielddata=true on [name] in order to load fielddata in memory by uninverting the inverted index.
            Note that this can however use significant memory. Alternatively use a keyword field instead.]
         */
        TermsAggregationBuilder nameAgr = AggregationBuilders.terms("by_name").field("name.keyword");
        // 设置子聚合
        SumAggregationBuilder sumAgr = AggregationBuilders.sum("by_score").field("score");
        nameAgr.subAggregation(sumAgr);
        builder.query(QueryBuilders.matchAllQuery()).aggregation(nameAgr);
        request.indices("student");
        request.source(builder);
        SearchResponse searchResponse = null;
        try {
            searchResponse = client.search(request, RequestOptions.DEFAULT);
        } catch (IOException e) {
            e.printStackTrace();
        }
        Terms terms = searchResponse.getAggregations().get("by_name");
        Map<Object, Double> result = new HashMap<>();
        for (Terms.Bucket entry : terms.getBuckets()) {
            Sum sum = entry.getAggregations().get("by_score");
            result.put(entry.getKey(), sum.getValue());
        }
        return result;
    }
}
