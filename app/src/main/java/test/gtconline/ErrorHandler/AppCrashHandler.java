package test.gtconline.ErrorHandler;


import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Parcelable;
import android.os.SystemClock;
import android.util.Log;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.text.DateFormat;
import java.util.Date;

import test.gtconline.BuildConfig;
import test.gtconline.pojo.request.ErrorMessage;
import test.gtconline.ui.ErrorDisplay;

public class AppCrashHandler implements
            java.lang.Thread.UncaughtExceptionHandler {
        private final Context myContext;
        private final String LINE_SEPARATOR = "\n";

        public AppCrashHandler(Context context) {
            myContext = context;
        }

        public void uncaughtException(Thread thread, Throwable exception) {
            StringWriter stackTrace = new StringWriter();
            exception.printStackTrace(new PrintWriter(stackTrace));
            StringBuilder errorReport = new StringBuilder();
            errorReport.append("************ CAUSE OF ERROR ************\n\n");
            errorReport.append(stackTrace.toString());

            errorReport.append("\n************ DEVICE INFORMATION ***********\n");
            errorReport.append("Brand: ");
            errorReport.append(Build.BRAND);
            errorReport.append(LINE_SEPARATOR);
            errorReport.append("Device: ");
            errorReport.append(Build.DEVICE);
            errorReport.append(LINE_SEPARATOR);
            errorReport.append("Model: ");
            errorReport.append(Build.MODEL);
            errorReport.append(LINE_SEPARATOR);
            errorReport.append("Id: ");
            errorReport.append(Build.ID);
            errorReport.append(LINE_SEPARATOR);
            errorReport.append("Product: ");
            errorReport.append(Build.PRODUCT);
            errorReport.append(LINE_SEPARATOR);
            errorReport.append("\n************ FIRMWARE ************\n");
            errorReport.append("SDK: ");
            errorReport.append(Build.VERSION.SDK);
            errorReport.append(LINE_SEPARATOR);
            errorReport.append("Release: ");
            errorReport.append(Build.VERSION.RELEASE);
            errorReport.append(LINE_SEPARATOR);
            errorReport.append("Incremental: ");
            errorReport.append(Build.VERSION.INCREMENTAL);
            errorReport.append(LINE_SEPARATOR);


            ErrorMessage errorMessage=new ErrorMessage();
            errorMessage.setErrorTrace(stackTrace.toString());
            errorMessage.setDeviceBrand(Build.BRAND);
            errorMessage.setDeviceName(Build.DEVICE);
            errorMessage.setDeviceModel(Build.MODEL);
            errorMessage.setDeviceId(Build.ID);
            errorMessage.setProduct(Build.PRODUCT);
            errorMessage.setSdkVersion(Build.VERSION.SDK);
            errorMessage.setReleseVersion(Build.VERSION.RELEASE);
            errorMessage.setIncrementalvVersion(Build.VERSION.INCREMENTAL);

            errorMessage.setErrorMessage(null);
            errorMessage.setMobileNo("9998887642");



            Date d=new Date();
            String date= (String) android.text.format.DateFormat.format("MMMMM/dd/yyyy",d.getTime());

            errorMessage.setDateTime(date);

            errorMessage.setAppVersion(BuildConfig.VERSION_NAME);


            Log.d("date",date);
            Log.d("date1",errorMessage.getAppVersion());
         //   Log.d("date2",errorMessage.getDeviceBrand());
         //   Log.d("date3",errorMessage.getDeviceId());
         //   Log.d("date4",errorMessage.getIncrementalvVersion());
         //   Log.d("date5",errorMessage.getProduct());
         //   Log.d("date6",errorMessage.getSdkVersion());
         //   Log.d("date7",errorMessage.getReleseVersion());

//            errorMessage.setErrorTrace("some error");

            Intent intent = new Intent(myContext, ErrorDisplay.class);
            intent.putExtra("error",errorMessage);
        //    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            myContext.startActivity(intent);

            android.os.Process.killProcess(android.os.Process.myPid());
            System.exit(10);
        }


}
