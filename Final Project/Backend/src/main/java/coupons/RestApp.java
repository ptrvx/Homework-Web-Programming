package coupons;

import coupons.filters.CorsFilter;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.server.ServerProperties;

import javax.ws.rs.ApplicationPath;

@ApplicationPath("/rest")
public class RestApp extends ResourceConfig {

    public RestApp() {
        register(CorsFilter.class);
        packages("coupons");

        property(ServerProperties.BV_SEND_ERROR_IN_RESPONSE, true);
    }

}
