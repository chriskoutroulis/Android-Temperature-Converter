package gr.teipir.eais.temperatureconverter;

import android.app.ListActivity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;


public class SettingsActivity extends ListActivity {
	private ArrayAdapter<String> myAdapter;
	private String[] myArray;
	private ListView list;
	private static final String IMAGE_KEY = "imageKey";
	
	private static final String PREF_NAME = "myPrefs";
	SharedPreferences myPrefs;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		
		myArray = getResources().getStringArray(R.array.myStringArray);		
		myAdapter = new ArrayAdapter<String>(this, 
				android.R.layout.simple_list_item_single_choice, myArray);
		setListAdapter(myAdapter);
		
		list = getListView();
		list.setChoiceMode(ListView.CHOICE_MODE_SINGLE);		
		
		//Set checked item according to the current theme
		myPrefs = getSharedPreferences(PREF_NAME, MODE_PRIVATE);
		if (myPrefs.contains(IMAGE_KEY)) {
			int backgroundID = myPrefs.getInt(IMAGE_KEY, R.drawable.papertexture);
			if (backgroundID == R.drawable.papertexture) {
				list.setItemChecked(0, true);
			} else if (backgroundID == R.drawable.greytexture) {
				list.setItemChecked(1, true);
			}
		}
		
		list.setOnItemClickListener(new AdapterView.OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub
				
				Intent returnIntent  = new Intent();
				if (position == 0) {
					returnIntent.putExtra(IMAGE_KEY, R.drawable.papertexture);
					setResult(RESULT_OK, returnIntent);
					finish();
				} else if (position == 1) {
					returnIntent.putExtra(IMAGE_KEY, R.drawable.greytexture);
					setResult(RESULT_OK, returnIntent);
					finish();
				}
				
			}
			
		});
		
	
	}
	
	

}
