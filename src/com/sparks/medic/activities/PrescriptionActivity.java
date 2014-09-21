package com.sparks.medic.activities;

import org.json.JSONArray;
import org.json.JSONObject;

import com.sparks.medic.R;
import com.sparks.medic.utils.AppConstants;
import com.sparks.medic.utils.ServiceHandler;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextThemeWrapper;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

public class PrescriptionActivity extends Activity {

	ProgressDialog pDialog;
	TextView doctorName, patientName;
	EditText info, tabletName, dose, countMorning, countNoon, countNight;
	Button sendButton;
	
	String[] drug_list, doctor_list;
	ArrayAdapter<String> adapter;
	AutoCompleteTextView textView;

	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_prescription);

		doctorName = (TextView) findViewById(R.id.doctor_name);
		patientName = (TextView) findViewById(R.id.patient_name);
		sendButton = (Button) findViewById(R.id.send);
		info = (EditText) findViewById(R.id.prescription);
		
		dose = (EditText) findViewById(R.id.strength);
		countMorning = (EditText) findViewById(R.id.morning);
		countNoon = (EditText) findViewById(R.id.afternoon);
		countNight = (EditText) findViewById(R.id.evening);

		sendButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				new SendData().execute("1", "2", info.getText().toString(),
						tabletName.getText().toString(), dose.getText()
								.toString(), countMorning.getText().toString(),
						countNoon.getText().toString(), countNight.getText()
								.toString());

			}
		});
		
		
		drug_list = getResources().getStringArray(R.array.drug_list);

		adapter = new ArrayAdapter<String>(getApplicationContext(),
				android.R.layout.simple_dropdown_item_1line, drug_list);
		

		textView = (AutoCompleteTextView) findViewById(R.id.tablet_name);
		textView.setThreshold(1);
		textView.setAdapter(adapter);

		textView.setOnItemClickListener(new OnItemClickListener() {
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				CharSequence drug = (CharSequence) arg0.getItemAtPosition(arg2);
				textView.setText(drug);
				
			}
		});
	}

	/**
	 * Async task class to get json by making HTTP call
	 */
	private class SendData extends AsyncTask<String, Void, Void> {

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			// Showing progress dialog
			pDialog = new ProgressDialog(PrescriptionActivity.this);
			pDialog.setMessage("Uploading... Please wait...");
			pDialog.setCancelable(false);
			pDialog.show();

		}

		@Override
		protected Void doInBackground(String... params) {
			// Creating service handler class instance
			ServiceHandler sh = new ServiceHandler();

			try {
				// Making a request to url and getting response
				String jsonStr = sh.makeServiceCall(
						AppConstants.SERVER_BASE_URL + "prescription.htm?"
								+ "patientName=" + Integer.parseInt(params[0])
								+ "&doctor.pkey=" + Integer.parseInt(params[1])
								+ "&info=" + params[2] + "&medicine="
								+ params[3] + "&morningPills="
								+ Integer.parseInt(params[5])
								+ "&afternoonPills="
								+ Integer.parseInt(params[6]) + "&nightPills="
								+ Integer.parseInt(params[7]) + "&dose="
								+ params[4] + "&patientAddress=bangalore",
						ServiceHandler.POST);

				Log.d("Response: ", "> " + jsonStr);

				// if (jsonStr != null) {
				// try {
				// JSONObject jsonObj = new JSONObject(jsonStr);
				// JSONArray result = jsonObj.getJSONArray("Result");
				// myRowId = result.getJSONObject(0).getString("RowId");
				// Log.d("RowId", myRowId);
				// } catch (JSONException e) {
				// e.printStackTrace();
				// }
				// } else {
				// Log.e("ServiceHandler",
				// "Couldn't get any data from the url");
				// }
			} catch (Exception e) {
				try {
					// Dismiss the progress dialog
					if (pDialog.isShowing())
						pDialog.dismiss();
				} catch (Exception ex) {
				}
			}

			return null;
		}

		@Override
		protected void onPostExecute(Void result) {
			super.onPostExecute(result);
			try {
				// Dismiss the progress dialog
				if (pDialog.isShowing())
					pDialog.dismiss();
			} catch (Exception e) {
			}

		}

	}

	public void showDialog(View v) {
		ContextThemeWrapper ctw = new ContextThemeWrapper(this,
				android.R.style.Theme_Holo_Light_Dialog);
		final CharSequence[] items = {
				"Apollo Pharmacy, Koramangala 6th block",
				"Sriram Medicals, Ashok nagar",
				"Medplus Pharmacy, Koramangala 4th block" };
		AlertDialog.Builder builder = new AlertDialog.Builder(ctw);
		builder.setTitle("Choose one medical shop");
		builder.setPositiveButton("ok", new DialogInterface.OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {
			}
		});

		builder.setSingleChoiceItems(items, -1,
				new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {

						Toast.makeText(getApplicationContext(),
								"Your order has been sent.", Toast.LENGTH_LONG)
								.show();

					}
				});
		builder.show();

	}

}
