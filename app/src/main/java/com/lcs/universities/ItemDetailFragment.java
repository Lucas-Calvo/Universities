package com.lcs.universities;

import android.content.ClipData;
import android.os.Bundle;
import android.view.DragEvent;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.lcs.universities.placeholder.PlaceholderContent;
import com.lcs.universities.databinding.FragmentItemDetailBinding;

/**
 * A fragment representing a single Item detail screen.
 * This fragment is either contained in a {@link ItemListFragment}
 * in two-pane mode (on larger screen devices) or self-contained
 * on handsets.
 */
public class ItemDetailFragment extends Fragment {

    /**
     * The fragment argument representing the item ID that this fragment
     * represents.
     */
    public static final String NAME="name";
    public static final String URL="url";

    private UniversityDetail detail;

    private FragmentItemDetailBinding binding;


    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public ItemDetailFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        UniversityBd unidb = new UniversityBd(getContext());



        TextView txtdescripcion= getActivity().findViewById(R.id.universitydescription);
        updateContent();

        String name = getArguments().getString(NAME);
        if(unidb.getUniversity(name).getName()==null){
            if(name != null){
                if(detail == null)
                    detail=new UniversityDetail();

                detail.setName(name);
                detail.setUrl(getArguments().getString(URL));

            }
        }else{
            detail.setName(name);
            detail.setUrl(getArguments().getString(URL));
            txtdescripcion.setText(unidb.getUniversity(name).getDescription().toString());
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding = FragmentItemDetailBinding.inflate(inflater, container, false);
        View rootView = binding.getRoot();

        updateContent();
        binding.botonmas.setOnClickListener(v -> {
            Bundle arguments = new Bundle();
            arguments.putString(ItemDetailFragment.NAME, detail.getName());
            Navigation.findNavController(rootView)
                    .navigate(R.id.activity_formulario, arguments);
        });
        return rootView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    private void updateContent() {
        if (detail != null) {
            binding.universityName.setText(detail.getName());
            binding.idurltext.setText(detail.getUrl());
        }
    }
}