package br.com.w2ti.chamados.ui.chamados

import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import br.com.w2ti.chamados.R
import br.com.w2ti.chamados.data.model.NovoChamadoRequest
import br.com.w2ti.chamados.databinding.ActivityNovoChamadoBinding
import br.com.w2ti.chamados.network.RetrofitClient
import br.com.w2ti.chamados.network.SessionManager
import kotlinx.coroutines.launch

class NovoChamadoActivity : AppCompatActivity() {

    private lateinit var binding: ActivityNovoChamadoBinding
    private lateinit var sessionManager: SessionManager
    private var clienteIdSelecionado: Int = -1
    private val clientesMap = mutableMapOf<String, Int>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNovoChamadoBinding.inflate(layoutInflater)
        setContentView(binding.root)

        sessionManager = SessionManager(this)
        supportActionBar?.title = "Novo Chamado"
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val prioridades = arrayOf("Baixa", "Média", "Alta", "Crítica")
        binding.spinnerPrioridade.adapter = ArrayAdapter(
            this, android.R.layout.simple_spinner_item, prioridades
        ).also { it.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item) }

        carregarClientes()

        binding.btnSalvar.setOnClickListener {
            salvarChamado()
        }
    }

    private fun carregarClientes() {
        val token = "Bearer ${sessionManager.getToken()}"
        lifecycleScope.launch {
            try {
                val response = RetrofitClient.apiService.listarClientes(token)
                if (response.isSuccessful && response.body()?.sucesso == true) {
                    val clientes = response.body()!!.dados ?: emptyList()
                    clientes.forEach { clientesMap[it.nome] = it.id }
                    val nomes = clientes.map { it.nome }.toTypedArray()
                    binding.spinnerCliente.adapter = ArrayAdapter(
                        this@NovoChamadoActivity,
                        android.R.layout.simple_spinner_item, nomes
                    ).also { it.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item) }
                }
            } catch (e: Exception) {
                Toast.makeText(this@NovoChamadoActivity, "Erro ao carregar clientes", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun salvarChamado() {
        val titulo = binding.etTitulo.text.toString().trim()
        val descricao = binding.etDescricao.text.toString().trim()
        val prioridade = binding.spinnerPrioridade.selectedItem.toString()
        val clienteNome = binding.spinnerCliente.selectedItem?.toString() ?: ""
        val clienteId = clientesMap[clienteNome] ?: -1

        if (titulo.isEmpty() || descricao.isEmpty() || clienteId == -1) {
            Toast.makeText(this, "Preencha todos os campos", Toast.LENGTH_SHORT).show()
            return
        }

        binding.progressBar.visibility = View.VISIBLE
        binding.btnSalvar.isEnabled = false

        val token = "Bearer ${sessionManager.getToken()}"
        lifecycleScope.launch {
            try {
                val response = RetrofitClient.apiService.abrirChamado(
                    token, NovoChamadoRequest(titulo, descricao, prioridade, clienteId)
                )
                if (response.isSuccessful && response.body()?.sucesso == true) {
                    Toast.makeText(this@NovoChamadoActivity, "Chamado aberto com sucesso!", Toast.LENGTH_SHORT).show()
                    finish()
                } else {
                    Toast.makeText(this@NovoChamadoActivity, "Erro ao abrir chamado", Toast.LENGTH_SHORT).show()
                }
            } catch (e: Exception) {
                Toast.makeText(this@NovoChamadoActivity, "Erro de conexão", Toast.LENGTH_SHORT).show()
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
