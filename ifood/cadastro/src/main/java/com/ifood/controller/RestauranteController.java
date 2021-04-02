package com.ifood.controller;


import com.ifood.mapper.RestauranteMapper;
import com.ifood.model.Prato;
import com.ifood.model.Restaurante;
import com.ifood.model.dto.BuscarRestauranteDto;
import com.ifood.model.request.AdicionarRestauranteRequest;
import com.ifood.model.request.PratoRequest;
import com.ifood.model.request.RestauranteRequest;
import com.ifood.repository.PratoRepository;
import com.ifood.repository.RestauranteManager;
import com.ifood.repository.RestauranteRepository;
import com.ifood.service.IRestauranteService;
import io.quarkus.security.User;
import io.quarkus.security.identity.SecurityIdentity;
import io.smallrye.jwt.auth.principal.DefaultJWTCallerPrincipal;
import org.eclipse.microprofile.openapi.annotations.enums.SecuritySchemeType;
import org.eclipse.microprofile.openapi.annotations.security.OAuthFlow;
import org.eclipse.microprofile.openapi.annotations.security.OAuthFlows;
import org.eclipse.microprofile.openapi.annotations.security.SecurityRequirement;
import org.eclipse.microprofile.openapi.annotations.security.SecurityScheme;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;

import javax.annotation.security.RolesAllowed;
import javax.inject.Inject;
import javax.swing.*;
import javax.transaction.Transactional;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Path("/restaurantes")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Tag(name = "restaurantes")
@RolesAllowed("opchub")
@SecurityScheme(securitySchemeName = "ifood-oauth", type = SecuritySchemeType.OAUTH2, flows = @OAuthFlows(password = @OAuthFlow(tokenUrl = "http://localhost:8180/auth/realms/rco/protocol/openid-connect/token")))
@SecurityRequirement(name = "ifood-oauth", scopes = {})
public class RestauranteController {

    private RestauranteRepository restauranteRepository;

    private RestauranteManager restauranteManager;

    private PratoRepository pratoRepository;

    private IRestauranteService restauranteService;

    @Inject
    SecurityIdentity identity;

    @Inject
    private RestauranteMapper restauranteMapper;



    @Inject
    public RestauranteController(RestauranteRepository restauranteRepository, RestauranteManager restauranteManager, PratoRepository pratoRepository, IRestauranteService restauranteService ){
        this.restauranteRepository = restauranteRepository;
        this.restauranteManager = restauranteManager;
        this.pratoRepository = pratoRepository;
        this.restauranteService = restauranteService;

    }

    @GET
    public List<BuscarRestauranteDto> buscar(){

        System.out.println(identity.getPrincipal().getName());
        System.out.println(identity.getRoles());
        DefaultJWTCallerPrincipal o = (DefaultJWTCallerPrincipal)identity.getAttributes().get("quarkus.user");
        System.out.print(o.claim("email"));

        List<Restaurante> restaurantes = this.restauranteRepository.listAll();

        return this.restauranteMapper.toBuscaRestauranteDto(restaurantes);
    }

    @POST
    @Transactional
    public Response cadastro(AdicionarRestauranteRequest request){
        this.restauranteRepository.listAll();
        this.restauranteService.CadastrarRestaurante(request);
        return Response.status(Response.Status.CREATED).build();
    }

    @PUT
    @Path("{id}")
    @Transactional
    public void atualizar(@PathParam("id") Long id, RestauranteRequest restauranteRequest){

        Optional<Restaurante> byIdOptional = this.restauranteRepository.findByIdOptional(id);

        if(byIdOptional.isEmpty()){
            throw new NotFoundException("Id nao encontrado");
        }
        Restaurante restaurante = this.restauranteMapper.toRestaurante(restauranteRequest, byIdOptional.get());

        this.restauranteRepository.persist(restaurante);
    }

    @DELETE
    @Path("{id}")
    @Transactional
    public void deletar(@PathParam("id") Long id){

        Optional<Restaurante> byIdOptional = this.restauranteRepository.findByIdOptional(id);

        byIdOptional.ifPresentOrElse(restauranteRepository::delete,() -> {
            throw new NotFoundException("Id nao encontrado");
        });
    }

    @GET
    @Path("{idRestaurante}/pratos")
    @Tag(name = "pratos")
    public List<Prato> buscar(@PathParam("idRestaurante") Long idRestaurante){
        Optional<Restaurante> byIdOptional = this.restauranteRepository.findByIdOptional(idRestaurante);

        if(byIdOptional.isEmpty()){
            throw new NotFoundException("O Restaurante nao existe");
        }

        return this.pratoRepository.list("restaurante", byIdOptional.get());
    }

    @POST
    @Transactional
    @Path("{idRestaurante}/pratos")
    @Tag(name = "pratos")
    public Response cadastrarPratos(@PathParam("idRestaurante") Long idRestaurante, PratoRequest pratoRequest){
        Optional<Restaurante> byIdOptional = this.restauranteRepository.findByIdOptional(idRestaurante);

        if(byIdOptional.isEmpty()){
            throw new NotFoundException("Restaurante nao encontrado");
        }

        Prato prato = new Prato();
        prato.setNome(pratoRequest.getNome());
        prato.setRestaurante(byIdOptional.get());

        this.pratoRepository.persist(prato);

        return Response.status(Response.Status.CREATED).build();

    }

    @PUT
    @Transactional
    @Path("{idRestaurante}/pratos/{idPrato}")
    @Tag(name = "pratos")
    public void atualizarPratos(@PathParam("idRestaurante") Long idRestaurante, @PathParam("idPrato")Long idPrato, PratoRequest pratoRequest){

        Optional<Restaurante> byIdOptional = this.restauranteRepository.findByIdOptional(idRestaurante);

        if(byIdOptional.isEmpty()){
            throw new NotFoundException("Restaurante nao encontrado");
        }

        Optional<Prato> byIdOptional1 = this.pratoRepository.findByIdOptional(idPrato);

        if(byIdOptional1.isEmpty()){
            throw new NotFoundException("Prato nao encontrado");
        }

        Prato prato = byIdOptional1.get();

        prato.setNome(pratoRequest.getNome());

        this.pratoRepository.persist(prato);

    }


    @DELETE
    @Transactional
    @Path("{idRestaurante}/pratos/{idPrato}")
    @Tag(name = "pratos")
    public void deletarPratos(@PathParam("idRestaurante") Long idRestaurante, @PathParam("idPrato")Long idPrato){

        Optional<Restaurante> byIdOptional = this.restauranteRepository.findByIdOptional(idRestaurante);

        if(byIdOptional.isEmpty()){
            throw new NotFoundException("Restaurante nao encontrado");
        }

        Optional<Prato> byIdOptional1 = this.pratoRepository.findByIdOptional(idPrato);

        byIdOptional1.ifPresentOrElse(Prato::delete, () -> {
            throw new NotFoundException("O Prato do restaurante nao foi encontrado");
        });


    }


}
