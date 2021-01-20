package uz.quantic.bookpdf.activities

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.util.Log
import android.view.MenuItem
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import shs.itexperts.bookpdf.R
import shs.itexperts.bookpdf.adapter.RecyclerPdfAdapter
import shs.itexperts.bookpdf.listener.ClickListener
import java.io.File

class ExternalStorageActivity : AppCompatActivity() {

    private val fileList = ArrayList<File>()
    private lateinit var rv : RecyclerView

    @RequiresApi(Build.VERSION_CODES.KITKAT)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_external_storage)
        onBack()

        // Recycler View ni topib olamiz va unga layout beriladi
        rv = findViewById(R.id.rv_pdf)
        rv.setHasFixedSize(true)
        rv.layoutManager = LinearLayoutManager(this)

        reFresh()
    }

    private fun reFresh(){
        val dir = File(this.getExternalFilesDir(null)?.absolutePath+"/Pdf App/")
        Log.d("absolutePath",dir.absolutePath)

        // searchDir() method orqali pdf fayllarni topib fileList ga ozlashtiramiz
        searchDir(dir)
        // adapter class chaqirilib file royxati beriladi
        val adapter = RecyclerPdfAdapter(this,fileList)
        rv.adapter = adapter

        adapter.setOnItemClickListener(object : ClickListener {
            override fun onItemClickListener(position: Int) {
                val intent = Intent(this@ExternalStorageActivity, OpenPdfActivity::class.java)
                intent.putExtra("pdf",fileList[position].absolutePath)
                startActivity(intent)
            }
        })
    }
    // xotira ichidagi ".pdf" ma'lumotlarni topadi.
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

    // back nav
    private fun onBack() {
        supportActionBar!!.setHomeAsUpIndicator(R.drawable.left)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        supportActionBar!!.setDisplayShowHomeEnabled(true)
        supportActionBar!!.setHomeButtonEnabled(true)
    }

    // item selected
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            onBackPressed()
        }
        return super.onOptionsItemSelected(item)
    }

}
