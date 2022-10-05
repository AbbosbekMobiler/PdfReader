package abbosbek.mobiler.pdfreader

import abbosbek.mobiler.pdfreader.databinding.ActivityMainBinding
import android.Manifest
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Environment
import android.widget.Toast
import androidx.recyclerview.widget.GridLayoutManager
import com.karumi.dexter.Dexter
import com.karumi.dexter.PermissionToken
import com.karumi.dexter.listener.PermissionDeniedResponse
import com.karumi.dexter.listener.PermissionGrantedResponse
import com.karumi.dexter.listener.PermissionRequest
import com.karumi.dexter.listener.single.PermissionListener
import java.io.File

class MainActivity : AppCompatActivity() {

    private lateinit var pdfList : ArrayList<File>

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        runtimePermission()

    }

    private fun runtimePermission() {
        Dexter.withContext(this).withPermission(Manifest.permission.READ_EXTERNAL_STORAGE)
            .withListener(object : PermissionListener{
                override fun onPermissionGranted(p0: PermissionGrantedResponse?) {
                    displayPdf()
                }

                override fun onPermissionDenied(p0: PermissionDeniedResponse?) {
                    Toast.makeText(this@MainActivity, "Permission is Required", Toast.LENGTH_SHORT).show()
                }

                override fun onPermissionRationaleShouldBeShown(
                    p0: PermissionRequest?,
                    p1: PermissionToken?
                ) {
                    p1?.continuePermissionRequest()
                }
            }).check()
    }

    fun findPdf(file: File) : ArrayList<File>{
        val arrayList = ArrayList<File>()
        val files = file.listFiles()

        for (singleFile in files){
            if (singleFile.isDirectory && !singleFile.isHidden){
                arrayList.addAll(findPdf(singleFile))
            } else {
                if (singleFile.name.endsWith(".pdf")){
                    arrayList.add(singleFile)
                }
            }
        }
        return arrayList
    }

    private fun displayPdf() {
        binding.recyclerView.setHasFixedSize(true)
        binding.recyclerView.layoutManager = GridLayoutManager(this,3)

        pdfList = ArrayList()

        pdfList.addAll(findPdf(Environment.getExternalStorageDirectory()))

        binding.recyclerView.adapter = PdfReaderAdapter(pdfList,object : OnPdfFileSelectedListener{
            override fun onPdfSelected(file: File) {
                startActivity(
                    Intent(this@MainActivity,DocumentActivity::class.java)
                        .putExtra("path",file.absolutePath)
                )
            }
        })
    }
}