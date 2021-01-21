package com.test.inside.repository;

import com.test.inside.model.document.Record_solr;
import org.springframework.data.solr.repository.SolrCrudRepository;

public interface RecordRepository extends SolrCrudRepository<Record_solr,Integer>{


}
