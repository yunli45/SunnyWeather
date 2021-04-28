package com.ws.sunnyweather.ui.place

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.ws.sunnyweather.R
import kotlinx.android.synthetic.main.fragment_place.*

class PlaceFragment : Fragment() {


    val viewModel by lazy { ViewModelProvider(this).get(PlaceViewModel::class.java) }

    private lateinit var adapter: PlaceAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_place, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        val layoutManager = LinearLayoutManager(activity)
        recyclerView.layoutManager = layoutManager
        adapter = PlaceAdapter(this, viewModel.placeList)
        recyclerView.adapter = adapter
        // 数据请求的发起
        searchPlaceEdit.addTextChangedListener {
            val content = it.toString()
            if (content.isNotEmpty()) {
                viewModel.searchPlaces(content)
            } else {
                recyclerView.visibility = View.GONE
                bgImageView.visibility = View.VISIBLE
                viewModel.placeList.clear()
                adapter.notifyDataSetChanged()
            }

        }
        // 获取服务器响应的数据 LiveData
        /**
         * 对PlaceViewModel中的placeLiveData对象进行
        观察，当有任何数据变化时，就会回调到传入的Observer接口实现中。然后我们会对回调的数
        据进行判断：如果数据不为空，那么就将这些数据添加到PlaceViewModel的placeList集合
        中，并通知PlaceAdapter刷新界面；如果数据为空，则说明发生了异常，此时弹出一个Toast
        提示，并将具体的异常原因打印出来。
         */
        viewModel.placeLiveData.observe(viewLifecycleOwner, Observer {
            val places = it.getOrNull()
            if (places != null) {
                recyclerView.visibility = View.VISIBLE
                bgImageView.visibility = View.GONE
                viewModel.placeList.clear()
                viewModel.placeList.addAll(places)
                adapter.notifyDataSetChanged()

            } else {
                Toast.makeText(activity, "未能查询到任何地点", Toast.LENGTH_SHORT).show()
                it.exceptionOrNull()?.printStackTrace()

            }
        })


    }
}
