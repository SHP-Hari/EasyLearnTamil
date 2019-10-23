package com.sliit.easylearner;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

public class WordListAdapter extends ArrayAdapter<WordListItem> {
    Context context;

    public WordListAdapter(Context context, int resource, List<WordListItem> items) {
        super(context, resource, items);
        this.context = context;
    }

    private class ViewHolder {
        TextView txtTamil;
        TextView txtSinhala;
        WebView webView;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        WordListItem wordListItem = getItem(position);

        LayoutInflater mInflater = (LayoutInflater) context
                .getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.word_list_layout, null);
            holder = new ViewHolder();
            holder.txtTamil = (TextView) convertView.findViewById(R.id.tamilWord);
            holder.txtSinhala = (TextView) convertView.findViewById(R.id.sinhalaWord);
            holder.webView = convertView.findViewById(R.id.webView);
            convertView.setTag(holder);
        } else
            holder = (ViewHolder) convertView.getTag();

        holder.txtTamil.setText(wordListItem.getSinhalaWord());
        holder.txtSinhala.setText(wordListItem.getTamilWord());
        holder.webView.getSettings().setJavaScriptEnabled(true);
        holder.webView.loadUrl("http://zacseed.com/speakTamil.php?tamil="+wordListItem.getTamilWord());

        return convertView;
    }
}
