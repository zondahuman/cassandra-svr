package com.abin.lee.cassandra.service.test;

import com.datastax.driver.core.Cluster;
import com.datastax.driver.core.ResultSet;
import com.datastax.driver.core.Row;
import com.datastax.driver.core.Session;

/**
 * Created by abin on 2017/12/22 19:04.
 * cassandra-svr
 * com.abin.lee.cassandra.common.util.test
 */
public class AppTest {


    // 此示例使用同步API，需要下载额外的Guava.jar
    public static void main(String[] args) {
        Cluster cluster = null;
        try {
            // Cluster对象是驱动程序的主入口点。 它保存了实际Cassandra集群（特别是元数据）的已知状态。
            // 这个类是线程安全的，你应该创建一个单独的实例（每个目标Cassandra集群），并在你的应用程序中共享它;
            cluster = Cluster.builder().addContactPoint("172.16.2.132").build();
//            cluster = Cluster.builder().addContactPoint("127.0.0.1").build();
            // Session用来执行查询。 同样，它是线程安全的，应该重复使用;
            Session session = cluster.connect();
            // 我们使用execute来向Cassandra发送查询。 这将返回一个ResultSet，它本质上是一个Row对象的集合。
            // 在下一行，我们提取第一行（在这种情况下是唯一的一行）;
            ResultSet rs = session
                    .execute("select release_version from system.local");
            Row row = rs.one();
            // 我们从行中提取第一个（和唯一的）列的值;
            System.out.println("-----------------------------------------------------------------");
            System.out.println(row.getString("release_version"));
            System.out.println("-----------------------------------------------------------------");
        } finally {
            if (cluster != null)
                // 最后，我们在完成后关闭集群。 这也将关闭从此群集创建的任何会话。
                // 此步骤很重要，因为它释放了底层资源（TCP连接，线程池...）。
                // 在实际应用程序中，通常在关闭时执行此操作（例如，取消部署webapp时）。
                cluster.close();
        }
    }


}
