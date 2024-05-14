package app.ammar.watefinder

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import android.util.Log
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.DividerItemDecoration

import app.ammar.watefinder.databinding.ActivityMainBinding
import app.ammar.watefinder.data.NonContact
import app.ammar.watefinder.data.NonContactDatabase


public class MainActivity : AppCompatActivity(), NonContactClickListener {

    private var _binding: ActivityMainBinding? = null
    private val binding: ActivityMainBinding
        get() = checkNotNull(_binding) { "Activity has been destroyed" }
      
	private lateinit var viewModel: MainViewModel
	private lateinit var historyAdapter: HistoryAdapter
	
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Inflate and get instance of binding
        _binding = ActivityMainBinding.inflate(layoutInflater)
        
        // set content view to binding's root
        setContentView(binding.root)
        
        setupIBs()
		setupRecycler()
		setupViewModel()
    }
    
    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

	
	private fun setupIBs() = with(binding) {
		ibWa.setOnClickListener { handle(false) }
		ibTe.setOnClickListener { handle(true) } 
	}

	private fun setupRecycler() {
		historyAdapter = HistoryAdapter(this)
		
        with(binding.recycler) {
            layoutManager = LinearLayoutManager(getApplicationContext())
            addItemDecoration(DividerItemDecoration(getApplicationContext(), LinearLayoutManager.VERTICAL))
            adapter = historyAdapter
        }
	}
	
	private fun setupViewModel() {
		try {
			viewModel = ViewModelProvider(this).get(MainViewModel::class.java)
			viewModel.history.observe(this, { historyAdapter.submitList(it) })
            
		} catch (ex: Exception) {
			Toast.makeText(this, "ERROR:\n${ex.toString()}", 1).show()
            Log.e("__setup_View_Model", ex.toString())
		}
	}
	
	private fun handle(isTe: Boolean) {
		val format = binding.etNumber.text.trim().toString()
		val message = binding.etText.text.trim().toString()
		
        var number = format.replace("\\D+".toRegex(), "")
		
		if (number.length < 11) {
			Toast.makeText(this, "NonContact number isn't valid!", 0).show()
			return
		}
        if (number.length == 11 && number.startsWith("01")) number = "2$number"
            
        try {
            viewModel.find(NonContact(number, format, message), isTe)
		} catch(ex: Exception) {
			Toast.makeText(this, "ERROR:\n${ex.toString()}", 1).show()
            Log.e("__handle__", ex.toString())
		}
	}
	
	
	override fun onClickNonContact(nonContact: NonContact) = with(binding) {
		etNumber.setText(nonContact.format)
		etText.setText(nonContact.message)
	}
	
    override fun onDeleteNonContact(nonContact: NonContact) {
		val deleted = true
        viewModel.delete(nonContact) 
		
		Toast.makeText(this,
			if (deleted) "Deleted!" else "An error occurred!", 
            0).show()
	}
	
}
