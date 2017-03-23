package org.bk2rl.uporotiyinvestor.view;

import android.content.Context;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import org.bk2rl.uporotiyinvestor.R;

public class GreetingsFragment extends Fragment {

    private OnFragmentInteractionListener mListener;
    private Toolbar toolbar;
    private TextView toolBarTitle;

    public GreetingsFragment() {
        // Required empty public constructor
    }

    public static GreetingsFragment newInstance() {
        GreetingsFragment fragment = new GreetingsFragment();
        Log.d("Greetings Fragment","instantiated");
//        Bundle args = new Bundle();
//        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d("Greetings Fragment","onCreate");
        if (getArguments() != null) {
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        Log.d("Greetings Fragment","onCreateView");
        View rootView = inflater.inflate(R.layout.fragment_greetings, container, false);
        toolbar = (Toolbar) rootView.findViewById(R.id.toolbar);
        toolbar.setTitle("");
        toolBarTitle = ((TextView) toolbar.findViewById(R.id.toolbar_title));
        toolBarTitle.setTypeface(Typeface.createFromAsset(getResources().getAssets(), "fonts/comicbd.ttf"));
        toolbar.setBackgroundColor(ContextCompat.getColor(getContext(),R.color.greetings));
        Window window = getActivity().getWindow();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.setStatusBarColor(ContextCompat.getColor(getActivity(), R.color.greetingsDark));
        }

        ((UporotoeActivity)getActivity()).setSupportActionBar(toolbar);
        rootView.findViewById(R.id.wake_up_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onButtonPressed("switch-to-start");
            }
        });
        return rootView;
    }


    public void onButtonPressed(String string) {
        if (mListener != null) {
            mListener.onFragmentInteraction(string);
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

    public interface OnFragmentInteractionListener {
        void onFragmentInteraction(String string);
    }
}
