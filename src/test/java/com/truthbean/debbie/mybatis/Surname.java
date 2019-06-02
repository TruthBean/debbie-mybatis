package com.truthbean.debbie.mybatis;

import com.truthbean.debbie.jdbc.annotation.SqlColumn;
import com.truthbean.debbie.jdbc.annotation.SqlEntity;
import com.truthbean.debbie.jdbc.column.PrimaryKeyType;
import org.apache.ibatis.type.Alias;

import java.net.URL;
import java.sql.Timestamp;

@Alias("Surname")
@SqlEntity(charset = "utf8mb4")
public class Surname {

    @SqlColumn(id = true, comment = "主键", primaryKey = PrimaryKeyType.AUTO_INCREMENT)
    private Long id;

    @SqlColumn(nullable = false)
    private String origin;

    private Timestamp begin;

    @SqlColumn(charMaxLength = 512)
    private String website;

    @SqlColumn(nullable = false, unique = true)
    private String name;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getOrigin() {
        return origin;
    }

    public void setOrigin(String origin) {
        this.origin = origin;
    }

    public Timestamp getBegin() {
        return begin;
    }

    public void setBegin(Timestamp begin) {
        this.begin = begin;
    }

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "{" +
                "\"id\":" + id +
                ",\"origin\":\"" + origin + '\"' +
                ",\"begin\":" + begin +
                ",\"website\":" + website +
                ",\"name\":\"" + name + '\"' +
                '}';
    }
}
