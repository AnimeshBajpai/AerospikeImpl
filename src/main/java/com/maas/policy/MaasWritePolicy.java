package com.maas.policy;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class MaasWritePolicy extends MaasPolicy {

    private int expiration;

}
