package com.maas.policy;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import com.aerospike.client.policy.Policy;
import com.aerospike.client.policy.WritePolicy;
import com.google.gson.Gson;

@Getter
@Setter
@Builder
public class AerospikePolicyAdaptor {

    public static Policy getAerospikePolicyFromPolicy(MaasPolicy policy) {
        if (policy == null) {
            return new Policy();
        }
        return new Gson().fromJson(new Gson().toJson(policy), Policy.class);
    }

    public static WritePolicy getAerospikeWritePolicyFromPolicy(MaasWritePolicy policy) {
        if (policy == null) {
            return new WritePolicy();
        }
        return new Gson().fromJson(new Gson().toJson(policy), WritePolicy.class);
    }
}
