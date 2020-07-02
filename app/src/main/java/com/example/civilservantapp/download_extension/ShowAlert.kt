package com.example.civilservantapp.download_extension

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import java.io.File


class ShowAlert internal constructor(private val ctx : Context){
    fun showQ(uri : Uri){
        AlertDialog.Builder(ctx).setMessage("စာချုပ်အား download/governments တွင်သိမ်းဆည်းထားသည် \n\nစာချုပ်အားယခုကြည့်မည်။")
            .setPositiveButton("ကြည့်မည်") { arg0, arg1 ->
                ctx.startActivity(
                    Intent(
                        Intent.ACTION_VIEW,
                        uri
                    ).addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
                )
            }
            .setNegativeButton("မကြည့်သေးပါ", { arg0, arg1 -> })
            .show()

    }
    fun showQ(uri : Uri,close: Activity){
        AlertDialog.Builder(ctx).setMessage("စာချုပ်အား download/governments တွင်သိမ်းဆည်းထားသည် \n\nစာချုပ်အားယခုကြည့်မည်။")
            .setPositiveButton("ကြည့်မည်") { arg0, arg1 ->
                ctx.startActivity(
                    Intent(
                        Intent.ACTION_VIEW,
                        uri
                    ).addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
                )
            }
            .setOnCancelListener{
                close.onBackPressed()
            }
            .setNegativeButton("မကြည့်သေးပါ", { arg0, arg1 -> })
            .show()

    }
    fun show(uri : Uri){
        AlertDialog.Builder(ctx).setMessage("စာချုပ်အား download/governments တွင်သိမ်းဆည်းထားသည် \n\nစာချုပ်အားယခုကြည့်မည်။")
            .setPositiveButton("ကြည့်မည်") { arg0, arg1 ->
                val file = File(uri.path!!)
                openPdf(file.absolutePath)
            }
            .setOnCancelListener{
                Log.d("test","Hello")
            }
            .setNegativeButton("မကြည့်သေးပါ", { arg0, arg1 -> })
            .show()

    }
    fun show(uri : Uri,activity: Activity){
        AlertDialog.Builder(ctx).setMessage("စာချုပ်အား download/governments တွင်သိမ်းဆည်းထားသည် \n\nစာချုပ်အားယခုကြည့်မည်။")
            .setPositiveButton("ကြည့်မည်") { arg0, arg1 ->
                val file = File(uri.path!!)
                openPdf(file.absolutePath)
            }
            .setOnCancelListener{
                activity.onBackPressed()
            }
            .setNegativeButton("မကြည့်သေးပါ", { arg0, arg1 -> })
            .show()

    }
    private fun openPdf(file : String){
        val i = Intent()
        i.setDataAndType(Uri.fromFile(File(file)), "application/pdf")
        Log.d("test","can read $file")

        try {
            ctx.startActivity(i)
        }
        catch (e: java.lang.Exception) {
            Log.d("testtest","errror ${e.localizedMessage}")
            Toast.makeText(ctx,"some error", Toast.LENGTH_SHORT).show()
        }
    }
}