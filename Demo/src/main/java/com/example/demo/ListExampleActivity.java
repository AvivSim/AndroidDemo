package com.example.demo;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.ListActivity;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

public class ListExampleActivity extends ListActivity {

    private PeopleAdapter peopleAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        createAdapter();
        setListAdapter(peopleAdapter);

        // Show the Up button in the action bar.
        setupActionBar();
    }

    private void createAdapter() {
        peopleAdapter = new PeopleAdapter(this, R.layout.list_item);
        for (int i = 0; i < 1000; i++) {
            Person person = new Person("Name " + i, "Home Address " + i);
            peopleAdapter.add(person);
        }
    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    private void setupActionBar() {
        getActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                // This ID represents the Home or Up button. In the case of this
                // activity, the Up button is shown. Use NavUtils to allow users
                // to navigate up one level in the application structure. For
                // more details, see the Navigation pattern on Android Design:
                //
                // http://developer.android.com/design/patterns/navigation.html#up-vs-back
                //
                NavUtils.navigateUpFromSameTask(this);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    @Override
    protected void onListItemClick(ListView l, final View v, final int position, long id) {
        v.animate().setDuration(500).alpha(0).withEndAction(new Runnable() {
            @Override
            public void run() {
                Person person = peopleAdapter.getItem(position);
                peopleAdapter.remove(person);
                peopleAdapter.notifyDataSetChanged();
                v.setAlpha(1);
            }
        });
    }

    private static class PeopleAdapter extends ArrayAdapter<Person> {

        private LayoutInflater inflater;

        public PeopleAdapter(Activity context, int textViewResourceId) {
            super(context, textViewResourceId);
            inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }

        static class ViewHolder {
            TextView name;
            TextView address;
        }

        @TargetApi(Build.VERSION_CODES.ICE_CREAM_SANDWICH)
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder holder;
            if (convertView == null) {
                convertView = inflater.inflate(R.layout.list_item, parent, false);
                holder = new ViewHolder();
                holder.name = (TextView) convertView.findViewById(R.id.name);
                holder.address = (TextView) convertView.findViewById(R.id.address);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }

            Person person = getItem(position);
            holder.name.setText(person.getName());
            holder.address.setText(person.getAddress());

            hideView(convertView);
            convertView.animate().alpha(1).setDuration(300).start();
            return convertView;
        }

        @TargetApi(Build.VERSION_CODES.HONEYCOMB)
        private void hideView(View view) {
            ObjectAnimator animator = ObjectAnimator.ofFloat(view, "alpha", 0);
            AnimatorSet set = new AnimatorSet();
            set.play(animator);
            set.setDuration(0);
            set.start();
        }
    }
}
