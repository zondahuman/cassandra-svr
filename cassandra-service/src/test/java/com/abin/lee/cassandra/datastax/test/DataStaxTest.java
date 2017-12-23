package com.abin.lee.cassandra.datastax.test;

import com.abin.lee.cassandra.model.User;
import com.datastax.driver.core.Cluster;
import com.datastax.driver.core.Session;
import com.datastax.driver.mapping.Mapper;
import com.datastax.driver.mapping.MappingManager;
import org.junit.Test;

/**
 * Created by abin on 2017/12/23 2017/12/23.
 * cassandra-svr
 * com.abin.lee.cassandra.datastax.test
 *
 create table IF NOT EXISTS test (
 id varchar,
 name varchar,
 target frozen<map<text, list<text>>>,
 primary key (id, name)
 );
 https://stackoverflow.com/questions/47933892/cassandra-spring-data
 */
public class DataStaxTest {

    @Test
    public void run() throws Exception {
        Cluster cluster = Cluster.builder().addContactPoints("127.0.0.1").build();
        Session session = cluster.connect("test_space");
        MappingManager manager = new MappingManager(session);
        Mapper<User> userMapper = manager.mapper(User.class);
        System.out.println(userMapper.get(2));
        session.close();
        cluster.close();
    }







}
