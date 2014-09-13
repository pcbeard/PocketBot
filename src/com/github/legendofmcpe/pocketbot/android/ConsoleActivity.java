package com.github.legendofmcpe.pocketbot.android;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.support.v7.app.ActionBarActivity;
import android.text.InputType;
import android.text.method.ScrollingMovementMethod;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.github.legendofmcpe.pocketbot.PocketBot;

public class ConsoleActivity extends ActionBarActivity implements View.OnClickListener, InputType{
	public class ConsoleTextViewUpdater extends Console.UpdateListener{
		@Override
		public void onLineAdded(String line){
			if(console.getText().length() > 0){
				console.append("\n");
			}
			console.append(line);
			scroller.fullScroll(ScrollView.FOCUS_DOWN);
		}
	}
	public final static String MY_NAME = "com.github.legendofmcpe.pocketbot";
	public final static String BOT_NAME_KEY = MY_NAME.concat(".bot_name");
	public final static String CONSOLE_LISTENER_KEY = MY_NAME.concat(".scroll_textview_updater");
	private PocketBotService botServ = null;
	private PocketBot bot = null;
	private boolean bound = false;
	private TextView console;
	private EditText cmdInput;
	private ScrollView scroller;
	private String name;

	private ServiceConnection conn = new ServiceConnection(){
		@Override
		public void onServiceConnected(ComponentName name, IBinder binder){
			botServ = ((PocketBotServiceBinder) binder).getService();
			bound = true;
		}
		@Override
		public void onServiceDisconnected(ComponentName name){
			bound = false;
		}
	};

	@Override
	protected void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_console);
		console = (TextView) findViewById(R.id.console_console);
		console.setMovementMethod(new ScrollingMovementMethod());
		cmdInput = (EditText) findViewById(R.id.console_input);
		cmdInput.setInputType(TYPE_CLASS_TEXT);
		scroller = (ScrollView) findViewById(R.id.console_scroll);
		name = getIntent().getStringExtra(BOT_NAME_KEY);
		setTitle(name);
	}
	@Override
	public boolean onCreateOptionsMenu(Menu menu){
		getMenuInflater().inflate(R.menu.console, menu);
		return true;
	}
	@Override
	public boolean onOptionsItemSelected(MenuItem item){
		int id = item.getItemId();
		if(id == R.id.action_settings){
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	@Override
	protected void onStart(){
		super.onStart();
		Intent intent = new Intent(this, PocketBotService.class);
		bindService(intent, conn, BIND_IMPORTANT);
		if(!bound){
			Toast.makeText(this, R.string.console_cannot_bind_service, Toast.LENGTH_LONG).show();
			finish();
		}
		Console console = (Console) bot.getLogger();
		console.addListener(CONSOLE_LISTENER_KEY, new ConsoleTextViewUpdater());
	}
	@Override
	protected void onStop(){
		super.onStop();
		if(bound){
			unbindService(conn);
			botServ = null;
			bound = false;
		}
	}
	@Override
	public void onClick(View v){
		if(v.getId() == R.id.console_send_button){
			if(bound){
				bot.queueMessage(cmdInput.getText().toString());
				cmdInput.getText().clear();
			}
		}
	}

	public PocketBotService getService(){
		return botServ;
	}
	public String getBotName(){
		return name;
	}
	public TextView getConsoleTextView(){
		return console;
	}
}
