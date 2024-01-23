package com.example.newsapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.newsapp.databinding.ActivityCountryBinding

class CountryActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        val binding = ActivityCountryBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.radioGroup.setOnCheckedChangeListener { group, checkedId ->
            when (checkedId) {
                R.id.rb_us -> changeCountry("us")
                R.id.rb_de -> changeCountry("de")
            }
        }
    }

    private fun changeCountry(code: String) {
        val prefs = getSharedPreferences("country", MODE_PRIVATE).edit()
        prefs.putString("code", code)
        prefs.apply()
        Toast.makeText(this, "Changed!", Toast.LENGTH_SHORT).show();
    }
}