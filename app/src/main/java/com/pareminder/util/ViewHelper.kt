package com.pareminder.common

import android.app.Activity
import android.content.Context
import android.widget.Toast


fun Context.printMessage(message:String){
    Toast.makeText(this,message,Toast.LENGTH_LONG).show()
}