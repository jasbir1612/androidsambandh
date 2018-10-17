package test.testing.ui;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.DatePicker;
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
import java.util.Calendar;
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
    EditText pledgesEt;
    private TextView tvFileName, dateEt;
    private ImageView imageView;
    private Uri fileUri;
    private Bitmap bitmap;
    private StorageReference imageReference, imageReference2;
    private FirebaseAuth mAuth;
    private ApiService apiService;
    private Database db;
    Button upload1, upload2 ,reUpload;
    Calendar myCalendar;
    String dateST, datecurrent;
    int flag = 0;
    int year, month, day;
    long back2 =1000*30*60*60;
    long back = back2*24;
    private DatePickerDialog.OnDateSetListener date;

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
        reUpload = findViewById(R.id.btn_reupload);
        reUpload.setVisibility(View.GONE);
        tvFileName.setText("");
        myCalendar = Calendar.getInstance();

        mAuth = FirebaseAuth.getInstance();
        imageReference = FirebaseStorage.getInstance().getReference().child("images");
        imageReference2 = FirebaseStorage.getInstance().getReference().child("images2");

        progressDialog = new ProgressDialog(this);

        findViewById(R.id.btn_choose_file).setOnClickListener(this);
        reUpload.setOnClickListener(this);
        upload1 = findViewById(R.id.btn_upload_file);
        upload1.setOnClickListener(this);
        upload1.setEnabled(true);
        upload2 = findViewById(R.id.btn_upload_file2);
        upload2.setOnClickListener(this);
        upload2.setEnabled(true);
        findViewById(R.id.btn_upload_data).setOnClickListener(this);
        year = myCalendar.get(Calendar.YEAR);
        month = myCalendar.get(Calendar.MONTH);
        day = myCalendar.get(Calendar.DAY_OF_MONTH);
        datecurrent = month + "/" +day+"/" + year;


        dateEt.setOnClickListener(this);

    }

    private void selectDate(){

        final DatePickerDialog dialog = new DatePickerDialog(UploadActivity.this,android.R.style.Theme_DeviceDefault, date,year, month, day);
//        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.getDatePicker().setMaxDate(System.currentTimeMillis());
        dialog.getDatePicker().setMinDate(System.currentTimeMillis()- back);
        dialog.show();


        date  = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int dyear, int dmonth, int ddayOfMonth) {
                dmonth = dmonth+1;
                dateST = dmonth + "/" +ddayOfMonth+"/" + dyear;
                Log.d("date", dateST);


                Toast.makeText(UploadActivity.this, dateST, Toast.LENGTH_SHORT).show();
                dateEt.setText(dateST);
            }
        };
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

                            upload1.setText("Uploaded "+taskSnapshot.getMetadata().getPath() + " - "
                                    + taskSnapshot.getMetadata().getSizeBytes() / 1024 + " KBs");
                            upload1.setEnabled(false);
                            upload1.setBackgroundColor(ContextCompat.getColor(UploadActivity.this, R.color.gray_btn_color));
                            imageView.setImageBitmap(null);
                            reUpload.setVisibility(View.VISIBLE);
                            flag = 1;
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

                    upload2.setText("Uploaded "+taskSnapshot.getMetadata().getPath() + " - "
                            + taskSnapshot.getMetadata().getSizeBytes() / 1024 + " KBs");
                    upload2.setBackgroundColor(ContextCompat.getColor(UploadActivity.this, R.color.gray_btn_color));
                    upload2.setEnabled(false);
                    imageView.setImageBitmap(null);
                    reUpload.setVisibility(View.VISIBLE);
                    flag = 1;
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
            imageView.setImageBitmap(null);
        } else if (i == R.id.btn_upload_file) {
            uploadFile();
        } else if (i == R.id.btn_upload_file2) {
            uploadFile2();
        } else if (i == R.id.btn_upload_data) {
//            selectDate();
            uploadForm();
        }else if(i ==R.id.btn_reupload)
        {
            upload1.setEnabled(true);
            upload1.setBackgroundColor(ContextCompat.getColor(UploadActivity.this, R.color.login_btn_color));
            upload1.setText("Upload Image 1");
            upload2.setEnabled(true);
            upload2.setBackgroundColor(ContextCompat.getColor(UploadActivity.this, R.color.login_btn_color));
            upload2.setText("Upload Image 2");
            flag = 0;


        }else if(i==R.id.update)
        {
            selectDate();
        }

    }

    private void uploadForm() {

        if(flag==1) {
            String date = dateST;
            String pledge = pledgesEt.getText().toString().trim();

            if (TextUtils.isEmpty(date)) {
                Toast.makeText(this, "Enter date.", Toast.LENGTH_SHORT).show();
                return;
            }

            if (TextUtils.isEmpty(pledge)) {
                Toast.makeText(this, "Enter number of students pledged.", Toast.LENGTH_SHORT).show();
                return;
            }
            if (pledge.length() > 5000) {
                Toast.makeText(this, "Please enter students less than 5000", Toast.LENGTH_SHORT).show();
            }

            UploadBody body = new UploadBody(date, pledge, db.getMobile(), "", db.getMobile());

            apiService.upload(body, new ResponseCallback<List<UploadResponse>>() {
                @Override
                public void success(List<UploadResponse> uploadResponses) {
                    Toast.makeText(UploadActivity.this, "Uploaded successfully", Toast.LENGTH_SHORT).show();
                    logout();
                }

                @Override
                public void failure(List<UploadResponse> uploadResponses) {
                    Toast.makeText(UploadActivity.this, "Error in uploading. Try again later.", Toast.LENGTH_SHORT).show();
                }
            });
        }else{
            Toast.makeText(this, "Please upload atleast one Image", Toast.LENGTH_SHORT).show();
        }
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
    private void logout(){
        db.clear();
        Intent intent = new Intent(UploadActivity.this, SplashActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        finish();
    }
}
