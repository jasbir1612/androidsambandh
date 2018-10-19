package test.gtconline.ui;

import android.app.ProgressDialog;
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
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import test.gtconline.R;

public class DownloadActivity extends AppCompatActivity {

    VideoView videoView;
    Button btndownload,btnpdf, btnhindi;
    ProgressDialog progressDialog;

    final String videoURL = "https://firebasestorage.googleapis.com/v0/b/sambandh-a8609.appspot.com/o/video.mp4?alt=media&token=4761a05d-e86e-4a04-8149-2fab3217a3c5";
    StorageReference storageReference;
    private FirebaseAuth mAuth;
    Uri fileUri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_download);
        videoView = findViewById(R.id.video);
        btndownload = findViewById(R.id.btdownload);
        btnpdf = findViewById(R.id.pdf);
        btnhindi = findViewById(R.id.btdownload_hindi);
//        storage = FirebaseStorage.getInstance();
//        storageReference = storage.getReference();
        mAuth = FirebaseAuth.getInstance();

        storageReference = FirebaseStorage.getInstance().getReferenceFromUrl(videoURL);
        progressDialog = new ProgressDialog(this);
        Log.d("storage", "storageref: " + storageReference);


        fileUri = Uri.parse(videoURL);
        Log.d("fileURI", fileUri.toString());
        videoView.setVideoURI(fileUri);
        videoView.requestFocus();
//        videoView.start();
        
        btnpdf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(DownloadActivity.this, "PDF will be available after Oct 18, 2018", Toast.LENGTH_SHORT).show();
            }
        });


        btndownload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                downloadFileinMemory(storageReference);
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
                Toast.makeText(DownloadActivity.this, "Video will be available after Oct 18,2018", Toast.LENGTH_SHORT).show();
//                                downloadFileloc(storageReference);


            }
        });

        btnhindi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(DownloadActivity.this, "Video will be available after Oct 18,2018", Toast.LENGTH_SHORT).show();


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
