package sv.com.dkps;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;

import org.eclipse.microprofile.config.ConfigProvider;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.eclipse.microprofile.rest.client.inject.RestClient;
import org.jboss.resteasy.reactive.RestQuery;
import org.jboss.resteasy.reactive.RestResponse;
import org.jboss.resteasy.reactive.RestResponse.Status;

import io.quarkus.hibernate.reactive.panache.common.runtime.ReactiveTransactional;
import io.smallrye.mutiny.Uni;

@Path("/tvseries")
@Produces({ MediaType.APPLICATION_JSON })
public class TvSeriesResource {
    String defaultTittle;

    @Inject
    @RestClient
    TvMazeSerieProxy proxy;

    @Inject
    TvSerieRepository repository;

    @PostConstruct
    void init() {
        ConfigProvider.getConfig()
                .getOptionalValue("default.title", String.class)
                .ifPresent(title -> defaultTittle = title);
    }

    @GET
    @Path("/fetch")
    @ReactiveTransactional
    public Uni<RestResponse<TvSerieEntity>> fetchTvSerie(@RestQuery("title") String titulo) {
        if (Objects.isNull(titulo)) {
            titulo = defaultTittle;
        }
        return proxy.get(titulo)
                .onItem().ifNotNull().transformToUni(tvSerie -> {
                    TvSerieEntity tvSerieEntity = new TvSerieEntity();
                    tvSerieEntity.setName(tvSerie.getName());
                    tvSerieEntity.setSummary(tvSerie.getSummary());

                    return repository.persist(tvSerieEntity)
                            .onItem().ifNotNull().transform(newEntity -> RestResponse.ok(newEntity))
                            .onItem().ifNull().continueWith(RestResponse.status(Status.BAD_REQUEST))
                            .onFailure().recoverWithItem(RestResponse.status(Status.INTERNAL_SERVER_ERROR));
                })
                .onItem().ifNull().continueWith(RestResponse.notFound());
    }

    @GET
    public Uni<RestResponse<List<TvSerieEntity>>> get(@QueryParam("title") String title) {
        return repository.listAll()
                .map(tvSeriesFound -> RestResponse.ok(tvSeriesFound));
    }
}
