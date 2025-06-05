// package com.example.friendo.CloudFlare;

// import java.util.Optional;
// import java.util.UUID;

// import org.apache.tomcat.util.http.fileupload.FileUploadException;
// import org.springframework.beans.factory.annotation.Value;
// import org.springframework.stereotype.Service;
// import org.springframework.web.multipart.MultipartFile;

// import com.example.friendo.CloudFlare.Exception.UnsupportedMediaTypeException;

// import io.jsonwebtoken.io.IOException;
// import lombok.RequiredArgsConstructor;
// import software.amazon.awssdk.awscore.exception.AwsServiceException;
// import software.amazon.awssdk.core.exception.SdkClientException;
// import software.amazon.awssdk.core.sync.RequestBody;
// import software.amazon.awssdk.services.s3.S3Client;
// import software.amazon.awssdk.services.s3.model.PutObjectRequest;
// import software.amazon.awssdk.services.s3.model.S3Exception;

// @Service
// @RequiredArgsConstructor
// public class MediaService {
//     private final S3Client s3Client;

//     @Value("${cloudflare.r2.bucket}")
//     private String bucket;

//     public String uploadFile(MultipartFile file) throws S3Exception, AwsServiceException, SdkClientException, java.io.IOException {
//         // 1. Resolve filename and content type
//         String original = Optional.ofNullable(file.getOriginalFilename())
//             .orElseThrow(() -> new UnsupportedMediaTypeException("Filename is missing"))
//             .toLowerCase();
//         String contentType = Optional.ofNullable(file.getContentType())
//             .orElseThrow(() -> new UnsupportedMediaTypeException("Content-Type is unknown"));

//         // 2. Decide folder by extension
//         String ext = getFileExtension(original);
//         String folder = switch (ext) {
//             case "jpg","jpeg","png","gif" -> "images";
//             case "mp4","mov"              -> "videos";
//             case "pdf","doc","docx","txt" -> "documents";
//             default -> throw new UnsupportedMediaTypeException("Unsupported file type: " + contentType);
//         };

//         // 3. Build object key
//         String key = String.format("%s/%s-%s", folder, UUID.randomUUID(), original);

//         // 4. Prepare S3 Put request
//         PutObjectRequest req = PutObjectRequest.builder()
//             .bucket(bucket)
//             .key(key)
//             .contentType(contentType)
//             .build();

//         // 5. Perform upload
//         try {
//             s3Client.putObject(req, RequestBody.fromBytes(file.getBytes()));
//         } catch (IOException e) {
//             throw new FileUploadException("File upload to Cloudflare R2 failed", e);
//         }

//         return key;
//     }

//     private String getFileExtension(String filename) {
//         int idx = filename.lastIndexOf('.');
//         if (idx < 0 || idx == filename.length()-1) {
//             throw new IllegalArgumentException("Invalid file extension in filename: " + filename);
//         }
//         return filename.substring(idx+1);
//     }
// }