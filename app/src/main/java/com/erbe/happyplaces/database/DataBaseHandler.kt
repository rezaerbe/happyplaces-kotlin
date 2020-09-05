package com.erbe.happyplaces.database

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteException
import android.database.sqlite.SQLiteOpenHelper
import com.erbe.happyplaces.model.HappyPlaceModel

class DataBaseHandler(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object {
        private val DATABASE_VERSION = 1
        private val DATABASE_NAME = "HappyPlacesDB"
        private val TABLE_HAPPY_PLACES = "HappyPlacesTable"

        private val COLUMN_ID = "_id"
        private val COLUMN_TITLE = "title"
        private val COLUMN_IMAGE = "image"
        private val COLUMN_DESCRIPTION = "description"
        private val COLUMN_DATE = "date"
        private val COLUMN_LOCATION = "location"
        private val COLUMN_LATITUDE = "latitude"
        private val COLUMN_LONGITUDE = "longitude"
    }

    override fun onCreate(db: SQLiteDatabase?) {

        val CREATE_HAPPY_PLACE_TABLE = ("CREATE TABLE " + TABLE_HAPPY_PLACES + "("
                + COLUMN_ID + " INTEGER PRIMARY KEY,"
                + COLUMN_TITLE + " TEXT,"
                + COLUMN_IMAGE + " TEXT,"
                + COLUMN_DESCRIPTION + " TEXT,"
                + COLUMN_DATE + " TEXT,"
                + COLUMN_LOCATION + " TEXT,"
                + COLUMN_LATITUDE + " TEXT,"
                + COLUMN_LONGITUDE + " TEXT)")

        db?.execSQL(CREATE_HAPPY_PLACE_TABLE)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {

        db?.execSQL("DROP TABLE IF EXISTS $TABLE_HAPPY_PLACES")
        onCreate(db)
    }

    fun addHappyPlace(happyPlace: HappyPlaceModel) : Long {
        val db = this.writableDatabase

        val values = ContentValues()
        values.put(COLUMN_TITLE, happyPlace.title)
        values.put(COLUMN_IMAGE, happyPlace.image)
        values.put(COLUMN_DESCRIPTION, happyPlace.description)
        values.put(COLUMN_DATE, happyPlace.date)
        values.put(COLUMN_LOCATION, happyPlace.location)
        values.put(COLUMN_LATITUDE, happyPlace.latitude)
        values.put(COLUMN_LONGITUDE, happyPlace.longitude)

        val result = db.insert(TABLE_HAPPY_PLACES, null, values)
        db.close()
        return result
    }

    fun updateHappyPlace(happyPlace: HappyPlaceModel) : Int {

        val db = this.writableDatabase
        val values = ContentValues()
        values.put(COLUMN_TITLE, happyPlace.title)
        values.put(COLUMN_IMAGE, happyPlace.image)
        values.put(COLUMN_DESCRIPTION, happyPlace.description)
        values.put(COLUMN_DATE, happyPlace.date)
        values.put(COLUMN_LOCATION, happyPlace.location)
        values.put(COLUMN_LATITUDE, happyPlace.latitude)
        values.put(COLUMN_LONGITUDE, happyPlace.longitude)

        val success = db.update(TABLE_HAPPY_PLACES, values, COLUMN_ID + "=" + happyPlace.id, null)
        db.close()
        return success
    }

    fun deleteHappyPlace(happyPlace: HappyPlaceModel) : Int {

        val db = this.writableDatabase

        val success = db.delete(TABLE_HAPPY_PLACES, COLUMN_ID + "=" + happyPlace.id, null)

        db.close()
        return success
    }

    fun getAllHappyPlaces() : ArrayList<HappyPlaceModel> {

        val happyPlaceList = ArrayList<HappyPlaceModel>()
        val selectQuery = "SELECT * FROM $TABLE_HAPPY_PLACES"
        val db = this.readableDatabase

        try {

            val cursor = db.rawQuery(selectQuery, null)

            if (cursor.moveToFirst()) {
                do {
                    val place = HappyPlaceModel(
                        cursor.getInt(cursor.getColumnIndex(COLUMN_ID)),
                        cursor.getString(cursor.getColumnIndex(COLUMN_TITLE)),
                        cursor.getString(cursor.getColumnIndex(COLUMN_IMAGE)),
                        cursor.getString(cursor.getColumnIndex(COLUMN_DESCRIPTION)),
                        cursor.getString(cursor.getColumnIndex(COLUMN_DATE)),
                        cursor.getString(cursor.getColumnIndex(COLUMN_LOCATION)),
                        cursor.getDouble(cursor.getColumnIndex(COLUMN_LATITUDE)),
                        cursor.getDouble(cursor.getColumnIndex(COLUMN_LONGITUDE)),
                    )

                    happyPlaceList.add(place)
                }
                while (cursor.moveToNext())
            }
            cursor.close()
        }
        catch (e: SQLiteException) {
            e.printStackTrace()
            db.execSQL(selectQuery)
            return ArrayList()
        }

        return happyPlaceList
    }
}