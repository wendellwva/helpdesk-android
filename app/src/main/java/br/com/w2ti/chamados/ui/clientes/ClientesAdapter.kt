package br.com.w2ti.chamados.ui.clientes

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import br.com.w2ti.chamados.data.model.Cliente
import br.com.w2ti.chamados.databinding.ItemClienteBinding

class ClientesAdapter : ListAdapter<Cliente, ClientesAdapter.ViewHolder>(DiffCallback()) {

    inner class ViewHolder(private val binding: ItemClienteBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(cliente: Cliente) {
            binding.tvNome.text = cliente.nome
            binding.tvEmail.text = cliente.email
            binding.tvTelefone.text = cliente.telefone
            binding.tvEndereco.text = cliente.endereco
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemClienteBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class DiffCallback : DiffUtil.ItemCallback<Cliente>() {
        override fun areItemsTheSame(oldItem: Cliente, newItem: Cliente) = oldItem.id == newItem.id
        override fun areContentsTheSame(oldItem: Cliente, newItem: Cliente) = oldItem == newItem
    }
}
