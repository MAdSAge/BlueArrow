package pyq.qbank.bluearrow;

import android.content.Context;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.google.gson.stream.JsonReader;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import javax.net.ssl.*;
import java.io.*;
import java.net.InetSocketAddress;
import java.net.Proxy;
import java.security.KeyStore;
import java.security.cert.Certificate;
import java.security.cert.CertificateFactory;
import java.util.zip.GZIPInputStream;

public class TorFileDownloader {

    private final Context context;

    public TorFileDownloader(Context context) {
        this.context = context;
    }

    public JsonArray downloadFile(String url) throws IOException {
        // Load the self-signed certificate
        SSLSocketFactory sslSocketFactory;
        X509TrustManager trustManager;
        try {
            // Load the .pem certificate from res/raw
            CertificateFactory cf = CertificateFactory.getInstance("X.509");
            InputStream certInput = context.getResources().openRawResource(R.raw.your_certificate); // Replace 'server_cert' with the actual name of your .pem file (without extension)
            Certificate ca;
            try {
                ca = cf.generateCertificate(certInput);
            } finally {
                certInput.close();
            }

            // Create a KeyStore containing the trusted certificate
            KeyStore keyStore = KeyStore.getInstance(KeyStore.getDefaultType());
            keyStore.load(null, null); // Initialize with an empty keystore
            keyStore.setCertificateEntry("ca", ca);

            // Create a TrustManager that trusts the certificate in the KeyStore
            TrustManagerFactory tmf = TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm());
            tmf.init(keyStore);

            // Get the TrustManager and SSLContext
            trustManager = (X509TrustManager) tmf.getTrustManagers()[0];
            SSLContext sslContext = SSLContext.getInstance("TLS");
            sslContext.init(null, tmf.getTrustManagers(), null);
            sslSocketFactory = sslContext.getSocketFactory();
        } catch (Exception e) {
            throw new IOException("Failed to set up SSL context with self-signed certificate", e);
        }

        // Set up SOCKS proxy to route traffic through Tor
        Proxy proxy = new Proxy(Proxy.Type.SOCKS, new InetSocketAddress("127.0.0.1", 9050));
//        OkHttpClient client = new OkHttpClient.Builder()
//                .proxy(proxy)
//                .sslSocketFactory(sslSocketFactory, trustManager)
//                .build();
//        OkHttpClient client = new OkHttpClient.Builder()
//                .sslSocketFactory(sslSocketFactory, trustManager)
//                .hostnameVerifier((hostname, session) -> true) // Trust all hostnames
//                .proxy(proxy)
//                .build();
        OkHttpClient client = new OkHttpClient.Builder()
                .sslSocketFactory(sslSocketFactory, trustManager)
                .hostnameVerifier((hostname, session) -> {
                    return hostname.equals("z5tkb2tiuesvhtfaixavc4cs2qcrfpriujxl6ujr2va4adwgu53ys2qd.onion");
                })
                .proxy(proxy)
                .build();



        // Request to download the file from the Onion URL
        Request request = new Request.Builder().url(url).build();

        try (Response response = client.newCall(request).execute()) {
            if (!response.isSuccessful()) {
                throw new IOException("Failed to download file: " + response.code() + " " + response.message());
            }

            // Read the response body as a stream
            InputStream inputStream = response.body().byteStream();
            GZIPInputStream gzipInputStream = new GZIPInputStream(inputStream);
            InputStreamReader inputStreamReader = new InputStreamReader(gzipInputStream);



            // Use JsonReader to parse the JSON stream
            JsonReader jsonReader = new JsonReader(inputStreamReader);

            // Create a JsonParser to handle the stream of data
            JsonParser parser = new JsonParser();
            JsonElement element = parser.parse(jsonReader);

            // Close the reader
            jsonReader.close();
            inputStreamReader.close();

            // Assuming the JSON is an array, return it as a JsonArray
            if (element.isJsonArray()) {
                return element.getAsJsonArray();
            } else {
                throw new IOException("Expected JSON array but received: " + element);
            }
        }
    }
}
