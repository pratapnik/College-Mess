package com.first.myapplication.collegemess;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

public class FeedbacksList extends ArrayAdapter<feed> {

    private Activity context;
    private List<feed> feedbacksList;

    public FeedbacksList(Activity context, List<feed> feedbacksList) {

        super(context, R.layout.list_layout, feedbacksList);
        this.context = context;
        this.feedbacksList = feedbacksList;
    }


    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();

        View listViewItem = inflater.inflate(R.layout.list_layout, null, true);

        TextView textViewGenre = listViewItem.findViewById(R.id.textViewGenre);
        TextView textViewMess = listViewItem.findViewById(R.id.textViewMess);
        TextView textViewFeedback = listViewItem.findViewById(R.id.textViewFeedback);
        TextView textViewEmail = listViewItem.findViewById(R.id.textViewEmail);

        feed feed = feedbacksList.get(position);

        textViewGenre.setText(feed.getFeedbackGenre());
        textViewMess.setText("Mess - " + feed.getMessType());
        textViewEmail.setText(feed.getEmailID());
        textViewFeedback.setText(feed.getActualFeedback());

        return listViewItem;


    }
}

