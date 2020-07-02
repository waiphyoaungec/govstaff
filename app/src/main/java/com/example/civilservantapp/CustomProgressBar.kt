package com.example.civilservantapp

import android.app.Dialog
import android.content.Context
import android.graphics.BlendMode
import android.graphics.BlendModeColorFilter
import android.graphics.Color
import android.graphics.PorterDuff
import android.graphics.drawable.Drawable
import android.os.Build
import android.view.LayoutInflater
import android.widget.TextView
import androidx.annotation.NonNull
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat
import kotlinx.android.synthetic.main.dialog_layout.view.*

class CustomProgressBar {

    lateinit var dialog: Dialog
    lateinit var textView : TextView

    fun show(context: Context): Dialog {
        return show(context, null)
    }

    fun show(context: Context, title:CharSequence?): Dialog {
        val inflator = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val view = inflator.inflate(R.layout.dialog_layout, null)
        textView = view.findViewById(R.id.cp_title)
        if (title != null) {
            textView.text = title
        }
        view.cp_bg_view.setBackgroundColor(ContextCompat.getColor(context,R.color.gray)) //Background Color
        view.cp_cardview.setCardBackgroundColor(ContextCompat.getColor(context,R.color.colorPrimary) )//Box Color
        setColorFilter(view.cp_pbar.indeterminateDrawable,
          Color.WHITE) //Progress Bar Color
        view.cp_title.setTextColor(Color.WHITE) //Text Color

        dialog = Dialog(context, R.style.CustomProgressBarTheme)
        dialog.setContentView(view)
        dialog.show()

        return dialog
    }

    private fun setColorFilter(@NonNull drawable: Drawable, color:Int) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            drawable.colorFilter = BlendModeColorFilter(color, BlendMode.SRC_ATOP)
        } else {
            @Suppress("DEPRECATION")
            drawable.setColorFilter(color, PorterDuff.Mode.SRC_ATOP)
        }
    }
}