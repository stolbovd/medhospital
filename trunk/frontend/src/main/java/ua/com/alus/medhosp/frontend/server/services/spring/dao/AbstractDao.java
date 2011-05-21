package ua.com.alus.medhosp.frontend.server.services.spring.dao;

import me.prettyprint.cassandra.serializers.LongSerializer;
import me.prettyprint.cassandra.serializers.StringSerializer;
import me.prettyprint.cassandra.serializers.UUIDSerializer;
import me.prettyprint.hector.api.Cluster;
import me.prettyprint.hector.api.Keyspace;

/**
 * Created by IntelliJ IDEA.
 * User: Admin
 * Date: 13.05.11
 * Time: 19:14
 */
public abstract class AbstractDao {


    final static StringSerializer ss = StringSerializer.get();
    final static LongSerializer ls = LongSerializer.get();
    final static UUIDSerializer us = UUIDSerializer.get();


    protected Cluster cluster;

    public Cluster getCluster() {
        return cluster;
    }

    public void setCluster(Cluster cluster) {
        this.cluster = cluster;
    }

    public Keyspace getKeyspace() {
        return keyspace;
    }

    public void setKeyspace(Keyspace keyspace) {
        this.keyspace = keyspace;
    }

    protected Keyspace keyspace;


}
