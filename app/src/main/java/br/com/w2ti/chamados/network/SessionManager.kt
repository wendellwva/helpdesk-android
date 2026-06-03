package br.com.w2ti.chamados.network

import android.content.Context
import android.content.SharedPreferences

class SessionManager(context: Context) {

    private val prefs: SharedPreferences =
        context.getSharedPreferences("w2_session", Context.MODE_PRIVATE)

    companion object {
        const val KEY_TOKEN = "auth_token"
        const val KEY_NOME = "user_nome"
        const val KEY_EMAIL = "user_email"
    }

    fun salvarSessao(token: String, nome: String, email: String) {
        prefs.edit().apply {
            putString(KEY_TOKEN, token)
            putString(KEY_NOME, nome)
            putString(KEY_EMAIL, email)
            apply()
        }
    }

    fun getToken(): String? = prefs.getString(KEY_TOKEN, null)
    fun getNome(): String? = prefs.getString(KEY_NOME, null)
    fun getEmail(): String? = prefs.getString(KEY_EMAIL, null)

    fun isLogado(): Boolean = getToken() != null

    fun limparSessao() {
        prefs.edit().clear().apply()
    }
}
