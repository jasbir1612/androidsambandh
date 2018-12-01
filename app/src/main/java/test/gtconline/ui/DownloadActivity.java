package test.gtconline.ui;

import android.Manifest;
import android.app.DownloadManager;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import android.widget.VideoView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.JsonRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.commit451.youtubeextractor.YouTubeExtractionResult;
import com.commit451.youtubeextractor.YouTubeExtractor;
import com.google.android.gms.flags.Flag;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnPausedListener;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.gson.JsonObject;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import test.gtconline.R;

public class DownloadActivity extends AppCompatActivity {

//    VideoView videoView;
    Button btndownload,btnpdf, btnhindi, viewHindi, viewEng, viewPdf;
    ProgressDialog progressDialog;

    final String pdfDownload = "https://firebasestorage.googleapis.com/v0/b/pledge-for-life.appspot.com/o/pdf%2FPFL_Instructions.pdf?alt=media&token=dfe74274-c5d1-4344-8f1e-5d4faba562c4";
    final String engURL="https://youtu.be/mH31g67Hjdk";
    final String engFURL = "https://firebasestorage.googleapis.com/v0/b/pledge-for-life.appspot.com/o/PFL_Video_Eng.mp4?alt=media&token=02775ddf-06e6-4a82-a1fc-6fc400ac8c7f";
    final String hindiFURL = "https://firebasestorage.googleapis.com/v0/b/pledge-for-life.appspot.com/o/PFL_Video_Hindi.mp4?alt=media&token=44c7847a-1b50-4718-8350-34114a29a660";
    final String hindiURL="https://youtu.be/gMCcAhrfhP8";
    final String videoURL = "https://firebasestorage.googleapis.com/v0/b/sambandh-a8609.appspot.com/o/video.mp4?alt=media&token=4761a05d-e86e-4a04-8149-2fab3217a3c5";
    StorageReference storageReference, videoReference;
    private FirebaseAuth mAuth;
    FirebaseStorage storage;
    Uri fileUri;
    int feng = 0;
    int fhindi = 0;
    int fpdf = 0;
    String filename;
    RequestQueue volleyQueue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_download);

        final StorageReference storageRef = FirebaseStorage.getInstance().getReference();
        videoReference = storageRef.child("video/video.mp4");

        volleyQueue= Volley.newRequestQueue(this);


//        videoView = findViewById(R.id.video);
        btndownload = findViewById(R.id.btdownload);
        btnpdf = findViewById(R.id.pdf);
        viewPdf = findViewById(R.id.viewpdf);
        btnhindi = findViewById(R.id.btdownload_hindi);
//        storage = FirebaseStorage.getInstance();
//        storageReference = storage.getReference();
        mAuth = FirebaseAuth.getInstance();
        viewEng = findViewById(R.id.vengvideo);
        viewHindi = findViewById(R.id.vhindivideo);


        progressDialog = new ProgressDialog(this);
        Log.d("storage", "storageref: " + storageReference);


        fileUri = Uri.parse(videoURL);
        Log.d("fileURI", fileUri.toString());
//        videoView.setVideoURI(fileUri);
//        videoView.requestFocus();
//        videoView.start();

//        videoView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                startActivity(new Intent(Intent.ACTION_VIEW,Uri.parse("https://youtu.be/mH31g67Hjdk")));
//                Log.i("Video", "Video Playing....");
//            }
//        });

        viewPdf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(DownloadActivity.this, PdfViewer.class);
                startActivity(i);
            }
        });

        viewHindi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(hindiURL)));
//                Log.i("Video", "Video Playing....");
            }
        });

        viewEng.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(engURL)));
//                Log.i("Video", "Video Playing....");

            }
        });

        btnpdf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

//                File pdfdir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS);
//                final File pdfFile = new File(pdfdir, "pfl_instructions.pdf");
//                try {
//                    pdfFile.createNewFile();
//                } catch (IOException e1) {
//                    e1.printStackTrace();
//                }
//                DownloadPdfFile(pdfDownload, pdfFile);
//                Toast.makeText(DownloadActivity.this, "Instructions Downloaded", Toast.LENGTH_SHORT).show();

                if(isStoragePermissionGranted())
                {
                    fpdf = 1;
                        storageReference = FirebaseStorage.getInstance().getReferenceFromUrl(pdfDownload);
                        downloadFirebaseFile(storageReference);
                    }

                    postRequest( getIntent().getExtras().getString("mobileNumber"),"PDF");

            }
        });

        btndownload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isStoragePermissionGranted())
                {
                        feng = 1;
                        storageReference = FirebaseStorage.getInstance().getReferenceFromUrl(engFURL);
                        downloadFirebaseFile(storageReference);



                      postRequest( getIntent().getExtras().getString("mobileNumber"),"VIDEO");

                }
            }
        });

        btnhindi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isStoragePermissionGranted()) {
                    fhindi = 1;
                        storageReference = FirebaseStorage.getInstance().getReferenceFromUrl(hindiFURL);
                        downloadFirebaseFile(storageReference);

                        postRequest( getIntent().getExtras().getString("mobileNumber"),"VIDEO");

                    }
            }
        });

    }


    public void downloadFirebaseFile(StorageReference fileReference){

        if(fileReference!=null) {
            progressDialog.setTitle("Downloading...");
            progressDialog.show();

            if(fhindi == 1)
            {
                filename = "pfl_hindi.mp4";
            }
            if(feng ==1)
            {
                filename = "pfl_eng.mp4";
            }
            if(fpdf ==1)
            {
                filename = "pfl_instructions.pdf";

            }
            try {
                File dir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS);
                final File localFile = new File(dir, filename);
                try {
                    localFile.createNewFile();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
                fileReference.getFile(localFile).addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {
                        progressDialog.dismiss();
                        Log.d("pflVideo",localFile.getPath());
                        if(fpdf == 1)
                        {
                            Toast.makeText(DownloadActivity.this, "Downloaded PDF", Toast.LENGTH_SHORT).show();
                        }
                        else {
                            Toast.makeText(DownloadActivity.this, "Downloaded video", Toast.LENGTH_SHORT).show();
                        }
                        startActivity(new Intent(DownloadManager.ACTION_VIEW_DOWNLOADS));
                        feng = fpdf = feng =0;
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        progressDialog.dismiss();
                        Log.d("Error", e.toString());
                        Toast.makeText(DownloadActivity.this, "Unable to download video", Toast.LENGTH_SHORT).show();

                    }
                }).addOnProgressListener(new OnProgressListener<FileDownloadTask.TaskSnapshot>() {
                    @Override
                    public void onProgress(FileDownloadTask.TaskSnapshot taskSnapshot) {
                        double progress = (100.0 * taskSnapshot.getBytesTransferred()) / taskSnapshot.getTotalByteCount();
                        // percentage in progress dialog
                        progressDialog.setMessage("Downloaded " + ((int) progress) + "%...");

                    }
                });

            } catch (Exception e) {

                progressDialog.dismiss();
                Toast.makeText(this, "Network error", Toast.LENGTH_SHORT).show();
                Log.d("Error", e.toString());
            }
        }

    }

    public void DownloadPdfFile(String fileURL, File directory) {
        
        
        try {
            FileOutputStream f = new FileOutputStream(directory);
            URL u = new URL(fileURL);
            HttpURLConnection c = (HttpURLConnection) u.openConnection();
            c.setRequestMethod("GET");
            c.setDoOutput(true);
            c.connect();

            InputStream in = c.getInputStream();

            byte[] buffer = new byte[1024];
            int len1 = 0;
            while ((len1 = in.read(buffer)) > 0) {
                f.write(buffer, 0, len1);
            }
            f.close();
            progressDialog.dismiss();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    public void postRequest(final String mobileNumber, final String downloadType)
    {


      //  final String moblieNumber="7678579823";
     //   final String downloadType="PDF";

        String url="http://sambandhhealthapiv2.sambandhhealth.org/api/Sambandh/DownloadFile?MobileNo="+mobileNumber+"&DownloadType="+downloadType;

        StringRequest postRequest = new StringRequest(Request.Method.POST, url, new com.android.volley.Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                Log.v("response",response);
            }
        },null){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> paramsMap=new HashMap();

                paramsMap.put("MobileNo",mobileNumber);
                paramsMap.put("DownloadType",downloadType);
                return paramsMap;

            }
        };



        volleyQueue.add(postRequest);

    }

    public  boolean isStoragePermissionGranted() {
        if (Build.VERSION.SDK_INT >= 23) {
            if (checkSelfPermission(android.Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    == PackageManager.PERMISSION_GRANTED) {
                Log.v("Storage","Permission is granted");
                return true;
            } else {

                Log.v("Storage","Permission is revoked");
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
                return false;
            }
        }
        else { //permission is automatically granted on sdk<23 upon installation
            Log.v("Storage","Permission is granted");
            return true;
        }
    }
}
