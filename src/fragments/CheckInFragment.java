package fragments;

import com.sparks.medic.R;
import com.sparks.medic.activities.PrescriptionActivity;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.preference.PreferenceActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;

public class CheckInFragment extends Fragment {

	String[] patient_list, doctor_list;
	ArrayAdapter<String> adapter,  doctorAdapter;
	AutoCompleteTextView textView, doctorTextView;
	Button goButton;
	CharSequence patient, doctor;

	public CheckInFragment() {
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		View rootView = inflater.inflate(R.layout.fragment_checkin, container,
				false);

		patient_list = getResources().getStringArray(R.array.patient_list);
		
		goButton = (Button) rootView.findViewById(R.id.go);
		goButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(getActivity(), PrescriptionActivity.class);
				intent.putExtra("patient", patient);
				intent.putExtra("doctor", doctor);
				startActivity(intent);
				
			}
		});

		adapter = new ArrayAdapter<String>(getActivity(),
				android.R.layout.simple_dropdown_item_1line, patient_list);
		
		doctor_list = getResources().getStringArray(R.array.doctor_list);

		doctorAdapter = new ArrayAdapter<String>(getActivity(),
				android.R.layout.simple_dropdown_item_1line, doctor_list);
		

		textView = (AutoCompleteTextView) rootView.findViewById(R.id.autotv);
		textView.setThreshold(1);
		textView.setAdapter(adapter);

		textView.setOnItemClickListener(new OnItemClickListener() {
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				patient = (CharSequence) arg0.getItemAtPosition(arg2);
				textView.setText(patient);
				
			}
		});

		
		doctorTextView = (AutoCompleteTextView) rootView.findViewById(R.id.autodoctortv);
		doctorTextView.setThreshold(1);
		doctorTextView.setAdapter(doctorAdapter);

		doctorTextView.setOnItemClickListener(new OnItemClickListener() {
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				doctor = (CharSequence) arg0.getItemAtPosition(arg2);
				doctorTextView.setText(doctor);
			}
		});

		
		
		return rootView;
	}
}
