package com.example.mylibrary;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class TabAdapter2 extends RecyclerView.Adapter<TabAdapter2.ViewHolder> {

    private int selectedItem = 0;
    private Context context;
    private List<String> tabList;
    private TabClickListener tabClickListener;

    public TabAdapter2(Context context, List<String> tabList) {
        this.context = context;
        this.tabList = tabList;
        this.tabClickListener = tabClickListener;
    }

    public void loadTabTitles(List<String> tab_title,TabClickListener tabClickListener){
        this.tabList=tab_title;
        this.tabClickListener = tabClickListener;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public TabAdapter2.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.tab_adapter, parent, false);
        return new TabAdapter2.ViewHolder(v);
    }



    @Override
    public void onBindViewHolder(@NonNull TabAdapter2.ViewHolder holder, int position) {

        String item = tabList.get(position);

        holder.tabTitle.setText(item);

        if (selectedItem == position) {
            Log.v("GGG","inidcator_visible="+String.valueOf(position)+"-->"+String.valueOf(SlidingTab.selected_inidcator_color));
            holder.view.setBackgroundColor(SlidingTab.selected_inidcator_color);
            holder.tabTitle.setTextColor(SlidingTab.selected_text_color);
        } else {
            Log.v("GGG","inidcator_not_visible="+String.valueOf(position));
            holder.view.setBackgroundColor(SlidingTab.unselected_inidcator_color);
            holder.tabTitle.setTextColor(SlidingTab.unselected_text_color);
        }

        holder.itemView.setOnClickListener(view -> {
            if(selectedItem != position){
                int PreviousSelectedItem = selectedItem;
                selectedItem = position;
                holder.tabTitle.setTextColor(SlidingTab.selected_text_color);
//                notifyItemChanged(PreviousSelectedItem);
                notifyDataSetChanged();
                tabClickListener.onClick(position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return tabList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private AppCompatImageView image;
        private AppCompatTextView tabTitle;
        private View view;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tabTitle = itemView.findViewById(R.id.tab_title);
            view = itemView.findViewById(R.id.selected_tab_indicator);
        }
    }
}