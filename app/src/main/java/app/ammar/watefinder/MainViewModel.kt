package app.ammar.watefinder

import android.content.Intent
import android.net.Uri
import android.widget.Toast
import android.util.Log
//import androidx.lifecycle.LiveData
//import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

import app.ammar.watefinder.data.NonContact
import app.ammar.watefinder.data.NonContactDatabase


class MainViewModel() : ViewModel() {

	private val ctx = FinderApp.getAppCtx()
	private val dao = NonContactDatabase.getDao(ctx)
    
	val history = dao.getAll()
	
	
	fun insert(nonContact: NonContact) =
		viewModelScope.launch { dao.insert(nonContact) }

	fun delete(nonContact: NonContact) =
		viewModelScope.launch { dao.delete(nonContact) }


	fun find(nonContact: NonContact, isTe: Boolean) {
		try {
			insert(nonContact)
            
            val url = buildUrl(nonContact.number, nonContact.message, isTe)
		    val intent = Intent(Intent.ACTION_VIEW)
        
		    intent.data = Uri.parse(url)
		    intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
        
			ctx.startActivity(intent)
			
		} catch (ex: Exception) {
			Toast.makeText(ctx, ex.toString(), 1).show()
            Log.e("_MainViewModel_find", ex.toString())
		}
    }
    
    
    private fun buildUrl(number: String, message: String, isTe: Boolean) = 
        if (isTe) telegramUrl(number, message) 
        else whatsappUrl(number, message)
    
    
    private fun whatsappUrl(number: String, message: String) =
		"https://api.whatsapp.com/send?phone=+$number&text=$message"
		
	private fun telegramUrl(number: String, message: String) =
		"https://t.me/+$number?text=$message&msg=$message&message=$message"
	
}