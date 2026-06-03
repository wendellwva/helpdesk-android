package br.com.w2ti.chamados.ui.clientes

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import br.com.w2ti.chamados.data.model.NovoClienteRequest
import br.com.w2ti.chamados.databinding.ActivityNovoClienteBinding
import br.com.w2ti.chamados.network.RetrofitClient
import br.com.w2ti.chamados.network.SessionManager
import kotlinx.coroutines.launch

class NovoClienteActivity : AppCompatActivity() {

    private lateinit var binding: ActivityNovoClienteBinding
    private lateinit var sessionManager: SessionManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNovoClienteBinding.inflate(layoutInflater)
        setContentView(binding.root)

        sessionManager = SessionManager(this)
        supportActionBar?.title = "Novo Cliente"
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        binding.btnSalvar.setOnClickListener {
            salvarCliente()
        }
    }

    private fun salvarCliente() {
        val nome = binding.etNome.text.toString().trim()
        val email = binding.etEmail.text.toString().trim()
        val telefone = binding.etTelefone.text.toString().trim()
        val endereco = binding.etEndereco.text.toString().trim()

        if (nome.isEmpty() || email.isEmpty() || telefone.isEmpty()) {
            Toast.makeText(this, "Preencha os campos obrigatórios", Toast.LENGTH_SHORT).show()
            return
        }

        binding.progressBar.visibility = View.VISIBLE
        binding.btnSalvar.isEnabled = false

        val token = "Bearer ${sessionManager.getToken()}"
        lifecycleScope.launch {
            try {
                val response = RetrofitClient.apiService.cadastrarCliente(
                    token, NovoClienteRequest(nome, email, telefone, endereco)
                )
                if (response.isSuccessful && response.body()?.sucesso == true) {
                    Toast.makeText(this@NovoClienteActivity, "Cliente cadastrado com sucesso!", Toast.LENGTH_SHORT).show()
                    finish()
                } else {
                    Toast.makeText(this@NovoClienteActivity, "Erro ao cadastrar cliente", Toast.LENGTH_SHORT).show()
                }
            } catch (e: Exception) {
                Toast.makeText(this@NovoClienteActivity, "Erro de conexão", Toast.LENGTH_SHORT).show()
            } finally {
                binding.progressBar.visibility = View.GONE
                binding.btnSalvar.isEnabled = true
            }
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        finish()
        return true
    }
}
