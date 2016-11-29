package com.maas.common;

@SuppressWarnings("serial")
public class AerospikeException extends RuntimeException {

    public AerospikeException(Error error) {
        super(error);
    }
}
