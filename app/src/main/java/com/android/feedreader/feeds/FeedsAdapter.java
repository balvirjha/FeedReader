package com.android.feedreader.feeds;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.android.feedreader.R;
import com.android.feedreader.databinding.RowFeedListBinding;
import com.android.feedreader.feeds.modals.Row;

import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("unused")
public class FeedsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<Row> feedsList = new ArrayList<>();
    private LayoutInflater layoutInflater;

    public FeedsAdapter() {

    }

    @SuppressWarnings("WeakerAccess")
    public class FeedsViewHolder extends RecyclerView.ViewHolder {
        private RowFeedListBinding rowFeedListBinding;

        public FeedsViewHolder(final RowFeedListBinding rowFeedListBinding) {
            super(rowFeedListBinding.getRoot());
            this.rowFeedListBinding = rowFeedListBinding;
        }
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (layoutInflater == null) {
            layoutInflater = LayoutInflater.from(parent.getContext());
        }
        RowFeedListBinding itemBinding =
                DataBindingUtil.inflate(layoutInflater, R.layout.row_feed_list, parent, false);
        return new FeedsAdapter.FeedsViewHolder(itemBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int position) {
        Row row = feedsList.get(position);
        if (row != null) {
            FeedsAdapter.FeedsViewHolder feedsViewHolder = (FeedsAdapter.FeedsViewHolder) viewHolder;
            feedsViewHolder.rowFeedListBinding.setRow(row);
        }
    }

    @SuppressWarnings("ConstantConditions")
    public void addAllFeeds(List<Row> feedsList) {
        if (this.feedsList != null && this.feedsList.size() > 0) {
            this.feedsList.clear();
        }
        this.feedsList.addAll(feedsList);
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        if (feedsList != null) {
            return feedsList.size();
        } else {
            return 0;
        }
    }

}
