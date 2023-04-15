package sv.com.dkps;

import javax.enterprise.context.ApplicationScoped;

import io.quarkus.hibernate.reactive.panache.PanacheRepository;

@ApplicationScoped
public class TvSerieRepository implements PanacheRepository<TvSerieEntity> {
    
}
