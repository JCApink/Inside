package com.test.inside.util;

import com.test.inside.model.document.Record_solr;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class Photo_sort {
    public static void sortByTime(List<Record_solr> list) {

        Collections.sort(list, new Comparator<Record_solr>() {
            @Override
            public int compare(Record_solr r1, Record_solr r2) {
                try {
                    DateTimeFormatter df = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
                    LocalDateTime dt1 = LocalDateTime.parse(r1.getUpdateTime(),df);
                    LocalDateTime dt2 = LocalDateTime.parse(r2.getUpdateTime(),df);
                    if (dt1.isBefore(dt2)) {
                        return 1;
                    } else if (dt1.isAfter(dt2)) {
                        return -1;
                    } else {
                        return 0;
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return 0;
            }
        });
    }
}
