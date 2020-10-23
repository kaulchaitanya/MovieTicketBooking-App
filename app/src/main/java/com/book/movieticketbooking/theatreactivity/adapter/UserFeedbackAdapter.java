package com.book.movieticketbooking.theatreactivity.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.book.movieticketbooking.useractivity.model.Userfeedback;
import com.book.movieticketbooking.R;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;

public class UserFeedbackAdapter extends FirebaseRecyclerAdapter<Userfeedback, UserFeedbackAdapter.myViewHolder> {


    /**
     * Initialize a {@link RecyclerView.Adapter} that listens to a Firebase query. See
     * {@link FirebaseRecyclerOptions} for configuration options.
     *
     * @param options
     */
    public UserFeedbackAdapter(@NonNull FirebaseRecyclerOptions<Userfeedback> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull myViewHolder holder, int position, @NonNull Userfeedback model) {
        holder.TextView1.setText(model.getUsername());
        holder.TextView2.setText(model.getRatingBar());
        holder.TextView3.setText(model.getFeedback());
    }

    @NonNull
    @Override
    public myViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.feedback_listview,parent,false);
        return new myViewHolder(view);
    }

    public class myViewHolder extends RecyclerView.ViewHolder{
        TextView TextView1,TextView2,TextView3;
        public myViewHolder(@NonNull View listViewItem) {
            super(listViewItem);
            TextView1 = (TextView)listViewItem.findViewById(R.id.fb_userName);
            TextView2 = (TextView) listViewItem.findViewById(R.id.fb_rating);
            TextView3 =  (TextView)listViewItem.findViewById(R.id.fb_Feedback);

        }
    }
    // TODO: 11-09-2020  @NonNull
    //    @Override
    //    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
    //        LayoutInflater inflater = context.getLayoutInflater();
    //
    //        View listViewItem = inflater.inflate(R.layout.feedback_listview,null,true);
    //
    //        TextView TextView1 = (TextView)listViewItem.findViewById(R.id.fb_userName);
    //        TextView TextView2 = (TextView) listViewItem.findViewById(R.id.fb_rating);
    //        TextView TextView3 =  (TextView)listViewItem.findViewById(R.id.fb_Feedback);
    //
    //        User_Feedback user_feedback = userFeedback.get(position);
    //
    //        TextView1.setText(user_feedback.getUsername());
    //        TextView2.setText(user_feedback.getRatingBar());
    //        TextView3.setText(user_feedback.getFeedback());
    //
    //        return listViewItem;
    //    }
}
