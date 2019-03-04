package com.arvoron.scherpel.arvoron;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

import static com.google.firebase.firestore.DocumentChange.Type.ADDED;


/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment {

    private RecyclerView blog_list_view;
    private List<BlogPost> blog_list;
    private FirebaseFirestore firebaseFirestore;
    private TreeRecyclerAdapter blogRecyclerAdapter;

    public HomeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        blog_list = new ArrayList<>();
        blog_list_view = view.findViewById(R.id.tree_list_view);
        blogRecyclerAdapter = new TreeRecyclerAdapter(blog_list);
        blog_list_view.setLayoutManager(new LinearLayoutManager(container.getContext()));
        blog_list_view.setAdapter(blogRecyclerAdapter);
        firebaseFirestore = FirebaseFirestore.getInstance();
        firebaseFirestore.collection("Trees").addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(QuerySnapshot documentSnapshots, FirebaseFirestoreException e) {
                for (DocumentChange doc :  documentSnapshots.getDocumentChanges()){
                    if(doc.getType() == DocumentChange.Type.ADDED){
                        BlogPost blogPost = doc.getDocument().toObject(BlogPost.class);
                        blog_list.add(blogPost);
                        blogRecyclerAdapter.notifyDataSetChanged();
                    }
                }
            }
        });
        return view;
    }

}
