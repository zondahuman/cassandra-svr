package com.abin.lee.cassandra.model;

import com.datastax.driver.mapping.annotations.Column;
import com.datastax.driver.mapping.annotations.PartitionKey;
import com.datastax.driver.mapping.annotations.Table;

import java.util.List;
import java.util.Map;

/**
 * Created by abin on 2017/12/23 2017/12/23.
 * cassandra-svr
 * com.abin.lee.cassandra.model
 create table IF NOT EXISTS user (
 id varchar,
 name varchar,
 tags frozen<map<text, list<text>>>,
 primary key (id, name)
 );
 */
@Table(keyspace = "test_space", name = "user")
public class User {

    @PartitionKey
    private int id;

    @Column
    private String name;

    @Column
    private Map<String, List<String>> tags;

}