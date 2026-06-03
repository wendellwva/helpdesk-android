package br.com.w2ti.chamados.ui.login

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import br.com.w2ti.chamados.databinding.ActivityLoginBinding
import br.com.w2ti.chamados.data.model.LoginRequest
import br.com.w2ti.chamados.network.RetrofitClient
import br.com.w2ti.chamados.network.SessionManager
import br.com.w2ti.chamados.ui.MainActivity
import kotlinx.coroutines.launch

class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding
    private lateinit var sessionManager: SessionManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        sessionManager = SessionManager(this)

        if (sessionManager.isLogado()) {
            irParaMain()
            return
        }

        binding.btnEntrar.setOnClickListener {
            val email = binding.etEmail.text.toString().trim()
            val senha = binding.etSenha.text.toString().trim()

            if (email.isEmpty() || senha.isEmpty()) {
                Toast.makeText(this, "Preencha todos os campos", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            fazerLogin(email, senha)
        }
    }

    private fun fazerLogin(email: String, senha: String) {
        binding.progressBar.visibility = View.VISIBLE
        binding.btnEntrar.isEnabled = false

        lifecycleScope.launch {
            try {
                val response = RetrofitClient.apiService.login(LoginRequest(email, senha))
                if (response.isSuccessful && response.body()?.sucesso == true) {
                    val usuario = response.body()!!.dados!!
                    sessionManager.salvarSessao(usuario.token, usuario.nome, usuario.email)
                    irParaMain()
                } else {
                    Toast.makeText(
                        this@LoginActivity,
                        response.body()?.mensagem ?: "Credenciais inválidas",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            } catch (e: Exception) {
                Toast.makeText(this@LoginActivity, "Erro de conexão: ${e.message}", Toast.LENGTH_SHORT).show()
            } finally {
                binding.progressBar.visibility = View.GONE
                binding.btnEntrar.isEnabled = true
            }
        }
    }

    private fun irParaMain() {
        startActivity(Intent(this, MainActivity::class.java))
        finish()
    }
}
