package com.okay.sampletamplate

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.widget.Toast
import com.okay.sampletamplate.adapter.TemplateAdapter
import kotlinx.android.synthetic.main.fragment_sample_main.*
import android.view.*


/**
 * @author Created by cz
 * @date 2019-05-09 14:34
 * @email chenzhen@okay.cn
 * 首页fragment对象
 */
class MainFragment: Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_sample_main,container,false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        val activity=activity as? AppCompatActivity?:return
        val id=activity.intent.getIntExtra("id",0)
        val title = activity.intent.getStringExtra("title")
        if(null==title) {
            //判断是否配置文档,打开optionMenu
            val document=FuncTemplate.document
            if(!document.isNullOrBlank()){
                setHasOptionsMenu(true)
            }
            toolBar.setTitle(R.string.app_name)
            activity.setSupportActionBar(toolBar)
        } else {
            toolBar.title = title
            toolBar.subtitle=activity.intent.getStringExtra("desc")
            activity.setSupportActionBar(toolBar)
            activity.supportActionBar?.setDisplayHomeAsUpEnabled(true)
            toolBar.setNavigationOnClickListener{ activity?.finish() }
        }
        val context=context?:return
        //添加分隔线
        recyclerView.addItemDecoration(DividerItemDecoration(context,RecyclerView.VERTICAL))
        recyclerView.layoutManager= LinearLayoutManager(context)
        val items = FuncTemplate[id]
        if(null!=items){
            recyclerView.adapter= TemplateAdapter(activity, items)
        } else {
            Toast.makeText(context,"No more items!", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        menu.clear()
        inflater.inflate(R.menu.main_sample_document_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_document -> {
                val document=FuncTemplate.document
                if(null!=document){
                    DocumentActivity.startActivity(context,document)
                }
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}