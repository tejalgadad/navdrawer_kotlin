package com.example.navdrawer_kotlin.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
@Parcelize
class User( val uid: String,val username: String): Parcelable{ //val profileImageUrl: String) {
    constructor() : this(  "","" )
}