package test.testing.rest;

import android.util.Log;

import java.io.IOException;
import java.text.MessageFormat;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;
import okio.Buffer;
import test.testing.BuildConfig;

/**
 * Created by rajdeep1008 on 03/01/18.
 */

public class HTTPLoggingInterceptor implements Interceptor {

    public static final String TAG = "network-call";

    @Override
    public Response intercept(Chain chain) throws IOException {
        boolean debug = true;
        if (!BuildConfig.DEBUG) debug = false;
        if (!debug) {
            Response response = chain.proceed(chain.request());
            String bodyString = response.body().string();
            return response.newBuilder()
                    .body(ResponseBody.create(response.body().contentType(), bodyString))
                    .build();
        }
        Log.i(TAG, "intercepted call");
        Request request = chain.request();

        long t1 = System.nanoTime();

        String requestLog = MessageFormat.format("Sending request {0} on {1} {2}", request.url(), chain.connection(), request.headers());

        if (request.method().compareToIgnoreCase("post") == 0)
            requestLog = MessageFormat.format("\n{0}\n{1}", requestLog, bodyToString(request));

        Log.d(TAG, MessageFormat.format("request\n{0}", requestLog));
        Response response = chain.proceed(request);
        long t2 = System.nanoTime();
        String bodyString = response.body().string();
        String responseLog = MessageFormat.format("Received response for {0} in {1}\n{2}", response.request().url(), (t2 - t1) / 1e6d, response.headers());
        Log.d(TAG, MessageFormat.format("response\n{0}\n{1}", responseLog, bodyString));

        try {
            Log.d(TAG, MessageFormat.format("Response Length {0}", response.body().contentLength()));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return response.newBuilder()
                .body(ResponseBody.create(response.body().contentType(), bodyString))
                .build();

    }

    private static String bodyToString(Request request) {
        try {
            Request temp = request.newBuilder().build();
            Buffer buffer = new Buffer();
            temp.body().writeTo(buffer);
            return buffer.readUtf8();
        } catch (IOException e) {
            e.printStackTrace();
            return "did not work";
        }
    }
}
