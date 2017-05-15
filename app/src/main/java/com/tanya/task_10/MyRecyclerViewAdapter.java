package com.tanya.task_10;


import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

public class MyRecyclerViewAdapter extends RecyclerView.Adapter<MyRecyclerViewAdapter.CustomViewHolder> {

    List<String> itemList;
    private Context mContext;
    int selectedPosition = -1;

    public MyRecyclerViewAdapter(Context context, List<String> itemList) {
        this.itemList = itemList;
        this.mContext = context;
    }

    @Override
    public CustomViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.cardview, null);
        CustomViewHolder viewHolder = new CustomViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(CustomViewHolder customViewHolder, int i) {
        String itemUri = itemList.get(i);
        if (!itemUri.isEmpty()) {
//            Picasso.with(mContext).load(itemUri)
//                    .error(R.drawable.placeholder)
//                    .resize(600, 600)
//                    .placeholder(R.drawable.placeholder)
//                    .into(customViewHolder.imageView);
//            customViewHolder.setUri(itemUri);
            customViewHolder.textView.setText(itemUri);
            customViewHolder.imageView.setImageDrawable(mContext.getResources().getDrawable(R.drawable.placeholder));
        }


    }

    @Override
    public int getItemCount() {
        return (null != itemList ? itemList.size() : 0);
    }

    private View.OnClickListener onStateChangedListener(final CheckBox checkBox, final int position) {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                notifyDataSetChanged(); // update adapter after changed
            }
        };
    }


    class CustomViewHolder extends RecyclerView.ViewHolder  {

        protected ImageView imageView;
        protected TextView textView;
        protected String uri;

        public String getUri() {
            return uri;
        }

        public void setUri(String uri) {
            this.uri = uri;
        }

        public CustomViewHolder(View view) {
            super(view);
            this.imageView = (ImageView) view.findViewById(R.id.image);
            this.textView = (TextView) view.findViewById(R.id.text);
        }

    }
}
