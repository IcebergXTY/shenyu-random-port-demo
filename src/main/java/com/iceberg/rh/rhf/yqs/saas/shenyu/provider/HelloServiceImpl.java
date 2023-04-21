package com.iceberg.rh.rhf.yqs.saas.shenyu.provider;

import com.iceberg.rh.rhf.yqs.saas.shenyu.api.HelloService;
import org.apache.dubbo.config.annotation.DubboService;
import org.apache.shenyu.client.dubbo.common.annotation.ShenyuDubboClient;

@DubboService
@ShenyuDubboClient("hello")
public class HelloServiceImpl implements HelloService {

    @Override
    @ShenyuDubboClient("sayHello")
    public String sayHello(String name) {
        return "Hello " + name;
    }
}
