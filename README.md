march action

# Cassandra
apache-cassandra-3.11.1

# ***********Update start_rpc ********
Consider changing /etc/cassandra.yaml:

# Whether to start the thrift rpc server.
start_rpc: false
to

start_rpc: true

# ##########KEYSPACE#############
CREATE KEYSPACE abin_ks WITH replication = {'class':'SimpleStrategy', 'replication_factor' : 3};

键空间(Keyspace)是用于保存列族，用户定义类型的对象。 键空间(Keyspace)就像RDBMS中的数据库，其中包含列族，索引，用户定义类型，数据中心意识，键空间(Keyspace)中使用的策略，复制因子等。

在Cassandra中，“Create Keyspace”命令用于创建keyspace。

语法：

CREATE KEYSPACE <identifier> WITH <properties>
SQL
或者 -

Create keyspace KeyspaceName with replicaton={'class':strategy name,
'replication_factor': No of replications on different nodes}



















# spring-data-cassandra
spring-data-cassandra的官方文档：http://docs.spring.io/spring-data/cassandra/docs/1.5.0.M1/reference/html/

这个目录下还有api、版本日志等：http://docs.spring.io/spring-data/cassandra/docs/1.5.0.M1/








