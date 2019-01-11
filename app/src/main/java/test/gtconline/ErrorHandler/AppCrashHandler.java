package test.gtconline.ErrorHandler;


import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Parcelable;

import java.io.PrintWriter;
import java.io.StringWriter;

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
            errorMessage.setErrorMessage(stackTrace.toString());
            errorMessage.setDeviceBrand(Build.BRAND);
            errorMessage.setDeviceName(Build.DEVICE);
            errorMessage.setDeviceModel(Build.MODEL);
            errorMessage.setDeviceId(Build.ID);
            errorMessage.setProduct(Build.PRODUCT);
            errorMessage.setSdkVersion(Build.VERSION.SDK);
            errorMessage.setReleseVersion(Build.VERSION.RELEASE);
            errorMessage.setIncrementalvVersion(Build.VERSION.INCREMENTAL);


            Intent intent = new Intent(myContext, ErrorDisplay.class);
            intent.putExtra("error",errorMessage);
        //    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            myContext.startActivity(intent);

            android.os.Process.killProcess(android.os.Process.myPid());
            System.exit(10);
        }


}
