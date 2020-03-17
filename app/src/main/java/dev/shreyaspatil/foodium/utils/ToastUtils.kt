package dev.shreyaspatil.foodium.utils

import android.app.Activity
import android.widget.Toast

fun Activity.showToast(message: String, duration: Int = Toast.LENGTH_SHORT) {
    Toast.makeText(applicationContext, message, duration).show()
}