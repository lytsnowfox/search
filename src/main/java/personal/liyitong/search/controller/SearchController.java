package personal.liyitong.search.controller;

import com.alibaba.fastjson.JSONArray;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import personal.liyitong.search.elasticsearch.param.ESQuery;
import personal.liyitong.search.service.SearchService;

@RestController
@RequestMapping("/ES")
public class SearchController {

    @Autowired
    private SearchService searchService;

    /**
     * 判断文档是否存在
     * @param index 索引名称
     * @param id    文档标识
     * @return
     */
    @RequestMapping("/isDocExists")
    public boolean isDocExists(String index, String id) {
        return searchService.isDocExists(index, id);
    }

    /**
     * 删除索引，清空该索引的所有数据
     * @param index 索引名称
     * @return
     */
    @RequestMapping("/dropIndex")
    public boolean dropIndex(String index) {
        return searchService.dropIndex(index);
    }

    /**
     * 创建索引
     * @param index 索引名称
     * @return
     */
    @RequestMapping("/createIndex")
    public boolean createIndex(String index) {
        return searchService.createIndex(index);
    }

    /**
     * 批量插入或更新，用户指定作为主键的属性名，根据指定主键新增或更新
     * @param index     索引名称
     * @param keyName   主键名称
     * @param dataList  插入数据，JSON数组形式
     * @return
     */
    @RequestMapping("/batchUpsert")
    public String batchUpsert(String index, String keyName, @RequestBody JSONArray dataList) {
        return searchService.insertData(index, keyName, dataList);
    }

    /**
     * 封装ES的multiMatchQuery
     * @param esQuery   通用查询参数
     * @return
     */
    @RequestMapping("/multiMatchQuery")
    public JSONArray multiMatchQuery(@RequestBody ESQuery esQuery) {
        return searchService.multiMatchQuery(esQuery);
    }

    /**
     * 封装ES的matchAllQuery
     * @param esQuery   通用查询参数
     * @return
     */
    @RequestMapping("/matchAllQuery")
    public JSONArray matchAllQuery(@RequestBody ESQuery esQuery) {
        return searchService.matchAllQuery(esQuery);
    }

    /**
     * 封装ES的termsQuery
     * @param esQuery   通用查询参数
     * @return
     */
    @RequestMapping("/termsQuery")
    public JSONArray termsQuery(@RequestBody ESQuery esQuery) {
        return searchService.termsQuery(esQuery);
    }
}
