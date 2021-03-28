package com.ifood.mapper;

import com.ifood.model.Prato;
import com.ifood.model.Restaurante;
import com.ifood.model.request.AdicionarRestauranteRequest;
import com.ifood.model.request.PratoRequest;
import org.mapstruct.Mapper;

@Mapper(componentModel = "cdi")
public interface PratoMapper {

    public Prato toPrato(PratoRequest resquest);
}
