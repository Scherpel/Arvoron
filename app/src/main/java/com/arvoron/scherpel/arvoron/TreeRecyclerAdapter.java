package com.arvoron.scherpel.arvoron;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.Date;
import java.util.List;

public class TreeRecyclerAdapter extends RecyclerView.Adapter<TreeRecyclerAdapter.ViewHolder> {

    public List<BlogPost> blog_list;
    public Context context;
    public TreeRecyclerAdapter(List<BlogPost> blog_list){
        this.blog_list = blog_list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int i) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.blog_list_item, parent, false);
        context = parent.getContext();
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {

        String image_url = blog_list.get(i).getImage_url();
        viewHolder.setBlogImage(image_url);

        String desc_data = blog_list.get(i).getDesc();
        viewHolder.setDescText(desc_data);

        String name_data = blog_list.get(i).getTree_name();
        viewHolder.setNameText(name_data);

        String family_data = blog_list.get(i).getTree_family();
        viewHolder.setFamilyText(family_data);

        String location_data = blog_list.get(i).getTree_location();
        viewHolder.setLocationText(location_data);

        String user_id = blog_list.get(i).getUser_id();
        long milliseconds = blog_list.get(i).getTimestamp().getTime();
        String dateString = DateFormat.format("MM/dd/yyyy", new Date(milliseconds)).toString();
        viewHolder.setTime(dateString);

    }

    @Override
    public int getItemCount() {
        return blog_list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        private View mView;
        private TextView descView;
        private TextView nameView;
        private TextView familyView;
        private TextView locationView;
        private TextView blogDate;
        private ImageView blogImageView;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            mView = itemView;
        }

        public void setBlogImage(String downloadUri){
            blogImageView = mView.findViewById(R.id.post_image);
            Glide.with(context).load(downloadUri).into(blogImageView);
        }


        public void setDescText(String descText){
            descView = mView.findViewById(R.id.post_tree_desc);
            descView.setText(descText);
        }
        public void setNameText(String nameText){
            nameView = mView.findViewById(R.id.post_tree_name);
            nameView.setText(nameText);
        }
        public void setFamilyText(String familyText){
            familyView = mView.findViewById(R.id.post_tree_family);
            familyView.setText(familyText);
        }
        public void setLocationText(String locationText){
            locationView = mView.findViewById(R.id.post_tree_location);
            locationView.setText(locationText);
        }

        public void setTime(String date){
            blogDate = mView.findViewById(R.id.post_tree_date);
            blogDate.setText(date);
        }

    }



}
