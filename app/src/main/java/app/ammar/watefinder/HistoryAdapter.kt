package app.ammar.watefinder

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.DiffUtil

import app.ammar.watefinder.data.NonContact


class HistoryAdapter(private val clickListener: NonContactClickListener) : 
    ListAdapter<NonContact, ViewHolder>(NonContactDiffCallback()) {

	// create new views
	override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
    	ViewHolder(LayoutInflater.from(parent.context)
            .inflate(R.layout.history_record, parent, false))

    // binds the list items to a view
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
		val nonContact = getItem(position)

        with(holder) {
			tvNumber.text = nonContact.format
			
            with(tvMessage) {
                text = nonContact.message
                setVisibility(
                    if (nonContact.message.isBlank()) View.GONE
                    else View.VISIBLE
                )
            }
			
			layout.setOnClickListener {
				clickListener.onClickNonContact(nonContact)
			}
		
			tvDelete.setOnClickListener {
				clickListener.onDeleteNonContact(nonContact)
				notifyDataSetChanged()
			}
		}
    }

}

interface NonContactClickListener {
	fun onClickNonContact(nonContact: NonContact)
	fun onDeleteNonContact(nonContact: NonContact)
}

class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
	val layout: LinearLayout = itemView.findViewById(R.id.layout)
	val tvNumber: TextView = itemView.findViewById(R.id.tv_number)
	val tvMessage: TextView = itemView.findViewById(R.id.tv_message)
	val tvDelete: TextView = itemView.findViewById(R.id.tv_delete)
}

class NonContactDiffCallback : DiffUtil.ItemCallback<NonContact>() {
    override fun areItemsTheSame(oldItem: NonContact, newItem: NonContact) = oldItem.number == newItem.number
	override fun areContentsTheSame(oldItem: NonContact, newItem: NonContact) = oldItem == newItem
}
