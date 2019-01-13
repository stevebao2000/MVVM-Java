package com.steve.MVVM.ui;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.steve.MVVM.R;
import com.steve.MVVM.model.Node;
import com.steve.MVVM.viewmodel.NodeViewModel;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    public static final int ADD_NODE_REQUEST = 11001;
    public static final int EDIT_NODE_REQUEST = 11002;
    private NodeViewModel nodeViewModel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FloatingActionButton addNodeButton = findViewById(R.id.btn_add);
        addNodeButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getBaseContext(), AddEditNodeActivity.class);
                startActivityForResult(intent, ADD_NODE_REQUEST);
            }
        });
        RecyclerView recyclerView = findViewById(R.id.recycle_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);

        final NodeAdapter adapter = new NodeAdapter();
        recyclerView.setAdapter(adapter);

        nodeViewModel = ViewModelProviders.of(this).get(NodeViewModel.class);
        nodeViewModel.getAllNodes().observe(this, new Observer<List<Node>>() {
            @Override
            public void onChanged(@Nullable List<Node> nodes) {
                adapter.submitList(nodes);
            }
        });

        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0,
                ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {

            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder viewHolder1) {
                return false;
            }

            // swipe left or right to remove the item.
            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
                nodeViewModel.delete(adapter.getNodeAt(viewHolder.getAdapterPosition()));
            }
        }).attachToRecyclerView(recyclerView);

        // click to edit the item.
        adapter.setOnItemClickListener(new NodeAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Node node) {
                Intent intent = new Intent(getBaseContext(), AddEditNodeActivity.class);
                intent.putExtra(AddEditNodeActivity.EXTRA_NAME, node.getName());
                intent.putExtra(AddEditNodeActivity.EXTRA_EMAIL, node.getEmail());
                intent.putExtra(AddEditNodeActivity.EXTRA_ID, node.getId());
                startActivityForResult(intent, EDIT_NODE_REQUEST);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == ADD_NODE_REQUEST && resultCode == RESULT_OK) {
            String title = data.getStringExtra(AddEditNodeActivity.EXTRA_NAME);
            String desc = data.getStringExtra(AddEditNodeActivity.EXTRA_EMAIL);
            Node node = new Node(title, desc);
            nodeViewModel.insert(node);
        } else if (requestCode == EDIT_NODE_REQUEST && resultCode == RESULT_OK) {
            int id = data.getIntExtra(AddEditNodeActivity.EXTRA_ID, -1);
            if (id == -1) {
                Toast.makeText(getBaseContext(), "Node data is mal-formated", Toast.LENGTH_SHORT).show();
            }
            String title = data.getStringExtra(AddEditNodeActivity.EXTRA_NAME);
            String desc = data.getStringExtra(AddEditNodeActivity.EXTRA_EMAIL);
            Node node = new Node(title, desc);
            node.setId(id);
            nodeViewModel.update(node);
        }
    }
}
