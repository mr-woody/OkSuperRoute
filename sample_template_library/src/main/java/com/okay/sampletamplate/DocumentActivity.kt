package com.okay.sampletamplate

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.webkit.WebChromeClient
import android.webkit.WebView
import kotlinx.android.synthetic.main.activity_sample_document.*

/**
 * @author :Created by cz
 * @date 2019-05-20 10:14
 * @email chenzhen@okay.cn
 * 加载一个从assets内的markdown文档
 */
class DocumentActivity : ToolBarActivity() {
    companion object {
        private const val URL="url"
        fun startActivity(context: Context?, url:String) {
            val intent= Intent(context,DocumentActivity::class.java)
            intent.putExtra("title",context?.getString(R.string.action_document))
            intent.putExtra(URL,url)
            context?.startActivity(intent)
        }
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sample_document)
        initLoadProgress()
        initMarkdown()
    }

    /**
     * 初始化markdown显示进度
     */
    private fun initLoadProgress() {
        //关联进度显示
        markdownView?.webChromeClient = object : WebChromeClient() {
            override fun onProgressChanged(view: WebView, newProgress: Int) {
                super.onProgressChanged(view, newProgress)
                if(null!=progressBar&&newProgress >= progressBar.getFirstProgress()){
                    progressBar.passProgressAnim { progressBar.animate().alpha(0f) }
                }
            }
        }
    }

    /**
     * 初始化markdown
     */
    private fun initMarkdown() {
        val url = intent?.getStringExtra(URL)
            //设置base_url session
        if (null==url) {
            markdownView.loadUrl("about:blank")
        } else {
            //启动进度
            progressBar.startProgressAnim()
            //加载markdown
            markdownView.loadMarkdownFromAssets(url)
        }
    }

}
