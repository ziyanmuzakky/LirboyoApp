package com.example.lirboyoapp.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.text.HtmlCompat
import com.bumptech.glide.Glide
import com.example.lirboyoapp.databinding.ActivityDetailBinding

class DetailActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val actionBar = supportActionBar
        actionBar!!.title = "Detail"

        val title   = intent.getStringExtra("title")
        val content = intent.getStringExtra("content")
        val date    = intent.getStringExtra("date")
        val image   = intent.getStringExtra("image")

        binding.textTitle.text = title
        binding.textDate.text = date
        binding.textDescription.text = HtmlCompat.fromHtml(content!!,HtmlCompat.FROM_HTML_MODE_LEGACY)

        Glide.with(applicationContext)
            .load(image)
            .into(binding.image)

    }
}