package sv.com.dkps;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;
import org.jboss.resteasy.reactive.RestQuery;

import io.smallrye.mutiny.Uni;

@Path("/singlesearch")
@Produces(MediaType.APPLICATION_JSON)
@RegisterRestClient(configKey = "tvmaze-api")
public interface TvMazeSerieProxy {
    
    @GET
    @Path("/shows")
    Uni<TvSerie> get(@RestQuery("q") String title);
}
