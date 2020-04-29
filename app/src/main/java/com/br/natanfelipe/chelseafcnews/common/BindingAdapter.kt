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
import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.AppCompatTextView
import androidx.databinding.BindingAdapter
import androidx.navigation.Navigation
import coil.api.load
import com.br.natanfelipe.chelseafcnews.R
import com.br.natanfelipe.chelseafcnews.ui.details.ArticleDetailsFragmentDirections
import java.text.SimpleDateFormat
import java.util.*

@BindingAdapter("app:image")
fun loadImage(imageView: AppCompatImageView, url: String?) {
    imageView.load(url) {
        crossfade(true)
        placeholder(R.drawable.ic_image_black)
        fallback(R.mipmap.ic_launcher)
    }
}

@BindingAdapter("android:textHtml", "linkUrl")
fun convertHtmlToText(textView: AppCompatTextView, text: String, url: String) {
    lateinit var spannableString: SpannableString
    val readMoreText = textView.context.getString(R.string.tap_to_read_more_label)
    val textBody = text.replaceAfter("[", readMoreText).replace(" [", " ")
    val readMoreCounter = readMoreText.length

    spannableString = if (Build.VERSION.SDK_INT >= 24) {
        SpannableString(Html.fromHtml(textBody, Html.FROM_HTML_MODE_LEGACY))
    } else {
        @Suppress("DEPRECATION")
        SpannableString(Html.fromHtml(textBody))
    }

    val startIndex = spannableString.lastIndexOf(readMoreText[0])
    val endIndex = startIndex + readMoreCounter

    val clickableSpan = object : ClickableSpan() {
        override fun onClick(widget: View) {
            val action = ArticleDetailsFragmentDirections.navigateToWebArticle(url)
            Navigation.findNavController(textView).navigate(action)
        }
    }

    spannableString.apply {
        setSpan(
            ForegroundColorSpan(Color.BLUE),
            startIndex - 1,
            endIndex,
            Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
        )
        setSpan(clickableSpan, startIndex - 1, endIndex, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
    }
    textView.apply {
        this.text = spannableString
        movementMethod = LinkMovementMethod.getInstance()
    }
}

@BindingAdapter("dateFormat")
fun convertTextToDate(textView: AppCompatTextView, date: String) {
    val fromServer = SimpleDateFormat("yyyy-MM-dd")
    val simpleDateFormat = SimpleDateFormat("yyyy/MM/dd")
    val newDate: Date = fromServer.parse(date)
    textView.text = simpleDateFormat.format(newDate)
}