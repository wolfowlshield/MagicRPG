package org.vashonsd.HTTPRequests;

import okhttp3.*;

import java.io.IOException;

public class Requester {
    public static final MediaType JSON
            = MediaType.get("application/json; charset=utf-8");

    static OkHttpClient client = new OkHttpClient();
    static String currentDeployment = "https://script.google.com/macros/s/AKfycbyWhIQZkbYSVIXuoQZ2I8IY-fPBbx09gDlsz2Dxdw4qNpMHX_g81C3tZ71UNVoEISw6xg/exec";

    public static void post(String json) throws IOException {
        RequestBody body = RequestBody.create(JSON, json);
        Request request = new Request.Builder()
                .url(currentDeployment)
                .post(body)
                .build();
        try (Response response = client.newCall(request).execute()) {
            response.body().string();
        }
    }

    public static String get() throws Exception {
        Request request = new Request.Builder()
                .url(currentDeployment)
                .build();

        try (Response response = client.newCall(request).execute()) {
            if (!response.isSuccessful()) throw new IOException("Unexpected code " + response);

            /*
            Headers responseHeaders = response.headers();
            for (int i = 0; i < responseHeaders.size(); i++) {
                System.out.println(responseHeaders.name(i) + ": " + responseHeaders.value(i));
            }
             */

            return response.body().string();
        }
    }
}
