package app.ammar.watefinder

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import app.ammar.watefinder.data.Account
import app.ammar.watefinder.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity(), AccountClickListener {

    private var _binding: ActivityMainBinding? = null
    private val binding: ActivityMainBinding
        get() = checkNotNull(_binding) { "Activity has been destroyed" }

    private lateinit var viewModel: MainViewModel
    private lateinit var historyAdapter: HistoryAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        _binding = ActivityMainBinding.inflate(layoutInflater)

        setContentView(binding.root)
    }

    override fun onStart() {
        super.onStart()

        setupRecyclerAdapter()
        setupViewModel()

        with(binding) {
            ibWa.setOnClickListener { handle(false) }
            ibTe.setOnClickListener { handle(true) }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }


    private fun setupRecyclerAdapter() {
        historyAdapter = HistoryAdapter(this)

        with(binding.recycler) {
            layoutManager = LinearLayoutManager(applicationContext)
            adapter = historyAdapter
        }
    }

    private fun setupViewModel() {
        try {
            viewModel = ViewModelProvider(this)[MainViewModel::class.java]
            viewModel.history.observe(this) { historyAdapter.submitList(it) }

        } catch (ex: Exception) {
            Toast.makeText(this, "ERROR:\n$ex", Toast.LENGTH_LONG).show()
            Log.e("__setup_View_Model", ex.toString())
        }
    }

    private fun handle(isTe: Boolean) {
        val format = binding.etNumber.text.trim().toString()
        val message = binding.etText.text.trim().toString()
        var number = format.replace("\\D+".toRegex(), "")

        if (number.length < 11) {
            Toast.makeText(this, "Invalid phone number!", Toast.LENGTH_SHORT).show()
            return
        }

        if (number.length == 11 && number.startsWith("01")) number = "2$number"

        val account = Account(number, format, message)
        viewModel.find(account, isTe)
    }


    override fun onAccountClick(account: Account) = with(binding) {
        etNumber.setText(account.displayFormat)
        etText.setText(account.message)
    }

    override fun onAccountDelete(account: Account) {
        val deleted = viewModel.delete(account) > 0
        val text = if (deleted) "Deleted!" else "An error occurred!"

        Toast.makeText(this, text, Toast.LENGTH_SHORT).show()
    }

}