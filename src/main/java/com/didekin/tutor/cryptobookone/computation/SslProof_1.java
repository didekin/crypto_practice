package com.didekin.tutor.cryptobookone.computation;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

/**
 * User: pedro@didekin
 * Date: 04/04/16
 * Time: 18:33
 */
public class SslProof_1 {

    public static void main(String[] args) throws IOException
    {
        URL url = new URL("https://wikipedia.org");
        URLConnection urlConnection = url.openConnection();
        InputStream in = urlConnection.getInputStream();
        copyInputStreamToOutputStream(in);
    }

    private static void copyInputStreamToOutputStream(InputStream in)
    {
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(in))) {
            String line = null;
            while ((line = reader.readLine()) != null) {
                System.out.println(line);
            }
        } catch (IOException x) {
            x.printStackTrace();
        }
    }
}
