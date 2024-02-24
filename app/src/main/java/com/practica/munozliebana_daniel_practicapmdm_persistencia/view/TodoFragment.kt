package com.practica.munozliebana_daniel_practicapmdm_persistencia.view

import android.app.AlertDialog
import android.app.DatePickerDialog
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.practica.munozliebana_daniel_practicapmdm_persistencia.R
import com.practica.munozliebana_daniel_practicapmdm_persistencia.databinding.FragmentTodoBinding
import com.practica.munozliebana_daniel_practicapmdm_persistencia.databinding.LayoutBottomsheetBinding
import com.practica.munozliebana_daniel_practicapmdm_persistencia.model.data.Todo
import com.practica.munozliebana_daniel_practicapmdm_persistencia.model.database.TaskDataBase
import com.practica.munozliebana_daniel_practicapmdm_persistencia.view.adapter.TaskAdapter
import com.practica.munozliebana_daniel_practicapmdm_persistencia.view.interfaces.TaskItemClickListener
import com.practica.munozliebana_daniel_practicapmdm_persistencia.viewmodel.TaskViewModel
import com.practica.munozliebana_daniel_practicapmdm_persistencia.viewmodel.TaskViewModelFactory
import java.util.Calendar


class TodoFragment : Fragment(), TaskItemClickListener {
    companion object{
        var ID_ASIGNATURE = "id_asignature"
    }
    private val taskViewModel: TaskViewModel by activityViewModels {
        TaskViewModelFactory(
            (activity?.application as TaskDataBase.Companion.TaskApplication).database.asignatureDao(),
            (activity?.application as TaskDataBase.Companion.TaskApplication).database.taskDao()
        )
    }
    private val calendar: Calendar = Calendar.getInstance()
    private var _binding: FragmentTodoBinding? = null
    private var _bindingSheet: LayoutBottomsheetBinding? = null
    private var idAsignature: Int = 0
    private lateinit var taskAdapter: TaskAdapter
    private val binding get() = _binding!!
    private val bindingSheet get() = _bindingSheet!!
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            idAsignature = it.getInt(ID_ASIGNATURE)
        }

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        taskAdapter = TaskAdapter(this)
        _binding = FragmentTodoBinding.inflate(inflater, container, false)
        _bindingSheet = LayoutBottomsheetBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val btnSheet = binding.floatingActionButtonAdd
        val rvTask = binding.recyclerTodo
        btnSheet.setOnClickListener {
            showDialog()
        }
        taskViewModel.getAllTask(idAsignature).observe(viewLifecycleOwner)
        { todo ->
            taskAdapter.submitList(todo)
        }
        rvTask.adapter = taskAdapter
        setAnimationList(rvTask)
        rvTask.layoutManager = LinearLayoutManager(requireContext())

        bindingSheet.dateButton.setOnClickListener {
            showDatePickerDialog()
        }
    }

    private fun setAnimationList(rvTask: RecyclerView){
        val itemAnimator = DefaultItemAnimator()
        itemAnimator.addDuration = 800
        itemAnimator.removeDuration = 800
        rvTask.itemAnimator = itemAnimator
    }

    private fun showDialog(){

        val bottomSheetDialog = BottomSheetDialog(requireContext())
        if (bindingSheet.root.parent != null) {
            (bindingSheet.root.parent as ViewGroup).removeView(bindingSheet.root)
        }
        bottomSheetDialog.setContentView(bindingSheet.root)
        bottomSheetDialog.show()
        bottomSheetDialog.window?.setWindowAnimations(R.style.DialogAnimation)
        bindingSheet.btnAddTask.setOnClickListener{
            addNewTask(bindingSheet.etTituloTask.text.toString(),
                bindingSheet.etDescripcionTask.text.toString(),
                bindingSheet.txtDate.text.toString()
                ,idAsignature)
            bindingSheet.etTituloTask.setText("")
            bindingSheet.etDescripcionTask.setText("")
        }
    }

    fun addNewTask(tituloTodo:String, descripcionTodo:String, fechaTodo: String,idasignatureTodo: Int){
        if (isEntryValid()){
            taskViewModel.addNewTask(tituloTodo,descripcionTodo,fechaTodo,idasignatureTodo)
        }else {
            Toast.makeText(requireContext(), "Introduzca el título", Toast.LENGTH_LONG).show()
        }
    }

    private fun isEntryValid(): Boolean{
        val etTitulo = bindingSheet.etTituloTask.text.toString()
        return taskViewModel.isEntryValid(etTitulo)
    }

    private fun deleteTask(task: Todo){
        taskViewModel.deleteTask(task)
    }

    private fun showDatePickerDialog() {
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val day = calendar.get(Calendar.DAY_OF_MONTH)

        val datePickerDialog = DatePickerDialog(
            requireContext(),
            { _, year, month, dayOfMonth ->
                val selectedDate = "$dayOfMonth/${month + 1}/$year"
                bindingSheet.txtDate.text = selectedDate
            },
            year, month, day
        )
        datePickerDialog.datePicker.minDate = System.currentTimeMillis() - 1000
        datePickerDialog.show()
    }
    private fun updateTask(task: Todo){
        taskViewModel.updateTask(task)
    }
    private fun getLayoutDialogUpdate(): View{
        val view = layoutInflater.inflate(R.layout.update_layout, null)
        return view
    }

    override fun onCheckboxClicked(task: Todo, isChecked: Boolean) {
        if (isChecked){
            deleteTask(task)
        }
    }

    override fun showDialogUpdate(task: Todo) {
        val builder = AlertDialog.Builder(requireContext())
        builder.apply {
            val viewUpdate = getLayoutDialogUpdate()
            setView(viewUpdate)
            setPositiveButton("Accept"){_,_->
                val etTitle = viewUpdate.findViewById<EditText>(R.id.etUpdateTitle)
                val etDesc = viewUpdate.findViewById<EditText>(R.id.etUpdateDescripcionTask)

                if (!etTitle.text.toString().isBlank()){
                    val newTask = task.copy(titulo = etTitle.text.toString(),
                                                description = etDesc.text.toString())
                    updateTask(newTask)
                }
            }
            setNegativeButton("Cancel"){_,_->
            }
        }
        val dialogUpdate = builder.create()
        dialogUpdate.show()
    }
}