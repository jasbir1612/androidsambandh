package test.gtconline.ui;

import android.Manifest;
import android.app.DownloadManager;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import test.gtconline.ErrorHandler.AppCrashHandler;
import test.gtconline.R;

public class DownloadActivty2 extends AppCompatActivity {

    EditText mobileEt;
    Button proceedButton, btdownload, btdownload_hindi, vEngVideo, vHindiVideo, vInstructions_btn, download_instructions_btn;

    final String pdfDownload = "https://firebasestorage.googleapis.com/v0/b/pledge-for-life.appspot.com/o/pdf%2FPFL_Instructions.pdf?alt=media&token=dfe74274-c5d1-4344-8f1e-5d4faba562c4";
    final String engURL = "https://youtu.be/mH31g67Hjdk";
    final String engFURL = "https://firebasestorage.googleapis.com/v0/b/pledge-for-life.appspot.com/o/PFL_Video_Eng.mp4?alt=media&token=02775ddf-06e6-4a82-a1fc-6fc400ac8c7f";
    final String hindiFURL = "https://firebasestorage.googleapis.com/v0/b/pledge-for-life.appspot.com/o/PFL_Video_Hindi.mp4?alt=media&token=44c7847a-1b50-4718-8350-34114a29a660";
    final String hindiURL = "https://youtu.be/gMCcAhrfhP8";
    final String videoURL = "https://firebasestorage.googleapis.com/v0/b/sambandh-a8609.appspot.com/o/video.mp4?alt=media&token=4761a05d-e86e-4a04-8149-2fab3217a3c5";
    StorageReference storageReference, videoReference;

    private FirebaseAuth mAuth;
    FirebaseStorage storage;
    Uri fileUri;
    int feng = 0;
    int fhindi = 0;
    int fpdf = 0;
    String filename;

    ProgressDialog progressDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_download_activty2);

        Thread.setDefaultUncaughtExceptionHandler(new AppCrashHandler(getApplicationContext()));


        mobileEt = findViewById(R.id.et_number);
        proceedButton = findViewById(R.id.proceed_btn);
        btdownload = findViewById(R.id.btdownload2);
        btdownload_hindi = findViewById(R.id.btdownload_hindi2);
        vInstructions_btn = findViewById(R.id.viewpdf2_2);
        download_instructions_btn = findViewById(R.id.pdf2);
        vEngVideo = findViewById(R.id.vengvideo2);
        vHindiVideo = findViewById(R.id.vhindivideo2);

        btdownload_hindi.setEnabled(false);
        btdownload.setEnabled(false);
        vInstructions_btn.setEnabled(false);
        download_instructions_btn.setEnabled(false);
        vEngVideo.setEnabled(false);
        vHindiVideo.setEnabled(false);

        final StorageReference storageRef = FirebaseStorage.getInstance().getReference();
        videoReference = storageRef.child("video/video.mp4");

        progressDialog = new ProgressDialog(this);
        Log.d("storage", "storageref: " + storageReference);


        fileUri = Uri.parse(videoURL);
        Log.d("fileURI", fileUri.toString());

        proceedButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (mobileEt.getText().toString().trim().matches("^[6-9][0-9]{9}$")) {
                    mobileEt.setEnabled(false);
                    proceedButton.setEnabled(false);

                    btdownload.setEnabled(true);
                    btdownload_hindi.setEnabled(true);
                    vInstructions_btn.setEnabled(true);
                    download_instructions_btn.setEnabled(true);
                    vHindiVideo.setEnabled(true);
                    vEngVideo.setEnabled(true);


                    btdownload.setBackgroundColor(ContextCompat.getColor(DownloadActivty2.this, R.color.login_btn_color));
                    vEngVideo.setBackgroundColor(ContextCompat.getColor(DownloadActivty2.this, R.color.register_btn_color));
                    vHindiVideo.setBackgroundColor(ContextCompat.getColor(DownloadActivty2.this, R.color.login_btn_color));
                    btdownload_hindi.setBackgroundColor(ContextCompat.getColor(DownloadActivty2.this, R.color.register_btn_color));
                    vInstructions_btn.setBackgroundColor(ContextCompat.getColor(DownloadActivty2.this, R.color.login_btn_color));
                    download_instructions_btn.setBackgroundColor(ContextCompat.getColor(DownloadActivty2.this, R.color.register_btn_color));

                    proceedButton.setBackgroundColor(ContextCompat.getColor(DownloadActivty2.this, R.color.gray_btn_color));

                } else {
                    Toast.makeText(DownloadActivty2.this, "Invalid Phone Number", Toast.LENGTH_SHORT).show();
                }
            }
        });

        vInstructions_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(DownloadActivty2.this, PdfViewer.class);
                startActivity(i);
            }
        });

        vHindiVideo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(hindiURL)));
//                Log.i("Video", "Video Playing....");
            }
        });

        vEngVideo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(engURL)));
//                Log.i("Video", "Video Playing....");

            }
        });

        download_instructions_btn.setOnClickListener(new View.OnClickListener() {
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

                if (isStoragePermissionGranted()) {
                    fpdf = 1;
                    storageReference = FirebaseStorage.getInstance().getReferenceFromUrl(pdfDownload);
                    downloadFirebaseFile(storageReference);
                }

            }
        });

        btdownload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isStoragePermissionGranted()) {
                    feng = 1;
                    storageReference = FirebaseStorage.getInstance().getReferenceFromUrl(engFURL);
                    downloadFirebaseFile(storageReference);
                }
            }
        });

        btdownload_hindi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isStoragePermissionGranted()) {
                    fhindi = 1;
                    storageReference = FirebaseStorage.getInstance().getReferenceFromUrl(hindiFURL);
                    downloadFirebaseFile(storageReference);
                }
            }
        });

    }



    public void downloadFirebaseFile(StorageReference fileReference) {

        if (fileReference != null) {
            progressDialog.setTitle("Downloading...");
            progressDialog.show();

            if (fhindi == 1) {
                filename = "pfl_hindi.mp4";
            }
            if (feng == 1) {
                filename = "pfl_eng.mp4";
            }
            if (fpdf == 1) {
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
                        Log.d("pflVideo", localFile.getPath());
                        if (fpdf == 1) {
                            Toast.makeText(DownloadActivty2.this, "Downloaded PDF", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(DownloadActivty2.this, "Downloaded video", Toast.LENGTH_SHORT).show();
                        }
                        startActivity(new Intent(DownloadManager.ACTION_VIEW_DOWNLOADS));
                        feng = fpdf = feng = 0;
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        progressDialog.dismiss();
                        Log.d("Error", e.toString());
                        Toast.makeText(DownloadActivty2.this, "Unable to download video", Toast.LENGTH_SHORT).show();

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

    public  boolean isStoragePermissionGranted() {
        if (Build.VERSION.SDK_INT >= 23) {
            if (checkSelfPermission(android.Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    == PackageManager.PERMISSION_GRANTED) {
                Log.v("Storage", "Permission is granted");
                return true;
            } else {

                Log.v("Storage", "Permission is revoked");
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
                return false;
            }
        } else { //permission is automatically granted on sdk<23 upon installation
            Log.v("Storage", "Permission is granted");
            return true;
        }

    }

}
