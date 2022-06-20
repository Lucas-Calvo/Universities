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
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.lcs.universities.databinding.ActivityItemDetailBinding;
import com.lcs.universities.databinding.FragmentActivityFormularioBinding;
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
    private UniversityBd bd = new UniversityBd(getContext());

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


        updateContent();

        String name = getArguments().getString(NAME);

        if(name != null){
            if(detail == null)
                detail=new UniversityDetail();

            detail.setName(name);
            detail.setUrl(getArguments().getString(URL));
        }else if(bd.getUniversity(detail.getName())!=null){
            detail.setImageUrl(bd.getUniversity(detail.getName()).getImageUrl());
            detail.setDescription(bd.getUniversity(detail.getName()).getDescription());
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


        try {
            if (detail != null) {
                binding.universityName.setText(detail.getName());
                binding.idurltext.setText(detail.getUrl());
                binding.universitydescription.setText("");
            }else if(bd.getUniversity(detail.getName())!=null){
                binding.universitydescription.setText(detail.getDescription());
            }
            //if(bd.getUniversity(detail.getName())==null){
            binding.imagenfondo.setBackgroundResource(R.drawable.defaultuniversity);
            //}else{
            //    Glide.with(getContext())
            //            .load(bd.getUniversity(detail.getName()).getImageUrl())
            //            .into(binding.imagenfondo);
            //}

        }catch(Exception e){
            e.printStackTrace();
        }

    }
}