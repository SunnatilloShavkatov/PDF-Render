package uz.quantic.pdfrender.activities

import android.Manifest
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import com.itextpdf.text.Document
import com.itextpdf.text.Paragraph
import com.itextpdf.text.pdf.PdfWriter
import uz.quantic.pdfrender.R
import java.io.File
import java.io.FileOutputStream
import java.text.SimpleDateFormat
import java.util.*

class WritePdfActivity : AppCompatActivity() {

    private lateinit var mTextEt: EditText
    private lateinit var mSaveBtn: Button

    @RequiresApi(Build.VERSION_CODES.KITKAT)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_write_pdf)
        onBack()

        mTextEt = findViewById(R.id.textEt)
        mSaveBtn = findViewById(R.id.saveBtn)


        mSaveBtn.setOnClickListener {
            //we need to handle runtime permission for devices with marshmallow and above
            if (Build.VERSION.SDK_INT > Build.VERSION_CODES.M) {
                //system OS >= Marshmallow(6.0), check if permission is enabled or not
                if (checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) ==
                    PackageManager.PERMISSION_DENIED
                ) {
                    //permission was not granted, request it
                    val permissions =
                        arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    requestPermissions(permissions, 1)
                } else {
                    //permission already granted, call save pdf method
                    savePdf()
                }
            } else {
                //system OS < Marshmallow, call save pdf method
                savePdf()
            }
        }

    }

    @RequiresApi(Build.VERSION_CODES.KITKAT)
    private fun savePdf() {
        //create object of Document class
        val mDoc = Document()
        //pdf file name
        val mFileName: String = SimpleDateFormat(
            "yyyyMMdd_HHmmss",
            Locale.getDefault()
        ).format(System.currentTimeMillis())
        //pdf file path
        val file = File(this.getExternalFilesDir(null), "Pdf App")
        Log.d("ddddd",file.absolutePath)
        if (!file.exists()){
            file.mkdirs()
        }
        val mFilePath: String = file.path+ "/"+mFileName+".pdf"
        try {
            //create instance of PdfWriter class
            PdfWriter.getInstance(mDoc, FileOutputStream(mFilePath))
            //open the document for writing
            mDoc.open()
            //get text from EditText i.e. mTextEt
            val mText : String = mTextEt.text.toString()

            //add author of the document (optional)
            mDoc.addAuthor("Atif Pervaiz")

            //add paragraph to the document
            mDoc.add(Paragraph(mText))

            //close the document
            mDoc.close()
            //show message that file is saved, it will show file name and file path too
            Toast.makeText(this, "$mFileName.pdf\nis saved to\n$mFilePath", Toast.LENGTH_SHORT)
                .show()
        } catch (e: Exception) {
            //if any thing goes wrong causing exception, get and show exception message
            Toast.makeText(this, e.message, Toast.LENGTH_SHORT).show()
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


