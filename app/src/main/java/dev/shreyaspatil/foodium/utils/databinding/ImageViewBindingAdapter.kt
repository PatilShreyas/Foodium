package dev.shreyaspatil.foodium.utils.databinding

import android.graphics.drawable.Drawable
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide

@BindingAdapter(value = ["imageUrl", "placeHolder"], requireAll = false)
fun ImageView.LoadImageUrl(imageUrl: String?, placeholder: Drawable?) {
    Glide.with(this)
        .load(imageUrl)
        .placeholder(placeholder)
        .error(placeholder)
        .into(this)
}