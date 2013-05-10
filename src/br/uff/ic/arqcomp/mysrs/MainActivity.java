package br.uff.ic.arqcomp.mysrs;


import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class MainActivity extends Activity implements OnClickListener {

	Button novoAbrirButton, sairButton;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		novoAbrirButton = (Button) findViewById(R.id.novoAbrirButton);
		novoAbrirButton.setOnClickListener(this);
		sairButton = (Button) findViewById(R.id.sairButton);
		sairButton.setOnClickListener(this);
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_main, menu);
		return true;
	}

	@Override
	public void onClick(View v) {
		if(v.equals(sairButton))
			this.finish();
		else if(v.equals(novoAbrirButton)){
			startActivity(new Intent(this, NovoAbrirActivity.class));
		}
	}

}
