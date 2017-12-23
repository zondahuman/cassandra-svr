package com.abin.lee.cassandra.util;


import com.datastax.driver.core.Cluster;
import com.datastax.driver.core.Session;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Created by abin on 2017/12/23 2017/12/23.
 * cassandra-svr
 * com.abin.lee.cassandra.util
 */
public class SessionRepository {


    private static Session instance = null;
    private static Cluster cluster = null;
    private static Lock lock = new ReentrantLock();

    private SessionRepository(){}

    public static Session getSession()
    {
        if (null == instance)
        {
            try
            {
                lock.lock();

                if (null == instance)
                {
                    cluster = Cluster.builder()
                            .addContactPoint("127.0.0.1")
//                            .withCredentials("admin", "admin")
                            .build();
                    instance = cluster.connect();
                    // 也可以针对一个特定的keyspace获取一个session
                    // instance = cluster.connect("mycas");
                }
            }
            finally
            {
                lock.unlock();
            }
        }
        return instance;
    }

    public static void close()
    {
        if (null == cluster)
        {
            try
            {
                lock.lock();

                if (null == cluster)
                {
                    cluster.close();
                }
            }
            finally
            {
                lock.unlock();
            }
        }
    }

    public static void main(String[] args) {
        Session session = getSession();
        System.out.println("session=" + session);
    }
}