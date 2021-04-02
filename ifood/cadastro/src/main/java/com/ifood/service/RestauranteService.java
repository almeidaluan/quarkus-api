package com.ifood.service;

import com.ifood.mapper.RestauranteMapper;
import com.ifood.model.Restaurante;
import com.ifood.model.request.AdicionarRestauranteRequest;
import com.ifood.repository.RestauranteRepository;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

@ApplicationScoped
public class RestauranteService implements IRestauranteService {

    @Inject
    private RestauranteMapper restauranteMapper;

    private RestauranteRepository restauranteRepository;

    @Inject
    public RestauranteService(RestauranteRepository restauranteRepository){
        this.restauranteRepository = restauranteRepository;
    }

    @Override
    public void CadastrarRestaurante(AdicionarRestauranteRequest request) {
        Restaurante restaurante = this.restauranteMapper.toRestaurante(request);
        this.restauranteRepository.persist(restaurante);
    }
}
