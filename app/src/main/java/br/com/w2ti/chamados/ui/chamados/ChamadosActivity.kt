package br.com.w2ti.chamados.ui.chamados

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import br.com.w2ti.chamados.databinding.ActivityChamadosBinding
import br.com.w2ti.chamados.network.RetrofitClient
import br.com.w2ti.chamados.network.SessionManager
import kotlinx.coroutines.launch

class ChamadosActivity : AppCompatActivity() {

    private lateinit var binding: ActivityChamadosBinding
    private lateinit var sessionManager: SessionManager
    private lateinit var adapter: ChamadosAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityChamadosBinding.inflate(layoutInflater)
        setContentView(binding.root)

        sessionManager = SessionManager(this)
        supportActionBar?.title = "Chamados"
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        adapter = ChamadosAdapter()
        binding.rvChamados.layoutManager = LinearLayoutManager(this)
        binding.rvChamados.adapter = adapter

        binding.fabNovoChamado.setOnClickListener {
            startActivity(Intent(this, NovoChamadoActivity::class.java))
        }

        carregarChamados()
    }

    override fun onResume() {
        super.onResume()
        carregarChamados()
    }

    private fun carregarChamados() {
        binding.progressBar.visibility = View.VISIBLE
        val token = "Bearer ${sessionManager.getToken()}"

        lifecycleScope.launch {
            try {
                val response = RetrofitClient.apiService.listarChamados(token)
                if (response.isSuccessful && response.body()?.sucesso == true) {
                    val lista = response.body()!!.dados ?: emptyList()
                    adapter.submitList(lista)
                    binding.tvVazio.visibility = if (lista.isEmpty()) View.VISIBLE else View.GONE
                } else {
                    Toast.makeText(this@ChamadosActivity, "Erro ao carregar chamados", Toast.LENGTH_SHORT).show()
                }
            } catch (e: Exception) {
                Toast.makeText(this@ChamadosActivity, "Erro de conexão", Toast.LENGTH_SHORT).show()
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
