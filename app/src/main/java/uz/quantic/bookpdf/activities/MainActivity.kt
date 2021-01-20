package uz.quantic.bookpdf.activities

import android.app.Dialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import shs.itexperts.bookpdf.fragment.PdfRendererBasicFragment
import shs.itexperts.bookpdf.R

class MainActivity : AppCompatActivity() {

    companion object {
        const val FRAGMENT_INFO = "info"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        onBack()
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                    .replace(
                        R.id.container,
                        PdfRendererBasicFragment()
                    )
                    .commitNow()
        }
    }
    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_info -> {
                InfoFragment().show(supportFragmentManager,
                    FRAGMENT_INFO
                )
                return true
            }
            android.R.id.home ->{
                onBackPressed()
            }
        }
        return super.onOptionsItemSelected(item)
    }

    class InfoFragment : DialogFragment() {
        override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
            return AlertDialog.Builder(requireContext())
                    .setMessage(R.string.intro_message)
                    .setPositiveButton(android.R.string.ok, null)
                    .show()
        }
    }

    // back nav
    private fun onBack() {
        supportActionBar!!.setHomeAsUpIndicator(R.drawable.left)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        supportActionBar!!.setDisplayShowHomeEnabled(true)
        supportActionBar!!.setHomeButtonEnabled(true)
    }

}
