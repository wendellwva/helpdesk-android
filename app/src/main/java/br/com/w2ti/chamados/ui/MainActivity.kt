package br.com.w2ti.chamados.ui

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import br.com.w2ti.chamados.databinding.ActivityMainBinding
import br.com.w2ti.chamados.network.SessionManager
import br.com.w2ti.chamados.ui.chamados.ChamadosActivity
import br.com.w2ti.chamados.ui.clientes.ClientesActivity
import br.com.w2ti.chamados.ui.login.LoginActivity

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var sessionManager: SessionManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        sessionManager = SessionManager(this)

        binding.tvNomeUsuario.text = "Olá, ${sessionManager.getNome() ?: "Usuário"}"

        binding.cardChamados.setOnClickListener {
            startActivity(Intent(this, ChamadosActivity::class.java))
        }

        binding.cardClientes.setOnClickListener {
            startActivity(Intent(this, ClientesActivity::class.java))
        }

        binding.btnSair.setOnClickListener {
            sessionManager.limparSessao()
            startActivity(Intent(this, LoginActivity::class.java))
            finish()
        }
    }
}
