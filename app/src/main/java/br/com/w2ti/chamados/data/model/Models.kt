package br.com.w2ti.chamados.data.model

import com.google.gson.annotations.SerializedName

data class Usuario(
    @SerializedName("id") val id: Int,
    @SerializedName("nome") val nome: String,
    @SerializedName("email") val email: String,
    @SerializedName("token") val token: String
)

data class LoginRequest(
    @SerializedName("email") val email: String,
    @SerializedName("senha") val senha: String
)

data class Chamado(
    @SerializedName("id") val id: Int,
    @SerializedName("titulo") val titulo: String,
    @SerializedName("descricao") val descricao: String,
    @SerializedName("status") val status: String,
    @SerializedName("prioridade") val prioridade: String,
    @SerializedName("cliente") val cliente: String,
    @SerializedName("dataCriacao") val dataCriacao: String,
    @SerializedName("tecnico") val tecnico: String? = null
)

data class NovoChamadoRequest(
    @SerializedName("titulo") val titulo: String,
    @SerializedName("descricao") val descricao: String,
    @SerializedName("prioridade") val prioridade: String,
    @SerializedName("clienteId") val clienteId: Int
)

data class Cliente(
    @SerializedName("id") val id: Int,
    @SerializedName("nome") val nome: String,
    @SerializedName("email") val email: String,
    @SerializedName("telefone") val telefone: String,
    @SerializedName("endereco") val endereco: String
)

data class NovoClienteRequest(
    @SerializedName("nome") val nome: String,
    @SerializedName("email") val email: String,
    @SerializedName("telefone") val telefone: String,
    @SerializedName("endereco") val endereco: String
)

data class ApiResponse<T>(
    @SerializedName("sucesso") val sucesso: Boolean,
    @SerializedName("mensagem") val mensagem: String,
    @SerializedName("dados") val dados: T?
)
