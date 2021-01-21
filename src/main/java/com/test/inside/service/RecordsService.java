package com.test.inside.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.test.inside.model.pojo.Group;
import com.test.inside.model.pojo.Records;
import org.apache.solr.client.solrj.SolrServerException;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Map;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author Bangtidy
 * @since 2020-08-02
 */
public interface RecordsService extends IService<Records> {

    Records addRecord(Records records, HttpServletRequest request);

    Map getByGroud(Group group, HttpServletRequest request) throws IOException, SolrServerException;

    String deleteRecord(int id, HttpServletRequest request);

    Map getNewRecords(HttpServletRequest request);

    Map<String,Object> getListByKey(String key,HttpServletRequest request) throws IOException, SolrServerException;

}
