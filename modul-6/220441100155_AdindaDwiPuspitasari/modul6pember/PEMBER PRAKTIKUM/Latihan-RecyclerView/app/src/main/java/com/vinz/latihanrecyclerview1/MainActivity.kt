package com.vinz.latihanrecyclerview1

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.google.android.material.textview.MaterialTextView
import com.vinz.latihanrecyclerview1.adapter.PlayerAdapter
import com.vinz.latihanrecyclerview1.adapter.PlayerAdapterGrid
import com.vinz.latihanrecyclerview1.adapter.PlayerAdapterStaggered
import com.vinz.latihanrecyclerview1.data.PeopleData
import com.vinz.latihanrecyclerview1.data.PlayerData
import com.vinz.latihanrecyclerview1.data.PlayerDataList

class MainActivity : AppCompatActivity() {

    // Deklarasi variabel untuk adapter dan RecyclerView
    private lateinit var playerAdapter: PlayerAdapter
    private lateinit var playerAdapterGrid: PlayerAdapterGrid
    private lateinit var playerAdapterStaggered: PlayerAdapterStaggered
    private lateinit var playerAdapterHorizontal: PlayerAdapter
    private lateinit var recyclerView: RecyclerView
    private lateinit var recyclerviewHorizontal: RecyclerView
    private lateinit var btnChangeRecyclerView: Button
    private var changeRV = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Mengambil data nama dari intent
        val getDataName = intent.getStringExtra("name")

        // Menampilkan teks sapaan dengan nama pengguna
        val displayTitle = findViewById<MaterialTextView>(R.id.greeting_text)
        displayTitle.text = "Halo, $getDataName"

        // Menetapkan aksi ketika tombol diklik, maka akan mengubah tampilan dari RecyclerView
        btnChangeRecyclerView = findViewById(R.id.btnChangeRV)
        btnChangeRecyclerView.setOnClickListener {
            changeRV++
            if (changeRV > 2) {
                changeRV = 0
            }
            changeRecyclerView()
        }

        // Menghubungkan variabel dengan komponen di layout
        recyclerView = findViewById(R.id.rv_player)
        recyclerviewHorizontal = findViewById(R.id.rv_player_horizontal)

        // Menginisialisasi semua adapter dengan data
        playerAdapter = PlayerAdapter(PlayerDataList.playerDataStaggeredValue)
        playerAdapterGrid = PlayerAdapterGrid(PlayerDataList.peopledummy)
        playerAdapterStaggered = PlayerAdapterStaggered(PlayerDataList.playerDataStaggeredValue)
        playerAdapterHorizontal = PlayerAdapter(PlayerDataList.playerDataValue)

        // Menampilkan RecyclerView
        showRecyclerList()
    }

    // Fungsi untuk menampilkan RecyclerView Default
    private fun showRecyclerList() {
        // Mengatur layoutManager dan adapter untuk RecyclerView
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = playerAdapter

        // Mengatur layoutManager dan adapter untuk RecyclerView horizontal
        recyclerviewHorizontal.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        recyclerviewHorizontal.adapter = playerAdapterHorizontal

        // Menetapkan aksi ketika item di RecyclerView diklik
        playerAdapter.setOnItemClickCallback(object : PlayerAdapter.OnItemClickCallback {
            override fun onItemClicked(data: PlayerData) {
                showSelectedPlayer(data)
            }
        })

        playerAdapterGrid.setOnItemClickCallback(object : PlayerAdapterGrid.OnItemClickCallback {
            override fun onItemClicked(data: PeopleData) {
                showSelectedPlayer2(data)
            }
        })

        playerAdapterStaggered.setOnItemClickCallback(object : PlayerAdapterStaggered.OnItemClickCallback {
            override fun onItemClicked(data: PlayerData) {
                showSelectedPlayer(data)
            }
        })

        playerAdapterHorizontal.setOnItemClickCallback(object : PlayerAdapter.OnItemClickCallback {
            override fun onItemClicked(data: PlayerData) {
                showSelectedPlayer(data)
            }
        })
    }

    // Fungsi untuk menampilkan detail pemain yang dipilih
    private fun showSelectedPlayer(data: PlayerData) {
        // Membuat intent untuk berpindah ke DetailPlayerActivity
        val navigateToDetail = Intent(this, DetailPlayerActivity::class.java)

        // Menambahkan dan membawa data pemain ke intent dengan tujuan ke DetailPlayerActivity
        navigateToDetail.putExtra("name", data.name)
        navigateToDetail.putExtra("description", data.description)
        navigateToDetail.putExtra("image", data.image)

        // Memulai activity baru
        startActivity(navigateToDetail)
    }


    // Fungsi untuk menampilkan detail pemain yang dipilih
    private fun showSelectedPlayer2(data: PeopleData) {
        // Membuat intent untuk berpindah ke DetailPlayerActivity
        val navigateToDetail = Intent(this, DetailPlayerActivity::class.java)

        // Menambahkan dan membawa data pemain ke intent dengan tujuan ke DetailPlayerActivity
        navigateToDetail.putExtra("name", data.name)
        navigateToDetail.putExtra("description", data.description)
        //putExtra: membuat data dari data satu ke data lainnya

        // Memulai activity baru
        startActivity(navigateToDetail)
    }

    // Fungsi untuk mengubah tampilan RecyclerView
    private fun changeRecyclerView() {
        val rvTitle = findViewById<MaterialTextView>(R.id.rv_vertical_title)
        when (changeRV) {
            0 -> {
                // Mengubah tampilan RecyclerView menjadi LinearLayoutManager (Vertikal)
                rvTitle.text = "LinearLayoutManager (Vertikal)"
                recyclerView.layoutManager = LinearLayoutManager(this)
                recyclerView.adapter = playerAdapter
            }
            1 -> {
                // Mengubah tampilan RecyclerView menjadi GridLayoutManager (Vertikal)
                rvTitle.text = "GridLayoutManager (Vertikal)"
                recyclerView.layoutManager = GridLayoutManager(this, 2)
                recyclerView.adapter = playerAdapterGrid
            }
            2 -> {
                // Mengubah tampilan RecyclerView menjadi StaggeredLayoutManager (Vertikal)
                rvTitle.text = "StaggeredLayoutManager (Vertikal)"
                recyclerView.layoutManager = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
                recyclerView.adapter = playerAdapterStaggered
            }
        }
    }
}