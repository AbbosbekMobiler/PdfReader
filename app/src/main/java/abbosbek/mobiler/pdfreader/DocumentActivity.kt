package abbosbek.mobiler.pdfreader

import abbosbek.mobiler.pdfreader.databinding.ActivityDocumentBinding
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import java.io.File

class DocumentActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDocumentBinding

    var filePath = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDocumentBinding.inflate(layoutInflater)
        setContentView(binding.root)

        filePath = intent.getStringExtra("path").toString()

        val file = File(filePath)
        val path = Uri.fromFile(file)
        binding.pdfView.fromUri(path).load()
    }
}