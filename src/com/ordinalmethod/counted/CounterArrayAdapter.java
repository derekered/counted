package com.ordinalmethod.counted;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.TextView;

public class CounterArrayAdapter extends ArrayAdapter<Counter> {
	private final Context context;
	private List<Counter> counters;
	private CounterArrayAdapterCallback callback;

	public CounterArrayAdapter(Context context, List<Counter> counters) {
		super(context, R.layout.list_item, counters);
		this.context = context;
		this.counters = counters;
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		View rowView = convertView;

		if (rowView == null) {
			LayoutInflater inflater = (LayoutInflater) context
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

			// Inflate list_item layout
			rowView = inflater.inflate(R.layout.list_item, parent, false);
			ViewHolder viewHolder = new ViewHolder();

			// Instantiate row layout elements
			viewHolder.flItemColor = (FrameLayout) rowView.findViewById(R.id.frameLayoutListItemColor);
			viewHolder.tvName = (TextView) rowView
					.findViewById(R.id.textViewEditCounterName);
			viewHolder.tvCount = (TextView) rowView
					.findViewById(R.id.textViewCount);
			viewHolder.btAdd = (Button) rowView.findViewById(R.id.buttonAdd);

			// Set holder
			rowView.setTag(viewHolder);
		}

		// Get holder
		ViewHolder holder = (ViewHolder) rowView.getTag();

		// Set row layout elements
		holder.tvName.setText(counters.get(position).getName());
		holder.tvCount.setText(Integer.toString(counters.get(position)
				.getCount()));
		holder.flItemColor.setBackgroundColor(counters.get(position).getColor());
		holder.btAdd.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {

				if (callback != null) {

					callback.addClicked(position);
				}
			}
		});

		return rowView;


	}


	public void setCallback(CounterArrayAdapterCallback callback) {

		this.callback = callback;
	}

	public interface CounterArrayAdapterCallback {

		public void addClicked(int position);
	}

	static class ViewHolder {
		FrameLayout flItemColor;
		TextView tvName;
		TextView tvCount;
		Button btAdd;
	}

}
