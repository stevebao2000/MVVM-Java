package com.steve.MVVM.ui;

import android.support.annotation.NonNull;
import android.support.v7.recyclerview.extensions.ListAdapter;
import android.support.v7.util.DiffUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.steve.MVVM.R;
import com.steve.MVVM.model.Node;

public class NodeAdapter extends ListAdapter<Node, NodeAdapter.NodeHolder> {
    private OnItemClickListener listener;

    public NodeAdapter() {
        super(DIFF_CALLBACK);
    }

    // make recycler view move smoothly after middle item removed
    private static final DiffUtil.ItemCallback<Node> DIFF_CALLBACK =
            new DiffUtil.ItemCallback<Node>() {
                @Override
                public boolean areItemsTheSame(@NonNull Node oldItem, @NonNull Node newItem) {
                    return oldItem.getId() == newItem.getId();
                }

                @Override
                public boolean areContentsTheSame(@NonNull Node oldItem, @NonNull Node newItem) {
                    return oldItem.getName().equals(newItem.getName()) &&
                            oldItem.getEmail().equals(newItem.getEmail());
                }
            };

    @NonNull
    @Override
    public NodeHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.node_item, parent, false);

        return new NodeHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull NodeHolder nodeHolder, int i) {
        Node currentNode = getItem(i);
        nodeHolder.tvName.setText(currentNode.getName());
        nodeHolder.tvEmail.setText(currentNode.getEmail());
    }

    public Node getNodeAt(int position) {
        return getItem(position);
    }

    //===============================

    class NodeHolder extends RecyclerView.ViewHolder {
        private TextView tvName;
        private TextView tvEmail;

        public NodeHolder(@NonNull View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.tv_name);
            tvEmail = itemView.findViewById(R.id.tv_email);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position = getAdapterPosition();
                    if (listener != null && position != RecyclerView.NO_POSITION)
                        listener.onItemClick(getItem(position));
                }
            });
        }
    }

    // callback function
    public interface OnItemClickListener {
        void onItemClick(Node node);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }
}
