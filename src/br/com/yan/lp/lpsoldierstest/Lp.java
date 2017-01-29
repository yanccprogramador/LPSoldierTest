package br.com.yan.lp.lpsoldierstest;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import br.com.yan.lp.lpsoldierstest.R.*;

import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.hardware.Camera;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

public class Lp extends Activity {
    Button btconsul;
    Button btVoltar;
    Button btcon;
    Button btque;
    Button btMPr;
    Button btDele;
    Button btCall, btPic;
    Button btquesti, btGravar;
    String etNome="";
    EditText et1, et2;
    string tv1, tv2, tv3;
    TextView tvNome, tvEndereco, tvTelefone;
    RadioButton rbt1, rbt2, rbt3, rbt4;
    int campoNome, campoEndereco, campoTelefone;
    SQLiteDatabase bancoDados = null; // banco de dados
    Cursor cursor;
    private Uri fileUri;
    String caminho;
    public static final int MEDIA_TYPE_IMAGE = 1;
    Intent i;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        chamaMenu();


    }


    public void chamaMenu() {
        setContentView(R.layout.main);
        listeners();
        abreouCriaBanco();


        fechaBanco();
    }

    public void ChamaConsulta() {
        setContentView(R.layout.consulta);
        listeners();
        abreouCriaBanco();
        buscarDados();
        mostrarDados();
        ImageView Foto = (ImageView) findViewById(R.id.ivFoto);
        camera c = new camera(); //Chama o método para carregar uma foto no ImageView
        try {
            c.carregarFoto(caminho,Foto);

            setContentView(R.layout.cam);
        } catch (Exception e) {
            mensagemExibir("Aviso Soldier", e.getMessage());
        }


        fechaBanco();
    }

    public void chamaQuest() {
        setContentView(R.layout.activity_lp);
        listeners();
        mensagemExibir("Aviso", "Escreva A primeira letra de cada palavra em maiúsculas");
    }

    public void mensagemExibir(String titulo, String texto) {
        AlertDialog.Builder mensagem = new AlertDialog.Builder(Lp.this);
        mensagem.setTitle(titulo);
        mensagem.setMessage(texto);
        mensagem.setNeutralButton("OK", null);
        mensagem.setIcon(drawable.ic_launcher);
        mensagem.show();

    }

    public void abreouCriaBanco() {
        try {
            String nomeBanco = "bancoEstoque";
            // cria ou abre o banco de dados
            bancoDados = openOrCreateDatabase(nomeBanco, MODE_WORLD_READABLE, null);
            String sql = "CREATE TABLE IF NOT EXISTS LP (id INTEGER PRIMARY KEY, MusicaFavo TEXT, MembroFavo TEXT,Membros TEXT);";
            bancoDados.execSQL(sql);
            //mensagemExibir("Banco", "Banco criado ou aberto com sucesso ");
        } catch (Exception erro) {
            mensagemExibir("Erro Banco", "Erro ao abrir ou criar o banco: "
                    + erro.getMessage());
        }
    }


    public void insereRegistro() {
        listeners();
        try {

                if (rbt1.isChecked() == true) {
                    etNome = rbt1.getText().toString();
                }
                if (rbt2.isChecked() == true) {
                    etNome = rbt2.getText().toString();
                }
                if (rbt3.isChecked() == true) {
                    etNome = rbt3.getText().toString();
                }
                if (rbt4.isChecked() == true) {
                    etNome = rbt4.getText().toString();
                }
            if (etNome!="" || et1.getText().toString() != "" || et2.getText().toString() != "") {
                String sql = "INSERT INTO LP (Membros, MembroFavo, MusicaFavo) values ('"
                        + etNome.toString() + "','"
                        + et1.getText().toString() + "','"
                        + et2.getText().toString() + "')";
                bancoDados.execSQL(sql);
                mensagemExibir("Aviso Soldier", "Dados Gravados com sucesso");
                bancoDados.update("LP", null, null, null);

            } else {
                mensagemExibir("Aviso Soldier", "Preencha tudo antes de salvar");

            }
        } catch (Exception erro) {
            //mensagemExibir("Erro Banco", "Erro ao gravar dados no banco: "+ erro.getMessage());

        }
    }

    public void mostrarDados() {
        abreouCriaBanco();
        listeners();


        if (buscarDados() == true) {


            try {


                tvNome.setText(cursor.getString(campoNome));
                tvEndereco.setText(cursor.getString(campoEndereco));
                tvTelefone.setText(cursor.getString(campoTelefone));
            } catch (Exception erro) {
                mensagemExibir("Erro Banco", "Erro setar texto: " + erro.getMessage());
            }
            try {
                String ordem1 = "Mike Shinoda,Chester Bennington,Mr hann,Phoenix,Rob Bourdon,Brad Delson";

                if (etNome.equals(ordem1)) {
                    //mensagemExibir(, );
                    AlertDialog.Builder mensagem = new AlertDialog.Builder(Lp.this);
                    mensagem.setTitle("Parabêns");
                    mensagem.setMessage("Você�é um verdadeiro soldier!");
                    mensagem.setNeutralButton("OK", null);
                    //mensagem.setIcon(Integer.parseInt(String.valueOf("foto.jpg")));
                    mensagem.show();

                } else {

                    mensagemExibir("Aviso Soldier", "Você é um poser lamento !");
                }

                fechaBanco();
            } catch (Exception erro) {

                mensagemExibir("Erro Banco", "Erro ao mostrar Dados:" + erro.getMessage());


            }
        } else {
            mensagemExibir("Aviso Soldier", "Responda o Questionário antes");
        }
    }

    public void listeners() {
        try {
            btconsul = (Button) findViewById(R.id.bt5);
            btquesti = (Button) findViewById(R.id.bt4);
        } catch (Exception e) {

        }
        try {
            btPic = (Button) findViewById(R.id.btPic);
        } catch (Exception e) {

        }
        try {
            btconsul.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {

                    ChamaConsulta();
                }
            });
            btquesti.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {

                    chamaQuest();
                }
            });
        } catch (Exception e) {

        }
        try {
            btVoltar = (Button) findViewById(R.id.bt1);
            btcon = (Button) findViewById(R.id.bt2);
            rbt1 = (RadioButton) findViewById(R.id.rbt1);
            rbt2 = (RadioButton) findViewById(R.id.rbt2);
            rbt3 = (RadioButton) findViewById(R.id.rbt3);
            rbt4 = (RadioButton) findViewById(R.id.rbt4);

            btGravar = (Button) findViewById(R.id.bt3);
        } catch (Exception e) {

        }
        try {


            et1 = (EditText) findViewById(R.id.favoritos);
            et2 = (EditText) findViewById(R.id.musica);
        } catch (Exception e) {

        }
        try {

            btcon.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {

                    ChamaConsulta();
                }
            });
            btPic.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {

                    pic();
                }
            });
        } catch (Exception e) {

        }

        try {
            btGravar.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    try {
                        abreouCriaBanco();

                        insereRegistro();


                        if (rbt1.isChecked() == true) {
                            rbt1.setChecked(false);
                        }
                        if (rbt2.isChecked() == true) {
                            rbt2.setChecked(false);
                        }
                        if (rbt3.isChecked() == true) {
                            rbt3.setChecked(false);
                        }
                        if (rbt4.isChecked() == true) {
                            rbt4.setChecked(false);
                        }
                        et1.setText("");
                        et2.setText("");

                        // etNome.requestFocus();
                        fechaBanco();
                    } catch (Exception erro) {

                        mensagemExibir("Erro Banco", "Erro ao gravar dados no banco: " + erro.getMessage());

                    }
                }
            });

        } catch (Exception e) {


        }
        try {
            btVoltar.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {

                    chamaMenu();
                }
            });


        } catch (Exception err) {

        }


        try {
            btque = (Button) findViewById(R.id.bt7);
            btMPr = (Button) findViewById(R.id.bt6);
            btDele = (Button) findViewById(R.id.bt8);
            btCall = (Button) findViewById(R.id.btCall);
        } catch (Exception e) {
            mensagemExibir("Erro", "Bugou: " + e.getMessage());
        }
        try {
            tvNome = (TextView) findViewById(R.id.tvM);
            tvTelefone = (TextView) findViewById(R.id.TvMF);
            tvEndereco = (TextView) findViewById(R.id.tvMuF);
        } catch (Exception e) {

        }
        try {
            btque.setOnClickListener(new

                                             View.OnClickListener() {
                                                 public void onClick(View v) {

                                                     chamaQuest();
                                                 }
                                             });
            btCall.setOnClickListener(new View.OnClickListener() {
                                          public void onClick(View v) {
                                              call();
                                          }
                                      }
            );
            btMPr.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {

                    chamaMenu();
                }
            });
            btDele.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    ApagarBancodeDados();
                }
            });
        } catch (Exception e) {

        }
    }

    public boolean buscarDados() {
        abreouCriaBanco();
        try {
            cursor = bancoDados.query("LP", new String[]{"Membros",
                            "MembroFavo", "MusicaFavo"}, null,// selection,
                    null,// selectionArgs,
                    null,// groupBy,
                    null,// having,
                    "id",// "order by nome"//orderBy)
                    null); // Limite de registros retornados
            campoNome = cursor.getColumnIndex("Membros");
            campoEndereco = cursor.getColumnIndex("MembroFavo");
            campoTelefone = cursor.getColumnIndex("MusicaFavo");
            int numeroRegistros = cursor.getCount();
            if (numeroRegistros != 0) {

                cursor.moveToLast();

                return true;
            } else
                return false;

        } catch (Exception erro) {
            mensagemExibir("Erro Banco", "Erro ao buscar dados no banco: " + erro.getMessage());
            return false;
        }
    }

    public void ApagarBancodeDados() {
        listeners();
        try {
            abreouCriaBanco();


            bancoDados.delete("LP", null, null);
            mensagemExibir("aviso Soldiers", "Dados apagado com sucesso");
            tvNome.setText("");
            tvEndereco.setText("");
            tvTelefone.setText("");
            fechaBanco();
            ;
        } catch (Exception erro) {
            mensagemExibir("Erro Banco", "Erro ao apagar dados no banco: "
                    + erro.getMessage());
        }
    }

    public void fechaBanco() {
        try {
            bancoDados.close(); //fecha banco de dados
        } catch (Exception erro) {
            mensagemExibir("Erro Banco", "Erro ao fechar o banco: " + erro.getMessage());
        }
    }

    public void call() {
        try {
            Uri uriLigar = Uri.parse("tel:5531993732191");
            Intent ILigar = new Intent(Intent.ACTION_CALL, uriLigar);
            startActivity(ILigar);
        } catch (Exception e) {
            mensagemExibir("Erro", "Não conseguimos ligar : " + e.getMessage());
        }
    }
    private static Uri getOutputMediaFileUri(int type){
        return Uri.fromFile(getOutputMediaFile(type));
    }

    /** Create a File for saving an image or video */
    private static File getOutputMediaFile(int type) {
        // To be safe, you should check that the SDCard is mounted
        // using Environment.getExternalStorageState() before doing this.

        File mediaStorageDir = new File(Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_PICTURES),"lpsoldiertest");
        // This location works best if you want the created images to be shared
        // between applications and persist after your app has been uninstalled.

        // Create the storage directory if it does not exist
        if (!mediaStorageDir.exists()) {
            if (!mediaStorageDir.mkdirs()) {
                Log.d("MyCameraApp", "failed to create directory");
                return null;
            }
        }

        // Create a media file name
        //String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        File mediaFile;
        if (type == MEDIA_TYPE_IMAGE){
            mediaFile = new File(mediaStorageDir.getPath() + File.separator +
                    "lp.jpg");
        } else {
            return null;
        }

        return mediaFile;
    }

    public void pic() {
        final int CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE = 100;

        try {
            //Cria a classe usarCamera

            camera c = new camera(); //Chamada do método CapturarFoto com o nome do arquivo usado quando for gravado a foto //Este método retornar uma intenção para que depois seja iniciada a captura da imagem
            Intent i = new Intent(MediaStore.ACTION_IMAGE_CAPTURE); //Inicia a inteção ou seja, captura a imagem

            startActivityForResult(i, CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE);
            Uri a=getOutputMediaFileUri(1);
            if (a!=null){
                i.putExtras(MediaStore.EXTRA_OUTPUT,a);
            }





        } catch (Exception e) {
            mensagemExibir("Erro", "Não conseguimos acessar a camera : " + e.getMessage());
        }

    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        final int CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE = 100;
        if (requestCode == CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                //caminho = data.getData();7

                // Image captured and saved to fileUri specified in the Intent
                Toast.makeText(this, "Image saved  ", Toast.LENGTH_LONG).show();
            } else if (resultCode == RESULT_CANCELED) {
                Toast.makeText(this, "Foto Cancelada.", Toast.LENGTH_LONG).show();
            } else {
                // Image capture failed, advise user
            }
        }else{
            mensagemExibir("Aviso Soldier","Não estamos conseguindo acessar a foto");
        }
    }
}
