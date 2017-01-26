package org.bk2rl.uporotiyinvestor.view;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

//import com.facebook.share.model.SharePhoto;
//import com.facebook.share.model.SharePhotoContent;
//import com.facebook.share.widget.ShareButton;

import org.bk2rl.uporotiyinvestor.R;

import java.io.ByteArrayOutputStream;


public class ResultFragment extends Fragment {

    private static final String ARG_RESULT_STRING_ARRAY = "result-string-array";
    private static final int MY_PERMISSIONS_REQUEST_WRITE_EXTERNAL_STORAGE = 200;

    private String[] mResultStringArray;

    private OnFragmentInteractionListener mListener;
    private Intent shareIntent;
    private Bitmap bitmap;
    private Toolbar toolbar;
    private TextView toolBarTitle;

    public ResultFragment() {
        // Required empty public constructor
    }

    public static ResultFragment newInstance(String[] result) {
        ResultFragment fragment = new ResultFragment();
        Bundle args = new Bundle();
        args.putStringArray(ARG_RESULT_STRING_ARRAY, result);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mResultStringArray = getArguments().getStringArray(ARG_RESULT_STRING_ARRAY);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View view = inflater.inflate(R.layout.fragment_result, container, false);
        final TextView textView = (TextView) view.findViewById(R.id.result);
//        final ShareButton shareButton = (ShareButton)view.findViewById(R.id.fb_share_button);
        toolbar = (Toolbar) view.findViewById(R.id.toolbar);
        toolbar.setTitle("");
        toolBarTitle = ((TextView) toolbar.findViewById(R.id.toolbar_title));
        toolBarTitle.setTypeface(Typeface.createFromAsset(getResources().getAssets(), "fonts/comicbd.ttf"));
        toolbar.setBackgroundColor(ContextCompat.getColor(getContext(),R.color.result));
        Window window = getActivity().getWindow();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.setStatusBarColor(ContextCompat.getColor(getActivity(), R.color.resultDark));
        }

        ((UporotoeActivity)getActivity()).setSupportActionBar(toolbar);
        textView.setText(
                mResultStringArray[UporotoeActivity.PRODUCT_INDEX] + ", "
                        + mResultStringArray[UporotoeActivity.FEATURE_INDEX] + ", "
                        + mResultStringArray[UporotoeActivity.PROBLEM_INDEX]);

        textView.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                    bitmap = Bitmap.createBitmap(textView.getMeasuredWidth(), textView.getMeasuredHeight(), Bitmap.Config.ARGB_8888);
                    Canvas canvas = new Canvas(bitmap);
                    textView.layout(0, 0, textView.getMeasuredWidth(), textView.getMeasuredHeight());
                    canvas.drawColor(Color.WHITE);
                    textView.draw(canvas);
//                    SharePhoto photo = new SharePhoto.Builder()
//                            .setBitmap(bitmap)
//                            .build();
//                    SharePhotoContent content = new SharePhotoContent.Builder()
//                            .addPhoto(photo)
//                            .build();
//                ShareDialog.show(ResultFragment.this, content);
//                ((ImageView) view.findViewById(R.id.testResult)).setImageBitmap(bitmap);
//                    shareButton.setShareContent(content);
                    textView.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                }
        });

        view.findViewById(R.id.share_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            if (ContextCompat.checkSelfPermission(getActivity(),
                    Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
                ByteArrayOutputStream bytes = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.PNG, 100, bytes);
                String path = MediaStore.Images.Media.insertImage(getActivity().getContentResolver(), bitmap, "Title", null);
                Uri bitmapUri = Uri.parse(path);
//                bitmap.compress(Bitmap.CompressFormat.PNG, 100, fOut);
//                file.setReadable(true, false);
                shareIntent = new Intent();
                shareIntent.setAction(Intent.ACTION_SEND);
                shareIntent.putExtra(Intent.EXTRA_STREAM, bitmapUri);
                shareIntent.setType("image/png");
                startActivity(Intent.createChooser(shareIntent, getResources().getText(R.string.send_to)));
            } else {
                if (ActivityCompat.shouldShowRequestPermissionRationale(getActivity(),
                        Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                } else {

                    // No explanation needed, we can request the permission.

                    ActivityCompat.requestPermissions(getActivity(),
                            new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                            MY_PERMISSIONS_REQUEST_WRITE_EXTERNAL_STORAGE);

                    // MY_PERMISSIONS_REQUEST_WRITE_EXTERNAL_STORAGE is an
                    // app-defined int constant. The callback method gets the
                    // result of the request.
                }
            }
        }});

        return view;
    }

    // TODO: Rename method, update argument and hook method into UI event
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
        // TODO: Update argument type and name
        void onFragmentInteraction(String string);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_WRITE_EXTERNAL_STORAGE: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    // permission was granted, yay! Do the
                    // contacts-related task you need to do.

                } else {

                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                }
                return;
            }

            // other 'case' lines to check for other
            // permissions this app might request
        }
    }
}
