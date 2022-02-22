package com.admxj.maven.plugin.cdn.upload;

import com.amazonaws.ClientConfiguration;
import com.amazonaws.Protocol;
import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.S3ClientOptions;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugins.annotations.Parameter;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URL;

/**
 * @author jin.xiang
 * @version Id: CephUploadClient, v 0.1 2022/2/11 11:15 AM jin.xiang Exp $
 */
public class CephUploadClient implements UploadClient {

    @Parameter(property = "accessKey", required = true)
    private String accessKey;

    @Parameter(property = "secretKey", required = true)
    private String secretKey;

    @Parameter(property = "endpoint", required = true)
    private String endpoint;

    @Parameter(property = "bucketName", required = true)
    private String bucketName;

    private AmazonS3Client s3client;

    public CephUploadClient(String accessKey, String secretKey, String endpoint, String bucketName) {
        this.accessKey = accessKey;
        this.secretKey = secretKey;
        this.endpoint = endpoint;
        this.bucketName = bucketName;

        AWSCredentials credentials = new BasicAWSCredentials(accessKey, secretKey);
        ClientConfiguration clientCfg = new ClientConfiguration();
        clientCfg.setProtocol(Protocol.HTTP);
        s3client = new AmazonS3Client(credentials, clientCfg);
        s3client.setEndpoint(endpoint);
        s3client.setS3ClientOptions(new S3ClientOptions().withPathStyleAccess(true));
    }

    @Override
    public String upload(File file, String targetPath) throws MojoExecutionException {
        try {
            String targetFile = targetPath + "/" + file.getName();
            FileInputStream inputStream = new FileInputStream(file);
            ObjectMetadata metadata = new ObjectMetadata();
            PutObjectRequest putObjectRequest = new PutObjectRequest(bucketName, targetFile, inputStream, metadata);
            putObjectRequest.setCannedAcl(CannedAccessControlList.PublicRead);
            s3client.putObject(putObjectRequest);
            return getUrlWithPublicRead(targetFile).toString();
        } catch (IOException e) {
            throw new MojoExecutionException(e.getMessage(), e);
        }
    }


    public URL getUrlWithPublicRead(String fileName) {
        return s3client.getUrl(bucketName, fileName);
    }
}
