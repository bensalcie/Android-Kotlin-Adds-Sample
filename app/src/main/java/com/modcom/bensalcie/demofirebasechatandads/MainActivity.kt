package com.modcom.bensalcie.demofirebasechatandads

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.firebase.ui.database.FirebaseRecyclerOptions
import com.google.android.gms.ads.*
import com.google.android.gms.ads.interstitial.InterstitialAd
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback
import com.google.firebase.database.FirebaseDatabase
import com.modcom.bensalcie.demofirebasechatandads.adapters.PostsAdapter
import com.modcom.bensalcie.demofirebasechatandads.models.Post

class MainActivity : AppCompatActivity() {
    private lateinit var mAdView: AdView
    private lateinit var mInterstitialAd: InterstitialAd
    private lateinit var recyclerPosts:RecyclerView
    private var mAdapter: PostsAdapter? = null



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        recyclerPosts = findViewById(R.id.recyclerposts)

        mAdView = findViewById(R.id.adView)
        initializeAds()
        loadBannerAd()
        loadPosts()
//        loadInterstitialAd()
    }

    private fun loadPosts() {
        val query= FirebaseDatabase.getInstance().reference.child("MODCOM").child("POSTS")
        val options = FirebaseRecyclerOptions.Builder<Post>().setQuery(query, Post::class.java).build()
        mAdapter = PostsAdapter(options)
        recyclerPosts.apply {
            layoutManager = GridLayoutManager(this@MainActivity,2)
            adapter = mAdapter
        }
        //stop progress here
    }


    private fun initializeAds() {
        MobileAds.initialize(this) { }

    }

    private fun loadBannerAd() {
        val adRequest = AdRequest.Builder().build()
        mAdView.loadAd(adRequest)
    }
    private fun loadInterstitialAd() {

        val adRequest = AdRequest.Builder().build()
        InterstitialAd.load(this,"ca-app-pub-3940256099942544/1033173712", adRequest, object : InterstitialAdLoadCallback() {
            override fun onAdFailedToLoad(adError: LoadAdError) {
                Log.d("inter", adError.message)
            }

            override fun onAdLoaded(interstitialAd: InterstitialAd) {
                mInterstitialAd = interstitialAd
                mInterstitialAd.show(this@MainActivity)

            }
        })
    }

    fun movetopost(view: View) {
        startActivity(Intent(this,PostActivity::class.java))
    }

    override fun onStart() {
        super.onStart()
        mAdapter!!.startListening()
    }

    override fun onStop() {
        super.onStop()

        if (mAdapter != null) {
            mAdapter!!.stopListening()
        }
    }
}