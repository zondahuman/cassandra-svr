package com.abin.lee.cassandra.service.test;

import com.datastax.driver.core.*;

import java.net.InetAddress;

/**
 * Created by abin on 2017/12/23 2017/12/23.
 * cassandra-svr
 * com.abin.lee.cassandra.service.test
 *
 *  -Dcom.datastax.driver.USE_NATIVE_CLOCK=false
 */
public class CassandraTest {
    public static Cluster cluster;

    public static Session session;

    public static void connect()
    {
        Cluster cluster = null;
        try {
//            System.setProperty("spark.cassandra.connection.local_dc", cass.getDC());
            cluster = Cluster.builder()                                                    // (1)
                    .addContactPoints("localhost")
//                    .addContactPoints("127.0.0.1")
                    .withProtocolVersion(ProtocolVersion.V3)
                    .withCompression(ProtocolOptions.Compression.LZ4)
                    .build();
            session = cluster.connect();                                           // (2)

            ResultSet rs = session.execute("select release_version from system.local");    // (3)
            Row row = rs.one();
            System.out.println("release_version---------" + row.getString("release_version"));                          // (4)
        } finally {
//            if (cluster != null) cluster.close();                                          // (5)
        }

    }
    public static void close(){
        if (cluster != null)
            cluster.close();
    }


    /**
     * 创建键空间
     */
    public static void createKeyspace()
    {
        // 单数据中心 复制策略 ：1
        String cql = "CREATE KEYSPACE if not exists mydb WITH replication = {'class': 'SimpleStrategy', 'replication_factor': '1'}";
        session.execute(cql);
    }

    /**
     * 创建表
     */
    public static void createTable()
    {
        // a,b为复合主键 a：分区键，b：集群键
        String cql = "CREATE TABLE if not exists mydb.test (a text,b int,c text,d int,PRIMARY KEY (a, b))";
        session.execute(cql);
    }

    /**
     * 插入
     */
    public static void insert()
    {
        String cql = "INSERT INTO mydb.test (a , b , c , d ) VALUES ( 'a2',4,'c2',6);";
        session.execute(cql);
    }

    /**
     * 修改
     */
    public static void update()
    {
        // a,b是复合主键 所以条件都要带上，少一个都会报错，而且update不能修改主键的值，这应该和cassandra的存储方式有关
        String cql = "UPDATE mydb.test SET d = 1234 WHERE a='aa' and b=2;";
        // 也可以这样 cassandra插入的数据如果主键已经存在，其实就是更新操作
        String cql2 = "INSERT INTO mydb.test (a,b,d) VALUES ( 'aa',2,1234);";
        // cql 和 cql2 的执行效果其实是一样的
        session.execute(cql);
    }

    /**
     * 删除
     */
    public static void delete()
    {
        // 删除一条记录里的单个字段 只能删除非主键，且要带上主键条件
        String cql = "DELETE d FROM mydb.test WHERE a='aa' AND b=2;";
        // 删除一张表里的一条或多条记录 条件里必须带上分区键
        String cql2 = "DELETE FROM mydb.test WHERE a='aa';";
        session.execute(cql);
        session.execute(cql2);
    }

    /**
     * 查询
     */
    public void query()
    {
        String cql = "SELECT * FROM mydb.test;";
        String cql2 = "SELECT a,b,c,d FROM mydb.test;";

        ResultSet resultSet = session.execute(cql);
        System.out.print("这里是字段名：");
        for (ColumnDefinitions.Definition definition : resultSet.getColumnDefinitions())
        {
            System.out.print(definition.getName() + " ");
        }
        System.out.println();
        System.out.println(String.format("%s\t%s\t%s\t%s\t\n%s", "a", "b", "c", "d",
                "--------------------------------------------------------------------------"));
        for (Row row : resultSet)
        {
            System.out.println(String.format("%s\t%d\t%s\t%d\t", row.getString("a"), row.getInt("b"),
                    row.getString("c"), row.getInt("d")));
        }
    }


    public static void main(String[] args) {
        connect();
        createKeyspace();
        createTable();
        insert();
        close();
    }



}
