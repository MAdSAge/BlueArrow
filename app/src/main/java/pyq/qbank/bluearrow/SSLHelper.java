package pyq.qbank.bluearrow;

import android.content.Context;
import java.io.InputStream;
import java.security.KeyStore;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import javax.net.ssl.TrustManager;
import javax.net.ssl.TrustManagerFactory;
import javax.net.ssl.SSLContext;

public class SSLHelper {

    public static void configureSSL(Context context) throws Exception {
        // Load the certificate from the raw resource folder
        InputStream certificateInputStream = context.getResources().openRawResource(R.raw.your_certificate); // your .pem file
        CertificateFactory certificateFactory = CertificateFactory.getInstance("X.509");
        X509Certificate certificate = (X509Certificate) certificateFactory.generateCertificate(certificateInputStream);

        // Create a KeyStore and load the certificate
        KeyStore keyStore = KeyStore.getInstance(KeyStore.getDefaultType());
        keyStore.load(null, null); // Initialize the KeyStore with no password
        keyStore.setCertificateEntry("self-signed", certificate); // Add the certificate to the KeyStore

        // Create a TrustManagerFactory and initialize it with the KeyStore
        TrustManagerFactory trustManagerFactory = TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm());
        trustManagerFactory.init(keyStore);

        // Create an SSLContext and initialize it with the TrustManager
        SSLContext sslContext = SSLContext.getInstance("TLS");
        sslContext.init(null, trustManagerFactory.getTrustManagers(), null);

        // Set the default SSLContext for the app
        SSLContext.setDefault(sslContext);
    }
}
