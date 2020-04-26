package shs.itexperts.bookpdf.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.widget.Toast
import com.github.barteksc.pdfviewer.PDFView
import shs.itexperts.bookpdf.R
import java.io.File

class OpenPdfActivity : AppCompatActivity() {

    private lateinit var pdfView : PDFView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_open_pdf)
        onBack()

        pdfView = findViewById(R.id.pdfOpen)

        val b   = intent.extras

        val uriPath  = b?.getString("pdf").toString()
        Toast.makeText(this,uriPath, Toast.LENGTH_LONG).show()
        val dir = File(uriPath)

        // pdfView ga fayl adresi berilmoqda
        pdfView.fromFile(dir).load()

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
