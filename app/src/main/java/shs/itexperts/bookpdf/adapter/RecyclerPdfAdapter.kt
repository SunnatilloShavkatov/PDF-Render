package shs.itexperts.bookpdf.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import shs.itexperts.bookpdf.R
import shs.itexperts.bookpdf.listener.ClickListener
import java.io.File

class RecyclerPdfAdapter(private val content: Context, private val list: List<File>) :
    RecyclerView.Adapter<RecyclerPdfAdapter.Holder>() {

    private lateinit var listener: ClickListener

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val view = LayoutInflater.from(content).inflate(R.layout.pdf_adapter, parent,
            false)
        return Holder(view, listener)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {

        holder.textView.text = list[position].name.toString()

    }

    class Holder(view : View, private var listener: ClickListener) : RecyclerView.ViewHolder(view) {
        val textView : TextView
        private val cardView: CardView
        init {
            textView = view.findViewById(R.id.pdfName)
            cardView = view.findViewById(R.id.cardView)
            cardView.setOnClickListener{
                listener.onItemClickListener(adapterPosition)
            }
        }
    }

    fun setOnItemClickListener(listener: ClickListener) {
        this.listener = listener
    }
}