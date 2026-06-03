package br.com.w2ti.chamados.network

import br.com.w2ti.chamados.data.model.*
import retrofit2.Response
import retrofit2.http.*

interface W2ApiService {

    @POST("auth/login")
    suspend fun login(@Body request: LoginRequest): Response<ApiResponse<Usuario>>

    @GET("chamados")
    suspend fun listarChamados(
        @Header("Authorization") token: String
    ): Response<ApiResponse<List<Chamado>>>

    @POST("chamados")
    suspend fun abrirChamado(
        @Header("Authorization") token: String,
        @Body request: NovoChamadoRequest
    ): Response<ApiResponse<Chamado>>

    @PUT("chamados/{id}/status")
    suspend fun atualizarStatusChamado(
        @Header("Authorization") token: String,
        @Path("id") id: Int,
        @Body status: Map<String, String>
    ): Response<ApiResponse<Chamado>>

    @GET("clientes")
    suspend fun listarClientes(
        @Header("Authorization") token: String
    ): Response<ApiResponse<List<Cliente>>>

    @POST("clientes")
    suspend fun cadastrarCliente(
        @Header("Authorization") token: String,
        @Body request: NovoClienteRequest
    ): Response<ApiResponse<Cliente>>
}
