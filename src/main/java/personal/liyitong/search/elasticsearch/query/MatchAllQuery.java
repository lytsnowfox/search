package personal.liyitong.search.elasticsearch.query;

import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.springframework.stereotype.Service;
import personal.liyitong.search.elasticsearch.param.ESQuery;

@Service("matchAllQuery")
public class MatchAllQuery extends CommonQuery {

    @Override
    protected void initQueryBuilder(SearchSourceBuilder builder, ESQuery param) {
        // 全文检索
        builder.query(QueryBuilders.matchAllQuery().queryName(param.getKeyword()));
    }
}