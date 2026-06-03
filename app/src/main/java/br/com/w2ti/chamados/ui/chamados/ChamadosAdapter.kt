package br.com.w2ti.chamados.ui.chamados

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import br.com.w2ti.chamados.R
import br.com.w2ti.chamados.data.model.Chamado
import br.com.w2ti.chamados.databinding.ItemChamadoBinding

class ChamadosAdapter : ListAdapter<Chamado, ChamadosAdapter.ViewHolder>(DiffCallback()) {

    inner class ViewHolder(private val binding: ItemChamadoBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(chamado: Chamado) {
            binding.tvTitulo.text = chamado.titulo
            binding.tvCliente.text = chamado.cliente
            binding.tvData.text = chamado.dataCriacao
            binding.tvStatus.text = chamado.status
            binding.tvPrioridade.text = chamado.prioridade

            val statusColor = when (chamado.status.lowercase()) {
                "aberto" -> R.color.status_aberto
                "em andamento" -> R.color.status_andamento
                "concluído", "concluido" -> R.color.status_concluido
                else -> R.color.status_aberto
            }
            binding.tvStatus.setTextColor(
                ContextCompat.getColor(binding.root.context, statusColor)
            )
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemChamadoBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class DiffCallback : DiffUtil.ItemCallback<Chamado>() {
        override fun areItemsTheSame(oldItem: Chamado, newItem: Chamado) = oldItem.id == newItem.id
        override fun areContentsTheSame(oldItem: Chamado, newItem: Chamado) = oldItem == newItem
    }
}
