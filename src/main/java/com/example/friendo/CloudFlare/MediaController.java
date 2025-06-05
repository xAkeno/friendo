// package com.example.friendo.CloudFlare;

// import java.io.IOException;

// import org.springframework.http.HttpStatus;
// import org.springframework.http.ResponseEntity;
// import org.springframework.web.bind.annotation.PostMapping;
// import org.springframework.web.bind.annotation.RequestMapping;
// import org.springframework.web.bind.annotation.RequestParam;
// import org.springframework.web.bind.annotation.RestController;
// import org.springframework.web.multipart.MultipartFile;

// import com.example.friendo.CloudFlare.Exception.UnsupportedMediaTypeException;

// import lombok.RequiredArgsConstructor;
// import software.amazon.awssdk.awscore.exception.AwsServiceException;
// import software.amazon.awssdk.core.exception.SdkClientException;
// import software.amazon.awssdk.services.s3.model.S3Exception;

// @RestController
// @RequestMapping("/api/media")
// @RequiredArgsConstructor
// public class MediaController {
//     private final MediaService mediaService;

//     @PostMapping("/upload")
//     public ResponseEntity<String> upload(@RequestParam("file") MultipartFile file) throws S3Exception {
//         try {
//             String key = mediaService.uploadFile(file);
//             return ResponseEntity.ok("File uploaded to: " + key);
//         } catch (UnsupportedMediaTypeException e) {
//             return ResponseEntity.status(HttpStatus.UNSUPPORTED_MEDIA_TYPE)
//                     .body("Unsupported file: " + e.getMessage());
//         } catch (AwsServiceException | SdkClientException e) {
//             return ResponseEntity.status(HttpStatus.BAD_GATEWAY)
//                     .body("AWS/R2 Error: " + e.getMessage());
//         } catch (IOException e) {
//             return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
//                     .body("I/O Error: " + e.getMessage());
//         } 
//     }
// }
