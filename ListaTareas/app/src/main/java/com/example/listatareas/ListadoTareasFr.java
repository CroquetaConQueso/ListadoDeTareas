package com.example.listatareas;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RatingBar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

import com.example.listatareas.databinding.FragmentListadoTareasBinding;
import com.example.listatareas.databinding.ViewholderElementoBinding;

import java.util.List;

public class ListadoTareasFr extends Fragment {

    private FragmentListadoTareasBinding binding;
    private TareaViewModel tareaViewModel;
    private ElementosAdapter elementosAdapter;

    /*
        Muestra la lista en el RecyclerView, permite crear nuevas tareas con el FAB, eliminarlas y vamos, el CRUD.
        Nos permite tambien ver el detalle al pulsar una sola tarea.
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding = FragmentListadoTareasBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view,
                              @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        tareaViewModel = new ViewModelProvider(requireActivity())
                .get(TareaViewModel.class);

        elementosAdapter = new ElementosAdapter();
        binding.recyclerView.setAdapter(elementosAdapter);

        // Observa lista de tareas
        tareaViewModel.obtener().observe(getViewLifecycleOwner(), new Observer<List<Tarea>>() {
            @Override
            public void onChanged(List<Tarea> tareas) {
                elementosAdapter.establecerLista(tareas);
            }
        });

        // FAB → nueva tarea
        binding.irANuevoElemento.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                NavController navController = Navigation.findNavController(v);
                navController.navigate(R.id.action_listadoTareasFr_to_nuevaTareaFr);
            }
        });

        // Swipe para eliminar
        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(
                ItemTouchHelper.UP | ItemTouchHelper.DOWN,
                ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {

            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView,
                                  @NonNull RecyclerView.ViewHolder viewHolder,
                                  @NonNull RecyclerView.ViewHolder target) {
                return true;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder,
                                 int direction) {

                int posicion = viewHolder.getAdapterPosition();
                Tarea tarea = elementosAdapter.obtenerElemento(posicion);
                if (tarea != null) {
                    tareaViewModel.eliminar(tarea);
                }
            }
        }).attachToRecyclerView(binding.recyclerView);
    }

    // ViewHolder
    class TareaViewHolder extends RecyclerView.ViewHolder {

        final ViewholderElementoBinding binding;

        public TareaViewHolder(ViewholderElementoBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }

    // Adapter
    class ElementosAdapter extends RecyclerView.Adapter<TareaViewHolder> {

        List<Tarea> elementos;

        @NonNull
        @Override
        public TareaViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

            ViewholderElementoBinding viewBinding =
                    ViewholderElementoBinding.inflate(
                            LayoutInflater.from(parent.getContext()),
                            parent,
                            false
                    );

            return new TareaViewHolder(viewBinding);
        }

        @Override
        public void onBindViewHolder(@NonNull TareaViewHolder holder, int position) {

            final Tarea elemento = elementos.get(position);

            holder.binding.nombre.setText(elemento.nombre);
            holder.binding.valoracion.setRating(elemento.valoracion);


            holder.binding.valoracion.setOnRatingBarChangeListener(
                    new RatingBar.OnRatingBarChangeListener() {
                        @Override
                        public void onRatingChanged(RatingBar ratingBar,
                                                    float rating,
                                                    boolean fromUser) {
                            if (fromUser) {
                                tareaViewModel.actualizar(elemento, rating);
                            }
                        }
                    });

            // El click
            holder.binding.getRoot().setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    tareaViewModel.seleccionar(elemento);
                    NavController navController = Navigation.findNavController(v);
                    navController.navigate(R.id.action_listadoTareasFr_to_mostrarDetalleTareaFr);
                }
            });
        }

        @Override
        public int getItemCount() {
            return elementos != null ? elementos.size() : 0;
        }

        public void establecerLista(List<Tarea> elementos) {
            this.elementos = elementos;
            notifyDataSetChanged();
        }

        public Tarea obtenerElemento(int posicion) {
            if (elementos == null || posicion < 0 || posicion >= elementos.size()) {
                return null;
            }
            return elementos.get(posicion);
        }
    }
}
