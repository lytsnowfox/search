package personal.liyitong.search.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.elasticsearch.action.admin.indices.alias.get.GetAliasesRequest;
import org.elasticsearch.action.admin.indices.delete.DeleteIndexRequest;
import org.elasticsearch.action.bulk.BulkItemResponse;
import org.elasticsearch.action.bulk.BulkRequest;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.action.delete.DeleteRequest;
import org.elasticsearch.action.get.GetRequest;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.action.update.UpdateResponse;
import org.elasticsearch.client.GetAliasesResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.indices.CreateIndexRequest;
import org.elasticsearch.cluster.metadata.AliasMetaData;
import org.elasticsearch.common.xcontent.XContentType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import personal.liyitong.search.elasticsearch.param.ESQuery;
import personal.liyitong.search.elasticsearch.query.CommonQuery;
import personal.liyitong.search.service.SearchService;

import java.io.IOException;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

@Service
public class SearchServiceImpl implements SearchService {

    @Autowired
    private RestHighLevelClient highLevelClient;

    @Autowired
    @Qualifier("matchAllQuery")
    private CommonQuery matchAllQuery;

    @Autowired
    @Qualifier("multiMatchQuery")
    private CommonQuery multiMatchQuery;

    @Autowired
    @Qualifier("termsQuery")
    private CommonQuery termsQuery;

    @Override
    public boolean isDocExists(String index, String id) {
        GetRequest getRequest = new GetRequest(index, id);
        boolean exists = false;
        try {
            exists = highLevelClient.exists(getRequest, RequestOptions.DEFAULT);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return exists;
    }

    @Override
    public String insertData(String index, JSONArray dataList) {
        return insertData(index, "id", dataList);
    }

    @Override
    public String insertData(String index, String keyName, JSONArray dataList) {
        BulkRequest request = new BulkRequest();
        for (int i = 0; i < dataList.size(); i++) {
            JSONObject obj = dataList.getJSONObject(i);
            String id = obj.containsKey(keyName) ? obj.getString(keyName) : UUID.randomUUID().toString();
            request.add(new IndexRequest(index).id(id).source(obj));
        }
        BulkResponse bulkResponse = null;
        try {
            bulkResponse = highLevelClient.bulk(request, RequestOptions.DEFAULT);
        } catch (IOException e) {
            e.printStackTrace();
            return e.getMessage();
        }
        if (bulkResponse.hasFailures()) {
            StringBuffer buffer = new StringBuffer();
            for (BulkItemResponse bulkItemResponse : bulkResponse) {
                if (bulkItemResponse.isFailed()) {
                    BulkItemResponse.Failure failure = bulkItemResponse.getFailure();
                    buffer.append(failure.getMessage());
                }
            }
            return buffer.toString();
        }
        return "success";
    }

    @Override
    public String updateData(String index, String id, JSONObject data) {
        UpdateRequest request = new UpdateRequest(index, id);
        request.doc(data, XContentType.JSON);
        UpdateResponse updateResponse = null;
        try {
            updateResponse = highLevelClient.update(request, RequestOptions.DEFAULT);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return updateResponse.getResult().toString();
    }

    @Override
    public boolean batchDeleteData(String index, String... ids) {
        BulkRequest request = new BulkRequest();
        for (String id : ids) {
            request.add(new DeleteRequest(index, id));
        }
        try {
            highLevelClient.bulk(request, RequestOptions.DEFAULT);
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    @Override
    public boolean deleteData(String index, String id) {
        DeleteRequest request = new DeleteRequest(index, id);
        try {
            highLevelClient.delete(request, RequestOptions.DEFAULT);
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    @Override
    public boolean dropIndex(String index) {
        DeleteIndexRequest request = new DeleteIndexRequest(index);
        try {
            highLevelClient.indices().delete(request, RequestOptions.DEFAULT);
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    @Override
    public boolean createIndex(String index) {
        CreateIndexRequest request = new CreateIndexRequest(index);
        try {
            highLevelClient.indices().create(request, RequestOptions.DEFAULT);
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    @Override
    public Set<String> getAllIndexes() {
        GetAliasesRequest request = new GetAliasesRequest();
        GetAliasesResponse getAliasesResponse = null;
        try {
            getAliasesResponse = highLevelClient.indices().getAlias(request, RequestOptions.DEFAULT);
        } catch (IOException e) {
            e.printStackTrace();
        }
        Map<String, Set<AliasMetaData>> map = getAliasesResponse.getAliases();
        return map.keySet();
    }

    @Override
    public JSONArray matchAllQuery(ESQuery param) {
        return matchAllQuery.commonQueryData(param);
    }

    @Override
    public JSONArray multiMatchQuery(ESQuery param) {
        return multiMatchQuery.commonQueryData(param);
    }

    @Override
    public JSONArray termsQuery(ESQuery param) {
        return termsQuery.commonQueryData(param);
    }

    @Override
    public long matchAllQueryCount(ESQuery queryParam) {
        return matchAllQuery.commonQueryCount(queryParam);
    }

    @Override
    public long multiMatchQueryCount(ESQuery queryParam) {
        return multiMatchQuery.commonQueryCount(queryParam);
    }

}
