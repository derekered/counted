package com.ordinalmethod.counted;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.fourmob.colorpicker.ColorPickerDialog;
import com.fourmob.colorpicker.ColorPickerSwatch.OnColorSelectedListener;

public class EditCounter extends FragmentActivity {

	private long id;
	private Boolean isNew;
	private CounterDataSource datasource;
	private Counter counter;
	private EditText etName;
	private Boolean colorDifferent = false;
	private FrameLayout flColor;
	private static final int colorDialogColumns = 3;
	private static final int colorDialogSize = 80;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_edit_counter);
		// Show the Up button in the action bar.
		setupActionBar();

		// Get intent
		Intent i = getIntent();
		id = i.getLongExtra("COUNTERID", 0);
		isNew = i.getBooleanExtra("ISNEW", false);

		// Setup datasource and get counter
		datasource = new CounterDataSource(this);
		datasource.open();

		counter = datasource.getCounterByID(id);

		// Set title
		setTitle(counter.getName());

		etName = (EditText) findViewById(R.id.editTextCounterName);
		flColor = (FrameLayout) findViewById(R.id.FrameLayoutPickColor);

		// If an existing counter, set the counter name in the Name field
		if (!isNew) {
			etName.setText(counter.getName());
		}

		flColor.setBackgroundColor(counter.getColor());

		findViewById(R.id.buttonSave).setOnClickListener(
				new View.OnClickListener() {

					@Override
					public void onClick(View v) {
						save();
						Intent i = new Intent(getApplicationContext(),
								CounterDetail.class);
						i.putExtra("COUNTERID", counter.getId());
						startActivity(i);

					}
				});

		// COLOR PICKER LOGIC
		int[] colors = { getResources().getColor(R.color.orange_red),
				getResources().getColor(R.color.golden),
				getResources().getColor(R.color.yellow_green),
				getResources().getColor(R.color.green),
				getResources().getColor(R.color.blue),
				getResources().getColor(R.color.purple),
				getResources().getColor(R.color.magenta),
				getResources().getColor(R.color.gray),
				getResources().getColor(R.color.black) };

		final ColorPickerDialog colorPickerDialog = new ColorPickerDialog();
		colorPickerDialog.initialize(R.string.color_picker_default_title,
				colors, 0, colorDialogColumns, colorDialogSize);
		colorPickerDialog
				.setOnColorSelectedListener(new OnColorSelectedListener() {

					@Override
					public void onColorSelected(int color) {

						if (color == counter.getColor()) {
							colorDifferent = false;
						} else {
							flColor.setBackgroundColor(color);
							counter.setColor(color);
							colorDifferent = true;
						}

					}
				});

		findViewById(R.id.buttonPickColor).setOnClickListener(
				new View.OnClickListener() {

					@Override
					public void onClick(View v) {
						colorPickerDialog.show(getSupportFragmentManager(),
								"Something");

					}
				});

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
		getMenuInflater().inflate(R.menu.edit_counter, menu);
		return true;
	}

	private void save() {
		// Is there are changes, save
		if (isNew || colorDifferent
				|| !counter.name.equals(etName.getText().toString())) {
			counter.setName(etName.getText().toString());
			datasource.updateCounter(counter);
			// Show save confirmation message
			Toast.makeText(this,
					getString(R.string.activity_edit_save_message),
					Toast.LENGTH_SHORT).show();
		}
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home:

			save();
			Intent i = new Intent(getApplicationContext(), CounterDetail.class);
			i.putExtra("COUNTERID", counter.getId());
			startActivity(i);
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	@Override
	public void onBackPressed() {
		// your code.
		save();
		Intent i = new Intent(getApplicationContext(), CounterDetail.class);
		i.putExtra("COUNTERID", counter.getId());
		startActivity(i);
	}

}
