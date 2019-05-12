package com.lhs.musiclab.config;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Component;

@Component
public class ElasticSearchConfiguration implements InitializingBean {
    static {
        //redis已经初始化了Netty，所以禁止es初始化netty，否则会报错
        System.setProperty("es.set.netty.runtime.available.processors", "false");
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        System.out.println("已阻止elasticsearch初始化netty");
    }
}
