package com.woodys.sampletamplate.markdown

import android.content.Context
import android.os.Build
import android.support.annotation.WorkerThread
import android.util.AttributeSet
import android.view.View
import android.webkit.WebSettings
import android.webkit.WebView
import java.io.File
import java.util.concurrent.Executors

/**
 * @author :Created by cz
 * @date 2019/4/23 下午2:21
 * @email chenzhen@woodys.cn
 * 一个动态装载markdown 文档的webView控件
 */
class MarkdownView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0) : WebView(context, attrs, defStyleAttr) {
    companion object {
        const val BASE_URL="file:///android_asset/"
    }
    private val threadExecutor = Executors.newSingleThreadExecutor()

    init {
        //this markdown library needed javascript
        overScrollMode= View.OVER_SCROLL_IF_CONTENT_SCROLLS
        isHorizontalScrollBarEnabled = false
        settings.javaScriptEnabled=true
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            settings.layoutAlgorithm = WebSettings.LayoutAlgorithm.TEXT_AUTOSIZING
        }

    }

    /**
     * Loads the given Markdown text to the view as rich formatted HTML. The
     * HTML output will be styled based on the given CSS file.
     *
     * @param text
     * - input in markdown format
     * @param cssUrl
     * - a URL to css File. If the file located in the project assets
     * folder then the URL should start with "file:///android_asset/"
     */
    fun loadMarkdownFromText(text: String, cssUrl: String? = null) {
        threadExecutor.execute { loadMarkdown(text, cssUrl) }
    }

    /**
     * Loads the given Markdown file to the view
     * @param file
     * - a local store file, It should be a markdown file
     * @param cssUrl
     * - a path to css file. If the file located in the project assets folder
     * then the URL should start with "file:///android_asset/"
     */
    fun loadMarkdownFromFile(file: File, cssUrl: String? = null){
        // Read file and load markdown
        threadExecutor.execute {
            val text=file.readText()
            // load markdown by text
            loadMarkdown(text,cssUrl)
        }
    }

    /**
     * Loads the given Markdown file to the view
     * @param file
     * - a local store file, It should be a markdown file
     * @param cssUrl
     * - a path to css file. If the file located in the project assets folder
     * then the URL should start with "file:///android_asset/"
     */
    fun loadMarkdownFromAssets(filePath: String, cssUrl: String? = null){
        // Read file and load markdown
        threadExecutor.execute {
            val text=context.assets.open(filePath).reader().use { it.readText() }
            // load markdown by text
            loadMarkdown(text,cssUrl)
        }
    }

    /**
     * Load markdown by text,Here we well use marked js convert text to html source
     * @link https://github.com/markedjs/marked
     *
     */
    @WorkerThread
    private fun loadMarkdown(text: String?, cssUrl: String? = null) {
        if(text.isNullOrBlank()){
            post { loadUrl("about:blank") }
        } else {
            // load markdown by text
            val inputStream = context.assets.open("markdown/markdown.html")
            val templateSource=inputStream.bufferedReader().use { it.readText() }
            val html=String.format(templateSource,text)
            post { loadDataWithBaseURL(BASE_URL, html, "text/html", "UTF-8", null) }
        }
    }
}