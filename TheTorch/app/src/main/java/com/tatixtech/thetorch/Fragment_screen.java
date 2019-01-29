package com.tatixtech.thetorch;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.hardware.Camera;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link Fragment_screen.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link Fragment_screen#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Fragment_screen extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public Fragment_screen() {
        // Required empty public constructor
    }

    public void ScreenLightOn() {


        Intent screenTorch = new Intent(getContext(), ScreenTorch.class);
        screenTorch.putExtra("disco",0);
        startActivity(screenTorch);
    }

    public void ScreenLightOff() {

    }
    boolean gScreen = false;
    boolean getScreenVariable()
    {
        return gScreen;
    }
    void setScreenVariable(boolean var)
    {
        gScreen = var;
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Fragment_screen.
     */
    // TODO: Rename and change types and number of parameters
    public static Fragment_screen newInstance(String param1, String param2) {
        Fragment_screen fragment = new Fragment_screen();
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

    boolean isPermissionGiven() {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (!Settings.System.canWrite(getActivity().getApplicationContext())) {
                return false;
            }
        } else {
            return true; // Find a way to do it for lower version than lollipop.
        }
        return true;
    }
    void checkAndGivePermission()
    {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (!Settings.System.canWrite(getActivity().getApplicationContext())) {


                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                builder.setTitle("Permission to modify system settings");
                builder.setMessage("We need to modify the system settings to control screen brightness, Please check it in the settings.");
                builder.setPositiveButton("Grant", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                        Intent intent = new Intent(Settings.ACTION_MANAGE_WRITE_SETTINGS);
                        intent.setData(Uri.parse("package:" + getContext().getPackageName()));
                        startActivityForResult(intent, 101);
                    }
                });
                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
                builder.show();


            }
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.fragment_fragment_screen, container, false);


        new Thread(new Runnable() {
              public void run() {
                  try {
                      Thread.sleep(2000);
                      getActivity().runOnUiThread(new Runnable() {
                          @Override
                          public void run() {
                              checkAndGivePermission();
                          }
                      });
                  }
                  catch (InterruptedException e)
                  {

                  }
              }
              }).start();


                    // checkAndGivePermission();
        // Inflate the layout for this fragment
        ImageView btn_screen = view.findViewById(R.id.imgbtn_screen);
        btn_screen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean torch = false;
              //  torch = getScreenVariable();
/*
                if(!isPermissionGiven())
                {
                    checkAndGivePermission();
                    return;
                }*/

                if(torch == true)
                {
                    ScreenLightOff();
                   // setScreenVariable(false);
                }
                else
                {
                    ScreenLightOn();
                   // setScreenVariable(true);
                }

            }
        });
        return view;
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
