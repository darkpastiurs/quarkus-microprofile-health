package sv.com.dkps.health;

import java.time.Duration;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

import org.eclipse.microprofile.health.HealthCheckResponse;
import org.eclipse.microprofile.health.Liveness;
import org.eclipse.microprofile.rest.client.inject.RestClient;

import io.smallrye.health.api.AsyncHealthCheck;
import io.smallrye.mutiny.Uni;
import sv.com.dkps.TvMazeSerieProxy;

@Liveness
@ApplicationScoped
public class TvSeriesProxyHealth implements AsyncHealthCheck {

    @Inject
    @RestClient
    TvMazeSerieProxy proxy;

    @Override
    public Uni<HealthCheckResponse> call() {
        return proxy.get("title")
                .onItem()
                .delayIt()
                .by(Duration.ofMillis(10))
                .onItem().transform(tv -> HealthCheckResponse.named("TVMaze APIs").up().build());
    }

}
