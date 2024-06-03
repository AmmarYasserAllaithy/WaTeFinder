package app.ammar.watefinder

import android.view.View
import android.widget.ImageButton
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView


class AccountViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    val layout: LinearLayout = itemView.findViewById(R.id.layout)
    val numberTV: TextView = itemView.findViewById(R.id.tv_number)
    val messageTV: TextView = itemView.findViewById(R.id.tv_message)
    val deleteIB: ImageButton = itemView.findViewById(R.id.ib_delete)
}