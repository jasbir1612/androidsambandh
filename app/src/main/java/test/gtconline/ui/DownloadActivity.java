package test.gtconline.ui;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import android.widget.VideoView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnPausedListener;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;

import java.io.File;
import java.io.FileInputStream;

import test.gtconline.R;

public class DownloadActivity extends AppCompatActivity {

//    VideoView videoView;
    Button btndownload,btnpdf, btnhindi, viewHindi, viewEng;
    ProgressDialog progressDialog;

    final String engURL="https://youtu.be/mH31g67Hjdk";
    final String engFURL = "https://firebasestorage.googleapis.com/v0/b/sambandh-a8609.appspot.com/o/video%2FPFL_Video_Eng.mp4?alt=media&token=ce345530-7dbb-4286-b5cc-525660602a49";
    final String hindiFURL = "https://firebasestorage.googleapis.com/v0/b/sambandh-a8609.appspot.com/o/video%2FPFL_Video_Hindi.mp4?alt=media&token=39eb8415-03ce-4b2b-8b2a-e9abdbb51b65";
    final String hindiURL="https://youtu.be/gMCcAhrfhP8";
    final String videoURL = "https://firebasestorage.googleapis.com/v0/b/sambandh-a8609.appspot.com/o/video.mp4?alt=media&token=4761a05d-e86e-4a04-8149-2fab3217a3c5";
    StorageReference storageReference;
    private FirebaseAuth mAuth;
    FirebaseStorage storage;
    Uri fileUri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_download);
//        videoView = findViewById(R.id.video);
        btndownload = findViewById(R.id.btdownload);
        btnpdf = findViewById(R.id.pdf);
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

        viewHindi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Intent.ACTION_VIEW,Uri.parse(hindiURL)));
//                Log.i("Video", "Video Playing....");
            }
        });

        viewEng.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Intent.ACTION_VIEW,Uri.parse(engURL)));
//                Log.i("Video", "Video Playing....");
            }
        });

        btnpdf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(DownloadActivity.this, PdfViewer.class);
                startActivity(i);
            }
        });


        btndownload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                storageReference = FirebaseStorage.getInstance().getReferenceFromUrl(engFURL);
                downloadFileinMemory(storageReference);
//                try {
//                    FileInputStream fis = new FileInputStream(new File(getCacheDir(), "cachefile"));
//                    if (fis != null) {
//
//                        storageReference.putStream(fis);
//                        Toast.makeText(DownloadActivity.this, "cache", Toast.LENGTH_SHORT).show();
//                    } else {
//                        storageReference = FirebaseStorage.getInstance().getReferenceFromUrl(videoURL);
//                        Toast.makeText(DownloadActivity.this, "url", Toast.LENGTH_SHORT).show();
//                        downloadFileinMemory(storageReference);
//                    }
//                } catch (FileNotFoundException e) {
//                    e.printStackTrace();
//                    Toast.makeText(DownloadActivity.this, "Downloaded", Toast.LENGTH_SHORT).show();
//                }
//                Toast.makeText(DownloadActivity.this, "Video will be available after Oct 29,2018", Toast.LENGTH_SHORT).show();
////                                downloadFileloc(storageReference);


            }
        });

        btnhindi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                storageReference = FirebaseStorage.getInstance().getReferenceFromUrl(hindiFURL);
                downloadFileinMemory(storageReference);
            }
        });

    }

    public void downloadFileinMemory(StorageReference fileReference) {
        if (fileReference != null) {

            progressDialog.setTitle("Downloading...");
            progressDialog.show();
            final long ONE_MEGABYTE = 10240 * 10240;

            fileReference.getBytes(ONE_MEGABYTE)
                    .addOnSuccessListener(new OnSuccessListener<byte[]>() {

                        @Override
                        public void onSuccess(byte[] bytes) {
                            progressDialog.dismiss();

                            Toast.makeText(DownloadActivity.this, "File Downloaded ", Toast.LENGTH_LONG).show();
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception exception) {
                            progressDialog.dismiss();

                            Toast.makeText(DownloadActivity.this, exception.getMessage(), Toast.LENGTH_LONG).show();
                        }
                    });
//                    .addOnProgressListener(new OnProgressListener<FileDownloadTask.TaskSnapshot>() {
//
//                        @Override
//                        public void onProgress(FileDownloadTask.TaskSnapshot taskSnapshot) {
//                            double progress = (100.0 * taskSnapshot.getBytesTransferred()) / taskSnapshot.getTotalByteCount();
//
//                            // percentage in progress dialog
//                            progressDialog.setMessage("Downloadeed " + ((int) progress) + "%...");
//                        }
//                    })
//                    .addOnPausedListener(new OnPausedListener<FileDownloadTask.TaskSnapshot>() {
//
//                        @Override
//                        public void onPaused(FileDownloadTask.TaskSnapshot taskSnapshot) {
//                            System.out.println("Upload is paused!");
//                        }
//                    });
        } else {
            Toast.makeText(DownloadActivity.this, "No File!", Toast.LENGTH_LONG).show();
        }

    }
}
