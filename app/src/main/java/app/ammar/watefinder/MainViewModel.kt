package app.ammar.watefinder

import android.content.Intent
import android.net.Uri
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import app.ammar.watefinder.data.Account
import app.ammar.watefinder.data.AccountDatabase
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking


class MainViewModel : ViewModel() {

    private val dao = AccountDatabase.getDao(FinderApp.getAppCtx())

    val history = dao.getAll()


    fun insert(account: Account) = viewModelScope.launch { dao.insert(account) }

    fun delete(account: Account): Int = runBlocking {
        async { dao.delete(account) }.await()
    }


    fun find(account: Account, isTe: Boolean) {
        try {
            insert(account)

            val url = buildUrl(account.number, account.message, isTe)

            Intent(Intent.ACTION_VIEW).run {
                data = Uri.parse(url)
                flags = Intent.FLAG_ACTIVITY_NEW_TASK

                FinderApp.getAppCtx().startActivity(this)
            }
        } catch (ex: Exception) {
            Toast.makeText(FinderApp.getAppCtx(), ex.toString(), Toast.LENGTH_LONG).show()
            Log.e("_MainViewModel_find", ex.toString())
        }
    }


    private fun buildUrl(number: String, message: String, isTe: Boolean) =
        if (isTe) telegramUrl(number, message) else whatsappUrl(number, message)

    private fun whatsappUrl(number: String, message: String) =
        "https://api.whatsapp.com/send?phone=+$number&text=$message"

    private fun telegramUrl(number: String, message: String) =
        "https://t.me/+$number?text=$message&msg=$message&message=$message"

}