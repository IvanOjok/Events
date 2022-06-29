package com.ivanojok.events.model

import android.app.Application
import android.content.Context
import android.content.SharedPreferences

enum class PrefsManager{
    INSTANCE;

    //constructor() : this()
    //private constructor()

    private var context: Application? = null

    constructor(application: Application) {
        this.context = application
    }

    constructor()

    fun onLogin(user: User): Boolean{
        val sharedPreferences = context!!.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
        val editor: SharedPreferences.Editor = sharedPreferences.edit()
        editor.putString(KEY_PHONE, user.phone)
        editor.putString(KEY_FIRST_NAME, user.firstName)
        editor.putString(KEY_LAST_NAME, user.lastName)
        editor.putString(KEY_NIN, user.nin)
        editor.putString(KEY_RESIDENCE, user.residence)
        editor.apply()
        return true
    }

    fun isLoggedIn():Boolean{
        val sharedPreferences = context!!.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
        if (sharedPreferences.getString(KEY_PHONE, null) != null)
            return true
        return false
    }

    fun getUser(): User {
        val sharedPreferences = context!!.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
        return User(
            phone = sharedPreferences.getString(KEY_PHONE, null)!!,
            firstName = sharedPreferences.getString(KEY_FIRST_NAME, null)!!,
            lastName = sharedPreferences.getString(KEY_LAST_NAME, null)!!,
            nin = sharedPreferences.getString(KEY_NIN, null)!!,
            residence = sharedPreferences.getString(KEY_RESIDENCE, null)!!,

        )
    }

    fun onVictimLogout(): Boolean{
        val sharedPreferences = context!!.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.clear()
        editor.apply()
        return true
    }


//    fun onCounsellorLogin(counsellor: Counsellor): Boolean{
//        val sharedPreferences = context!!.getSharedPreferences(C_PREF_NAME, Context.MODE_PRIVATE)
//        val editor: SharedPreferences.Editor = sharedPreferences.edit()
//        editor.putString(C_PHONE, counsellor.phoneNo)
//        editor.putString(C_NAME, counsellor.name)
//        editor.putString(C_NIN, counsellor.nin)
//        editor.putString(C_IMAGE, counsellor.image)
//        editor.putString(C_LAT, counsellor.latitude)
//        editor.putString(C_LONG, counsellor.longitude)
//        editor.apply()
//        return true
//    }
//
//    fun isCounsellorLoggedIn():Boolean{
//        val sharedPreferences = context!!.getSharedPreferences(C_PREF_NAME, Context.MODE_PRIVATE)
//        if (sharedPreferences.getString(C_PHONE, null) != null)
//            return true
//        return false
//    }
//
//    fun getCounsellor(): Counsellor {
//        val sharedPreferences = context!!.getSharedPreferences(C_PREF_NAME, Context.MODE_PRIVATE)
//        return Counsellor(
//            phoneNo = sharedPreferences.getString(C_PHONE, null)!!,
//            name = sharedPreferences.getString(C_NAME, null)!!,
//            nin = sharedPreferences.getString(C_NIN, null)!!,
//            image = sharedPreferences.getString(C_IMAGE, null)!!,
//            latitude = sharedPreferences.getString(C_LAT, null)!!,
//            longitude = sharedPreferences.getString(C_LONG, null)!!,
//        )
//    }
//
//    fun onCounsellorLogout(): Boolean{
//        val sharedPreferences = context!!.getSharedPreferences(C_PREF_NAME, Context.MODE_PRIVATE)
//        val editor = sharedPreferences.edit()
//        editor.clear()
//        editor.apply()
//        return true
//    }



    fun setContext(ctx: Application){
        context = ctx
    }
}