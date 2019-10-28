import org.glassfish.jersey.server.ResourceConfig;


public class RestApp extends ResourceConfig {
	
	public RestApp() {
		packages("coupon");
	}
}
