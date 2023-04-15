package sv.com.dkps;

import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import org.eclipse.microprofile.rest.client.inject.RestClient;
import org.jboss.resteasy.reactive.RestQuery;
import org.jboss.resteasy.reactive.RestResponse;
import org.jboss.resteasy.reactive.RestResponse.Status;

import io.quarkus.hibernate.reactive.panache.common.runtime.ReactiveTransactional;
import io.smallrye.mutiny.Uni;

@Path("/tvseries")
@Produces({ MediaType.APPLICATION_JSON })
public class TvSeriesResource {

    @Inject
    @RestClient
    TvMazeSerieProxy proxy;

    @Inject
    TvSerieRepository repository;

    @GET
    @Path("/fetch")
    @ReactiveTransactional
    public Uni<RestResponse<TvSerieEntity>> fetchTvSerie(@RestQuery("title") String titulo) {
        //GET TV Serie from TVMaze Public API
        TvSerie tvSerie = proxy.get(titulo);
        //Create new TVSerie object for database
        TvSerieEntity tvSerieEntity = new TvSerieEntity();
        tvSerieEntity.setName(tvSerie.getName());
        tvSerieEntity.setSummary(tvSerie.getSummary());

        return repository.persist(tvSerieEntity)
                .onItem().ifNotNull().transform(newEntity -> RestResponse.ok(newEntity))
                .onItem().ifNull().continueWith(RestResponse.status(Status.BAD_REQUEST))
                .onFailure().recoverWithItem(RestResponse.status(Status.INTERNAL_SERVER_ERROR));
    }

    @GET
    public Uni<RestResponse<List<TvSerieEntity>>> get(@QueryParam("title") String title) {
        return repository.listAll()
                .map(tvSeriesFound -> RestResponse.ok(tvSeriesFound));
    }
}
