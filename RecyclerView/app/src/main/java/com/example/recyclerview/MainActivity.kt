package com.example.recyclerview

import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.recyclerview.data.repository.ItemRepoImplement
import com.example.recyclerview.presentation.adapter.ItemAdapter

class MainActivity : AppCompatActivity() {

    private val repository = ItemRepoImplement()
    private lateinit var adapter: ItemAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        setupRecyclerView()
        loadData()
    }

    private fun setupRecyclerView() {
        val recyclerView = findViewById<RecyclerView>(R.id.rv_items)
        adapter = ItemAdapter(
            items = emptyList(),
            onItemClick = { item ->
                Toast.makeText(this, "Item telah ditekan untuk ${item.id}", Toast.LENGTH_SHORT).show()
            },
            onSwitchChange = { item, isChecked ->
                if (isChecked) {
                    Toast.makeText(this, "Switch hidup pada item ${item.id}", Toast.LENGTH_SHORT).show()
                }
            },
            onButtonClick = { item ->
                Toast.makeText(this, "Tombol telah ditekan untuk tombol ${item.id}", Toast.LENGTH_SHORT).show()
            }
        )
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adapter
    }

    private fun loadData() {
        val items = repository.getItems()
        adapter.updateData(items)
    }
}
