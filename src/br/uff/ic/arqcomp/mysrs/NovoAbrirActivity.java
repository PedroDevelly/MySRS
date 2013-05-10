package br.uff.ic.arqcomp.mysrs;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.URISyntaxException;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnKeyListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.ipaulpro.afilechooser.utils.FileUtils;

public class NovoAbrirActivity extends Activity implements OnClickListener, OnKeyListener {

	public Deck deck;
    private int cardsRevisados;
    private boolean revisaoEmAndamento;
    private boolean arquivoAberto;
    private boolean isSaveYet;
    private String filepath;
    private boolean arquivoInvalido;
   
    
    Button facil, medio, dificil, mostrar;
    EditText limitePerguntas;
    //ScrollView svPergunta, svResposta;
    TextView pergunta, resposta;
    
    private static final int REQUEST_CODE = 6384; // onActivityResult request code
    private static final int SAVE_CODE = 2473;
    
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_novo_abrir);
		
		facil = (Button) findViewById(R.id.facilButton);
		facil.setOnClickListener(this);
		medio = (Button) findViewById(R.id.medioButton);
		medio.setOnClickListener(this);
		dificil = (Button) findViewById(R.id.dificilButton);
		dificil.setOnClickListener(this);
		mostrar = (Button) findViewById(R.id.mostrarButton);
		mostrar.setOnClickListener(this);
		
		limitePerguntas = (EditText) findViewById(R.id.editText);
		
		
		pergunta = (TextView) findViewById(R.id.perguntaTextView);
		resposta = (TextView) findViewById(R.id.respostaTextView);
		
		facil.setEnabled(false);
		medio.setEnabled(false);
		dificil.setEnabled(false);
		mostrar.setEnabled(false);
		limitePerguntas.setEnabled(false);
		
		Toast.makeText(NovoAbrirActivity.this, "Favor Escolha uma das opções do Menu", Toast.LENGTH_LONG).show();
		
		
	}
	
	//@Override
	//protected void onStop(){
		//if(arquivoAberto)
            //fechar();
		//this.finish();
	//}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_novo_abrir, menu);
		return true;
	}
	
	public boolean onOptionsItemSelected(MenuItem item) {
	    //respond to menu item selection
		switch(item.getItemId()){
		
		case R.id.adicionar_remover:
			startActivity(new Intent(this, AdicionarRemoverActivity.class));
			return true;
			
		case R.id.novo:			
				
				//startActivity(new Intent(this, TelaNovo.class));				
			try {
				inicializar(null);
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
						
			//Toast.makeText(NovoAbrirActivity.this, "Seu Deck foi criado", Toast.LENGTH_LONG).show();
			return true;
			
		case R.id.abrir:
			showChooser();
			
			return true;
			
		case R.id.fechar:
			fechar();
			return true;
			
		case R.id.sair:
			if(arquivoAberto)
	            fechar();
	        this.finish();
			
		default:
		    return super.onOptionsItemSelected(item);
		    
		}
	}
	
	
	

	@Override
	public void onClick(View v) {
		if(v.equals(mostrar)){
			if (revisaoEmAndamento) {
	            mostrar.setEnabled(false);
	            dificil.setEnabled(true);
	            facil.setEnabled(true);
	            medio.setEnabled(true);
	            resposta.setText(deck.getResposta(cardsRevisados));
	        } else {
	            if (deck.getLimite() > deck.getAllCards().size()) {
	                deck.setLimite(deck.getAllCards().size());
	            }

	            //***********************************
	            //Modificação feita em 26.02
	            //Checagem do valor dado como entrada do número de cartas a serem revistas
	            //Se o valor for igual a zero aparece a mensagem pedindo um valor válido

	            if (deck.getLimite() > 0 && !("0".equals(limitePerguntas.getText()))) { // checa se há perguntas para mostrar.

	                comecarRevisao();
	            } else {
	                if ("0".equals(limitePerguntas.getText())) {
	                    //JOptionPane.showMessageDialog(null, "Insira uma quantidade válida de cartas");
	                    new AlertDialog.Builder(this)
	                    .setIcon(android.R.drawable.ic_dialog_alert)
	                    .setTitle("Quantidade inválida de cartas!")
	                    .setMessage("Insira uma quantidade válida de cartas")
	                    .setNeutralButton("OK", null)
	                    .show();
	                } else //*************************************
	                {
	                    //JOptionPane.showMessageDialog(null, "Não há cartas para revisão."+ "\nVá em editar>Adicionar/Remover Flashcards.");
	                    new AlertDialog.Builder(this)
	                    .setIcon(android.R.drawable.ic_dialog_alert)
	                    .setTitle("Não há cartas para revisão.")
	                    .setMessage("Vá em Menu>Adicionar/Remover.")
	                    .setNeutralButton("OK", null)
	                    .show();
	                }
	            }
	        }
		}
		
		else if(v.equals(dificil)){
			if (deck.getFatorRepeticao(cardsRevisados) == 0) {
	            deck.setNewFatorRepeticao(cardsRevisados, 10);
	        } else {

	            deck.setFatorRepeticao(cardsRevisados, .5);
	        }
	        proximaPergunta();
		}
		
		else if (v.equals(medio)){
			if (deck.getFatorRepeticao(cardsRevisados) == 0) {
	            deck.setNewFatorRepeticao(cardsRevisados, 15);
	        } else {

	            deck.setFatorRepeticao(cardsRevisados, 1.5);
	        }
	        proximaPergunta();
		}
		
		else if (v.equals(facil)){
			if (deck.getFatorRepeticao(cardsRevisados) == 0) {
	            deck.setNewFatorRepeticao(cardsRevisados, 20);
	        } else {

	            deck.setFatorRepeticao(cardsRevisados, 2.0);
	        }
	        proximaPergunta();
		}
		
	}
	
	@Override
	public boolean onKey(View v, int keyCode, KeyEvent event) {
		if(v.equals(mostrar)){
			if (!revisaoEmAndamento) {
	            if (mostrar.onKeyDown(1, null)) {
	                mostrar.setEnabled(false);
	                dificil.setEnabled(true);
	                medio.setEnabled(true);
	                facil.setEnabled(true);
	                resposta.setText(deck.getResposta(cardsRevisados));
	                return true;
	            }

	        }
		}
		return false;
	}
	
	
	private void showChooser() {
		// Use the GET_CONTENT intent from the utility class
		Intent target = FileUtils.createGetContentIntent();
		// Create the chooser Intent
		Intent intent = Intent.createChooser(target, getString(R.string.chooser_title));
		try {
			startActivityForResult(intent, REQUEST_CODE);
		} catch (ActivityNotFoundException e) {
			// The reason for the existence of aFileChooser
		}				
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		switch (requestCode) {
		case REQUEST_CODE:	
			// If the file selection was successful
			if (resultCode == RESULT_OK) {
				
				if (arquivoAberto) {
	                fechar();
				}
				if (data != null) {
					// Get the URI of the selected file
					final Uri uri = data.getData();

					try {
						//Create a file instance from the URI
						final File file = FileUtils.getFile(uri);
						Toast.makeText(NovoAbrirActivity.this, "File Selected: "+file.getAbsolutePath(), Toast.LENGTH_LONG).show();
						this.setTitle("MySRS    Deck - " + file.getName());						
						inicializar(file.getAbsolutePath());
			            
					}
					catch (Exception e) {
						Log.e("FileSelectorTestActivity", "File select error", e);
					}
				}
				break;
				
			}
			case FILE_SELECT_CODE:      
            if (resultCode == RESULT_OK) {  
                // Get the Uri of the selected file 
                Uri uri = data.getData();
                
                // Get the path
                try {
					filepath = FileUtils.getPath(this, uri);
				} catch (URISyntaxException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
                
                // Get the file instance
                // File file = new File(path);
                // Initiate the upload
            }           
            break;
			default:
            super.onActivityResult(requestCode, resultCode, data);
		}

	}
	
	
	private void inicializar(String filename) throws ClassNotFoundException {
	
			
		//Toast.makeText(NovoAbrirActivity.this, "Estramos na inicializar", Toast.LENGTH_LONG).show();
        revisaoEmAndamento = false;
        arquivoAberto = true;
        //jMenuEditar.setEnabled(true);
        limitePerguntas.setEnabled(true);
        //jMenuItemFechar.setEnabled(true);
        mostrar.setText("Começar");
        mostrar.setEnabled(true);
        arquivoInvalido = false;

        /////////////////////////////////////////////////
        //leitura do deck ou inicialização abaixo
        if (filename == null) {
            deck = new Deck(0);
            //startActivity(new Intent(this, TelaNovo.class));
            isSaveYet = false;
        } else {
            try {
                FileInputStream fileIn = new FileInputStream(filename);
                ObjectInputStream in = new ObjectInputStream(fileIn);
            	//Reader fileIn = new FileReader(filename);
            	//BufferedReader in = new BufferedReader(fileIn);
                deck = (Deck)  in.readObject();
                in.close();
                fileIn.close();
            } catch (IOException e) {
            	Toast.makeText(NovoAbrirActivity.this, "Arquivo Inválido", Toast.LENGTH_LONG).show();
                arquivoInvalido = true;
                fechar();
            }
            isSaveYet = true;
            filepath = filename;
        }
        /////////////////////////////////////////////////
        if(!arquivoInvalido)
            limitePerguntas.setText(Integer.toString(deck.getLimite()));
    }
	
	
	

	private void fechar() {

        //***************************
        //Modificação feita em 26.02
        //Retirada da mensagem abaixo pra toda vez que o método fachar() é chamado
        //Ela só deve aparecer quando o número de cartas chega ao fim, ou seja, no fim da revisão
        //JOptionPane.showMessageDialog(null, "Fim!\n Você revisou " + cardsRevisados + " flashcards hoje!");
        //***************************

        revisaoEmAndamento = false;
        arquivoAberto = false;
        pergunta.setText("");
        resposta.setText("");
        pergunta.setBackgroundColor(Color.RED);
        resposta.setBackgroundColor(Color.RED);
        dificil.setEnabled(false);
        facil.setEnabled(false);
        medio.setEnabled(false);
        mostrar.setEnabled(false);
        //jMenuEditar.setEnabled(false);
        //jMenuItemFechar.setEnabled(false);
        limitePerguntas.setEnabled(false);
        limitePerguntas.setText("");
        mostrar.setText("Começar");
        this.setTitle("MySRS");
        

        
        if(!arquivoInvalido){
            //************************
            //Modificação 26.02
            //Toda vez que o arquivo é fechado deve ser ordanado
            deck.sort();
            //***************************

            //Modificado 12.03 para o JOptionPane com opção de salvar ou nao.
            new AlertDialog.Builder(this)
            .setIcon(android.R.drawable.ic_dialog_alert)
            .setTitle("ATENÇÃO!")
            .setMessage("Deseja salvar as alterações no deck?")
            .setPositiveButton("Sim", new DialogInterface.OnClickListener()
        {
            @Override
            public void onClick(DialogInterface dialog, int which) {
            	if (!isSaveYet) {
                    
            		showFileChooser();
                    //int val = filechooser.showSaveDialog(jLabel1);
                    //if (val == JFileChooser.APPROVE_OPTION) {
                        //filepath = filechooser.getSelectedFile().getAbsolutePath();
                    //}
                }

                try {
                    FileOutputStream fileOut = new FileOutputStream(filepath);
                    ObjectOutputStream out = new ObjectOutputStream(fileOut);
                    out.writeObject(deck);
                    out.close();
                    fileOut.close();
                } catch (IOException i) {
                    System.out.println("Falha inesperada");
                } catch (java.lang.NullPointerException np) {
                }
            }

        })
        .setNegativeButton("Não", null)
        .show();
        }
    }
	
	private void comecarRevisao() {

        limitePerguntas.setEnabled(false);
		pergunta.setBackgroundColor(Color.WHITE);
        resposta.setBackgroundColor(Color.WHITE);
        cardsRevisados = 0;
        mostrar.setText("Mostrar Resposta");
        revisaoEmAndamento = true;
        pergunta.setText(deck.getPergunta(cardsRevisados));

    }
	
	private void proximaPergunta() {
        cardsRevisados++;
        if (cardsRevisados >= deck.getLimite()) {

            //***********************
            //Modificação feita em 26.02
            //Acrescimo da mensagem de fim quando não á mais cartas a serem revisadas
            //JOptionPane.showMessageDialog(null, "Fim!\n Você revisou " + cardsRevisados + " flashcards hoje!");
            //***********************
            
            new AlertDialog.Builder(this)
            .setIcon(android.R.drawable.ic_dialog_alert)
            .setTitle("FIM!")
            .setMessage("Você revisou " + cardsRevisados + " flashcards hoje!")
            .setNeutralButton("OK", null)
            .show();

            fechar();
        } else {
            pergunta.setText(deck.getPergunta(cardsRevisados));
            resposta.setText("");
            mostrar.setEnabled(true);
            dificil.setEnabled(false);
            facil.setEnabled(false);
            medio.setEnabled(false);
        }

    }

	private static final int FILE_SELECT_CODE = 0;

    private void showFileChooser() {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT); 
        intent.setType("*/*"); 
        intent.addCategory(Intent.CATEGORY_OPENABLE);

        try {
            startActivityForResult(
                    Intent.createChooser(intent, "Select a File to Upload"),
                    FILE_SELECT_CODE);
        } catch (android.content.ActivityNotFoundException ex) {
            // Potentially direct the user to the Market with a Dialog
            Toast.makeText(this, "Please install a File Manager.", 
                    Toast.LENGTH_SHORT).show();
        }
    }
    public static String getPath(Context context, Uri uri) throws URISyntaxException {
        if ("content".equalsIgnoreCase(uri.getScheme())) {
            String[] projection = { "_data" };
            Cursor cursor = null;

            try {
                cursor = context.getContentResolver().query(uri, projection, null, null, null);
                int column_index = cursor
                .getColumnIndexOrThrow("_data");
                if (cursor.moveToFirst()) {
                    return cursor.getString(column_index);
                }
            } catch (Exception e) {
                // Eat it
            }
        }

        else if ("file".equalsIgnoreCase(uri.getScheme())) {
            return uri.getPath();
        }

        return null;
    }
}
