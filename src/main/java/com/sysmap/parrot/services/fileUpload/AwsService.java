package com.sysmap.parrot.services.fileUpload;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.PutObjectRequest;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Objects;

@Service
@AllArgsConstructor
public class AwsService {

    private AmazonS3 amazonS3;

    public String upload(MultipartFile multipartFile, String fileName) throws Exception {
            var fileUri = "";
            try{
                var convertedFile = convertMultipartToFile(multipartFile);
                amazonS3.putObject(new PutObjectRequest("demo-bucket", fileName, convertedFile).withCannedAcl(CannedAccessControlList.PublicRead));

                fileUri = "http://s3.localhost.localstack.cloud:4566" + "/" + "demo-bucket" + "/" + fileName;
                convertedFile.delete();

            }catch (Exception e ){
                throw new Exception(e.getMessage());
            }
            return fileUri;
    }

    private File convertMultipartToFile(MultipartFile file) throws IOException{
        var convFile = new File(Objects.requireNonNull(file.getOriginalFilename()));
        var fos = new FileOutputStream(convFile);

        fos.write(file.getBytes());
        fos.close();

        return convFile;
    }
}
