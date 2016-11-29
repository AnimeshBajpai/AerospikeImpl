package com.maas.common;

import java.util.List;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import com.aerospike.client.AerospikeException;
import com.aerospike.client.Bin;
import com.aerospike.client.Key;
import com.aerospike.client.Record;
import com.aerospike.client.policy.Policy;
import com.aerospike.client.policy.WritePolicy;
import com.maas.client.MaasAerospikeClient;
import com.maas.common.AerospikeConfigurationParams;

@Getter
@Setter
@Slf4j
public abstract class AbstractAerospikeCacheAccessObject {

    private static final String                         DEFAULT_NAME_SPACE = "maas";

    private static final String                         DEFAULT_SET_NAME   = "prod";

    protected MaasAerospikeClient                        client;

    protected boolean                                   keepClient;

    protected AerospikeConfigurationParams params;

    public AbstractAerospikeCacheAccessObject(AerospikeConfigurationParams params) {
        this.params = params;
    }

    protected String getNameSpace() {
        return DEFAULT_NAME_SPACE;
    }

    protected String getSetName() {
        return DEFAULT_SET_NAME;
    }

    protected Key getKey(String key) {
        try {
            return new Key(params.getNameSpace(), params.getSet(), key);
        } catch (AerospikeException ae) {
            log.error("Exception while getting key", ae);
            return null;
        }
    }

    private MaasAerospikeClient getClient() {
        if (keepClient) {
            if (this.client == null) {
                this.client = new MaasAerospikeClient(params);
            }
            return this.client;
        }
        return new MaasAerospikeClient(params);
    }

    public Record getValue(String keyStr, Policy policy) {
        MaasAerospikeClient client = null;
        Record record = null;
        try {
            client = getClient();
            Key key = getKey(keyStr);
            record = client.getValue(policy, key);
        } catch (Exception e) {
            closeBroken(client);
            log.error("Error occured while getting value for key {} ", keyStr, e);
        } finally {
            client.closeConnection(keepClient);
        }
        return record;
    }

    public void putValue(String keyStr, List<Bin> bins, WritePolicy policy) {
        MaasAerospikeClient client = null;
        try {
            client = getClient();
            Key key = getKey(keyStr);
            client.put(policy, key, bins);
        } catch (Exception e) {
            closeBroken(client);
            log.error("Error occured while putting values for key {} ", keyStr, e);
        } finally {
            client.closeConnection(keepClient);
        }
    }

    public void free() {
        if (client != null) {
            client.closeConnection(keepClient);
        }
    }

    private void closeBroken(MaasAerospikeClient client) {
        client.closeConnection(true);
        this.client = null;
    }
}
