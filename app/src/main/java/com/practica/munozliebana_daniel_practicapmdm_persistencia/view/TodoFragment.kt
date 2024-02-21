package com.practica.munozliebana_daniel_practicapmdm_persistencia.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
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
    private val taskCheckedList: HashSet<Todo> = HashSet()

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
        rvTask.layoutManager = LinearLayoutManager(requireContext())
    }

    fun showDialog(){

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
                idAsignature)
        }
    }

    fun addNewTask(tituloTodo:String, descripcionTodo:String, idasignatureTodo: Int){
        if (isEntryValid()){
            taskViewModel.addNewTask(tituloTodo,descripcionTodo,idasignatureTodo)
        }else {
            Toast.makeText(requireContext(), "Introduzca el título", Toast.LENGTH_LONG).show()
        }
    }

    fun isEntryValid(): Boolean{
        return taskViewModel.isEntryValid(bindingSheet.etTituloTask.text.toString())
    }

    override fun onCheckboxClicked(task: Todo, isChecked: Boolean) {

    }
}