package com.redhat.edwin.service;

import org.apache.http.client.HttpClient;
import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.TrustSelfSignedStrategy;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.ssl.SSLContextBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

import javax.net.ssl.SSLContext;
import java.io.FileInputStream;
import java.security.KeyStore;

/**
 * <pre>
 *     com.redhat.edwin.service.RestTemplate
 * </pre>
 *
 * @author Muhammad Edwin < edwin at redhat dot com >
 * 09 Mei 2020 15:51
 */
@Configuration
public class MyRestTemplate {

    @Value("${server.ssl.key-store}")
    private String sslKeyStore;

    @Value("${server.ssl.key-store-password}")
    private String sslPassword;

    @Bean
    public RestTemplate restTemplate() throws Exception {
        KeyStore clientStore = KeyStore.getInstance("PKCS12");
        clientStore.load(new FileInputStream(sslKeyStore), sslPassword.toCharArray());

        SSLContext sslContext = SSLContextBuilder
                .create()
                .loadTrustMaterial(clientStore, new TrustSelfSignedStrategy())
                .build();
        SSLConnectionSocketFactory socketFactory = new SSLConnectionSocketFactory(sslContext, NoopHostnameVerifier.INSTANCE);
        HttpClient httpClient = HttpClients.custom()
                .setSSLSocketFactory(socketFactory)
                .build();
        HttpComponentsClientHttpRequestFactory factory = new HttpComponentsClientHttpRequestFactory(httpClient);

        return new RestTemplate(factory);
    }
}
