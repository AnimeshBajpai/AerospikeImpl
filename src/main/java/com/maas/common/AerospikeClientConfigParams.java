package com.maas.common;

import com.maas.policy.MaasPolicy;
import com.maas.policy.MaasWritePolicy;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class AerospikeClientConfigParams {
    private Boolean        isKeepClient = false;
    private MaasWritePolicy writePolicy;
    private MaasPolicy      readPolicy;

}
