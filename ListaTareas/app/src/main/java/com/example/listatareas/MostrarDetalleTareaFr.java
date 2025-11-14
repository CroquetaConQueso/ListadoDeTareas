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
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.listatareas.databinding.FragmentMostrarDetalleTareaBinding;

public class MostrarDetalleTareaFr extends Fragment {

    /*
        Muestra los datelles de la tarea seleccionada, nos permite modificar la tarea, editar el nombre, la descripcion, cambiar la prioridad y al pulsar
        Guardar cambios actualiza los datos dentro del ViewModel / Repo
     */
    private FragmentMostrarDetalleTareaBinding binding;
    private TareaViewModel tareaViewModel;
    private Tarea tareaActual;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding = FragmentMostrarDetalleTareaBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view,
                              @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        tareaViewModel = new ViewModelProvider(requireActivity())
                .get(TareaViewModel.class);

        // Observar tarea seleccionada
        tareaViewModel.seleccionado().observe(getViewLifecycleOwner(), new Observer<Tarea>() {
            @Override
            public void onChanged(Tarea tarea) {
                tareaActual = tarea;
                if (tarea != null) {
                    binding.nombre.setText(tarea.nombre);
                    binding.descripcion.setText(tarea.descripcion);
                    binding.valoracion.setRating(tarea.valoracion);
                }
            }
        });

        // Botón GUARDAR → modificar tarea
        binding.guardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (tareaActual == null) return;

                String nuevoNombre = binding.nombre.getText().toString().trim();
                String nuevaDescripcion = binding.descripcion.getText().toString().trim();
                float nuevaValoracion = binding.valoracion.getRating();

                if (TextUtils.isEmpty(nuevoNombre)) {
                    binding.nombre.setError("El nombre es obligatorio");
                    return;
                }

                tareaViewModel.actualizar(
                        tareaActual,
                        nuevoNombre,
                        nuevaDescripcion,
                        nuevaValoracion
                );

                Toast.makeText(requireContext(),
                        "Tarea actualizada",
                        Toast.LENGTH_SHORT).show();
            }
        });
    }
}
