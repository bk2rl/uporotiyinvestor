package org.bk2rl.uporotiyinvestor.view;

import android.content.Context;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
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

import java.util.List;

public class ItemFragment extends Fragment {
    private static final String ARG_COLUMN_COUNT = "column-count";
    private static final String ARG_ITEM_TYPE = "item-type";
    private int mColumnCount = 3;
    private Item.ItemType mItemType;
    private OnListFragmentInteractionListener mListener;
    private List<Item> randomItems;
    private RecyclerView recyclerView;
    private View rootView;
    private ImageView titleImageView;
    private Toolbar toolbar;
    private TextView toolBarTitle;

    public ItemFragment() {
    }

    public static ItemFragment newInstance(int columnCount, Item.ItemType itemType) {
        ItemFragment fragment = new ItemFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_COLUMN_COUNT, columnCount);
        args.putSerializable(ARG_ITEM_TYPE, itemType);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            mColumnCount = getArguments().getInt(ARG_COLUMN_COUNT);
            mItemType = (Item.ItemType) getArguments().getSerializable(ARG_ITEM_TYPE);
        }
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

        switch (mItemType) {
            case PRODUCT:
                titleImageView.setImageResource(R.drawable.product2);
                toolBarTitle.setText(R.string.second_stage_product);
                toolbar.setBackgroundColor(ContextCompat.getColor(getActivity(), R.color.product));
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    window.setStatusBarColor(ContextCompat.getColor(getActivity(), R.color.productDark));
                }
                break;
            case PROBLEM:
                titleImageView.setImageResource(R.drawable.problem1);
                toolBarTitle.setText(R.string.first_stage_problem);
                toolbar.setBackgroundColor(ContextCompat.getColor(getActivity(), R.color.problem));
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    window.setStatusBarColor(ContextCompat.getColor(getActivity(), R.color.problemDark));
                }
                break;
            case FEATURE:
                toolBarTitle.setText(R.string.third_stage_feature);
                titleImageView.setImageResource(R.drawable.feature3);
                toolbar.setBackgroundColor(ContextCompat.getColor(getActivity(), R.color.feature));
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    window.setStatusBarColor(ContextCompat.getColor(getActivity(), R.color.featureDark));
                }
                break;
        }

        ((UporotoeActivity) getActivity()).setSupportActionBar(toolbar);
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

                RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(width/mColumnCount - 10,height/(RandomItems.MAX_ITEMS_VALUE/mColumnCount) - 10);
                layoutParams.setMargins(5,5,5,5);

                //TODO set gravity
                recyclerView.setLayoutManager(layout);
//                        visibleItems = column * rows;
//                        soundItems = soundItems.subList(p_start = 0, p_end = visibleItems);
//                        mAdapter = new SoundItemAdapter(soundItems, mListener, mActivity, mImageLoadingListener);
                switch (mItemType) {
                    case PRODUCT:
                        titleImageView.setImageResource(R.drawable.product2);
                        randomItems = RandomItems.getRandomProducts();
                        break;
                    case PROBLEM:
                        titleImageView.setImageResource(R.drawable.problem1);
                        randomItems = RandomItems.getRandomProblems();
                        break;
                    case FEATURE:
                        titleImageView.setImageResource(R.drawable.feature3);
                        randomItems = RandomItems.getRandomFeatures();
                        break;
                }

                recyclerView.setAdapter(new ItemAdapter(getActivity().getApplicationContext(), randomItems, layoutParams, mListener));

//                itemView.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
//                    @Override
//                    public void onGlobalLayout() {
//                        int itemWidth = itemView.getWidth();
//                        int itemHeight = itemView.getHeight();
//                        int column = width / itemWidth;
//                        int rows = height / itemHeight;
//                        int addWidthMargin = (width - column * itemWidth) / 2;
//                        int addHeightMargin = (height - rows * itemHeight) / 2;
//
//                        ViewGroup.MarginLayoutParams lp = (ViewGroup.MarginLayoutParams) recyclerView.getLayoutParams();
//                        lp.setMargins(lp.leftMargin + addWidthMargin, lp.topMargin + addHeightMargin,
//                                lp.rightMargin + addWidthMargin, lp.bottomMargin + addHeightMargin);
//                        recyclerView.setLayoutParams(lp);
////                        loadScreen.setLayoutParams(lp);
//
//
//
//                        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN) {
//                            itemView.getViewTreeObserver().removeGlobalOnLayoutListener(this);
//                        } else {
//                            itemView.getViewTreeObserver().removeOnGlobalLayoutListener(this);
//                        }
//
////                        currentPokemonIds.setText(
////                                p_start + 1 != p_end ? String.format(Locale.getDefault(), "%d...%d", p_start + 1, p_end) :
////                                        String.format(Locale.getDefault(), "%d", p_end));
//                    }
//                });

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
}
