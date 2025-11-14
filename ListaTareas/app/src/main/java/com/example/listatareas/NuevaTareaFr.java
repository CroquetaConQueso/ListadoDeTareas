package com.example.listatareas;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;

import com.example.listatareas.databinding.FragmentNuevaTareaBinding;

public class NuevaTareaFr extends Fragment {

    private FragmentNuevaTareaBinding binding;
    private TareaViewModel tareaViewModel;

    /*
        El titulo de la clase explicama casi todo, pero muestra el formulario para crear una tarea
        toma los valores y los valida para no estar vacio, envia la tarea al ViewModel y vuelve al fragment del listado
        cuando este creada
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding = FragmentNuevaTareaBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view,
                              @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        tareaViewModel = new ViewModelProvider(requireActivity())
                .get(TareaViewModel.class);

        binding.crear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String nombre = binding.nombre.getText().toString().trim();
                String descripcion = binding.descripcion.getText().toString().trim();

                if (TextUtils.isEmpty(nombre)) {
                    binding.nombre.setError("El nombre es obligatorio");
                    return;
                }

                float valoracion = binding.valoracion.getRating();

                // Creamos una tarea CON prioridad
                Tarea nueva = new Tarea(nombre, descripcion, valoracion);
                tareaViewModel.insertar(nueva);

                Toast.makeText(requireContext(),
                        "Tarea creada",
                        Toast.LENGTH_SHORT).show();

                NavController navController =
                        NavHostFragment.findNavController(NuevaTareaFr.this);
                navController.popBackStack();
            }
        });
    }
}
