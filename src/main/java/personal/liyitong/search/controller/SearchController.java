package personal.liyitong.search.controller;

import com.alibaba.fastjson.JSONObject;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import personal.liyitong.search.elasticsearch.ESClient;
import personal.liyitong.search.elasticsearch.ESClient_Search;

import java.util.Map;

@RestController
@RequestMapping("/ES")
public class SearchController {

    @RequestMapping("/connectToES")
    public boolean connectToES() {
        ESClient client = new ESClient();
        return client.testConnect();
    }

    @RequestMapping("/createIndex")
    public boolean createIndex() {
        ESClient client = new ESClient();
        return client.testIndex();
    }

    @RequestMapping("/getAPI")
    public String getAPI() {
        ESClient client = new ESClient();
        return client.getAPI();
    }

    @RequestMapping("/updateAPI")
    public String updateAPI() {
        ESClient client = new ESClient();
        return client.updateAPI();
    }

    @RequestMapping("/deleteAPI")
    public void deleteAPI() {
        ESClient client = new ESClient();
        client.deleteAPI();
    }

    @RequestMapping("/testBulk")
    public String testBulk() {
        ESClient client = new ESClient();
        return client.testBulk();
    }

    @RequestMapping("/testMultiGet")
    public String testMultiGet() {
        ESClient client = new ESClient();
        return client.testMultiGet();
    }

    @RequestMapping("/multiInsert")
    public boolean multiInsert() {
        ESClient_Search client = new ESClient_Search();
        client.insertRandomData();
        return true;
    }

    @RequestMapping("/testSearch")
    public boolean testSearch(@RequestParam String param) {
        ESClient_Search client = new ESClient_Search();
        client.testSearch(param);
        return true;
    }

    @RequestMapping("/testAggregation")
    public Map<Object, Long> testAggregation() {
        ESClient_Search client = new ESClient_Search();
        return client.testAggregation();
    }

    @RequestMapping("/testComplexAgr")
    public Map<Object, Double> testComplexAgr() {
        ESClient_Search client = new ESClient_Search();
        return client.testComplexAgr();
    }
}
