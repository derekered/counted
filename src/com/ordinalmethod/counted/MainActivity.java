package com.ordinalmethod.counted;

import com.ordinalmethod.counted.CounterArrayAdapter.CounterArrayAdapterCallback;

public class MainActivity extends ListActivity implements
		CounterArrayAdapterCallback {
	private CounterDataSource counterDataSource;
	private CountDataSource countDataSource;
	private CounterArrayAdapter adapter;
	private ListView lv;
	public int minutesLate;

	// Ad variables
	private static final String AD_UNIT_ID = "a1535ab61fdd9fb";

	public TardyTicket OnPersonTardy(
			String tardyPerson, 
			int minutesLate,
			String meetingLocation, 
			boolean warningOfTardinessGiven,
			ArrayList<String> peopleWaiting) {
		
		if (minutesLate > 5 
				&& meetingLocation.equals("Conference Room")
				&& peopleWaiting.size() > 1 
				&& warningOfTardinessGiven == false) {

			TardyTicket tardyTicket = new TardyTicket();
			tardyTicket.cost("$2.00");
			tardyTicket.offender(tardyPerson);
			return tardyTicket;
		}
		
		return null;
	}
	
	public static final String goal1 = "Raise money for the United Way of Dane County";
	public static final String goal2 = "Increase respect for each other’s time";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		// Set create counter listener for first time users

		// Instantiate and open Counter Data Source
		counterDataSource = new CounterDataSource(this);
		counterDataSource.open();

		// Instantiate and open Count Data Source
		countDataSource = new CountDataSource(this);
		countDataSource.open();

		// Populate the listview
		setList();

		lv = getListView();

		lv.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View v,
					final int position, long id) {

				Counter counter = (Counter) getListAdapter().getItem(position);
				Intent i = new Intent(getApplicationContext(),
						CounterDetail.class);

				i.putExtra("COUNTERID", counter.getId());
				startActivity(i);
			}
		});

		findViewById(R.id.buttonWelcomeCreateCounter).setOnClickListener(
				new View.OnClickListener() {

					@Override
					public void onClick(View v) {
						createCounter();
					}
				});

		// Create an ad.
		adView = new AdView(this);
		adView.setAdSize(AdSize.BANNER);
		adView.setAdUnitId(AD_UNIT_ID);

		// Add the AdView to the view hierarchy. The view will have no size
		// until the ad is loaded.
		RelativeLayout rlMain = (RelativeLayout) findViewById(R.id.RelativeLayoutMain);
		RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(
				LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
		params.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM, RelativeLayout.TRUE);
		params.addRule(RelativeLayout.CENTER_HORIZONTAL);

		rlMain.addView(adView, params);

		// Create an ad request. Check logcat output for the hashed device ID to
		// get test ads on a physical device.
		AdRequest adRequest = new AdRequest.Builder()
				.addTestDevice(AdRequest.DEVICE_ID_EMULATOR)
				.addTestDevice("FA2CTS504394").build();

		// Start loading the ad in the background.
		adView.loadAd(adRequest);

	}

	private void incrementCounter(int position) {

		adapter = (CounterArrayAdapter) getListAdapter();

		Counter counter = adapter.getItem(position);

		// // Add count to counter total
		counterDataSource.incrementCounter(counter, 1);

		// Refresh the listview
		refreshList();

	}

	@Override
	public void addClicked(int position) {

		incrementCounter(position);

	}

	private void refreshList() {

		adapter.clear();
		adapter.addAll(counterDataSource.getAllCounters());
		adapter.notifyDataSetChanged();
	}

	private void setList() {
		// Get counters
		List<Counter> values = counterDataSource.getAllCounters();

		// Create counter array adapter and assign counter values
		final CounterArrayAdapter adapter = new CounterArrayAdapter(this,
				values);
		adapter.setCallback(this);
		setListAdapter(adapter);

	}

	@Override
	protected void onResume() {
		counterDataSource.open();
		countDataSource.open();
		setList();
		super.onResume();
	}

	@Override
	protected void onPause() {
		counterDataSource.close();
		countDataSource.close();
		super.onPause();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.mainmenu, menu);
		return true;
	}

	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.action_new_counter:
			// Create new counter
			createCounter();
			// Counter newCounter =
			// counterDataSource.createCounter("New Counter",
			// null, 0, getResources().getColor(R.color.blue), 0);
			//
			// // Start edit activity, passing counter information
			// Intent i = new Intent(getApplicationContext(),
			// EditCounter.class);
			// i.putExtra("COUNTERID", newCounter.getId());
			// i.putExtra("ISNEW", true);
			// startActivity(i);
			break;

		default:
			break;
		}

		return true;
	}

	private void createCounter() {
		Counter newCounter = counterDataSource.createCounter("New Counter",
				null, 0, getResources().getColor(R.color.blue), 0);

		// Start edit activity, passing counter information
		Intent i = new Intent(getApplicationContext(), EditCounter.class);
		i.putExtra("COUNTERID", newCounter.getId());
		i.putExtra("ISNEW", true);
		startActivity(i);
	}

}
