package com.lcs.universities;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.lcs.universities.databinding.FragmentActivityFormularioBinding;
import com.lcs.universities.databinding.FragmentItemDetailBinding;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link activity_formulario#newInstance} factory method to
 * create an instance of this fragment.
 */
public class activity_formulario extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    public static final String NAME="name";
    public static final String URL="url";

    private FragmentActivityFormularioBinding binding;

    private UniversityBd unibd = new UniversityBd(getContext());

    private UniversityDetail detail;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public activity_formulario() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment activity_formulario.
     */
    // TODO: Rename and change types and number of parameters
    public static activity_formulario newInstance(String param1, String param2) {
        activity_formulario fragment = new activity_formulario();
        Bundle args = new Bundle();

        fragment.setArguments(args);
        return fragment;
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
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding = FragmentActivityFormularioBinding.inflate(inflater, container, false);
        View rootView = binding.getRoot();
        updateContent();

        //if(unibd.getUniversity(detail.getName())!=null){
        //    txtdescription.setText(unibd.getUniversity(detail.getDescription()).toString());
        //    txtimg.setText(unibd.getUniversity(detail.getImageUrl()).toString());
        //}
        return rootView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Button btnsave = getActivity().findViewById(R.id.btnsave);
        Button btndelete = getActivity().findViewById(R.id.btndelete);

        EditText txtimg=getActivity().findViewById(R.id.id_txtUrl);
        EditText txtdescription=getActivity().findViewById(R.id.id_txtDescription);

        String name = getArguments().getString(NAME);
        String url = getArguments().getString(URL);

        btnsave.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                detail.setName(detail.getName());
                detail.setImageUrl(txtimg.getText().toString());
                detail.setDescription(txtdescription.getText().toString());

                try {
                    if(unibd.getUniversity(detail.getName())==null){
                        unibd.insertarUniversity(detail);
                    }else{
                        unibd.actualizarUniversity(detail);
                    }
                }catch (Exception e){
                    e.printStackTrace();
                }

                Navigation.findNavController(view).navigate(R.id.item_list_fragment);
            }
        });
        btndelete.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {

                unibd.eliminarUniversity(name);

                Navigation.findNavController(view).navigate(R.id.item_list_fragment);
            }
        });
    }
    private void updateContent() {
        if (detail != null) {
            binding.universityName.setText(detail.getName());
        }
    }
}