package com.ifood.repository;

import com.ifood.model.Restaurante;
import io.quarkus.hibernate.orm.panache.PanacheRepository;

import javax.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class RestauranteRepository  implements PanacheRepository<Restaurante>{

}
