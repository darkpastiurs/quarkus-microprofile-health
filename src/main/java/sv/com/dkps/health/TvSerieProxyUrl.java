package sv.com.dkps.health;

import javax.enterprise.context.ApplicationScoped;

import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.eclipse.microprofile.health.HealthCheck;
import org.eclipse.microprofile.health.Liveness;

import io.smallrye.health.checks.UrlHealthCheck;

@ApplicationScoped
public class TvSerieProxyUrl {

    @ConfigProperty(name = "quarkus.rest-client.tvmaze-api.uri")
    String url;
    
    @Liveness
    HealthCheck url() {
        return new UrlHealthCheck(url).name("API URL Check");
    }

}
