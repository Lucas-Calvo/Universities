package com.lcs.universities;

import android.app.Application;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;

import androidx.core.view.ViewCompat;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.navigation.Navigator;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.RecyclerView;

import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.gson.GsonBuilder;
import com.lcs.universities.databinding.FragmentItemListBinding;
import com.lcs.universities.databinding.ItemListContentBinding;

import com.lcs.universities.placeholder.PlaceholderContent;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * A fragment representing a list of Items. This fragment
 * has different presentations for handset and larger screen devices. On
 * handsets, the fragment presents a list of items, which when touched,
 * lead to a {@link ItemDetailFragment} representing
 * item details. On larger screens, the Navigation controller presents the list of items and
 * item details side-by-side using two vertical panes.
 */
public class ItemListFragment extends Fragment {


    private FragmentItemListBinding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentItemListBinding.inflate(inflater, container, false);
        return binding.getRoot();


    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        RecyclerView recyclerView = binding.itemList;

        // Leaving this not using view binding as it relies on if the view is visible the current
        // layout configuration (layout, layout-sw600dp)
        View itemDetailFragmentContainer = view.findViewById(R.id.item_detail_nav_container);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://universities.hipolabs.com")
                .addConverterFactory(GsonConverterFactory.create(
                        new GsonBuilder().serializeNulls().create()
                )).build();
        UniversityAPIservice uniapi = retrofit.create(UniversityAPIservice.class);
        Call<List<University>> llamada = uniapi.getAll();

        llamada.enqueue(new Callback<List<University>>() {
            @Override
            public void onResponse(Call<List<University>> call, Response<List<University>> response) {
                if(response.isSuccessful()){
                    List<University> unilist = response.body();
                    setupRecyclerView((RecyclerView) recyclerView, itemDetailFragmentContainer, (ArrayList<University>) unilist);
                }
            }

            @Override
            public void onFailure(Call<List<University>> call, Throwable t) {

            }
        });

    }

    private void setupRecyclerView(
            RecyclerView recyclerView,
            View itemDetailFragmentContainer,
            List<University> unilist) {
        recyclerView.addItemDecoration(new DividerItemDecoration(recyclerView.getContext(), DividerItemDecoration.VERTICAL));
        recyclerView.setAdapter(new SimpleItemRecyclerViewAdapter(
                unilist, itemDetailFragmentContainer
        ));
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    public static class SimpleItemRecyclerViewAdapter
            extends RecyclerView.Adapter<SimpleItemRecyclerViewAdapter.ViewHolder> {

        private final List<University> mValues;
        private final View mItemDetailFragmentContainer;

        SimpleItemRecyclerViewAdapter(List<University> items,
                                      View itemDetailFragmentContainer) {
            mValues = items;
            mItemDetailFragmentContainer = itemDetailFragmentContainer;
        }

        private final View.OnClickListener mOnClickListener = new
                View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        Navigation.findNavController(view).navigate(R.id.item_detail_fragment);
                    }
                };

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

            ItemListContentBinding binding =
                    ItemListContentBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
            return new ViewHolder(binding);

        }

        @Override
        public void onBindViewHolder(final ViewHolder holder, int position) {

            String nombreuni = mValues.get(position).getName();
            holder.mContentView.setText(nombreuni);
            holder.itemView.setTag(mValues.get(position));
            holder.itemView.setOnClickListener(itemVew ->{
                Bundle arguments=new Bundle();
                arguments.putString(ItemDetailFragment.NAME, nombreuni);
                arguments.putString(ItemDetailFragment.URL, mValues.get(position).getWebPages().get(0));
                if(mItemDetailFragmentContainer != null){
                    Navigation.findNavController(mItemDetailFragmentContainer).navigate(R.id.item_detail_fragment, arguments);
                }else{
                    Navigation.findNavController(itemVew).navigate(R.id.item_detail_fragment, arguments);
                }
            });

        }

        @Override
        public int getItemCount() {
            return mValues.size();
        }

        class ViewHolder extends RecyclerView.ViewHolder {
            final TextView mContentView;

            ViewHolder(ItemListContentBinding binding) {
                super(binding.getRoot());
                mContentView = binding.content;
            }

        }
    }
}