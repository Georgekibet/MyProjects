package data.collect.com.mydata;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.gordonwong.materialsheetfab.MaterialSheetFab;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import DataBase.Farmer;
import Repositories.FarmerRepository;
import utils.Fab;
import utils.SearchAbleAdapter;

/**
 * Created by george on 10/10/2015.
 */
public class DataListingFragment extends BaseFragment {
    Farmer farmer;
    ListView listView;
    List<Farmer> farmers;

    @Override
    public View onCreateView(LayoutInflater inflater ,ViewGroup container, Bundle savedInstanceState) {


        View view=inflater.inflate( R.layout.data_listview, container, false);;

        farmer=new Farmer();

        Date currDateTime = new Date(System.currentTimeMillis());
        listView=(ListView)view.findViewById(R.id.data_listview);

        farmers= new ArrayList<Farmer>();

        try {

            farmers=new FarmerRepository(getActivity()).getAllUsers();
        }catch (Exception e){
            farmers.add(farmer);
        }
          return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        BaseAdapter adapter= new SearchAbleAdapter(getActivity(),farmers);
        listView.setAdapter(adapter);

        setUpFab(view);
    }
    protected void setUpFab(View view) {
      Fab fabButton = (Fab) view.findViewById(R.id.fab);
        View sheetView = view.findViewById(R.id.fab_sheet);
        View overlay = view.findViewById(R.id.overlay);


        int sheetColor = getResources().getColor(R.color.theme_primary);
        int fabColor = getResources().getColor(R.color.theme_primary);




        // Initialize material sheet FAB
         new MaterialSheetFab<>(fabButton, sheetView, overlay, sheetColor, fabColor);

        TextView add=(TextView)view.findViewById(R.id.fab_sheet_item_note);
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentManager fm=getFragmentManager();
                Fragment fragment=new InputFragment();
                FragmentTransaction fragmentTransaction=fm.beginTransaction();
                fragmentTransaction.replace(R.id.fragment_place, fragment);
                fragmentTransaction.commit();
            }
        });
    }
}
