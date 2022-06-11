package com.example.navdrawer_kotlin

class StaticRvModel(image: Int, text: String)  {
    internal var text: String
    internal var image: Int
    init {
        this.text = text
        this.image =image
    }
    fun gettext(): String? {
        return text
    }
    fun settext(name: String?) {
        text = name!!
    }
    fun getimg(): Int? {
        return image
    }
    fun setimg(genre: Int?) {
        this.image = image!!
    }
}