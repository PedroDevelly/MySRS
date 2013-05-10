package br.uff.ic.arqcomp.mysrs;


import java.io.File;
import java.io.FileOutputStream;


import android.app.Activity;
import android.os.Bundle;
import android.os.Environment;
import android.view.Menu;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class AdicionarRemoverActivity extends Activity {
	
	private TextView txtRoot;
    private TextView txtResposta;
    private TextView txtPergunta;
    
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_adicionar_remover);
		try {
            
 
            txtRoot = (TextView) findViewById(R.id.txtRoot2);
            txtResposta = (TextView) findViewById(R.id.edtPergunta);
            txtPergunta = (TextView) findViewById(R.id.edtResposta);
            
         
         
            txtRoot.append(GetRoot());       
         
        } catch (Exception e) {
            trace("Erro : " + e.getMessage());
        }        
	}
	private String GetRoot()
    {
        File root = android.os.Environment.getExternalStorageDirectory();
        return root.toString();
    }
	public void click_Salvar(View v)
    {
        SalvarArquivo();
    }
 
    public void click_Limpar(View v)
    {
    	txtResposta.setText("");
    	txtPergunta.setText("");
        
    }
    private void SalvarArquivo()
    {
        String lstrNomeArq;
        File arq;
        byte[] dados;
        try
        {
            //pega o nome do arquivo a ser gravado
            lstrNomeArq = txtResposta.getText().toString();
             
            arq = new File(Environment.getExternalStorageDirectory(), lstrNomeArq);
            FileOutputStream fos;
             
            //transforma o texto digitado em array de bytes
            dados = txtPergunta.getText().toString().getBytes();
             
            fos = new FileOutputStream(arq);
             
            //escreve os dados e fecha o arquivo
            fos.write(dados);
            fos.flush();
            fos.close();
        } catch (Exception e) {
            trace("Erro : " + e.getMessage());
        }       
    }
     
    public void toast (String msg)
    {
        Toast.makeText (getApplicationContext(), msg, Toast.LENGTH_SHORT).show ();
    }
     
    private void trace (String msg)
    {
        toast (msg);
    }    
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.adicionar_remover, menu);
		return true;
	}

}
