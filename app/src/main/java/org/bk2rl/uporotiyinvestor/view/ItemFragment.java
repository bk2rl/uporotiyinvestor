package org.bk2rl.uporotiyinvestor.view;

import android.content.Context;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.DrawableRes;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import org.bk2rl.uporotiyinvestor.R;
import org.bk2rl.uporotiyinvestor.model.Item;
import org.bk2rl.uporotiyinvestor.model.RandomItems;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class ItemFragment extends Fragment {

    private static final String ARG_ITEMS_LIST = "items-list";
    private static final String ARG_COLUMN_COUNT = "column-count";
    private static final String ARG_TOOLBAR_DRAWABLE = "toolbar-drawableRes";
    private static final String ARG_TOOLBAR_TEXT = "toolbar-text";
    private static final String ARG_TOOLBAR_FIRST_COLOR = "toolbar-first-color";
    private static final String ARG_TOOLBAR_SECOND_COLOR = "toolbar-second-color";

    private List<Item> mRandomItems;
    private int mColumnCount;
    private int mDrawableResource;
    private String mText;
    private int mFirstColor;
    private int mSecondColor;

    private OnListFragmentInteractionListener mListener;
    private RecyclerView recyclerView;
    private View rootView;
    private ImageView titleImageView;
    private Toolbar toolbar;
    private TextView toolBarTitle;
    private UporotoeActivity mActivity;

    public ItemFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            mColumnCount = getArguments().getInt(ARG_COLUMN_COUNT);
            mDrawableResource = getArguments().getInt(ARG_TOOLBAR_DRAWABLE);
            mText = getArguments().getString(ARG_TOOLBAR_TEXT);
            mFirstColor = getArguments().getInt(ARG_TOOLBAR_FIRST_COLOR);
            mSecondColor = getArguments().getInt(ARG_TOOLBAR_SECOND_COLOR);
            mRandomItems = ((List<Item>) (getArguments().getSerializable(ARG_ITEMS_LIST)));
        }

        mActivity = ((UporotoeActivity) getActivity());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_item_list, container, false);
        titleImageView = (ImageView) rootView.findViewById(R.id.title_image);
        View list = rootView.findViewById(R.id.list);
        // Set the adapter
        if (list instanceof RecyclerView) {
            Context context = rootView.getContext();
            recyclerView = (RecyclerView) list;
            if (mColumnCount <= 1) {
                recyclerView.setLayoutManager(new LinearLayoutManager(context));
            } else {
                recyclerView.setLayoutManager(new GridLayoutManager(context, mColumnCount));
            }
            setRecyclerViewLayoutParams();
        }

        toolbarInitialize();

        return rootView;
    }

    private void toolbarInitialize() {
        toolbar = (Toolbar) rootView.findViewById(R.id.toolbar);
        toolbar.setTitle("");
        toolBarTitle = ((TextView) toolbar.findViewById(R.id.toolbar_title));
        toolBarTitle.setTypeface(Typeface.createFromAsset(getResources().getAssets(), "fonts/comicbd.ttf"));

        Window window = getActivity().getWindow();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }

        setToolbarResources(mActivity.getResources().getDrawable(mDrawableResource), mText, mFirstColor, mSecondColor);

        ((UporotoeActivity) getActivity()).setSupportActionBar(toolbar);
    }

    private void setToolbarResources(Drawable drawableRes, String text, int firstColor, int secondColor) {
        titleImageView.setImageDrawable(drawableRes);
        toolBarTitle.setText(text);
        toolbar.setBackgroundColor(firstColor);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getActivity().getWindow().setStatusBarColor(secondColor);
        }
    }

    private void setRecyclerViewLayoutParams() {
        recyclerView.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {

                final int width = recyclerView.getMeasuredWidth();
                final int height = recyclerView.getMeasuredHeight();

                final View itemView = rootView.findViewById(R.id.dummy_content);

                GridLayoutManager layout = new GridLayoutManager(ItemFragment.this.getActivity(), mColumnCount) {
                    @Override
                    public boolean canScrollVertically() {
                        super.canScrollVertically();
                        return false;
                    }
                };

                RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(width / mColumnCount - 10, height / (RandomItems.MAX_ITEMS_VALUE / mColumnCount) - 10);
                layoutParams.setMargins(5, 5, 5, 5);

                recyclerView.setLayoutManager(layout);

                titleImageView.setImageResource(mDrawableResource);

                recyclerView.setAdapter(new ItemAdapter(getActivity().getApplicationContext(), mRandomItems, layoutParams, mListener));


                if (Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN) {
                    recyclerView.getViewTreeObserver().removeGlobalOnLayoutListener(this);
                } else {
                    recyclerView.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                }
            }
        });
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnListFragmentInteractionListener) {
            mListener = (OnListFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnListFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public interface OnListFragmentInteractionListener {
        // TODO: Update argument type and name
        void onListFragmentInteraction(Item item);
    }

    public static class ItemFragmentBuilder {

        private int columnCount;
        private int drawableRes;
        private String text;
        private int firstColor;
        private int secondColor;
        private ArrayList<Item> randomItems;


        public ItemFragmentBuilder() {
        }

        public ItemFragmentBuilder setColumnCount(int columnCount) {
            this.columnCount = columnCount;
            return this;
        }

        public ItemFragmentBuilder setDrawableResource(@DrawableRes int drawable) {
            this.drawableRes = drawable;
            return this;
        }

        public ItemFragmentBuilder setText(String text) {
            this.text = text;
            return this;
        }

        public ItemFragmentBuilder setFirstColor(int firstColor) {
            this.firstColor = firstColor;
            return this;
        }

        public ItemFragmentBuilder setSecondColor(int secondColor) {
            this.secondColor = secondColor;
            return this;
        }

        public ItemFragmentBuilder setRandomItems(ArrayList<Item> randomItems) {
            this.randomItems = randomItems;
            return this;
        }

        public ItemFragment build() {
            ItemFragment fragment = new ItemFragment();
            Bundle args = new Bundle();
            args.putInt(ARG_COLUMN_COUNT, columnCount);
            args.putInt(ARG_TOOLBAR_DRAWABLE, drawableRes);
            args.putString(ARG_TOOLBAR_TEXT, text);
            args.putInt(ARG_TOOLBAR_FIRST_COLOR, firstColor);
            args.putInt(ARG_TOOLBAR_SECOND_COLOR, secondColor);
            args.putSerializable(ARG_ITEMS_LIST, randomItems);
            fragment.setArguments(args);
            return fragment;
        }


        private class SerializableOverlay implements Serializable {
            private Object object;

            public SerializableOverlay(Object drawable) {
                this.object = drawable;
            }

            public Object getObject() {
                return object;
            }
        }

    }
}
