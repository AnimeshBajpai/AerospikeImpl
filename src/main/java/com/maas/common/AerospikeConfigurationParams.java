package com.maas.common;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class AerospikeConfigurationParams {

    private String  hostName;
    private Integer port;
    private String  nameSpace;
    private String  set;
}
