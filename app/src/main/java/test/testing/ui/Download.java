package test.testing.ui;

import android.app.DownloadManager;
import android.app.ProgressDialog;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import android.widget.VideoView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnPausedListener;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.File;
import java.io.IOException;

import test.testing.R;

public class Download extends AppCompatActivity {

    VideoView videoView;
    Button btndownload;
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
//        storage = FirebaseStorage.getInstance();
//        storageReference = storage.getReference();
        mAuth = FirebaseAuth.getInstance();

        storageReference = FirebaseStorage.getInstance().getReferenceFromUrl(videoURL);
        progressDialog = new ProgressDialog(this);
        Log.d("storage", "storageref: " +storageReference);


        fileUri= Uri.parse(videoURL);
        Log.d("fileURI", fileUri.toString());
        videoView.setVideoURI(fileUri);
        videoView.requestFocus();
        videoView.start();


        btndownload.setOnClickListener(new View.OnClickListener() {
            @Override
                public void onClick(View v) {

//                downloadFileinMemory(storageReference);
                                downloadFileloc(storageReference);


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

                            Toast.makeText(Download.this, "File Downloaded ", Toast.LENGTH_LONG).show();
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception exception) {
                            progressDialog.dismiss();

                            Toast.makeText(Download.this, exception.getMessage(), Toast.LENGTH_LONG).show();
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
            Toast.makeText(Download.this, "No File!", Toast.LENGTH_LONG).show();
        }

    }

    public void downloadFileloc(StorageReference reference)
    {
        if (reference != null) {
            progressDialog.setTitle("Downloading...");
            progressDialog.setMessage(null);
            progressDialog.show();

            try {
                final File file = File.createTempFile("video", ".mp4");
                Log.d("fname", file.toString());
                reference.getFile(file).addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {
                        Toast.makeText(Download.this, "File Downloaded", Toast.LENGTH_SHORT).show();
                        progressDialog.dismiss();

                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        progressDialog.dismiss();
                        Toast.makeText(Download.this, e.getMessage(), Toast.LENGTH_LONG).show();

                    }
                }).addOnProgressListener(new OnProgressListener<FileDownloadTask.TaskSnapshot>() {
                    @Override
                    public void onProgress(FileDownloadTask.TaskSnapshot taskSnapshot) {
                        double progress = (100.0 * taskSnapshot.getBytesTransferred()) / taskSnapshot.getTotalByteCount();

                        // percentage in progress dialog
                        progressDialog.setMessage("Downloaded " + ((int) progress) + "%...");

                    }
                });

            } catch (IOException e) {
                e.printStackTrace();
            }

        }else{
            Toast.makeText(this, "No File", Toast.LENGTH_SHORT).show();
        }
        Log.d("referece", reference.toString());

    }
}
