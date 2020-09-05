package com.erbe.happyplaces

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.erbe.happyplaces.model.HappyPlaceModel
import kotlinx.android.synthetic.main.activity_happy_place_detail.*

class HappyPlaceDetailActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_happy_place_detail)

        var happyPlaceDetailModel : HappyPlaceModel? = null

        if (intent.hasExtra(MainActivity.EXTRA_PLACE_DETAILS)) {
            happyPlaceDetailModel = intent.getParcelableExtra(MainActivity.EXTRA_PLACE_DETAILS)
        }

        if (happyPlaceDetailModel != null) {
            setSupportActionBar(happy_place_detail_toolbar)
            supportActionBar!!.setDisplayHomeAsUpEnabled(true)
            supportActionBar!!.title = happyPlaceDetailModel.title

            happy_place_detail_toolbar.setNavigationOnClickListener {
                onBackPressed()
            }

            place_image_iv.setImageURI(Uri.parse(happyPlaceDetailModel.image))
            detail_description_tv.text = happyPlaceDetailModel.description
            detail_location_tv.text = happyPlaceDetailModel.location

            view_on_map_btn.setOnClickListener {
                val intent = Intent(this, MapsActivity::class.java)
                intent.putExtra(MainActivity.EXTRA_PLACE_DETAILS, happyPlaceDetailModel)
                startActivity(intent)
            }
        }
    }
}