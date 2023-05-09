package com.sysmap.parrot.services.fileUpload;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.client.builder.AwsClientBuilder;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AwsConfig {
    @Bean
    public AmazonS3 amazonS3(){
        return AmazonS3ClientBuilder
                .standard()
                .withCredentials(new AWSStaticCredentialsProvider(new BasicAWSCredentials("mykey", "mykey")))
                .withEndpointConfiguration (new AwsClientBuilder.EndpointConfiguration("http://localstack:4566", Regions.US_WEST_2.getName()))
                .enablePathStyleAccess()
                .build();
    }
}
