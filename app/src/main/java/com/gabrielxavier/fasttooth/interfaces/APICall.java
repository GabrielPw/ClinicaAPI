package com.gabrielxavier.fasttooth.interfaces;

import com.gabrielxavier.fasttooth.model.Cliente;
import com.gabrielxavier.fasttooth.model.Servico;
import com.gabrielxavier.fasttooth.model.Usuario;

import java.util.List;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.HTTP;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface APICall {

    // https://clinica-api-tcc.herokuapp.com/       ---> GABRIEL
    // https://clinica-tcc-api.herokuapp.com/       ---> PEGORARI


    // Dps vai ter q integrar as API.
    // Métodos API GABRIEL.
    // Usuario.
    @GET("usuarios/getAll")
    Call<List<Usuario>> getUsuario();

    @GET("usuarios/getByCpf/{cpf}")
    Call<Usuario> getByCpf(@Path("cpf") String cpf);

    @POST("usuarios/newUsuario")
    Call<Usuario> newUsuario(@Body Usuario usuario);

    @POST("usuarios/login")
    Call<Usuario> login(@Body Usuario usuario);

    // Cliente

    @POST("clientes/cadastrar")
    Call<Cliente> newCliente(@Body Cliente cliente);

    // Pegorari's API.

    @GET("servico/listar")
    Call<List<Servico>> listarServico();
}
