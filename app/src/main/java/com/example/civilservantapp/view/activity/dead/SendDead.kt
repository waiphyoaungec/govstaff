package com.example.civilservantapp.view.activity.dead

import android.Manifest
import android.content.*
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.util.Base64
import android.util.Base64OutputStream
import android.util.Log
import android.view.View
import android.webkit.MimeTypeMap
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.civilservantapp.CustomProgressBar
import com.example.civilservantapp.R
import com.example.civilservantapp.cache.SharedPreference
import com.example.civilservantapp.download_extension.DownloadPdf
import com.example.civilservantapp.download_extension.ShowAlert
import com.example.civilservantapp.model.NetWorkViewState
import com.example.civilservantapp.viewmodel.FetchDataViewModel
import kotlinx.android.synthetic.main.activity_check_dead.*
import org.apache.commons.io.IOUtils
import java.io.*


class SendDead : AppCompatActivity() {
    val fetchDataViewModel : FetchDataViewModel by lazy{
        ViewModelProvider(this).get(FetchDataViewModel::class.java)
    }
    lateinit var base64 : String
    lateinit var ext: String
    lateinit var progressBar: CustomProgressBar
    lateinit var id : String
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_check_dead)

        val name : String? = intent.getStringExtra("name")
        val age : Int? = intent.getIntExtra("age",0)
        val deadDate : String? = intent.getStringExtra("deadDate")
        val amount = intent.getDoubleExtra("ammount",0.0)
        progressBar = CustomProgressBar()

        val sharedPreference = SharedPreference(this)
        id  = sharedPreference.getUserInfo("user_id")!!


        check_rest.visibility = View.GONE
        die_name.setText(name)
        die_age.setText("$age နှစ်")
        die_date.setText(deadDate)
        edt_ammount.setText("${amount} ကျပ်")
        die_from_edt.setText("လျှောက်လွှာရွေးပါ (pdf ရွေးပါ)")

        tool_send_die.title = "လျှောက်ထားရန်.."
        tool_send_die.navigationIcon = ContextCompat.getDrawable(this, R.drawable.back_arrow)
        tool_send_die.setNavigationOnClickListener {
            finish()
        }

        requestPermission()

        check_rest.setOnClickListener {
            upload(id,amount.toString(),ext,base64,deadDate!!)
        }
        choose_pdf.setOnClickListener {
            chooseFile()
        }
        fetchDataViewModel.sendDead.observe(this, Observer {
            when(it){
                is NetWorkViewState.Loading->{
                    progressBar.show(this,"ခဏစောင့်ပါ...")
                }
                is NetWorkViewState.Success->{
                    progressBar.dialog.dismiss()
                    if(it.item.response.equals("success")){
                        if(checkPermission()) {
                            saveFile(it.item.message.data[0])
                        }
                        else
                            requestPermission()
                    }

                }
                is NetWorkViewState.Error->{
                    progressBar.dialog.dismiss()
                }

            }
        })
    }

    private fun saveToInternalStorage(byte : ByteArray) : String{
        val cw =  ContextWrapper(applicationContext)

        val directory = cw.getDir("upload", Context.MODE_PRIVATE);
        val mypath= File(directory,"book.pdf")

        try {
            val stream =  FileOutputStream(mypath, true)
            stream.write(byte)
            stream.flush()
            base64 = convertImageFileToBase64(mypath)

        } catch (e : Exception) {
            e.printStackTrace()
        } finally {

        }
        return base64
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(requestCode == 123){

            val uri = data?.data
            val mimeType = MimeTypeMap.getSingleton()
            val extension = mimeType.getExtensionFromMimeType(contentResolver.getType(uri!!))
            val path = uri.path!!.split("/")
            Log.d("test",path[path.size-1])
            if(extension.equals("pdf")) {
                ext = "pdf"
                check_rest.visibility = View.VISIBLE
                die_from_edt.setText("ရွေးချယ်ပြီးပါပြီ။")
                val input = contentResolver.openInputStream(uri)
                val byte = IOUtils.toByteArray(input)
                saveToInternalStorage(byte)
            }
            else{
                Toast.makeText(applicationContext,"Please Choose Another File",Toast.LENGTH_SHORT).show()
            }
        }
    }
    fun upload(personId : String,amount : String,extension : String,certificate : String,date : String){
        fetchDataViewModel.sendDead(personId,amount,extension,certificate,date)
    }
    fun convertImageFileToBase64(imageFile: File): String {
        return FileInputStream(imageFile).use { inputStream ->
            ByteArrayOutputStream().use { outputStream ->
                Base64OutputStream(outputStream, Base64.DEFAULT).use { base64FilterStream ->
                    inputStream.copyTo(base64FilterStream)
                    base64FilterStream.close()
                    outputStream.toString()
                }
            }
        }
    }
    fun chooseFile(){
        val intent = Intent()
        intent.action = Intent.ACTION_GET_CONTENT
        intent.addCategory(Intent.CATEGORY_OPENABLE)
        intent.type = "application/pdf"
        intent.flags = Intent.FLAG_ACTIVITY_BROUGHT_TO_FRONT
        intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true)
        try {
            startActivityForResult(
                Intent.createChooser(intent, "Select Your .pdf File"),
                123
            )
        } catch (e: ActivityNotFoundException) {
            Toast.makeText(
                applicationContext,
                "Please Install a File Manager",
                Toast.LENGTH_SHORT
            ).show()
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {
            200 -> if (grantResults.isNotEmpty()) {
                val readFile = grantResults[0] == PackageManager.PERMISSION_GRANTED
                val writeFile = grantResults[1] == PackageManager.PERMISSION_GRANTED
                if (readFile && writeFile)
                    Log.d("test","Successfullt")
                else {
                    Toast.makeText(applicationContext, "Please allow permission", Toast.LENGTH_SHORT).show()

                }
            }
        }
    }
    private fun checkPermission(): Boolean {
        val result = ContextCompat.checkSelfPermission(applicationContext, Manifest.permission.READ_EXTERNAL_STORAGE)
        val result1 = ContextCompat.checkSelfPermission(applicationContext, Manifest.permission.WRITE_EXTERNAL_STORAGE)
        return result == PackageManager.PERMISSION_GRANTED && result1 == PackageManager.PERMISSION_GRANTED
    }
    private fun requestPermission() {
        ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE), 200)

    }
   fun saveFile(data : String){
       val downloadPdf = DownloadPdf(this)
       if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q){
         val uri = downloadPdf.downloadQ("dieform.pdf",data)
           ShowAlert(this).showQ(uri)
       }
       else if(Build.VERSION.SDK_INT in 23..28){
           val uri = downloadPdf.downLoadMiddle("dead_form.pdf",data)
           ShowAlert(this).show(uri)
       }
       else{
          val uri =  downloadPdf.downloadLower("dead_form.pdf",data)
           ShowAlert(this).show(uri)
       }
   }
}
