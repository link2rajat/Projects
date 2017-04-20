package com.example.rajatrathi.picture_capture;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class MainActivity extends Activity {

    Button btpic,btnup;
    String ba1;
    public static  String URL = "http://52.25.199.242/sagedom/index.php";
    String mCurrentPhotoPath;
    ImageView mImageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mImageView =(ImageView) findViewById(R.id.Imageprev);
        btpic = (Button)findViewById(R.id.cpic);

        btpic.setOnClickListener( new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                captureImage();
            }
        });

        btnup = (Button)findViewById(R.id.up);
        btnup.setOnClickListener( new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                upload();
            }
        });

    }
    public  void upload()
    {
        Bitmap bm = BitmapFactory.decodeFile(mCurrentPhotoPath);
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bm.compress(Bitmap.CompressFormat.JPEG, 90, byteArrayOutputStream);
        byte[] ba = byteArrayOutputStream.toByteArray();
        ba1 = Base64.encodeToString(ba, Base64.DEFAULT);

        new uploadToServer().execute();
    }

    private  void  captureImage()
    {

        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

        if (takePictureIntent.resolveActivity(getPackageManager())!= null)
        {
            File photoFile = null;

            try {
                photoFile = createImageFile();
                }
            catch (IOException e)
            {
                e.printStackTrace();
            }
            if (photoFile != null)
            {
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(photoFile));
                startActivityForResult(takePictureIntent,100);
            }
        }
    }

    protected  void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        if (requestCode ==100 && requestCode == RESULT_OK)
        {
        setPic();
        }
    }

    public class uploadToServer extends AsyncTask<Void,Void, String> {
        private ProgressDialog pd = new ProgressDialog(MainActivity.this);

        protected void onPreExecute() {
            super.onPreExecute();
            pd.setMessage("Wait till Image is uploading..!!");
            pd.show();
        }

        @Override
        protected String doInBackground(Void... params) {
            ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
            nameValuePairs.add(new BasicNameValuePair("base64", ba1));
            nameValuePairs.add(new BasicNameValuePair("ImageName", System.currentTimeMillis() + ".jpeg"));
            try {
                HttpClient httpClient = new DefaultHttpClient();
                HttpPost httppost = new HttpPost(URL);
                httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
                HttpResponse response = httpClient.execute(httppost);
                String st = EntityUtils.toString(response.getEntity());
                Log.v("log_tag", "In the try Loop" + st);
            } catch (Exception e) {
                Log.v("log_tag", "Error in http connection" + e.toString());
            }
            return "Success";
        }

        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            pd.hide();
            pd.dismiss();
        }
    }
        private  void setPic() {
            int targetW = mImageView.getWidth();
            int targetH = mImageView.getHeight();
            BitmapFactory.Options bmOptions = new BitmapFactory.Options();
            bmOptions.inJustDecodeBounds = true;
            BitmapFactory.decodeFile(mCurrentPhotoPath, bmOptions);
            int photoW = bmOptions.outWidth;
            int photoH = bmOptions.outHeight;

            int scaleFactor = Math.min(photoW / targetW, photoH / targetH);

            bmOptions.inJustDecodeBounds = false;
            bmOptions.inSampleSize = scaleFactor;
            bmOptions.inPurgeable = true;

            Bitmap bitmap = BitmapFactory.decodeFile(mCurrentPhotoPath, bmOptions);
            mImageView.setImageBitmap(bitmap);
        }
         private  File createImageFile() throws  IOException {

             String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
             String imageFileName = "JPEG_"+ timeStamp+"_";
             File storageDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);

             File image = File.createTempFile( imageFileName,".jpg",storageDir);
             mCurrentPhotoPath = image.getAbsolutePath();
             Log.e("Getpath","Nice"+mCurrentPhotoPath);
             return image;

        }
    }

