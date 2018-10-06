package test.testing.ui;

import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnPausedListener;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.IOException;
import java.util.List;

import test.testing.R;
import test.testing.pojo.request.UploadBody;
import test.testing.pojo.response.UploadResponse;
import test.testing.rest.ApiService;
import test.testing.rest.Database;
import test.testing.rest.ResponseCallback;

public class UploadActivity extends AppCompatActivity implements View.OnClickListener {
    private static final String TAG = "UploadActivity";
    //track Choosing Image Intent
    private static final int CHOOSING_IMAGE_REQUEST = 1234;
    String videoUrl = "https://firebasestorage.googleapis.com/v0/b/pet-simplified-automation.appspot.com/o/devpet%2FGB%2010sec%20video-1537724669843.mp4?alt=media&token=fa764176-c7bd-4f7d-9378-99009c3dfa40";
    String fileName, fileName2;
    ProgressDialog progressDialog;
    EditText dateEt, pledgesEt;
    private TextView tvFileName;
    private ImageView imageView;
    private Uri fileUri;
    private Bitmap bitmap;
    private StorageReference imageReference, imageReference2;
    private FirebaseAuth mAuth;
    private ApiService apiService;
    private Database db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload);

        apiService = new ApiService();
        db = new Database(getApplicationContext());

        imageView = findViewById(R.id.img_file);
        tvFileName = findViewById(R.id.tv_file_name);
        dateEt = findViewById(R.id.update);
        pledgesEt = findViewById(R.id.upnumber);
        tvFileName.setText("");

        mAuth = FirebaseAuth.getInstance();
        imageReference = FirebaseStorage.getInstance().getReference().child("images");
        imageReference2 = FirebaseStorage.getInstance().getReference().child("images2");

        progressDialog = new ProgressDialog(this);

        findViewById(R.id.btn_choose_file).setOnClickListener(this);
        findViewById(R.id.btn_upload_file).setOnClickListener(this);
        findViewById(R.id.btn_upload_file2).setOnClickListener(this);
        findViewById(R.id.btn_choose_file2).setOnClickListener(this);
        findViewById(R.id.btn_upload_data).setOnClickListener(this);

    }

    private void uploadFile() {
        if (fileUri != null) {

            fileName = fileUri.getLastPathSegment();
            if (!validateInputFileName(fileName)) {
                return;
            }

            progressDialog.setTitle("Uploading...");
            progressDialog.show();

            StorageReference fileRef = imageReference.child(fileName + "." + getFileExtension(fileUri));
            fileRef.putFile(fileUri)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            progressDialog.dismiss();

//                            Log.e(TAG, "Uri: " + taskSnapshot.getDownloadUrl());
                            Log.e(TAG, "Name: " + taskSnapshot.getMetadata().getName());

                            tvFileName.setText(taskSnapshot.getMetadata().getPath() + " - "
                                    + taskSnapshot.getMetadata().getSizeBytes() / 1024 + " KBs");
                            Toast.makeText(UploadActivity.this, "File Uploaded ", Toast.LENGTH_LONG).show();
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception exception) {
                            progressDialog.dismiss();

                            Toast.makeText(UploadActivity.this, exception.getMessage(), Toast.LENGTH_LONG).show();
                        }
                    })
                    .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                            // progress percentage
                            double progress = (100.0 * taskSnapshot.getBytesTransferred()) / taskSnapshot.getTotalByteCount();

                            // percentage in progress dialog
                            progressDialog.setMessage("Uploaded " + ((int) progress) + "%...");
                        }
                    })
                    .addOnPausedListener(new OnPausedListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onPaused(UploadTask.TaskSnapshot taskSnapshot) {
                            System.out.println("Upload is paused!");
                        }
                    });
        } else {
            Toast.makeText(UploadActivity.this, "No File!", Toast.LENGTH_LONG).show();
        }


    }

    private void uploadFile2() {
        if (fileUri != null) {
            fileName2 = fileUri.getLastPathSegment();

            if (!validateInputFileName(fileName2)) {
                return;
            }

            progressDialog.setTitle("Uploading...");
            progressDialog.show();

            StorageReference fileRef2 = imageReference2.child(fileName2 + "." + getFileExtension(fileUri));
            fileRef2.putFile(fileUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    progressDialog.dismiss();
                    Log.e(TAG, "Name: " + taskSnapshot.getMetadata().getName());

                    tvFileName.setText(taskSnapshot.getMetadata().getPath() + " - "
                            + taskSnapshot.getMetadata().getSizeBytes() / 1024 + " KBs");
                    Toast.makeText(UploadActivity.this, "File Uploaded ", Toast.LENGTH_LONG).show();

                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    progressDialog.dismiss();

                    Toast.makeText(UploadActivity.this, e.getMessage(), Toast.LENGTH_LONG).show();
                }
            }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                    double progress = (100.0 * taskSnapshot.getBytesTransferred()) / taskSnapshot.getTotalByteCount();

                    // percentage in progress dialog
                    progressDialog.setMessage("Uploaded " + ((int) progress) + "%...");

                }
            }).addOnPausedListener(new OnPausedListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onPaused(UploadTask.TaskSnapshot taskSnapshot) {
                    System.out.println("Upload is paused!");
                }
            });

        } else {
            Toast.makeText(UploadActivity.this, "No File!", Toast.LENGTH_LONG).show();
        }

    }

    private void showChoosingFile() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Image"), CHOOSING_IMAGE_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (bitmap != null) {
            bitmap.recycle();
        }

        if (requestCode == CHOOSING_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            fileUri = data.getData();
            try {
                bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), fileUri);
                imageView.setImageBitmap(bitmap);

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void onClick(View v) {

        int i = v.getId();

        if (i == R.id.btn_choose_file) {
            showChoosingFile();
        } else if (i == R.id.btn_upload_file) {
            uploadFile();
        } else if (i == R.id.btn_choose_file2) {
            showChoosingFile();
        } else if (i == R.id.btn_upload_file2) {
            uploadFile2();
        } else if (i == R.id.btn_upload_data) {
            uploadForm();
        }

    }

    private void uploadForm() {
        String date = dateEt.getText().toString().trim();
        String pledge = pledgesEt.getText().toString().trim();

        if (TextUtils.isEmpty(date)) {
            Toast.makeText(this, "Enter date.", Toast.LENGTH_SHORT).show();
            return;
        }

        if (TextUtils.isEmpty(pledge)) {
            Toast.makeText(this, "Enter number of students pledged.", Toast.LENGTH_SHORT).show();
            return;
        }

        UploadBody body = new UploadBody(date, pledge, db.getMobile(), "", db.getMobile());

        apiService.upload(body, new ResponseCallback<List<UploadResponse>>() {
            @Override
            public void success(List<UploadResponse> uploadResponses) {
                Toast.makeText(UploadActivity.this, "Uploaded successfully", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void failure(List<UploadResponse> uploadResponses) {
                Toast.makeText(UploadActivity.this, "Error in uploading. Try again later.", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private String getFileExtension(Uri uri) {
        ContentResolver contentResolver = getContentResolver();
        MimeTypeMap mime = MimeTypeMap.getSingleton();
        return mime.getExtensionFromMimeType(contentResolver.getType(uri));
    }

    private boolean validateInputFileName(String fileName) {

        if (TextUtils.isEmpty(fileName)) {
            Toast.makeText(UploadActivity.this, "Enter file name!", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }
}
