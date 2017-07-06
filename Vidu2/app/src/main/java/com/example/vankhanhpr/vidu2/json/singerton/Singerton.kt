package com.example.vankhanhpr.vidu2.json.singerton

/**
 * Created by VANKHANHPR on 7/4/2017.
 */
class Singleton private constructor() {

    var string: String? = null

    init
    {
        string = "Hello"
    }

    companion object
    {
        private var mInstance: Singleton? = null

        val instance: Singleton
            get()
            {
                if (mInstance == null)
                {
                    mInstance = Singleton()
                }
                return mInstance!!
            }
    }

//    fun getInstance(): Singleton {
//        if (mInstance == null) {
//            mInstance = Singleton()
//        }
//        return mInstance
//    }

}