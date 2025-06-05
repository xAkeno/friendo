// package com.example.friendo.CloudFlare;

// import java.net.URI;

// import org.springframework.boot.context.properties.EnableConfigurationProperties;
// import org.springframework.context.annotation.Bean;
// import org.springframework.context.annotation.Configuration;

// import lombok.RequiredArgsConstructor;
// import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
// import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
// import software.amazon.awssdk.http.apache.ApacheHttpClient;
// import software.amazon.awssdk.regions.Region;
// import software.amazon.awssdk.services.s3.S3Client;
// import software.amazon.awssdk.services.s3.S3Configuration;

// @Configuration
// @EnableConfigurationProperties(CloudflareProperties.class)
// @RequiredArgsConstructor
// public class CloudflareR2Config {

//     private final CloudflareProperties cloudflareProperties;

//     @Bean
//     public S3Client s3Client() {

//         S3Configuration serviceConfig = S3Configuration.builder()
//                 // path-style is required for R2
//                 .pathStyleAccessEnabled(true)
//                 // disable AWS4 chunked uploads
//                 .chunkedEncodingEnabled(false)
//                 .build();
//         System.out.println(cloudflareProperties.getAccessKey() + "<<<<");
//         return S3Client.builder()
//                 .httpClientBuilder(ApacheHttpClient.builder())
//                 .region(Region.of("auto"))
//                 .endpointOverride(URI.create(cloudflareProperties.getEndpoint()))
//                 .credentialsProvider(StaticCredentialsProvider.create(
//                         AwsBasicCredentials.create(
//                                 cloudflareProperties.getAccessKey(),
//                                 cloudflareProperties.getSecretKey())))
//                 .serviceConfiguration(serviceConfig)
//                 .build();

//     }

// }