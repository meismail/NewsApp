package com.example.newsapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.example.newsapp.databinding.ActivityHomeBinding
import com.google.android.gms.ads.AdError
import com.google.android.gms.ads.FullScreenContentCallback
import com.google.android.gms.ads.LoadAdError
import com.google.android.gms.ads.MobileAds
import com.google.android.gms.ads.admanager.AdManagerAdRequest
import com.google.android.gms.ads.admanager.AdManagerInterstitialAd
import com.google.android.gms.ads.admanager.AdManagerInterstitialAdLoadCallback
import com.google.android.gms.ads.interstitial.InterstitialAd

class HomeActivity : AppCompatActivity() {
    private var mAdManagerInterstitialAd: InterstitialAd? = null
    private var category = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        val binding = ActivityHomeBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        // ads
        MobileAds.initialize(this) {}
        //call for ad loading
        loadAd()
        Log.d("ADstatus", "load time")
        //call for add showing when press button
        binding.techNews.setOnClickListener {
            showAd()
            category = "technology"
            Log.d("trace","techBtn was clicked")
            Log.d("ADstatus","show time")
        }
        binding.scienceNews.setOnClickListener {
            showAd()
            category = "science"
            Log.d("trace","sceinceBtn was clicked")
            Log.d("ADstatus","show time")
        }
        binding.sportNews.setOnClickListener {
            showAd()
            category = "sports"
            Log.d("trace","sportBtn was clicked")
            Log.d("ADstatus","show time")
        }

        binding.topAppBar.setOnMenuItemClickListener {
            return@setOnMenuItemClickListener when (it.itemId) {
                R.id.countries_item -> {
                    startActivity(Intent(this, CountryActivity::class.java))
                    Log.d("trace","go to CountryActivity")
                    true

                }

                else -> {Log.d("trace","fail to go to CountryActivity")
                false}

            }

        }
    }

    private fun openNewsPage() {
        val i = Intent(this, MainActivity::class.java)
        i.putExtra("cat", category)
        Log.d("trace","go to MainActivity")
        startActivity(i)
    }

    // fun for load ads
    private fun loadAd() {
        val adRequest = AdManagerAdRequest.Builder().build()

        AdManagerInterstitialAd.load(this, "/6499/example/interstitial", adRequest,
            object : AdManagerInterstitialAdLoadCallback() {
                override fun onAdFailedToLoad(adError: LoadAdError) {
                    mAdManagerInterstitialAd = null
                    Log.d("ADstatus", "The interstitial ad fail to load.")
                }

                override fun onAdLoaded(interstitialAd: AdManagerInterstitialAd) {
                    mAdManagerInterstitialAd = interstitialAd
                    Log.d("ADstatus", "The interstitial ad was loaded.")
                }
            })
    }

    // fun for show add
    private fun showAd() {
        if (mAdManagerInterstitialAd != null) {
            Log.d("ADstatus", "The interstitial ad was shown.")
            mAdManagerInterstitialAd?.show(this)
            mAdManagerInterstitialAd?.fullScreenContentCallback =
                object : FullScreenContentCallback() {

                    override fun onAdDismissedFullScreenContent() {
                        // Called when ad is dismissed.
                        mAdManagerInterstitialAd = null
                        Log.d("ADstatus", "The interstitial ad dismissed.")
                        openNewsPage()
                    }

                    override fun onAdFailedToShowFullScreenContent(adError: AdError) {
                        // Called when ad fails to show.
                        mAdManagerInterstitialAd = null
                        Log.d("ADstatus", "The interstitial ad fail to show.")
                        openNewsPage()
                    }
                }
        } else {
            Log.d("ADstatus", "The interstitial ad wasn't ready yet.")
            openNewsPage()
        }
    }
}


