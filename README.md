# Android-Kotlin-Adds-Sample
This sample will show you how to Integrate  Ads (Interstitial and Banner) to your Android App

# This Sample uses Official test Ads
This is a simple app uses test ads from google, please visit https://admob.google.com/home/ to get your own production ads. Caution, do not click on your own ads!!!

# Sample Screens
 <p float="center">
  <img src="https://github.com/bensalcie/Android-Kotlin-Adds-Sample/blob/master/Screenshot_20210727-085003_Demo%20Firebase%20Chat%20and%20Ads.jpg" width="250" />
 <span><span><span>
  <img src="https://github.com/bensalcie/Android-Kotlin-Adds-Sample/blob/master/Screenshot_20210727-085012_Demo%20Firebase%20Chat%20and%20Ads.jpg" width="250" /> 

  
</p>



# Requirements:
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
  
  
# Adding Firebase to your Project
 Create a new Activity called Post Activity.
 In activity_post.xml change th parent layout to Linear layout (Vertical), then paste the following
  
    <ImageView
           android:layout_width="match_parent"
           android:id="@+id/ivImage"
           android:scaleType="centerCrop"
           android:src="@android:drawable/ic_menu_gallery"
           android:layout_margin="15dp"
           android:layout_height="200dp"/>

     <com.google.android.material.textfield.TextInputLayout
         android:layout_width="match_parent"
         android:layout_margin="20dp"
         android:layout_height="wrap_content">
         <com.google.android.material.textfield.TextInputEditText
             android:layout_width="match_parent"
             android:hint="Enter Title"
             android:inputType="textPersonName"
             android:id="@+id/etTitle"
             android:layout_height="wrap_content"/>
     </com.google.android.material.textfield.TextInputLayout>
  
     <com.google.android.material.textfield.TextInputLayout
         android:layout_width="match_parent"
         android:layout_margin="20dp"
         android:layout_height="wrap_content">
         <com.google.android.material.textfield.TextInputEditText
             android:layout_width="match_parent"
             android:hint="Description"
             android:lines="5"
             android:id="@+id/etDescripion"
             android:gravity="start"
             android:layout_height="wrap_content"/>
     </com.google.android.material.textfield.TextInputLayout>

     <ProgressBar
         android:layout_width="wrap_content"
         android:indeterminate="true"
         android:layout_margin="5dp"
         android:layout_gravity="center"
         android:visibility="gone"
         android:id="@+id/progressbar"
         android:layout_height="wrap_content"/>
     <com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton
         android:layout_width="wrap_content"
         android:text="POST NOW"
         android:layout_gravity="center"
         android:textColor="@color/white"
         android:gravity="center"
         android:onClick="postItem"
         android:id="@+id/btnPost"
         android:layout_height="wrap_content"/>    
  
# PostActivity.kt File
  Add these at the start of PostActivity.kt
  
     private lateinit var storageReference: StorageReference
    private lateinit var databaseReference: DatabaseReference
    private lateinit var progressBar: ProgressBar
  
## Inside on create function 
  Paste the following inside the on create function
  
         progressBar = findViewById(R.id.progressbar)
              storageReference = FirebaseStorage.getInstance().reference.child("MODCOM/IMAGES").child("${System.currentTimeMillis()}"+".jpg")
              databaseReference = FirebaseDatabase.getInstance().reference.child("MODCOM/POSTS")
       When taping on the image view we created above, we shiuld get to the gallery, This is achieved by the following code, add it after the above code
          ivImage.setOnClickListener {
                  val galleryIntent = Intent(Intent.ACTION_GET_CONTENT)
                  galleryIntent.setType("image/*")
                  startActivityForResult(galleryIntent,GALLERY_REQUEST_CODE)
              }

  
  # Handling he post and Mking it ready for upload
  This function will pick our inputs and Vlidate them before uplaoding to firebase, Please note that we have also allowed our users to upload the post without an image
         
  
             fun postItem(view: View) {
                      progressBar.visibility = View.VISIBLE
                      val title = etTitle.text.toString()
                      val descrition = etDescription.text.toString()
                      if (!TextUtils.isEmpty(title) && !TextUtils.isEmpty(descrition)){
                          //ready to post
                              var imageurl = "default"
                          if (imageUri!=null){
                              //image  supplied
                              val uploadTask:UploadTask = storageReference.putFile(imageUri!!)
                              uploadTask.continueWithTask { task ->
                                  if (!task.isSuccessful) {
                                      task.exception?.let {
                                          throw it
                                      }
                                  }
                                  storageReference.downloadUrl
                              }.addOnCompleteListener { task ->
                                  if (task.isSuccessful) {
                                      val downloadUri = task.result
                                      uploadToFirebase(title = title,description= descrition,imageurl = downloadUri.toString())

                                  } else {
                                      Toast.makeText(this, "Something went wrong while uploading image", Toast.LENGTH_SHORT).show()
                                  }
                              }

                          }else{
                              //image not supplied
                              progressBar.visibility = View.VISIBLE
                              uploadToFirebase(title = title,description= descrition,imageurl = imageurl)

                          }



                      }


                  }

  # Its time to upload our post to Firebase
 Add this method in your PostActivity.kt
//function to upload to firebase
    //please supply title, description and image
  
    private fun uploadToFirebase(title:String, description:String,  imageurl:String){
        val hashMap = HashMap<String, Any>()
        hashMap["title"] = title
        hashMap["description"] = description
        hashMap["timestamp"] = System.currentTimeMillis()
        hashMap["image"]=imageurl
        //random key
        val postid = databaseReference.push().key.toString()
        databaseReference.child(postid).updateChildren(hashMap).addOnCompleteListener{
            if (it.isSuccessful){
                progressBar.visibility = View.GONE
                Toast.makeText(this, "Posted Successfully", Toast.LENGTH_SHORT).show()
                startActivity(Intent(this,MainActivity::class.java))
                finish()
                    }else{
                progressBar.visibility = View.GONE
                Toast.makeText(this, "Error:${it.exception?.message}", Toast.LENGTH_SHORT).show()
            }
        }
    }
# Get the Image path.
  We have to identify the path where we selected the image from the gallery, so this function will help us achieve that and return the result to the imageview created earlier
    
      override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
            super.onActivityResult(requestCode, resultCode, data)
            if (requestCode == GALLERY_REQUEST_CODE && resultCode == RESULT_OK){
                imageUri = data?.data!!
                ivImage.setImageURI(imageUri)
            }
        }
# Test Case
  This is what you will see when you check in your databse after uploading it.
    <img src="https://github.com/bensalcie/Android-Kotlin-Adds-Sample/blob/master/7.png" width="100%" /> 

You definatley need a big clap for reaching this end, Hope you learnt something.
If you had any problem during this tutorial please write back to me:
# Twitter:@ibensalcie
# Email:bensalcie@gmail.com,
# WhatsApp number:+254704808070

