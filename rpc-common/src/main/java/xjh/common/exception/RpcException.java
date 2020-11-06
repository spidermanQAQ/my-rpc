package xjh.common.exception;

import xjh.common.enumeration.RpcError;

public class RpcException extends RuntimeException {

    public RpcException(RpcError error){
        super(error.getMessage());
    }

    public RpcException(RpcError error,String detail){
        super(error.getMessage()+":"+detail);
    }

    public RpcException(String message,Throwable cause){
        super(message,cause);
    }

}
