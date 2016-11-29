package com.maas.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import com.aerospike.client.Bin;
import com.aerospike.client.Record;
import com.aerospike.client.policy.Policy;
import com.aerospike.client.policy.WritePolicy;
import com.aerospike.client.Value;
import com.maas.common.AerospikeConfigurationParams;
import com.maas.cao.ServiceCAO;
import com.maas.common.AerospikeClientConfigParams;
import com.maas.policy.AerospikePolicyAdaptor;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class AerospikeManager {

    protected AerospikeConfigurationParams params;
    
    /**
     * 
     */
    public AerospikeManager(AerospikeConfigurationParams params) {
       this.params = params;
    }
    
    @SuppressWarnings("unchecked")
    public  <T> Map<String, T> getValuesFromAero(String key, AerospikeClientConfigParams clientParams
           ) {
        ServiceCAO cao = new ServiceCAO(params);
        try {
            Policy policy = AerospikePolicyAdaptor.getAerospikePolicyFromPolicy(clientParams.getReadPolicy());
            Record record = cao.getValue(key, policy);
            if (Objects.isNull(record)) {
                return null;
            }
            return (Map<String, T>) record.bins;
        } catch (Exception e) {
            log.error("Error while putting Value for Key {} and NameSpace {} ", key, cao.getParams().getNameSpace(), e);
        } finally {
            cao.free();
        }
        return null;
    }
    public <T> void putValueInAero(String key, Map<String, T> binMap, AerospikeClientConfigParams clientParams
           ) {
        ServiceCAO cao = new ServiceCAO(params);
        try {
            List<Bin> bins = new ArrayList<>();
            for (Map.Entry<String, T> entry : binMap.entrySet()) {
                Bin bin = new Bin(entry.getKey(), Value.getAsBlob(entry.getValue()));
                bins.add(bin);
            }
            WritePolicy policy = AerospikePolicyAdaptor.getAerospikeWritePolicyFromPolicy(clientParams.getWritePolicy());
            cao.putValue(key, bins, policy);
        } catch (Exception e) {
            log.error("Error while putting Value for Key {} and NameSpace {} ", key, cao.getParams().getNameSpace(), e);
        } finally {
            cao.free();
        }
    }
}
