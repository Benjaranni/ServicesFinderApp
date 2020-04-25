package com.example.androidregisterandlogin;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;


public class MyAdapter  extends   RecyclerView.Adapter<MyAdapter.ViewHolder>{

    private List<ListItem> listItems;

    private Context context;

    //listener for the interface

    private  OnItemClickListener mListener;

    //interface to handle item onclick event
    public  interface  OnItemClickListener{
        void onItemClick(int position);
    }


    public void setOnItemClickListener(OnItemClickListener listener){
        mListener = listener;
    }

    public  MyAdapter(List<ListItem> listItems,Context context){
        this.listItems = listItems;
        this.context = context;
    }





    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item,parent,false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        ListItem listItem = listItems.get(position);

        holder.textViewHead.setText(listItem.getHead());
        holder.textViewDesc.setText(listItem.getDesc());
        holder.textViewLastName.setText(listItem.getLname());
        holder.textViewSurname.setText(listItem.getSname());
        holder.textViewExpertise.setText(listItem.getExp());
        holder.textViewLocation.setText(listItem.getLocate());

        Picasso.with(context)
                .load(listItem.getImageUrl()).fit().centerInside()
                .into(holder.Imageview);


    }

    @Override
    public int getItemCount() {
        return listItems.size();

    }

    public  class ViewHolder extends RecyclerView.ViewHolder{

        public TextView textViewHead;
        public TextView textViewDesc;
        public TextView textViewLastName;
        public TextView textViewSurname;
        public TextView textViewExpertise;
        public TextView textViewLocation;
        public CircleImageView Imageview;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            textViewHead = itemView.findViewById(R.id.textViewHead);
            textViewDesc = itemView.findViewById(R.id.textDesc);
            textViewLastName = itemView.findViewById(R.id.textViewlastname);
            textViewSurname = itemView.findViewById(R.id.textViewsurname);
            textViewExpertise = itemView.findViewById(R.id.textViewexpertise);
            textViewLocation = itemView.findViewById(R.id.textViewlocation);

            Imageview = itemView.findViewById(R.id.imageView);


            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(mListener != null){
                        int position = getAdapterPosition();

                        if(position != RecyclerView.NO_POSITION){
                            mListener.onItemClick(position);
                        }
                    }
                }
            });




        }
    }





}

