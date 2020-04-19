package personal.liyitong.search.service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import personal.liyitong.search.elasticsearch.param.ESQuery;

import java.util.Set;

public interface SearchService {

    boolean isDocExists(String index, String id);

    String insertData(String index, JSONArray dataList);

    String insertData(String index, String keyName, JSONArray dataList);

    String updateData(String index, String id, JSONObject data);

    boolean batchDeleteData(String index, String... ids);

    boolean deleteData(String index, String id);

    boolean dropIndex(String index);

    boolean createIndex(String index);

    Set<String> getAllIndexes();

    JSONArray matchAllQuery(ESQuery param);

    JSONArray multiMatchQuery(ESQuery param);

    JSONArray termsQuery(ESQuery param);

    long matchAllQueryCount(ESQuery queryParam);

    long multiMatchQueryCount(ESQuery queryParam);
}