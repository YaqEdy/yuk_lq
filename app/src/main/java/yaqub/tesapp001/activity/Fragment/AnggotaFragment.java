package yaqub.tesapp001.activity.Fragment;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import yaqub.tesapp001.Adapter.AdapterPostAnggota;
import yaqub.tesapp001.R;
import yaqub.tesapp001.activity.AnggotaGroupActivity;
import yaqub.tesapp001.activity.UpdateAnggotaActivity;
import yaqub.tesapp001.api.APIRetrofitAnggota;
import yaqub.tesapp001.model.ResponseAnggota;

/**
 * A simple {@link Fragment} subclass.
 */
public class AnggotaFragment extends Fragment {
//    public RecyclerView recyclerView;
//    public static AdapterPostAnggota adapterPostAnggota;
//    FloatingActionButton fabAdd;
//    public static AnggotaFragment anggotaFragment;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
       View view= inflater.inflate(R.layout.fragment_anggota, container, false);
//       anggotaFragment=this;
//
//        fabAdd= view.findViewById(R.id.fabAddAnggota);
//
//        fabAdd.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                updateAnggota();
//                UpdateAnggotaActivity.sParam=0;
//            }
//        });
//        APIRetrofitAnggota.DoGetAllAnggota();

//        adapterPostAnggota = new AdapterPostAnggota(dataAnggota); //
//        LinearLayoutManager layoutManager=new LinearLayoutManager(getActivity());
////        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this.getActivity());
//        recyclerView.setLayoutManager(layoutManager);
//        recyclerView.setAdapter(adapterPostAnggota);

//        recyclerView = (RecyclerView) view.findViewById(R.id.rvAnggota);
//        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(getActivity());
//        recyclerView.setLayoutManager(linearLayoutManager);
//        recyclerView.setAdapter(adapterPostAnggota);


        return view;
    }

    //    public void showAnggota(List<ResponseAnggota> data) {
//        adapterPostAnggota = new AdapterPostAnggota(data); //
////        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this.getActivity());
////        recyclerView.setLayoutManager(layoutManager);
////        recyclerView.setAdapter(adapterPostAnggota);
//    }

//    public void updateAnggota(){
//        Intent intentUpdate=new Intent(this.getActivity(),UpdateAnggotaActivity.class);
//        startActivity(intentUpdate);
//    }


}
