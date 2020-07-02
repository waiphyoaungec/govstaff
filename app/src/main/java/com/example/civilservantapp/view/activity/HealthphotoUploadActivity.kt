package com.example.civilservantapp.view.activity

import android.Manifest
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import com.karumi.dexter.Dexter
import com.karumi.dexter.PermissionToken
import com.karumi.dexter.listener.PermissionRequest
import com.karumi.dexter.MultiplePermissionsReport
import com.karumi.dexter.listener.multi.MultiplePermissionsListener
import kotlinx.android.synthetic.main.activity_healthphoto_upload.*
import android.util.Log
import android.widget.Toast
import com.example.civilservantapp.R
import com.example.civilservantapp.view.LifeinsuranceSecond
import android.util.Base64
import java.lang.Exception
import android.os.Environment
import android.os.FileUtils
import com.example.civilservantapp.cache.SharedPreference
import java.io.*
import android.net.Uri
import androidx.core.content.ContextCompat
import androidx.core.graphics.BitmapCompat
import com.google.gson.Gson
import java.nio.file.Files
import java.nio.file.Paths


class HealthphotoUploadActivity : AppCompatActivity() {

    val REQUEST_IMAGE_CAPTURE = 1
    val PICKFILE_RESULT_CODE = 2
    var resizedBitmap: Bitmap? = null
    var health_recommended_letter: String = ""
    var bitmaptype: String = ""
    var cachePath: String = ""
    /*
   variable for first
    */
    private var name: String?=""
    private var dob: String?=""
    private var nrc: String?=""
    private var permanent_addr: String?=""
    private var birth_place: String?=""
    private var gender: String?=""
    private var fathername: String?=""
    private var mark: String?=""
    private var height: String?=""
    private var weight: String?=""
    private var officedept: String?=""
    private var occupation: String?=""
    private var salary: Int?=0
    private var officeId: Int?=0
    private var insuranceamt: Int?=0
    private var insuranceperiod: Int?=0
    private var deposit_amount: Int?=0
    private var townshipid: Int?=0
    private var departmentid: Int?=0
    private var age_next_year: Int=0
    private var amount: Int?=0
    private var phone_no: String?=""
    /*
preview information data
*/
    var previewofficename : String?=""
    var previewgender: String?=""
    var previewtownship: String?=""
    var previewdepartment: String?=""
    private var previewmedicalList: ArrayList<String> = arrayListOf()
    private var TAG="Healthphoto"
    lateinit var sharedPreference: SharedPreference
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_healthphoto_upload)
        var bundle: Bundle? = intent.extras
        sharedPreference = SharedPreference(this)
        bundle?.let {
            bundle.apply {
                name = getString("name")
                dob = getString("dob")
                nrc = getString("nrc")
                permanent_addr = getString("permanent_addr")
                birth_place = getString("birth_place")
                gender = getString("gender")
                fathername = getString("fathername")
                mark = getString("mark")
                height = getString("height")
                weight = getString("weight")
                //officedept = getString("officedept")
                occupation = getString("occupation")
                salary = bundle.get("salary") as Int
                officeId = bundle.get("officeId") as Int
                insuranceamt = bundle.get("insuranceamt") as Int
                insuranceperiod = bundle.get("insuranceperiod") as Int
                deposit_amount = bundle.get("deposit_amount") as Int
                townshipid = bundle.get("townshipid") as Int
                departmentid = bundle.get("departmentid") as Int
                previewofficename = getString("previewofficename")
                previewgender = getString("previewgender")
                previewtownship = getString("previewtownship")
                previewdepartment = getString("previewdepartment")
                age_next_year = getInt("age_next_year")
                amount = getInt("amount")
                phone_no = getString("phone_no")
                Log.d(TAG, name+"\n"+dob+"\n"+nrc+"\n"+permanent_addr+"\n"+birth_place+"\n"+gender+"\n"+fathername+"\n"
                        +mark+"\n"+height+"\n"+weight+"\n"+officedept+"\t"+occupation+"\n"+salary+"\n"+officeId+"\n"+insuranceamt+"\n"
                        +insuranceperiod+"\n"+deposit_amount+"\n"+age_next_year+"\n"+amount)
            }
        }
        toolbar.title = "ပုံရယူသည့်အဆိုလွှာ"
        toolbar.setTitleTextColor(ContextCompat.getColor(this,R.color.btnforeColor))
        setSupportActionBar(toolbar)
        toolbar.setNavigationIcon(ContextCompat.getDrawable(this,R.drawable.back_arrow))
        toolbar.setNavigationOnClickListener {
            finish()
        }
        Dexter.withActivity(this)
            .withPermissions(
                Manifest.permission.CAMERA,
                Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.WRITE_EXTERNAL_STORAGE
            ).withListener(object : MultiplePermissionsListener {
                override fun onPermissionsChecked(report: MultiplePermissionsReport) {/* ... */
                }

                override fun onPermissionRationaleShouldBeShown(
                    permissions: List<PermissionRequest>,
                    token: PermissionToken
                ) {/* ... */
                }
            }).check()

        btn_takephoto.setOnClickListener {
            //dispatchTakePictureIntent()
            var chooseFile = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
            chooseFile.setType("image/*")
            chooseFile = Intent.createChooser(chooseFile, "Choose a file")
            startActivityForResult(chooseFile, PICKFILE_RESULT_CODE)
        }
        btn_uploadphoto.setOnClickListener {
            if(resizedBitmap == null){
                Toast.makeText(this, "Please choose file", Toast.LENGTH_SHORT).show()
            }else{
                try {
                    var intent = Intent(this, LifeinsuranceSecond::class.java)
                    intent.putExtra("name", name)
                    intent.putExtra("dob", dob)
                    intent.putExtra("nrc", nrc)
                    intent.putExtra("permanent_addr", permanent_addr)
                    intent.putExtra("birth_place", birth_place)
                    intent.putExtra("gender", gender)
                    intent.putExtra("fathername", fathername)
                    intent.putExtra("mark", mark)
                    intent.putExtra("height", height)
                    intent.putExtra("weight", weight)
                    //intent.putExtra("officedept",officedept)
                    intent.putExtra("occupation", occupation)
                    intent.putExtra("salary", salary!!)
                    intent.putExtra("officeId", officeId!!)
                    intent.putExtra("insuranceamt", insuranceamt!!)
                    intent.putExtra("insuranceperiod", insuranceperiod!!)
                    intent.putExtra("deposit_amount", deposit_amount)
                    intent.putExtra("townshipid", townshipid)
                    intent.putExtra("departmentid", departmentid)
                    intent.putExtra("previewofficename", previewofficename)
                    intent.putExtra("previewgender", previewgender)
                    intent.putExtra("previewtownship", previewtownship)
                    intent.putExtra("previewdepartment", previewdepartment)
                    intent.putExtra("age_next_year", age_next_year)
                    intent.putExtra("amount", amount)
                    intent.putExtra("phone_no", phone_no)
                    startActivity(intent)
                }catch (e:Exception){

                    Log.d(TAG, e.toString())
                }
            }
        }
    }
    private fun dispatchTakePictureIntent(){
        Intent(MediaStore.ACTION_IMAGE_CAPTURE).also {
                takePictureIntent->takePictureIntent.resolveActivity(packageManager)?.also {
            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE)
        }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        Log.d(TAG, data.toString()+"\n"+Gson().toJson(data))
        if(requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK){
            val imageBitmap = data!!.extras!!.get("data") as Bitmap
            btn_takephoto.setImageBitmap(imageBitmap)
        }else if(requestCode == PICKFILE_RESULT_CODE && resultCode == RESULT_OK){
            //get the url from data
            val selectedImage = data!!.data

            Log.d(TAG, "URI PATH "+ selectedImage.toString())
            val realPath = getRealPathFromURI(selectedImage!!)
            Log.d(TAG, "Real URI PATH "+ realPath)
            val bmp = MediaStore.Images.Media.getBitmap(contentResolver, selectedImage)
            btn_takephoto.setImageBitmap(bmp)
            val bitmap = BitmapFactory.decodeStream(contentResolver.openInputStream(selectedImage!!))
           Log.d("BeforeCompress", BitmapCompat.getAllocationByteCount(bitmap).toString())
            resizedBitmap = getResizedBitmap(bitmap!!, 800)
            //health_recommended_letter = bitmapToBase64(resizedBitmap!!)
            save_image(resizedBitmap!!)
            Log.d(TAG, health_recommended_letter)
            sharedPreference.saveUserInfo("health_recommended_letter", cachePath)
            sharedPreference.saveUserInfo("extension", bitmaptype)
//            //Log.d("aftercompress", BitmapCompat.getAllocationByteCount(resizedBitmap).toString())
           btn_takephoto.setImageBitmap(resizedBitmap)
           Log.d(TAG, sharedPreference.getUserInfo("health_recommended_letter")+"\t"+
                   sharedPreference.getUserInfo("extension"))
          btn_takephoto.setImageBitmap(BitmapFactory.decodeFile(sharedPreference.getUserInfo("health_recommended_letter")))
        }
    }
    /**
     * reduces the size of the image
     * @param image
     * @param maxSize
     * @return
     */
    fun getResizedBitmap(image: Bitmap, maxSize: Int): Bitmap {
        var width = image.width
        var height = image.height

        val bitmapRatio = width.toFloat() / height.toFloat()
        if (bitmapRatio > 1) {
            width = maxSize
            height = (width / bitmapRatio).toInt()
        } else {
            height = maxSize
            width = (height * bitmapRatio).toInt()
        }
        return Bitmap.createScaledBitmap(image, width, height, true)
    }
    fun getRealPathFromURI(contentUri: Uri): String {

        // can post image
        val proj = arrayOf(MediaStore.Images.Media.DATA)
        val cursor = managedQuery(
            contentUri,
            proj, // WHERE clause selection arguments (none)
            null, null, null
        )// Which columns to return
        // WHERE clause; which rows to return (all rows)
        // Order-by clause (ascending by name)
        val column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA)
        cursor.moveToFirst()

        return cursor.getString(column_index)
    }

    private fun save_image(bmp: Bitmap){
        val filesDir = applicationContext.getFilesDir()
        val imageFile = File(filesDir, "govstaff.jpg")
        cachePath = imageFile.absolutePath

        val os: OutputStream
        try {
            os = FileOutputStream(imageFile)
            bmp.compress(Bitmap.CompressFormat.JPEG, 100, os)
            os.flush()
            os.close()
            Log.d(TAG, imageFile.absolutePath)

        } catch (e: Exception) {
            Log.e(javaClass.simpleName, "Error writing bitmap", e)
        }


    }




}
