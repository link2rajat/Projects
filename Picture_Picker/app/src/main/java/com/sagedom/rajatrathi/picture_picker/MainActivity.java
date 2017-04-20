package com.sagedom.rajatrathi.picture_picker;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.ByteArrayBody;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.InputStreamReader;
import java.util.Date;

public class MainActivity extends Activity {

    private static final int PICTURE_SELECT =0;
    private ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        imageView = (ImageView)findViewById(R.id.icon);
    }

    public void pickPhoto(View v)
    {
        Intent i = new Intent();
        i.setType("image/*");
        i.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(i,"Select Picture"),PICTURE_SELECT);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    if (resultCode == RESULT_OK)
    {
        Bitmap bitmap = getPath(data.getData());
        imageView.setImageBitmap(bitmap);
    }
    }

    private Bitmap getPath(Uri uri)
    {
        String[] projection ={ MediaStore.Images.Media.DATA };
        Cursor cursor = managedQuery(uri,projection,null,null,null);
        int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        cursor.moveToFirst();
        String filePath = cursor.getString(column_index);
        cursor.close();

        Bitmap bitmap = BitmapFactory.decodeFile(filePath);
      return bitmap;
    }

    public void uploadPhoto(View view)
    {
        try
        {
            executeMultipartPost();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

    }

    public void executeMultipartPost() throws Exception
    {
     try {
         ByteArrayOutputStream bos = new ByteArrayOutputStream();
         BitmapDrawable drawable = (BitmapDrawable) imageView.getDrawable();
         Bitmap bitmap = drawable.getBitmap();
         bitmap.compress(Bitmap.CompressFormat.JPEG, 50, bos);
         byte[] data = bos.toByteArray();
         HttpClient httpClient = new DefaultHttpClient();
         HttpPost postRequest = new HttpPost("http://52.25.199.242/sagedom/index.php");
         String fileName = String.format("File_%d.png", new Date().getTime());
         ByteArrayBody byteArrayBody = new ByteArrayBody(data, fileName);

         // File file = new File("/mnt/sdcard/forest.png");
          // FileBody bin = new FileBody(file);

         MultipartEntity reqEntity = new MultipartEntity(HttpMultipartMode.BROWSER_COMPATIBLE);
         reqEntity.addPart("file", byteArrayBody);
         postRequest.setEntity(reqEntity);
         int timeoutConnection = 73000;
         HttpParams httpParams = new BasicHttpParams();
         HttpConnectionParams.setConnectionTimeout(httpParams, timeoutConnection);
         int timeoutSocket = 73000;
         HttpConnectionParams.setSoTimeout(httpParams, timeoutSocket);
         HttpConnectionParams.setTcpNoDelay(httpParams, true);

         HttpResponse response = httpClient.execute(postRequest);

         BufferedReader reader = new BufferedReader(new InputStreamReader(response.getEntity().getContent(), "UTF-8"));
         String sResponse;
         StringBuilder s = new StringBuilder();

         while ((sResponse = reader.readLine()) != null) {
             s = s.append(sResponse);

         }

         System.out.println("Response: " + s);
     }
     catch (Exception e)

     {
         e.printStackTrace();
     }


     }

    }


