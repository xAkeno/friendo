package com.example.friendo.MicrosoftAzure;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.example.friendo.FeedFeature.Model.Feed;
import com.example.friendo.MicrosoftAzure.ImageMetaDataRepository;
import com.azure.storage.blob.BlobClient;
import com.azure.storage.blob.BlobServiceClient;
import com.azure.storage.blob.BlobServiceClientBuilder;

import jakarta.annotation.PostConstruct;

@Service
public class imageMetaDataService {
    @Autowired
    private ImageMetaDataRepository imageMetaDataRepository;

    @Value("${spring.cloud.azure.storage.blob.container-name}")
    private String containerName;

    @Value("${azure.blob-storage.connection-string.for-public}")
    private String conntectionString;

    public BlobServiceClient blobServiceClient;

    public List<ImageMetaModel> findAll(){
        return imageMetaDataRepository.findAll();
    }

    @PostConstruct
    public void init() {
        // Create BlobServiceClient using full connection string (with AccountKey)
        this.blobServiceClient = new BlobServiceClientBuilder()
            .connectionString(conntectionString)
            .buildClient();
    }
    public List<String> uploadImageWithCaption(MultipartFile[] images,Feed feed){
        List<String> uploadedUrls = new ArrayList<>();
        try {
            for(MultipartFile image : images){
                String bloblFileName = image.getOriginalFilename();
                BlobClient blobClient = blobServiceClient
                    .getBlobContainerClient(containerName)
                    .getBlobClient(bloblFileName);
                blobClient.upload(image.getInputStream(),image.getSize(),true);   
                String blobUrl = blobClient.getBlobUrl();
                ImageMetaModel imageModel = new ImageMetaModel();
                imageModel.setImageUrl(blobUrl);
                imageModel.setFeed(feed);
                System.out.println("Image successfully uploaded to blob url name : " + blobUrl);
                uploadedUrls.add(blobUrl);
                imageMetaDataRepository.save(imageModel);
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return uploadedUrls;
    }
    public String uploadProfileImg(MultipartFile image){
        try {
            String bloblFileName = image.getOriginalFilename();
            BlobClient blobClient = blobServiceClient
                .getBlobContainerClient(containerName)
                .getBlobClient(bloblFileName);
            blobClient.upload(image.getInputStream(),image.getSize(),true);   
            String blobUrl = blobClient.getBlobUrl();
            System.out.println("Image successfully uploaded to blob url name : " + blobUrl);
            return blobUrl;
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return null;
    }
}
