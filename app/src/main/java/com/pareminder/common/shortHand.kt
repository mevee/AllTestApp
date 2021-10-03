package com.pareminder.common

import android.app.Activity
import android.widget.Toast


fun Activity.printMessage(message:String){
    Toast.makeText(this,message,Toast.LENGTH_LONG).show()
}