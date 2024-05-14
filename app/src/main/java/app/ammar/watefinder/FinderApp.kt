package app.ammar.watefinder;

import android.app.Application
import android.content.Context


class FinderApp : Application() {

    init {
        app = this
    }
    
    companion object {
        private lateinit var app: FinderApp
        
        fun getAppCtx(): Context = app.applicationContext
    }
    
}
