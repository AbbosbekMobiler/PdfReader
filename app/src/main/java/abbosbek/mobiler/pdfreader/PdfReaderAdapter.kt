package abbosbek.mobiler.pdfreader

import abbosbek.mobiler.pdfreader.databinding.ItemLayoutBinding
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import java.io.File

interface OnPdfFileSelectedListener{
    fun onPdfSelected(file : File)
}

class PdfReaderAdapter(val items : ArrayList<File>,val onPdfFileSelectedListener: OnPdfFileSelectedListener) : RecyclerView.Adapter<PdfReaderAdapter.ItemHolder>() {
    inner class ItemHolder(val binding: ItemLayoutBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemHolder {
        return ItemHolder(
            ItemLayoutBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,false
            )
        )
    }

    override fun onBindViewHolder(holder: ItemHolder, position: Int) {
        val item = items[position]

        holder.binding.textPdfName.text = item.name
        holder.binding.textPdfName.isSelected = true

        holder.binding.container.setOnClickListener {
            onPdfFileSelectedListener.onPdfSelected(item)
        }
    }

    override fun getItemCount(): Int {
        return items.size
    }
}