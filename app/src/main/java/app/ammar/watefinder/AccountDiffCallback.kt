package app.ammar.watefinder

import androidx.recyclerview.widget.DiffUtil
import app.ammar.watefinder.data.Account


class AccountDiffCallback : DiffUtil.ItemCallback<Account>() {
    override fun areItemsTheSame(oldItem: Account, newItem: Account) =
        oldItem.number == newItem.number

    override fun areContentsTheSame(oldItem: Account, newItem: Account) = oldItem == newItem
}