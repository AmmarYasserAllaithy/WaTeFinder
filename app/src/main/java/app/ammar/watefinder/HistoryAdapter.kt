package app.ammar.watefinder

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import app.ammar.watefinder.data.Account


class HistoryAdapter(private val clickListener: AccountClickListener) :
    ListAdapter<Account, AccountViewHolder>(AccountDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AccountViewHolder =
        AccountViewHolder(
            LayoutInflater
                .from(parent.context)
                .inflate(R.layout.history_record, parent, false)
        )

    override fun onBindViewHolder(holder: AccountViewHolder, position: Int) {
        val account = getItem(position)

        with(holder) {
            numberTV.text = account.displayFormat

            with(messageTV) {
                text = account.message
                visibility = if (account.message.isBlank()) View.GONE else View.VISIBLE
            }

            layout.setOnClickListener {
                clickListener.onAccountClick(account)
            }

            deleteIB.setOnClickListener {
                clickListener.onAccountDelete(account)
                notifyDataSetChanged()
            }
        }
    }

}