package com.ordinalmethod.counted;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;

import android.R.color;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Vibrator;
import android.support.v4.app.NavUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;
import android.widget.RelativeLayout.LayoutParams;

public class CounterDetail extends Activity {

	private long id;
	private Button btCount;
	private Button btDecrement;
	private Counter counter;
	private RelativeLayout rlActivityDetail;

	private CounterDataSource counterDataSource;

	// Ad variables
	private AdView adView;
	private static final String AD_UNIT_ID = "a1535ab61fdd9fb";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_counter_detail);
		// Setup action bar
		setupActionBar();

		// Get the counter id
		Intent i = getIntent();
		id = i.getLongExtra("COUNTERID", 0);

		// Instantiate data source and get the counter
		counterDataSource = new CounterDataSource(this);
		counterDataSource.open();

		counter = counterDataSource.getCounterByID(id);

		// Instantiate activity elements and set listeners/values

		btCount = (Button) findViewById(R.id.buttonCount);
		btDecrement = (Button) findViewById(R.id.buttonDecrement);
		rlActivityDetail = (RelativeLayout) findViewById(R.id.relativeLayoutActivityDetail);

		btCount.setOnClickListener(OnClickListener);
		btDecrement.setOnClickListener(OnClickListener);

		btCount.setText(String.valueOf(counter.getCount()));
		rlActivityDetail.setBackgroundColor(counter.getColor());
		btDecrement.setTextColor(counter.getColor());

		setTitle(counter.getName());

		// Create an ad.
		adView = new AdView(this);
		adView.setAdSize(AdSize.BANNER);
		adView.setAdUnitId(AD_UNIT_ID);

		// Add the AdView to the view hierarchy. The view will have no size
		// until the ad is loaded.
		RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(
				LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
		params.addRule(RelativeLayout.ALIGN_PARENT_TOP, RelativeLayout.TRUE);
		params.addRule(RelativeLayout.CENTER_HORIZONTAL);

		rlActivityDetail.addView(adView, params);

		// Create an ad request. Check logcat output for the hashed device ID to
		// get test ads on a physical device.
		AdRequest adRequest = new AdRequest.Builder()
				.addTestDevice(AdRequest.DEVICE_ID_EMULATOR)
				.addTestDevice("FA2CTS504394").build();

		// Start loading the ad in the background.
		adView.loadAd(adRequest);
	}

	/**
	 * Set up the {@link android.app.ActionBar}.
	 */
	private void setupActionBar() {

		getActionBar().setDisplayHomeAsUpEnabled(true);

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.counter_detail, menu);
		return true;
	}

//	This method handles the action bar options menu
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
		case R.id.action_edit:
			// Start the edit activity when the edit button in clicked
			Intent i = new Intent(this, EditCounter.class);
			i.putExtra("COUNTERID", counter.getId());
			i.putExtra("ISNEW", false);
			startActivity(i);
			return true;
		case R.id.action_delete:
			// Show the delete dialog when the delete button is clicked
			Builder builder = new AlertDialog.Builder(this);
			builder.setMessage(R.string.delete_dialog_message);
			builder.setCancelable(true);
			builder.setPositiveButton(R.string.delete_dialog_positive_action,
					new DeleteOnClickListener());
			builder.setNegativeButton(R.string.delete_dialog_negative_action,
					new CancelOnClickListener());
			AlertDialog dialog = builder.create();
			dialog.show();
			return true;

		default:
			break;
		}
		return super.onOptionsItemSelected(item);
	}

	// Global On click listener for all views
	final OnClickListener OnClickListener = new OnClickListener() {
		public void onClick(final View v) {
			// Create vibrator
			Vibrator vib = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
			switch (v.getId()) {

			case R.id.buttonCount:

				// Increment the counter
				counter = counterDataSource.incrementCounter(counter, 1);
				btCount.setText(String.valueOf(counter.getCount()));

				// Vibrate
				vib.vibrate(50);
				break;

			case R.id.buttonDecrement:

				// Decrement the counter
				counter = counterDataSource.incrementCounter(counter, -1);
				btCount.setText(String.valueOf(counter.getCount()));

				// Vibrate
				vib.vibrate(200);
				break;
			}
		}
	};

	private final class CancelOnClickListener implements
			DialogInterface.OnClickListener {
		public void onClick(DialogInterface dialog, int which) {
			dialog.dismiss();
		}
	}

	private final class DeleteOnClickListener implements
			DialogInterface.OnClickListener {
		public void onClick(DialogInterface dialog, int which) {
			// Delete counter and navigate to listview
			counterDataSource.deleteCounter(counter);
			NavUtils.navigateUpFromSameTask(CounterDetail.this);

		}
	}

}
