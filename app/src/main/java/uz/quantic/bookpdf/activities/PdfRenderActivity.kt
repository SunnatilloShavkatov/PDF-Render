package uz.quantic.bookpdf.activities

import android.annotation.SuppressLint
import android.content.ContextWrapper
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Environment
import android.view.MenuItem
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import kotlinx.android.synthetic.main.activity_pdf_render.*
import shs.itexperts.bookpdf.R
import java.io.*
import java.lang.Exception

class PdfRenderActivity : AppCompatActivity() {

    private lateinit var btnSave : Button
    private lateinit var btnDisplay: Button
    private lateinit var editText: EditText
    private lateinit var textView: TextView
    private var myFile = "MyFiles"
    private var fileName = "message.txt"
    private lateinit var file : File

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pdf_render)
        onBack()

        btnDisplay = findViewById(R.id.buttonDisplay)
        btnSave = findViewById(R.id.buttonSave)
        textView = findViewById(R.id.messageDisplay)
        editText = findViewById(R.id.edit_message)

        if (isExternalStorageWritable()){
            buttonSave.isEnabled = false
        }else{
            val contextWrapper = ContextWrapper(applicationContext)
            file = File(contextWrapper.getExternalFilesDir(myFile), fileName)
        }

        btnSave.setOnClickListener {
            val message : String = editText.text.toString()

            try {
                val outputStream = FileOutputStream(file)
                outputStream.write(message.toByteArray())
                outputStream.flush()
                outputStream.close()
                editText.setText("")
            }catch (e : Exception){
                e.stackTrace
            }
        }

        btnDisplay.setOnClickListener {
            try {
                val fileInputStream = FileInputStream(file)
                val inputStreamReader = InputStreamReader(fileInputStream)
                val bufferReader = BufferedReader(inputStreamReader)
                var message : String
                val stringBuilder = StringBuilder()
                while (bufferReader.readLine() != null){
                    message = bufferReader.readLine()
                    stringBuilder.append(message)
                }
                textView.text = "Message: $stringBuilder"
            }catch (e :Exception){
                e.stackTrace
            }
        }
    }

    private fun isExternalStorageWritable() : Boolean{
        val state:String = Environment.getExternalStorageState()
        return Environment.MEDIA_MOUNTED != state
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
