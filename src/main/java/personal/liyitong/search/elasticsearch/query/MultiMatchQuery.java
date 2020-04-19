package personal.liyitong.search.elasticsearch.query;

import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.springframework.stereotype.Service;
import personal.liyitong.search.elasticsearch.param.ESQuery;

@Service("multiMatchQuery")
public class MultiMatchQuery extends CommonQuery {

    @Override
    protected void initQueryBuilder(SearchSourceBuilder builder, ESQuery param) {
        String[] keys = param.getKeys();
        builder.query(QueryBuilders.multiMatchQuery(param.getKeyword(), keys));
    }
}