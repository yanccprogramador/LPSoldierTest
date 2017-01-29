package br.com.yan.lp.lpsoldierstest;

import android.app.Activity;
import  android.*;
import android.app.AlertDialog;
import android.media.Image;
import android.net.Uri;
import android.widget.*;
import android.os.*;
import android.graphics.*;
import android.provider.*;
import android.content.*;



import java.io.*;
import java.util.Date;
import java.util.List;

public class camera extends Activity {
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */

    File imageFile;

   String caminho;



    public  void carregarFoto(String nomeArquivoFoto, ImageView ivFoto) throws FileNotFoundException {
//Local onde será buscado a foto a ser carregada na ImageView //Environment.DIRECTORY_PICTURES pega o diretório Pictures do Android
        String local =Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES)+File.separator+nomeArquivoFoto;
        //imageFile=new File();
        //FileInputStream fto=new FileInputStream(imageFile);

//Cria uma imagem a partir de um caminho onde se encontrar um arquivo
       Bitmap imagem = BitmapFactory.decodeFile(local);
//Altera um imagemView para uma nova imagem, neste caso a imagem com o caminho especificado acima

            ivFoto.setImageBitmap(imagem);

    }





}
