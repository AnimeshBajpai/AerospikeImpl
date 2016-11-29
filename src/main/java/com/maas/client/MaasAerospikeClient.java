package com.maas.client;

import java.util.List;

import lombok.extern.slf4j.Slf4j;

import com.aerospike.client.AerospikeClient;
import com.aerospike.client.AerospikeException;
import com.aerospike.client.Bin;
import com.aerospike.client.Host;
import com.aerospike.client.Key;
import com.aerospike.client.Record;
import com.aerospike.client.policy.ClientPolicy;
import com.aerospike.client.policy.Policy;
import com.aerospike.client.policy.WritePolicy;
import com.maas.common.AerospikeConfigurationParams;

@Slf4j
public class MaasAerospikeClient {

	private AerospikeClient client;
	private Boolean isClosed = false;

	public MaasAerospikeClient(AerospikeConfigurationParams params) {
		try {
			ClientPolicy policy = new ClientPolicy();
			policy.readPolicyDefault.timeout = 50;
			policy.readPolicyDefault.maxRetries = 1;
			policy.readPolicyDefault.sleepBetweenRetries = 10;
			policy.writePolicyDefault.timeout = 200;
			policy.writePolicyDefault.maxRetries = 1;
			policy.writePolicyDefault.sleepBetweenRetries = 50;
			new Host("localhost", 3000);
			client = new AerospikeClient(policy, params.getHostName(), params.getPort());
		} catch (AerospikeException e) {
			log.error("Exception while getting Aerospike Client", e);
		} catch (Exception e) {
			System.out.println(e);
		}
	}

	public void put(Key key, List<Bin> bins) throws Exception {
		client.put(null, key, bins.toArray(new Bin[bins.size()]));
	}

	public void put(WritePolicy policy, Key key, List<Bin> bins) throws Exception {
		client.put(policy, key, bins.toArray(new Bin[bins.size()]));
	}

	public Record getValue(Key key) throws Exception {
		return client.get(null, key);
	}

	public Record getValue(Policy policy, Key key) throws Exception {
		return client.get(policy, key);
	}

	public Record getValueForBinNames(Key key, List<String> binNames) throws Exception {
		return client.get(null, key, binNames.toArray(new String[binNames.size()]));
	}

	public Record getValueForBinNames(Policy policy, Key key, List<String> binNames) throws Exception {
		return client.get(policy, key, binNames.toArray(new String[binNames.size()]));
	}

	public Record[] getMultipleValues(Key[] keys) throws Exception {
		return client.get(null, keys);
	}

	public Record[] getMultipleValuesForBinNames(Key[] keys, List<String> binNames) throws Exception {
		return client.get(null, keys, binNames.toArray(new String[binNames.size()]));
	}

	public void closeConnection(boolean keepClient) {
		if (!isClosed && !keepClient) {
			client.close();
			isClosed = true;
		}
	}

}
