package shs.itexperts.bookpdf.fragment

import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import shs.itexperts.bookpdf.R
import shs.itexperts.bookpdf.model.PdfRendererBasicViewModel
import shs.itexperts.bookpdf.views.TouchImageView

class PdfRendererBasicFragment : Fragment() {
    private val viewModel: PdfRendererBasicViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.pdf_renderer_basic_fragment, container, false)
    }

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        // View references.
        val image: TouchImageView = view.findViewById(R.id.image)
        val buttonPrevious: Button = view.findViewById(R.id.previous)
        val buttonNext: Button = view.findViewById(R.id.next)

        // Bind data.
        viewModel.pageInfo.observe(viewLifecycleOwner, Observer { (index, count) ->
            activity?.title = getString(R.string.app_name_with_index, index + 1, count)
        })
        viewModel.pageBitmap.observe(viewLifecycleOwner, Observer { image.setImageBitmap(it) })
        viewModel.previousEnabled.observe(viewLifecycleOwner, Observer {
            buttonPrevious.isEnabled = it
        })
        viewModel.nextEnabled.observe(viewLifecycleOwner, Observer {
            buttonNext.isEnabled = it
        })

        // Bind events.
        buttonPrevious.setOnClickListener { viewModel.showPrevious() }
        buttonNext.setOnClickListener { viewModel.showNext() }
    }
}