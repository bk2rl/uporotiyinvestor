package org.bk2rl.uporotiyinvestor.view;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import org.bk2rl.uporotiyinvestor.R;
import org.bk2rl.uporotiyinvestor.model.Item;

import java.util.List;

public class ItemAdapter extends RecyclerView.Adapter<ItemAdapter.ViewHolder> {

    private final List<Item> mValues;
    private final ItemFragment.OnListFragmentInteractionListener mListener;
    private final Context mContext;
    private final RelativeLayout.LayoutParams mLayoutParams;

    public ItemAdapter(Context context, List<Item> items, RelativeLayout.LayoutParams layoutParams, ItemFragment.OnListFragmentInteractionListener listener) {
        mValues = items;
        mListener = listener;
        mContext = context;
        mLayoutParams = layoutParams;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.mItem = mValues.get(position);

        holder.mView.setLayoutParams(mLayoutParams);
//        mLayoutParams.width += 2;
//        mLayoutParams.height += 2;
//        holder.mImageView.setLayoutParams(mLayoutParams);
//        holder.mView.setBackground(ContextCompat.getDrawable(mContext,R.drawable.item_border));

        String imgSrc = holder.mItem.getFullImgSrc();

        Glide.with(mContext).load(imgSrc)
                .asBitmap().into(holder.mImageView);
        holder.mTextView.setText(holder.mItem.getItem());

        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != mListener) {
                    // Notify the active callbacks interface (the activity, if the
                    // fragment is attached to one) that an item has been selected.
                    mListener.onListFragmentInteraction(holder.mItem);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final ImageView mImageView;
        public final TextView mTextView;
        public Item mItem;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            mImageView = (ImageView) view.findViewById(R.id.imageView);
            mTextView = (TextView) view.findViewById(R.id.textView);
        }

        @Override
        public String toString() {
            return super.toString() + " '" + mItem.getItem() + "'";
        }
    }
}
