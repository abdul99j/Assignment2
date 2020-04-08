package com.example.assignment2

import java.io.Serializable

class Student (_name:String,_rollNo:String,_checked:Int):Serializable{
    val name: String = _name
    val rollNo: String = _rollNo
    var isChecked:Int = _checked
}