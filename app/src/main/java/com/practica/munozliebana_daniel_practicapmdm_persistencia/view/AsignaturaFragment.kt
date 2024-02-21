package com.practica.munozliebana_daniel_practicapmdm_persistencia.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.practica.munozliebana_daniel_practicapmdm_persistencia.databinding.FragmentAsignaturaBinding
import com.practica.munozliebana_daniel_practicapmdm_persistencia.view.adapter.AsignatureAdapter
import com.practica.munozliebana_daniel_practicapmdm_persistencia.model.database.TaskDataBase
import com.practica.munozliebana_daniel_practicapmdm_persistencia.viewmodel.TaskViewModel
import com.practica.munozliebana_daniel_practicapmdm_persistencia.viewmodel.TaskViewModelFactory


class AsignaturaFragment : Fragment() {

    private var _binding: FragmentAsignaturaBinding? = null
    private val taskViewModel: TaskViewModel by activityViewModels {
        TaskViewModelFactory(
            (activity?.application as TaskDataBase.Companion.TaskApplication).database.asignatureDao(),
            (activity?.application as TaskDataBase.Companion.TaskApplication).database.taskDao()
        )
    }
    private lateinit var asignatureAdapter: AsignatureAdapter
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        asignatureAdapter = AsignatureAdapter()
        _binding = FragmentAsignaturaBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val rvAsignatura = binding.recyclerAsig

        taskViewModel.getAllAsignature().observe(viewLifecycleOwner

        ) { asignatures ->
            // Actualizar el adaptador con las asignaturas
            asignatureAdapter.submitList(asignatures)
        }

        rvAsignatura.adapter =   asignatureAdapter
        rvAsignatura.layoutManager = LinearLayoutManager(requireContext())

    }

}