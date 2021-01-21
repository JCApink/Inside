package com.test.inside.model.document;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Data;
import org.apache.solr.client.solrj.beans.Field;
import org.springframework.data.solr.core.mapping.SolrDocument;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@SolrDocument(collection = "record")
public class Record_solr implements Serializable {
    @Field
    private String id;

    @Field
    private String title;

    @Field
    private String content;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern="yyyy-MM-dd HH:mm:ss")
    @Field
    private String updateTime;

    /**
     * 星标
     */
    @Field
    private String isStar;

    /**
     * 文章类别
     */
    private String group;

    /**
     * 图片url
     */
    @Field
    private String url;

}
