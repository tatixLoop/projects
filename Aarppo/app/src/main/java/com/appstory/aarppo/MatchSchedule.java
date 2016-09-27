package com.appstory.aarppo;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link MatchSchedule.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link MatchSchedule#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MatchSchedule extends Fragment implements  AdapterView.OnItemClickListener{
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    AarpoDb db;
    ListView lv_pushup;
    List<MatchList> list1;

    public MatchSchedule() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment MatchSchedule.
     */
    // TODO: Rename and change types and number of parameters
    public static MatchSchedule newInstance(String param1, String param2) {
        MatchSchedule fragment = new MatchSchedule();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }




    }
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {


        String fid = list1.get(position).getId();
        Log.d("JKS","clicked on " + fid);


    }
    private  String getTeamName(int teamId)
    {
        String teamName = "empty";
        String query = "select teamName from tbl_teamName WHERE teamId="+teamId;
        Cursor c = db.selectData(query);
        if (c != null) {
            while (c.moveToNext()) {
                teamName = c.getString(0);
            }
        }
        return  teamName;
    }
    private String getHomeGround(int teamId)
    {
        String teamName = "empty";
        String query = "select homeGround from tbl_teamName WHERE teamId="+teamId;
        Cursor c = db.selectData(query);
        if (c != null) {
            while (c.moveToNext()) {
                teamName = c.getString(0);
            }
        }
        return  teamName;
    }
    private void getListSchedules() {

        String se = "select * from tbl_schedule";

        Log.d("JKS","sql query = "+se);
        Cursor c = db.selectData(se);
        if (c != null) {
            while (c.moveToNext()) {
                //Log.d("JKS","result "+c.getString(0)+" =  "+c.getString(1) + "team1 = "+getTeamName(c.getInt(3))+" team2="+getTeamName(c.getInt(4))) ;
                String dateTime = c.getString(1);
                String format = c.getString(2);
                try {
                    SimpleDateFormat dateformat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                    Date newDate = dateformat.parse(c.getString(1));

                    dateformat = new SimpleDateFormat("dd-MM-yyyy HH:mm");
                    dateTime = dateformat.format(newDate);
                    //Log.d("JKS","Date="+dateTime);
                }
                catch (ParseException ex)
                {

                }
                int img = R.drawable.ic_menu_camera;
                MatchList p1 = new MatchList();
                p1.setName(dateTime);
                p1.setTeam1(getTeamName(c.getInt(3)));
                p1.setTeam2(getTeamName(c.getInt(4)));
                p1.setId(c.getString(0));
                p1.setLocation(getHomeGround(c.getInt(3)));
                p1.setImg(img);
                list1.add(p1);
            }
        }

        AdapterMatchList a = new AdapterMatchList(getActivity(), list1);
        lv_pushup.setAdapter(a);

    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_match_schedule, container, false);
        db = new AarpoDb();
        db.openConnection();
        lv_pushup = (ListView) rootView.findViewById(R.id.lv_matchList);
        list1 = new ArrayList<>();

        getListSchedules();

        db.closeConnection();
        return  rootView;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
