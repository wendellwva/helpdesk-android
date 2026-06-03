package br.com.w2ti.chamados.ui.clientes

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import br.com.w2ti.chamados.databinding.ActivityClientesBinding
import br.com.w2ti.chamados.network.RetrofitClient
import br.com.w2ti.chamados.network.SessionManager
import kotlinx.coroutines.launch

class ClientesActivity : AppCompatActivity() {

    private lateinit var binding: ActivityClientesBinding
    private lateinit var sessionManager: SessionManager
    private lateinit var adapter: ClientesAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityClientesBinding.inflate(layoutInflater)
        setContentView(binding.root)

        sessionManager = SessionManager(this)
        supportActionBar?.title = "Clientes"
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        adapter = ClientesAdapter()
        binding.rvClientes.layoutManager = LinearLayoutManager(this)
        binding.rvClientes.adapter = adapter

        binding.fabNovoCliente.setOnClickListener {
            startActivity(Intent(this, NovoClienteActivity::class.java))
        }

        carregarClientes()
    }

    override fun onResume() {
        super.onResume()
        carregarClientes()
    }

    private fun carregarClientes() {
        binding.progressBar.visibility = View.VISIBLE
        val token = "Bearer ${sessionManager.getToken()}"

        lifecycleScope.launch {
            try {
                val response = RetrofitClient.apiService.listarClientes(token)
                if (response.isSuccessful && response.body()?.sucesso == true) {
                    val lista = response.body()!!.dados ?: emptyList()
                    adapter.submitList(lista)
                    binding.tvVazio.visibility = if (lista.isEmpty()) View.VISIBLE else View.GONE
                } else {
                    Toast.makeText(this@ClientesActivity, "Erro ao carregar clientes", Toast.LENGTH_SHORT).show()
                }
            } catch (e: Exception) {
                Toast.makeText(this@ClientesActivity, "Erro de conexão", Toast.LENGTH_SHORT).show()
            } finally {
                binding.progressBar.visibility = View.GONE
            }
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        finish()
        return true
    }
}
