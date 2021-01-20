package uz.quantic.pdfrender.adapter

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import uz.quantic.pdfrender.R
import uz.quantic.pdfrender.listener.ClickListener
import java.io.File

class RecyclerPdfAdapter(private val content: Context, private var list: List<File>) :
    RecyclerView.Adapter<RecyclerPdfAdapter.Holder>() {

    private lateinit var listener: ClickListener
    private val fileList = ArrayList<File>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val view = LayoutInflater.from(content).inflate(
            R.layout.pdf_adapter, parent,
            false
        )
        return Holder(view, listener)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {

        holder.textView.text = list[position].name.toString()
        holder.imageView.setImageResource(R.drawable.more_vert)
        holder.menuCardView.setOnClickListener {
            val popupMenu = PopupMenu(content, holder.menuCardView)
            popupMenu.inflate(R.menu.menu_cardview)
            popupMenu.setOnMenuItemClickListener(object : PopupMenu.OnMenuItemClickListener {
                override fun onMenuItemClick(item: MenuItem?): Boolean {
                    when (item?.itemId) {
                        R.id.delete -> {
                            list[position].delete()
                            notifyDataSetChanged()
                            return true
                        }
                        R.id.rename -> {
                            fileRenameDialog(position)
                            return true
                        }
                        R.id.share ->{
                            fileShare(position)
                            return true
                        }
                        else -> {
                            return false
                        }
                    }
                }

            })
            popupMenu.show()
        }
//        notifyDataSetChanged()

    }

    class Holder(view: View, private var listener: ClickListener) : RecyclerView.ViewHolder(view) {
        val textView: TextView
        private val cardView: CardView
        val menuCardView: CardView
        val imageView: ImageView

        init {
            textView = view.findViewById(R.id.pdfName)
            cardView = view.findViewById(R.id.cardView)
            menuCardView = view.findViewById(R.id.cardMenu)
            imageView = view.findViewById(R.id.imageMenu)
            cardView.setOnClickListener {
                listener.onItemClickListener(adapterPosition)
            }
        }
    }

    fun setOnItemClickListener(listener: ClickListener) {
        this.listener = listener
    }

    @SuppressLint("InflateParams")
    private fun fileRenameDialog(position: Int) {
        val builder =  AlertDialog.Builder(content)
        val view: View =  LayoutInflater.from(content).inflate(R.layout.file_rename_dialog, null)
        builder.setView(view)
        val dialog = builder.create()
        val editText = view.findViewById<EditText>(R.id.edit)
        val cancel = view.findViewById<Button>(R.id.cancel)
        val ok = view.findViewById<Button>(R.id.ok)
        ok.setOnClickListener {
            if (editText.text.toString().isEmpty()) {
                Toast.makeText(
                    content, "Ma'lumotlarni to'liq kiriting",
                    Toast.LENGTH_SHORT
                ).show()
            } else {
                val dir = File(content.getExternalFilesDir(null)?.absolutePath+"/Pdf App/")
                // file rename
                if (dir.exists()){
                    val to = File(dir, editText.text.toString()+".pdf")
                    if (list[position].exists()) {
                        list[position].renameTo(to)
                    }
                    searchDir(dir)
                    list = fileList
                    notifyDataSetChanged()
                }
                dialog.dismiss()
            }
        }
        cancel.setOnClickListener { dialog.dismiss() }
        dialog.show()
    }

    private fun searchDir(dir: File) {
        val pdfPattern = ".pdf"
        val arrayOfFiles = dir.listFiles()
        if (arrayOfFiles != null) {
            for (i in arrayOfFiles.indices) {
                if (arrayOfFiles[i].isDirectory) {
                    searchDir(arrayOfFiles[i])
                } else {
                    if (arrayOfFiles[i].name.endsWith(pdfPattern)) {
                        fileList.add(arrayOfFiles[i])
                    }
                }
            }
        }
    }

    private fun fileShare(position: Int){
        val uri = Uri.parse(list[position].absolutePath)
        val intent = Intent(Intent.ACTION_SEND)
        intent.type = "application/pdf"
        intent.putExtra(Intent.EXTRA_STREAM, uri)
        content.startActivity(Intent.createChooser(intent,"Sharing pdf using"))
    }

}