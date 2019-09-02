package personal.liyitong.search.elasticsearch;

import com.alibaba.fastjson.JSONObject;
import org.apache.http.HttpHost;
import org.elasticsearch.action.bulk.BulkItemResponse;
import org.elasticsearch.action.bulk.BulkRequest;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.action.delete.DeleteRequest;
import org.elasticsearch.action.get.*;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.action.update.UpdateResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.xcontent.XContentType;

import java.io.IOException;
import java.util.Date;

public class ESClient {

    protected final RestHighLevelClient client;
    public ESClient() {
        client = new RestHighLevelClient(
                RestClient.builder(
                        new HttpHost("192.168.30.131", 9200, "http")));
    }

    public boolean testConnect() {
        GetRequest getRequest = new GetRequest(
                "posts",
                "1");
        boolean exists = false;
        try {
            exists = client.exists(getRequest, RequestOptions.DEFAULT);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                client.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return exists;
    }

    public boolean testIndex() {
        IndexRequest request = new IndexRequest("posts2");
        request.id("2");
        JSONObject jObj = new JSONObject();
        jObj.put("user", "lyt");
        jObj.put("postDate", new Date());
        jObj.put("message", "帅彤彤");
        request.source(jObj.toJSONString(), XContentType.JSON);
        try {
            IndexResponse indexResponse = client.index(request, RequestOptions.DEFAULT);
            System.out.println(indexResponse.getId());
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        } finally {
            try {
                client.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return true;
    }

    public String getAPI() {
        GetRequest request = new GetRequest(
                "posts",
                "1");
        GetResponse response = null;
        try {
            response = client.get(request, RequestOptions.DEFAULT);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                client.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return response.getSourceAsString();
    }

    public String updateAPI() {
        UpdateRequest request = new UpdateRequest(
                "posts",
                "1");
        JSONObject jObj = new JSONObject();
        jObj.put("date", "2019-07-09");
        jObj.put("reason", "testUpdate");
        request.doc(jObj.toJSONString(), XContentType.JSON);
        UpdateResponse updateResponse = null;
        try {
            updateResponse = client.update(
                    request, RequestOptions.DEFAULT);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                client.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return updateResponse.getResult().toString();
    }

    public void deleteAPI() {
        DeleteRequest request = new DeleteRequest(
                "posts",
                "1");
        try {
            client.delete(request, RequestOptions.DEFAULT);
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

    public String testBulk() {
        BulkRequest request = new BulkRequest();
        request.add(new IndexRequest("posts").id("1")
                .source(XContentType.JSON,"field", "foo"));
        request.add(new DeleteRequest("posts", "2"));
        request.add(new IndexRequest("posts").id("3")
                .source(XContentType.JSON,"field", "baz"));
        BulkResponse bulkResponse = null;
        try {
            bulkResponse = client.bulk(request, RequestOptions.DEFAULT);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                client.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        if (bulkResponse.hasFailures()) {
            StringBuffer buffer = new StringBuffer();
            for (BulkItemResponse bulkItemResponse : bulkResponse) {
                if (bulkItemResponse.isFailed()) {
                    BulkItemResponse.Failure failure =
                            bulkItemResponse.getFailure();
                    buffer.append(failure.getMessage());
                }
            }
            return buffer.toString();
        }
        return "success";
    }

    public String testMultiGet() {
        MultiGetRequest request = new MultiGetRequest();
        request.add(new MultiGetRequest.Item(
                "posts",
                "1"));
        request.add(new MultiGetRequest.Item("posts", "3"));
        MultiGetResponse responses = null;
        try {
            responses = client.mget(request, RequestOptions.DEFAULT);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        } finally {
            try {
                client.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        StringBuffer buffer = new StringBuffer();
        for (MultiGetItemResponse item : responses) {
            GetResponse response = item.getResponse();
            if (response.isExists()) {
                buffer.append(response.getSourceAsString());
            }
        }
        return buffer.toString();
    }
}
