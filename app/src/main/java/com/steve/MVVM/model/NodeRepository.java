package com.steve.MVVM.model;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.os.AsyncTask;

import java.util.List;

public class NodeRepository {

    private NodeDao noteDao;
    private LiveData<List<Node>> allNodes;

    public NodeRepository(Application application) {
        NodeDatabase database = NodeDatabase.getInstance(application);
        noteDao = database.noteDao();
        allNodes = noteDao.getAllNodes();
    }

    public void insert(Node node) {
        new InsertNodeAsyncTask(noteDao).execute(node);
    }

    public void update(Node node) {
        new UpdateNodeAsyncTask(noteDao).execute(node);
    }

    public void delete(Node node) {
        new DeleteNodeAsyncTask(noteDao).execute(node);
    }

    public void deleteAllNodes() {
        new DeleteAllNodesAsyncTask(noteDao).execute();
    }

    public LiveData<List<Node>> getAllNodes() {
        return allNodes;
    }

    private static class InsertNodeAsyncTask extends AsyncTask<Node, Void, Void> {
        private NodeDao noteDao;

        private InsertNodeAsyncTask(NodeDao noteDao) {
            this.noteDao = noteDao;
        }
        @Override
        protected Void doInBackground(Node... nodes) {
            noteDao.insert(nodes[0]);
            return null;
        }
    }

    private static class UpdateNodeAsyncTask extends AsyncTask<Node, Void, Void> {
        private NodeDao noteDao;

        private UpdateNodeAsyncTask(NodeDao noteDao) {
            this.noteDao = noteDao;
        }
        @Override
        protected Void doInBackground(Node... nodes) {
            noteDao.update(nodes[0]);
            return null;
        }
    }

    private static class DeleteNodeAsyncTask extends AsyncTask<Node, Void, Void> {
        private NodeDao noteDao;

        private DeleteNodeAsyncTask(NodeDao noteDao) {
            this.noteDao = noteDao;
        }
        @Override
        protected Void doInBackground(Node... nodes) {
            noteDao.delete(nodes[0]);
            return null;
        }
    }

    private static class DeleteAllNodesAsyncTask extends AsyncTask<Void, Void, Void> {
        private NodeDao noteDao;

        private DeleteAllNodesAsyncTask(NodeDao noteDao) {
            this.noteDao = noteDao;
        }
        @Override
        protected Void doInBackground(Void... voids) {
            noteDao.deleteAllNodes();
            return null;
        }
    }
}
