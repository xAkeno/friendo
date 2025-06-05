package com.example.friendo.MicrosoftAzure;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/auth/api/image")
public class ImageMetaDataController {
    @Autowired
    private imageMetaDataService imageMetaDataServices;

    @GetMapping("/all")
    public List<ImageMetaModel> getAllImage(){
        return imageMetaDataServices.findAll();
    }
    // @PostMapping("/upload")
    // public String uploadImage(@RequestParam("image")MultipartFile image,@RequestParam("caption")String caption) throws IOException{
    //     System.out.println("hello te");
    //     return imageMetaDataServices.uploadImageWithCaption(image);
    // }
}
