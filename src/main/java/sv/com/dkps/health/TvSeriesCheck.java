package sv.com.dkps.health;

import java.util.Optional;

import org.eclipse.microprofile.config.ConfigProvider;
import org.eclipse.microprofile.health.HealthCheckResponse;
import org.eclipse.microprofile.health.Readiness;

import io.smallrye.health.api.AsyncHealthCheck;
import io.smallrye.mutiny.Uni;

@Readiness
public class TvSeriesCheck implements AsyncHealthCheck {

    @Override
    public Uni<HealthCheckResponse> call() {
        Optional<String> defaultTitle = ConfigProvider.getConfig().getOptionalValue("default.title", String.class);
        if (defaultTitle.isPresent()) {
            return Uni.createFrom().item(HealthCheckResponse.named("TVSeries Check").up().build());
        } else {
            return Uni.createFrom().item(HealthCheckResponse.named("TVSeries Check").down().build());
        }
    }
    
}
