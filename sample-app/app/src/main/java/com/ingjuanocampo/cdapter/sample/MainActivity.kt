package com.ingjuanocampo.cdapter.sample

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.RecyclerView
import com.ingjuanocampo.cdapter.CompositeDelegateAdapter
import com.ingjuanocampo.cdapter.sample.delegate.ColorItemViewHolder
import com.ingjuanocampo.cdapter.sample.delegate.ColorRecyclerViewType

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val recyclerView = findViewById<RecyclerView>(R.id.recyclerView)

        val adapter = CompositeDelegateAdapter(10)

        adapter.addDelegate(1) { ColorItemViewHolder(it) }

        recyclerView.adapter = adapter

        adapter.addItem(ColorRecyclerViewType(
                "Test 1",
                R.color.white,
                R.color.design_default_color_error
        ))

        adapter.addItem(ColorRecyclerViewType(
                "Test 2",
                R.color.black,
                R.color.white
        ))
        adapter.addItem(ColorRecyclerViewType(
                "Test 3",
                R.color.purple_200,
                R.color.teal_700
        ))
        adapter.addItem(ColorRecyclerViewType(
                "Test 4",
                R.color.design_default_color_primary,
                R.color.design_default_color_secondary
        ))
        adapter.addItem(ColorRecyclerViewType(
                "Test 5",
                R.color.material_on_background_disabled,
                R.color.design_default_color_on_secondary
        ))

    }
}