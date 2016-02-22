package gr.teipir.eais.temperatureconverter;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity {

	EditText userInput;
	TextView result;
	Button calcButton;
	Button closeButton;
	RadioGroup radioGroup;
	RadioButton radioCelsius;
	RadioButton radioFahren;
	LinearLayout rootLayout;
	ImageView image;
	private static final int REQUEST_CODE = 1;
	private static final String IMAGE_KEY = "imageKey";
	
	private static final String PREF_NAME = "myPrefs";
	SharedPreferences myPrefs;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		userInput = (EditText) findViewById(R.id.editText1);
		result = (TextView) findViewById(R.id.textView1);
		
		//Preserve values during orientation
		if (savedInstanceState != null && savedInstanceState.containsKey("resultText")) {
			result.setText(savedInstanceState.getString("resultText"));
		}
		
		calcButton = (Button) findViewById(R.id.calcButton);
		closeButton = (Button) findViewById(R.id.closeButton);
		radioGroup = (RadioGroup) findViewById(R.id.radioGroup1);
		radioCelsius = (RadioButton) findViewById(R.id.celciusRadio);
		radioFahren = (RadioButton) findViewById(R.id.farenheitRadio);
		rootLayout = (LinearLayout) findViewById(R.id.LinearLayout1);
		image = (ImageView) findViewById(R.id.imageView1);
		
		myPrefs = getSharedPreferences(PREF_NAME, MODE_PRIVATE);
		
		//Theme application during startup or change of orientation
		if (myPrefs.contains(IMAGE_KEY)) {
			int backgroundID = myPrefs.getInt(IMAGE_KEY, R.drawable.papertexture);
			if (backgroundID == R.drawable.greytexture) {
				applyCoolTheme();
			} else if (backgroundID == R.drawable.papertexture) {
				applyWarmTheme();
			}
		}

		calcButton.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (userInput.getText().toString().equals("")) {
					Toast.makeText(MainActivity.this, getString(R.string.invalidValue),
							Toast.LENGTH_SHORT).show();
				} else {
					if (radioGroup.getCheckedRadioButtonId() == R.id.celciusRadio) {
						double userTemp = Double.parseDouble(userInput.getText().toString());
						double fahrenTemp = 32 + 1.8 *(userTemp);
						result.setText(getString(R.string.farenResult) + " " + String.format("%.1f",fahrenTemp));

					} else if (radioGroup.getCheckedRadioButtonId() == R.id.farenheitRadio) {
						double userTemp = Double.parseDouble(userInput.getText().toString());
						double celciusTemp = (userTemp - 32)*(5.0/9);
						result.setText(getString(R.string.celsiusResult) + " " + String.format("%.1f",celciusTemp));
					} else {
						Toast.makeText(MainActivity.this, getString(R.string.chooseConversion),
								Toast.LENGTH_SHORT).show();
					}

				}

			}
		});
		
		closeButton.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
				AlertDialog.Builder alertBuilder = new AlertDialog.Builder(MainActivity.this);
				alertBuilder.setTitle(R.string.alertTitle);
				alertBuilder.setIcon(R.drawable.ic_launcher);
				alertBuilder.setPositiveButton(R.string.alertPositiveButton, new DialogInterface.OnClickListener() {
									
					@Override
					public void onClick(DialogInterface dialog, int which) {
						// TODO Auto-generated method stub
						finish();
					}
				});
				
				alertBuilder.setNegativeButton(R.string.alertNegativeButton, new DialogInterface.OnClickListener() {
					
					@Override
					public void onClick(DialogInterface dialog, int which) {
						// TODO Auto-generated method stub
						Toast.makeText(MainActivity.this, getString(R.string.continueRunning),
								Toast.LENGTH_SHORT).show();
					}
				});
				
				alertBuilder.show();
				
			} //End of closeButton listener
		}); 
	}
	
	

	@Override
	protected void onSaveInstanceState(Bundle outState) {
		// TODO Auto-generated method stub
		outState.putString("resultText", result.getText().toString());
		super.onSaveInstanceState(outState);
		
	}



	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);		
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			Intent settingsIntent = new Intent(this,SettingsActivity.class);
			startActivityForResult(settingsIntent, REQUEST_CODE);
			return true;
		}
		return super.onOptionsItemSelected(item);
	}



	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);
		
		if (requestCode == REQUEST_CODE && resultCode == RESULT_OK) {
			if (data!=null && data.hasExtra(IMAGE_KEY)) {
				int imageID = (Integer) data.getExtras().get(IMAGE_KEY);
				SharedPreferences.Editor editor = myPrefs.edit();		
				editor.putInt(IMAGE_KEY, imageID);
				editor.commit();				
				if (imageID == R.drawable.greytexture) {
								
					applyCoolTheme();				
					
				} else if (imageID == R.drawable.papertexture) {
					applyWarmTheme();
				}
				
			}
		}
		
	}
	
	protected void applyCoolTheme() {
		rootLayout.setBackgroundResource(R.drawable.greytexture);
		result.setTextColor(getResources().getColor(android.R.color.holo_orange_light));
		userInput.setTextColor(Color.WHITE);
		radioCelsius.setTextColor(Color.WHITE);
		radioFahren.setTextColor(Color.WHITE);		
		image.setImageResource(R.drawable.thermometerorange);
	}
	
	protected void applyWarmTheme() {
		rootLayout.setBackgroundResource(R.drawable.papertexture);
		result.setTextColor(getResources().getColor(android.R.color.holo_blue_dark));
		userInput.setTextColor(Color.BLACK);
		radioCelsius.setTextColor(Color.BLACK);
		radioFahren.setTextColor(Color.BLACK);
		image.setImageResource(R.drawable.thermometer);
	}
	
	
}
