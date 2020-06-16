package com.example.patri.refinado.Util

import android.content.Context
import android.graphics.Bitmap
import android.widget.Toast
import android.R.attr.bitmap
import java.io.ByteArrayOutputStream
import android.graphics.BitmapFactory




fun toastLong(message: String, context: Context): Unit {
    Toast.makeText(context, message, Toast.LENGTH_LONG).show()

}

fun toastShort(message: String, context: Context): Unit {
    Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
}

fun getByteImage(x: Bitmap): ByteArray{
    val stream = ByteArrayOutputStream()
    x.compress(Bitmap.CompressFormat.PNG, 100, stream)
    return stream.toByteArray()
}

fun getBitmapImage(image: ByteArray): Bitmap {
    return BitmapFactory.decodeByteArray(image, 0, image.size)
}