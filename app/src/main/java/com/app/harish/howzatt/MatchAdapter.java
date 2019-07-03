package com.app.harish.howzatt;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

class MatchAdapter extends RecyclerView.Adapter<MatchAdapter.MatchViewHolder> {

    private List<Matches> matchesList;
    private RecyclerViewClickListener mListener;

    MatchAdapter(List<Matches> matchesList,RecyclerViewClickListener listener) {
        this.matchesList = matchesList;
        mListener = listener;
    }

    @NonNull
    @Override
    public MatchViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.row,viewGroup,false);
        return new MatchViewHolder(view,mListener);
    }

    @Override
    public void onBindViewHolder(@NonNull MatchViewHolder matchViewHolder, int i) {
        Matches match = matchesList.get(i);
        try{
            matchViewHolder.team1.setText(match.getTeam1());
            matchViewHolder.team2.setText(match.getTeam2());
            matchViewHolder.type.setText(match.getType());

        }catch (NullPointerException e){
            e.printStackTrace();
        }

    }

    @Override
    public int getItemCount() {
        return matchesList.size();
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }


    class MatchViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        TextView team1;
        TextView team2;
        TextView type;
        private RecyclerViewClickListener mListener;

        MatchViewHolder(@NonNull View itemView,RecyclerViewClickListener listener) {
            super(itemView);

            team1 = itemView.findViewById(R.id.team1);
            team2 = itemView.findViewById(R.id.team2);
            type  = itemView.findViewById(R.id.type);

            mListener = listener;
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            mListener.onItemClick(getAdapterPosition());
        }

    }

    interface RecyclerViewClickListener{
        void onItemClick(int position);
    }
}
