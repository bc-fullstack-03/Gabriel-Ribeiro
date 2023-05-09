package com.sysmap.parrot.services.fileUpload;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@AllArgsConstructor
@Service
public class FileUploadService implements IFileUploadService{

        private AwsService _awsService;

    public String upload(MultipartFile file, String fileName){
        String fileUri = "";

        try {
        fileUri = _awsService.upload(file,fileName);
        }catch (Exception e){
            return e.getMessage();
        }
        return fileUri;
    }
}
