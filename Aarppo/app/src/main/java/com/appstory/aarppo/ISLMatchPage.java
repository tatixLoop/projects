package com.appstory.aarppo;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.StrictMode;
import android.os.SystemClock;
import android.provider.Settings;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import org.apache.commons.net.ntp.NTPUDPClient;
import org.apache.commons.net.ntp.TimeInfo;
import org.w3c.dom.Text;

import java.io.IOException;
import java.net.InetAddress;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.TimeUnit;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link ISLMatchPage.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link ISLMatchPage#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ISLMatchPage extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private  String mId;
    TextView countDown;
    Handler handler = new Handler();
    long driftTime = 0L;

Date matchDate;
    private OnFragmentInteractionListener mListener;

    public ISLMatchPage() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ISLMatchPage.
     */
    // TODO: Rename and change types and number of parameters
    public static ISLMatchPage newInstance(String param1, String param2) {
        ISLMatchPage fragment = new ISLMatchPage();
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
    //*************** STOP WATCH ****super*********
    public Runnable updateTimer = new Runnable() {
        public void run() {


            Date curDate = new Date();
            long diffTime = matchDate.getTime() - curDate.getTime();
            diffTime = diffTime +driftTime;
            long secs = TimeUnit.MILLISECONDS.toSeconds(diffTime) %60;
            long minutes = TimeUnit.MILLISECONDS.toMinutes(diffTime) %60;
            long hours = TimeUnit.MILLISECONDS.toHours(diffTime)%24;
            long days = TimeUnit.MILLISECONDS.toDays(diffTime);

            countDown.setText("" + days + ":" + String.format("%02d", hours) + ":" + String.format("%02d", minutes) + ":"
                    + String.format("%02d", secs));

            handler.postDelayed(this, 500);
        }};

    public static final String TIME_SERVER = "time-a.nist.gov";

    public static Date getNetworkTime() throws IOException {
        NTPUDPClient timeClient = new NTPUDPClient();
        InetAddress inetAddress = InetAddress.getByName(TIME_SERVER);
        TimeInfo timeInfo = timeClient.getTime(inetAddress);
        //long returnTime = timeInfo.getReturnTime();   //local device time
        long returnTime = timeInfo.getMessage().getTransmitTimeStamp().getTime();   //server time

        Date time = new Date(returnTime);
        return time;
    }
    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        driftTime = 0;
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        View rootView = inflater.inflate(R.layout.fragment_islmatch_page, container, false);

        Log.d("JKS","==================================");
if(isNetworkAvailable()) {
    Log.d("JKS","Inter net connection is available");

    try {
        Date netWorkTime = getNetworkTime();
        Date curTime = new Date();
        long diffTime = netWorkTime.getTime() - curTime.getTime();
        driftTime  = diffTime;
        long secs = TimeUnit.MILLISECONDS.toSeconds(diffTime) % 60;
        long minutes = TimeUnit.MILLISECONDS.toMinutes(diffTime) % 60;
        long hours = TimeUnit.MILLISECONDS.toHours(diffTime) % 24;
        long days = TimeUnit.MILLISECONDS.toDays(diffTime);

        Log.d("JKS", "Time drift" + days + ":" + String.format("%02d", hours) + ":" + String.format("%02d", minutes) + ":"
                + String.format("%02d", secs));
    } catch (IOException ex) {

    }
}
        else
{
    Log.d("JKS","Internet is not present");
}
//        Context context = getActivity();
//        SharedPreferences sharedPref = context.getSharedPreferences(
//                (Settings.System.AUTO_TIME), Context.MODE_PRIVATE);
        //android.provider.Settings.Global.putInt(getActivity().getContentResolver(), android.provider.Settings.Global.AUTO_TIME, 0);
        //Settings.System.AUTO_TIME
        //startActivityForResult(new Intent(android.provider.Settings.ACTION_DATE_SETTINGS), 0);

        if( android.provider.Settings.Global.getInt(getActivity().getContentResolver(), android.provider.Settings.Global.AUTO_TIME, 0)== 1)
        {
            Log.d("JKS","Auto update time is on");
        }
        else
        {
            Log.d("JKS","Auto update time is off");
            int requestCode = 99;
            startActivityForResult(new Intent(android.provider.Settings.ACTION_DATE_SETTINGS), requestCode) ;
            if( android.provider.Settings.Global.getInt(getActivity().getContentResolver(), android.provider.Settings.Global.AUTO_TIME, 0)== 0)
            {
                Toast.makeText(rootView.getContext(),"We need to get network time to make it work",Toast.LENGTH_LONG).show();
            }

        }
        Log.d("JKS","==================================");
        String strtext = getArguments().getString("id");
        Log.d("JKS","id ="+strtext);
        mId = strtext;

        String query = "select date_time from tbl_schedule WHERE sched_id="+mId;
        //Log.d("JKS", "querry  =" +query);
        AarpoDb db = new AarpoDb();
        db.openConnection();
        Cursor crsor = db.selectData(query);
        if (crsor != null) {
            while (crsor.moveToNext()) {
                //Log.d("JKS","result "+c.getString(0)+" =  "+c.getString(1) + "team1 = "+getTeamName(c.getInt(3))+" team2="+getTeamName(c.getInt(4))) ;
                try
                {
                    SimpleDateFormat dateformat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                    matchDate = dateformat.parse(crsor.getString(0));
                    Log.d("JKS","Match date ="+crsor.getString(0));
                }
                catch (ParseException ex)
                {

                }
            }
        }
        db.closeConnection();

        handler.postDelayed(updateTimer, 0);
        countDown= (TextView) rootView.findViewById(R.id.txt_countDown);
        countDown.setText("000 days 00 hours 00 mins 00 seconds");

        return rootView;
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
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }


}
