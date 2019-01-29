package com.tatixtech.thetorch;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.hardware.Camera;
import android.hardware.Camera.Parameters;
import android.widget.ImageView;
import android.widget.Toast;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link Fragment_torch.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link Fragment_torch#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Fragment_torch extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";


    public static Camera cam = null;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    void getCameraPermission()
    {


        // permission granting
        String permission = android.Manifest.permission.CAMERA;
        int res = getContext().checkCallingOrSelfPermission(permission);
        if (res == PackageManager.PERMISSION_GRANTED)
        {
            //Log.i("JKS","Permission is granted");

        }
        else
        {
            Log.i("JKS","Permission is not granted");
            ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.CAMERA}, 100);
        }


    }

    public Fragment_torch() {
        // Required empty public constructor
    }
    boolean isCameraPermissionPresent()
    {
        String permission = android.Manifest.permission.CAMERA;
        int res = getContext().checkCallingOrSelfPermission(permission);
        if (res == PackageManager.PERMISSION_GRANTED)
        {
            return true;
        }
        else {
            return false;
        }
    }
    public void flashLightOn() {

        try {
            getCameraPermission();
            if (getActivity().getPackageManager().hasSystemFeature(
                    PackageManager.FEATURE_CAMERA_FLASH)) {
                cam = Camera.open();
                Parameters p = cam.getParameters();
                p.setFlashMode(Parameters.FLASH_MODE_TORCH);
                cam.setParameters(p);
                cam.startPreview();
                print("JKS camera flash on");
            }
            else {
                print("doesnt have camera feature");
            }
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(getActivity().getBaseContext(), "Error occurred while switching on torch light !",
                    Toast.LENGTH_SHORT).show();

        }
    }

    public void flashLightOff() {
        try {
            if (getActivity().getPackageManager().hasSystemFeature(
                    PackageManager.FEATURE_CAMERA_FLASH)) {
                cam.stopPreview();
                cam.release();
                cam = null;
            }
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(getActivity().getBaseContext(), "Torch light is not ON. May be it was not closed properly !!!",
                    Toast.LENGTH_SHORT).show();
        }
    }

    void print(String str)
    {
        Log.d("JKS",str);
    }
    boolean gTorch = false;
    boolean getTorchVariable()
    {
        SharedPreferences sp = getContext().getSharedPreferences(PREFS_GAME ,Context.MODE_PRIVATE);
        int torch = sp.getInt(TORCH_VAR,-1);
        if(torch == 1)
            return true;
        else
            return false;
    }
    void setTorchVariable(boolean var)
    {
        SharedPreferences sp = getContext().getSharedPreferences(PREFS_GAME ,Context.MODE_PRIVATE);
        if(var)
        {
            sp.edit().putInt(TORCH_VAR, 1).commit();
        }
        else
        {
            sp.edit().putInt(TORCH_VAR, 0).commit();
        }
    }
    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Fragment_torch.
     */
    // TODO: Rename and change types and number of parameters
    public static Fragment_torch newInstance(String param1, String param2) {
        Fragment_torch fragment = new Fragment_torch();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    private  String PREFS_GAME = "TORCHSTATUS";
    private String TORCH_VAR= "TORCH_VAR";
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }


        SharedPreferences sp = getContext().getSharedPreferences(PREFS_GAME ,Context.MODE_PRIVATE);
        int torch = sp.getInt(TORCH_VAR,-1);
        if(torch == -1) {
            Log.i("JKS","Set shared preference for the first time to 0");
            sp.edit().putInt(TORCH_VAR, 0).commit();
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view;
        view = inflater.inflate(R.layout.fragment_fragment_torch, container, false);

        if(getTorchVariable())
        {
            ImageView torchImg = view.findViewById(R.id.imgbtn_torch);
            torchImg.setImageDrawable(getContext().getResources().getDrawable(R.drawable.torchon));
        }
        else
        {
            ImageView torchImg = view.findViewById(R.id.imgbtn_torch);
            torchImg.setImageDrawable(getContext().getResources().getDrawable(R.drawable.torchoff));
        }

        ImageView btn_torch = view.findViewById(R.id.imgbtn_torch);
        btn_torch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean torch = false;
                torch = getTorchVariable();

                ImageView torchImg = view.findViewById(R.id.imgbtn_torch);
                if(!isCameraPermissionPresent())
                {
                    getCameraPermission();
                    return;
                }
                if(torch == true)
                {
                    flashLightOff();
                    setTorchVariable(false);
                    torchImg.setImageDrawable(getContext().getResources().getDrawable(R.drawable.torchoff));
                }
                else
                {
                    flashLightOn();
                    setTorchVariable(true);
                    torchImg.setImageDrawable(getContext().getResources().getDrawable(R.drawable.torchon));
                }

            }
        });
        // Inflate the layout for this fragment
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
