package com.br.natanfelipe.chelseafcnews.common

import android.graphics.Color
import android.os.Build
import android.text.Html
import android.text.Spannable
import android.text.SpannableString
import android.text.method.LinkMovementMethod
import android.text.style.ClickableSpan
import android.text.style.ForegroundColorSpan
import android.view.View
import android.widget.Toast
import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.AppCompatTextView
import androidx.databinding.BindingAdapter
import coil.api.load
import com.br.natanfelipe.chelseafcnews.R
import java.text.SimpleDateFormat

@BindingAdapter("app:image")
fun loadImage(imageView: AppCompatImageView, url: String) {
    imageView.load(url) {
        crossfade(true)
        placeholder(R.drawable.ic_image_black)
    }
}

@BindingAdapter("android:textHtml","linkUrl")
fun convertHtmlToText(textView: AppCompatTextView, text: String, url: String) {
    lateinit var spannableString: SpannableString
    lateinit var textBody: String
    val readMoreText = textView.context.getString(R.string.tap_to_read_more_label)
    textBody = text.replaceAfter("[", readMoreText).replace(" ["," ")

    if (Build.VERSION.SDK_INT >= 24) {
        spannableString = SpannableString(Html.fromHtml(textBody, Html.FROM_HTML_MODE_LEGACY))
    } else {
        @Suppress("DEPRECATION")
        spannableString = SpannableString(Html.fromHtml(textBody))
    }

    val readMoreCounter = readMoreText.length
    val startIndex = spannableString.lastIndexOf(readMoreText[0])
    val endIndex = startIndex + readMoreCounter

    val clickableSpan = object : ClickableSpan() {
        override fun onClick(widget: View) {
            Toast.makeText(textView.context, url, Toast.LENGTH_LONG).show()
        }
    }

    spannableString.setSpan(ForegroundColorSpan(Color.BLUE), startIndex - 1, endIndex, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
    spannableString.setSpan(clickableSpan, startIndex - 1, endIndex, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
    textView.text = spannableString
    textView.movementMethod = LinkMovementMethod.getInstance()
}

@BindingAdapter("dateFormat")
fun convertTextToDate(textView: AppCompatTextView, date: String) {
    val fromServer = SimpleDateFormat("yyyy-MM-dd")
    val simpleDateFormat = SimpleDateFormat("yyyy/MM/dd")
    val newDate = fromServer.parse(date)
    textView.text = simpleDateFormat.format(newDate)
}