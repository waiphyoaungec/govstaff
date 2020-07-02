package com.example.civilservantapp.view.activity.refund

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.ContextWrapper
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.util.Log
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
import com.example.civilservantapp.view.activity.ParentHomeActivity
import com.example.civilservantapp.viewmodel.FetchDataViewModel
import kotlinx.android.synthetic.main.activity_do_re_fund.*
import java.io.File
import java.io.FileOutputStream
import java.io.IOException


class DoReFundActivity : AppCompatActivity() {

    private val viewModel : FetchDataViewModel by lazy {
        ViewModelProvider(this).get(FetchDataViewModel::class.java)
    }
    lateinit var customProgressBar: CustomProgressBar
    lateinit var date : String
    lateinit var claim : String
    lateinit var loan : String
    lateinit var loaninterest : String
    lateinit var premium : String
    lateinit var persistance : String
    lateinit var contract_copy : String
    lateinit var contract_fines : String
    lateinit var lost_contract_stamp : String
    lateinit var showDate : String
    lateinit var uri : Uri
    lateinit  var fos : FileOutputStream
    var file_path = ""
    lateinit var id : String
    var isView = false
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_do_re_fund)



        date = intent.getStringExtra("date")!!
        claim = intent.getStringExtra("claim")!!
        showDate = intent.getStringExtra("showdate")!!
        loan = intent.getStringExtra("loan")!!
        loaninterest = intent.getStringExtra("loaninterest")!!
        premium = intent.getStringExtra("premium")!!
        persistance = intent.getStringExtra("persistance")!!
        contract_fines = intent.getStringExtra("contact_fine")!!
        contract_copy = intent.getStringExtra("contract_copy")!!
        lost_contract_stamp = intent.getStringExtra("lost")!!






        val sharedPreference = SharedPreference(this)
        id  = sharedPreference.getUserInfo("user_id")!!

        do_refund_tool.title = "အမ်းငွေ"
        do_refund_tool.navigationIcon = ContextCompat.getDrawable(this, R.drawable.back_arrow)
        setSupportActionBar(do_refund_tool)
        do_refund_tool.setNavigationOnClickListener {
            finish()
        }

        requestPermission()

       customProgressBar = CustomProgressBar()

        refund_ammount.setText("$claim ကျပ်")
        refund_date.setText(showDate)

        btn_choose.setOnClickListener {
            chooseImage()
        }
        btn_refund.setOnClickListener {
            if(file_path.isEmpty()){
                Toast.makeText(applicationContext,"အထောက်အထားစာရွက်ရွေးပေးပါ",Toast.LENGTH_SHORT).show()
            }
            else {
                val file = File("$file_path/profile.jpg")
                Log.d("test", file.absolutePath)
                viewModel.doRefund(id, claim, file, "jpg", date,loan,loaninterest,premium,persistance,contract_fines,
                contract_copy,lost_contract_stamp)
            }
        }
        viewModel.doRefund.observe(this, Observer {
            when(it){
                is NetWorkViewState.Loading->{
                    Log.d("test","Loading")
                    customProgressBar.show(this,"Loading..")
                }
                is NetWorkViewState.Success->{
                    Log.d("test","status is ${it.item.response}")
                    writeToSDFile(it.item.message.data[0])
                    customProgressBar.dialog.dismiss()


                }
                is NetWorkViewState.Error->{
                    Log.d("test",it.errormessage.localizedMessage!!)
                    Toast.makeText(applicationContext,it.errormessage.localizedMessage,Toast.LENGTH_LONG).show()
                   customProgressBar.dialog.dismiss()
                }
            }
        })
    }
    fun chooseImage(){
        val intent = Intent()
        intent.type = "image/*"
        intent.action = Intent.ACTION_GET_CONTENT
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), 2)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(resultCode == Activity.RESULT_OK && requestCode == 2){
            uri = data!!.data!!
            val input = contentResolver.openInputStream(uri)
            val bitmap = BitmapFactory.decodeStream(input)
            img_refund.setImageBitmap(bitmap)
            file_path = saveToInternalStorage(bitmap)

        }
    }
    private fun writeToSDFile(s : String) { // Find the root of the external storage.
        val downloadPdf = DownloadPdf(this)
             if (Build.VERSION.SDK_INT in 23..28) {
                Log.d("test", "23")
                if (checkPermission()) {
                    val uri = downloadPdf.downLoadMiddle("refund.pdf",s)
                    ShowAlert(this).show(uri)
                } else {
                    requestPermission() // Code for permission
                }
            }
            else if(Build.VERSION.SDK_INT >= 29){
                if (checkPermission()) {
                    val uri = downloadPdf.downloadQ("refund.pdf",s)
                    ShowAlert(this).showQ(uri)
                } else {
                    requestPermission() // Code for permission
                }

            }
            else {
                if (checkPermission()) {
                    val uri =  downloadPdf.downloadLower("refund.pdf",s)
                    ShowAlert(this).show(uri)
                } else {
                    requestPermission() // Code for permission
                }
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
     private fun saveToInternalStorage(bitmapImage : Bitmap) : String{
            val cw =  ContextWrapper(applicationContext);
            val directory = cw.getDir("upload", Context.MODE_PRIVATE);
            val mypath= File(directory,"profile.jpg");
         try {
                fos =  FileOutputStream(mypath);
                bitmapImage.compress(Bitmap.CompressFormat.JPEG, 100, fos);

            } catch (e : Exception) {
                  e.printStackTrace()
            } finally {
                try {
                  fos.close();
                } catch (e : IOException) {
                  e.printStackTrace();
                }
            }
            return directory.absolutePath;
        }

    override fun onBackPressed() {
        super.onBackPressed()
        if(isView){
            startActivity(Intent(this,
                ParentHomeActivity::class.java))
        }
    }

}
