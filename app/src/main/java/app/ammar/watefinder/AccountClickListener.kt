package app.ammar.watefinder

import app.ammar.watefinder.data.Account


interface AccountClickListener {

    fun onAccountClick(account: Account)

    fun onAccountDelete(account: Account)

}