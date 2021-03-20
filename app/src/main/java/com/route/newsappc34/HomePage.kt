package com.route.newsappc34

import android.content.DialogInterface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ProgressBar
import android.widget.TableLayout
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.tabs.TabLayout
import com.route.islamigsun.base.BaseActivity
import com.route.newsappc34.api.ApiManager
import com.route.newsappc34.api.model.ArticlesItem
import com.route.newsappc34.api.model.NewsResponse
import com.route.newsappc34.api.model.SourcesItem
import com.route.newsappc34.api.model.SourcesResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HomePage : BaseActivity(),TabLayout.OnTabSelectedListener {
    //5909ae28122a471d8b0c237d5989cb73
    lateinit var progressBar: ProgressBar
    lateinit var tabLayout: TabLayout
    lateinit var recyclerView: RecyclerView
    lateinit var adapter: NewsAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home_page)
        setUpViews()
        changedFunctionName()

    }

    private fun setUpViews() {
        progressBar =findViewById(R.id.progress)
        tabLayout =findViewById(R.id.tabLayout)
        recyclerView =findViewById(R.id.recycler_view)
        adapter = NewsAdapter(null)
        recyclerView.adapter=adapter
    }

    fun changedFunctionName(){
        ApiManager.getApis()
            .getNewsSources(Constants.apiKey,
            "en",country = "us")
            .enqueue(object :Callback<SourcesResponse>{
                override fun onResponse(
                    call: Call<SourcesResponse>,
                    response: Response<SourcesResponse>
                ) {
                    progressBar.visibility = View.GONE
                    if(response.isSuccessful){// 200-299
                        showSourcesInTabLayout(response.body()?.sources)
                    }else {
                        showDialoge(message =response.body()?.message?:"" ,
                            posActionName = getString(R.string.ok),
                            posAction = DialogInterface.OnClickListener { dialog, which ->
                                dialog.dismiss()
                            })
                    }
                }

                override fun onFailure(call: Call<SourcesResponse>, t: Throwable) {
                    progressBar.visibility = View.GONE
                    showDialoge(message =t.localizedMessage,
                        posActionName = getString(R.string.retry),
                    posAction = DialogInterface.OnClickListener { dialog, which ->
                        call.enqueue(this)
                        dialog.dismiss()
                    })
                }
            })
    }

    private fun showSourcesInTabLayout(sources: List<SourcesItem?>?) {

        sources?.forEach {item->
            val tab = tabLayout.newTab()
            tab.setText(item?.name)
            tab.setTag(item)
            tabLayout.addTab(tab)
        }
        tabLayout.addOnTabSelectedListener(this)
        tabLayout.getTabAt(0)?.select()
    }
    override fun onTabReselected(tab: TabLayout.Tab?) {
        val item = tab?.tag as SourcesItem
        getNews(item.id)
    }

    override fun onTabSelected(tab: TabLayout.Tab?) {
        val item = tab?.tag as SourcesItem
        getNews(item.id)
    }


    override fun onTabUnselected(tab: TabLayout.Tab?) {
    }

    private fun getNews(sourceId: String?) {
        adapter.changeData(null)
        progressBar.visibility=View.VISIBLE
        ApiManager.getApis()
            .getNews(Constants.apiKey,"en",sourceId?:"","")
            .enqueue(object :Callback<NewsResponse>{
                override fun onResponse(
                    call: Call<NewsResponse>,
                    response: Response<NewsResponse>
                ) {
                    progressBar.visibility=View.GONE

                    if(response.isSuccessful){// 200-299
                        showNewsInRecyclerView(response.body()?.articles)

                    }else {
                        showDialoge(message =response.body()?.message?:"" ,
                            posActionName = getString(R.string.ok),
                            posAction = DialogInterface.OnClickListener { dialog, which ->
                                dialog.dismiss()
                            })
                    }
                }

                override fun onFailure(call: Call<NewsResponse>, t: Throwable) {
                    progressBar.visibility=View.GONE
                    showDialoge(message =t.localizedMessage,
                        posActionName = getString(R.string.retry),
                        posAction = DialogInterface.OnClickListener { dialog, which ->
                            call.enqueue(this)
                            dialog.dismiss()
                        })
                }
            })
    }

    private fun showNewsInRecyclerView(newsList: List<ArticlesItem?>?) {
        adapter.changeData(newsList)
    }

}