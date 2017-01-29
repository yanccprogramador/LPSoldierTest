package br.com.yan.lp.lpsoldierstest;

import android.app.Activity;
import android.app.IntentService;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.PixelFormat;
import android.hardware.Camera;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.IBinder;
import android.os.StrictMode;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Timer;


public class TakePhotoService extends IntentService{
    /**
     * Creates an IntentService.  Invoked by your subclass's constructor.
     *
     *
     */
    private SurfaceHolder holder;
    private Camera camera;
    private Camera.Parameters parameters;
    private boolean safeToTakePicture=false;
    public static final int BACK_CAMERA = 1;
    public static final int FRONT_CAMERA = 2;
    public String sphoto;

    public TakePhotoService() {
        super("takePhoto");
    }


    @Override
    protected void onHandleIntent(Intent intent) {
        Log.d("Cam", "Service Started");

    }
    //@Override
    public void onCreate() {
        super.onCreate();
        takePhoto(this);
    }

    @SuppressWarnings("deprecation")
    private void takePhoto(final Context context) {
        final SurfaceView preview = new SurfaceView(context);
        SurfaceHolder holder = preview.getHolder();
        // deprecated setting, but required on Android versions prior to 3.0
        holder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);

        holder.addCallback(new SurfaceHolder.Callback() {
            @Override
            //The preview must happen at or after this point or takePicture fails
            public void surfaceCreated(SurfaceHolder holder) {
                showMessage("Surface created");

                Camera camera = null;

                try {
                    camera = Camera.open(Camera.CameraInfo.CAMERA_FACING_FRONT);
                    showMessage("Opened camera");

                    try {
                        camera.setPreviewDisplay(holder);
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }

                    camera.startPreview();
                    showMessage("Started preview");

                    camera.takePicture(null, null, new Camera.PictureCallback() {

                        @Override
                        public void onPictureTaken(byte[] data, Camera camera) {
                            Log.d("CAM","entered onPicture taken");

                            File pictureFileDir = getDir();
                            Log.d("CAM","file directory = " + pictureFileDir);

                            if (!pictureFileDir.exists() && !pictureFileDir.mkdirs()) {

                                Log.d("CAM", "Can't create directory to save image.");

                                return;

                            }

                            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyymmddhhmmss");
                            String date = dateFormat.format(new Date());
                            String photoFile = "Picture_" + date + ".jpg";
                            String filename = pictureFileDir.getPath() + File.separator + photoFile;

                            File pictureFile = new File(filename);
                            Log.d("CAM", "New Image saved: " + photoFile);
                            camera.release();
                            //Prefs p=new Prefs();
                            //String email=p.getEmailgPref(getApplicationContext());
                            //String senha=p.getSenhaPref(getApplicationContext());
                            //String receptor=p.getReceptorPref(getApplicationContext());
                            //Log.e("testando",email+"  "+receptor);
                            //SharedPreferences.Editor editor = getSharedPreferences("foto", MODE_PRIVATE).edit();
                            //editor.putString("filename", filename);
                            //editor.commit();
                            //
                            //SendMail sendMail=new SendMail(getApplicationContext(),receptor,"Alguem tentou desbloquear seu celular",filename,1,email,senha);
                            //sendMail.execute();

                            try {
                                FileOutputStream fos = new FileOutputStream(pictureFile);
                                fos.write(data);
                                fos.close();




                            } catch (Exception error) {
                                Log.d("CAM",
                                        "File" + filename + "not saved: " + error.getMessage());

                                Log.d("CAM","image could not be saved");
                            }
                            Log.d("Cam","Aqui acaba");

                        }

                        private File getDir() {
                            File sdDir = Environment
                                    .getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
                            return new File(sdDir, "Nao te Emprestei");
                        }

                    });
                } catch (Exception e) {
                    if (camera != null)
                        camera.release();
                    throw new RuntimeException(e);
                }
                Log.e("Cam","asdasd");
            }


            @Override
            public void surfaceDestroyed(SurfaceHolder holder) {
            }

            @Override
            public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {}
        });

        WindowManager wm = (WindowManager)context
                .getSystemService(Context.WINDOW_SERVICE);
        WindowManager.LayoutParams params = new WindowManager.LayoutParams(
                1, 1, //Must be at least 1x1
                WindowManager.LayoutParams.TYPE_SYSTEM_OVERLAY,
                0,
                //Don't know if this is a safe default
                PixelFormat.UNKNOWN);

        //Don't set the preview visibility to GONE or INVISIBLE
        wm.addView(preview, params);
    }

    private static void showMessage(String message) {
        Log.i("Camera", message);
    }


    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }}



