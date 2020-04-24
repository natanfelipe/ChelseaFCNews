package com.br.natanfelipe.chelseafcnews.common

import android.graphics.Color
import android.os.Build
import android.text.Html
import android.text.Spannable
import android.text.SpannableString
import android.text.style.ForegroundColorSpan
import androidx.appcompat.widget.AppCompatTextView
import com.br.natanfelipe.chelseafcnews.R

class Utils{
    fun tapToReadMoreString (textView: AppCompatTextView, text: String){
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

        spannableString.setSpan(ForegroundColorSpan(Color.BLUE), startIndex - 1, endIndex, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
        textView.text = spannableString
    }
}