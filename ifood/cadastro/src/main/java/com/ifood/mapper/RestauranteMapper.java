package com.ifood.mapper;

import com.ifood.model.Restaurante;
import com.ifood.model.dto.BuscarRestauranteDto;
import com.ifood.model.request.AdicionarRestauranteRequest;
import com.ifood.model.request.RestauranteRequest;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import javax.enterprise.context.ApplicationScoped;
import java.util.List;

@Mapper(componentModel = "cdi")
public interface RestauranteMapper {

    public Restaurante toRestaurante(AdicionarRestauranteRequest resquest);

    public Restaurante toRestaurante(RestauranteRequest request ,
                           @MappingTarget Restaurante restaurante);

    public Restaurante toRestaurante(RestauranteRequest resquest);

    @Mapping(target = "dataCriacao", dateFormat = "yyyy-MM-dd")
    public BuscarRestauranteDto toBuscaRestauranteDto(Restaurante listRestaurante);

    public List<BuscarRestauranteDto> toBuscaRestauranteDto(List<Restaurante> listRestaurante);

}
