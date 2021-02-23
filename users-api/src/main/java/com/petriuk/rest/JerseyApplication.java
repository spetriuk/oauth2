package com.petriuk.rest;

import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.server.ServerProperties;

import javax.ws.rs.ApplicationPath;

@ApplicationPath("rest")
public class JerseyApplication extends ResourceConfig {
    public JerseyApplication() {
        packages("com.petriuk.rest");
    }

    @Override
    public ResourceConfig property(String name, Object value) {
        return super.property(ServerProperties.BV_SEND_ERROR_IN_RESPONSE, true);
    }
}
