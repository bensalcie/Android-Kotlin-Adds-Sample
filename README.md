# Android-Kotlin-Adds-Sample
This sample will show you how to Integrate  Ads (Interstitial and Banner) to your Android App

# This Sample uses Official test Ads
This is a simple app uses test ads from google, please visit https://admob.google.com/home/ to get your own production ads. Caution, do not click on your own ads!!!

# Sample Screens
 <p float="center">
  <img src="https://github.com/bensalcie/TextDetector/blob/master/screenshots/s1.png" width="150" />
  <img src="https://github.com/bensalcie/TextDetector/blob/master/screenshots/s2.png" width="150" /> 
  <img src="https://github.com/bensalcie/TextDetector/blob/master/screenshots/s4.png" width="150" />
</p>



Requirements:
1.Android Studio (Fully Setup)
2.Basic Knowledge of Kotlin
3.Basic Interaction with Google Admob Account


# SETUP YOUR ANDROID STUDIO FOR ADS:
1.Create a New Android Studio Project
2.In your new project, add the following dependacies in the build.gradle(module)

        implementation 'com.google.android.gms:play-services-ads:20.2.0'
        implementation 'androidx.multidex:multidex: 2.0.1'
        

3. Enable mutlidex by adding the following in your default config (Same file--build.gradle(module))

       defaultConfig {
              ....
              //add this line
              multiDexEnabled true
              ......
          }
4.Head to your manifest add the following (Inside the Application tags)

          <meta-data
                    android:name="com.google.android.gms.ads.APPLICATION_ID"
                    android:value="ca-app-pub-3940256099942544~3347511713"/>
                    <!-- the value ca-app-pub-3940256099942544~3347511713 is a sample app id, replace with your own here https://admob.google.com/home/-->



# SETUP AND TRIGGER THE ADS.
We will start by creating the banner ad, in order for us to create the banner, we will add an xml layout to help us place it at the right place.
To your desired xml layout, add the following xml view:

    <com.google.android.gms.ads.AdView
        android:id="@+id/adView"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:layout_centerInParent="true"
        app:adSize="BANNER"
        app:adUnitId="ca-app-pub-3940256099942544/6300978111"
        app:layout_constraintBottom_toBottomOf="parent"
        app:layout_constraintEnd_toEndOf="parent"
        app:layout_constraintStart_toStartOf="parent" />



# Head to your Kotlin File:
Head over to your desired Kotlin File, Create the variable to hold the two ads that we are going to display:

    private lateinit var mAdView: AdView
    private lateinit var mInterstitialAd: InterstitialAd
Inside your on create function, Initialize the banner ad by finding it by id, then call the othe functions to load the ads. We will create them shortly.

          //add this after setContentView(R.layout.activity_main)
         mAdView = findViewById(R.id.adView)
                initializeAds()
                loadBannerAd()
                loadInterstitialAd()

# Initialize the Ads
To initialize the Ads, add the following function

    private fun initializeAds() {
        MobileAds.initialize(this) { }

    }

Next, we define the loadBannerAd() function

    private fun loadBannerAd() {
        val adRequest = AdRequest.Builder().build()
        mAdView.loadAd(adRequest)
    }

     
# Finally define load Interstitial ads function:
The ad unit id used over here is a test unit id, to get one visit https://admob.google.com/home/

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

# Sync your project and Run.

You definatley need a big clap for reaching this end, Hope you learnt something.
If you had any problem during this tutorial please write back to me:
# Twitter:@ibensalcie
# Email:bensalcie@gmail.com,
# WhatsApp number:+254704808070

