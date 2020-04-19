package personal.liyitong.search.elasticsearch.query;

import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.springframework.stereotype.Service;
import personal.liyitong.search.elasticsearch.param.ESQuery;
import personal.liyitong.search.elasticsearch.param.TermsParam;

@Service("termsQuery")
public class TermsQuery extends CommonQuery {

    @Override
    protected void initQueryBuilder(SearchSourceBuilder builder, ESQuery param) {
        if (param.getTerms() != null) {
            for (TermsParam t: param.getTerms()) {
                builder.query(QueryBuilders.termsQuery(t.getField(), t.getValues()));
            }
        }
    }
}