package com.ifood.repository;

import com.ifood.model.Restaurante;

import javax.enterprise.context.ApplicationScoped;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;

@ApplicationScoped
public class RestauranteManager {

    @PersistenceContext
    private EntityManager manager;

    public List<Restaurante> BuscaTodosRestaurantes(){
        Query query = manager.createQuery("select c from Restaurante c");

        return query.getResultList();
    }
}
