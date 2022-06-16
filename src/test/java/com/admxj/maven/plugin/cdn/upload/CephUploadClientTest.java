package com.admxj.maven.plugin.cdn.upload;


import org.apache.maven.plugin.MojoExecutionException;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * @author jin.xiang
 * @version Id: CephUploadClientTest, v 0.1 2022/6/16 14:50 jin.xiang Exp $
 */
public class CephUploadClientTest {

    private final static String ACCESS_KEY_PROPERTY = "access-key";
    private final static String SECRET_KEY_PROPERTY = "secret-key";
    private final static String ENDPOINT_PROPERTY = "endpoint";
    private final static String BUCKET_NAME_PROPERTY = "bucket-name";

    CephUploadClient uploadClient;

    @Before
    public void before() throws IOException {
        InputStream configStream = this.getClass().getResourceAsStream("/config.properties");
        Properties properties = new Properties();
        properties.load(configStream);
        String accessKey = properties.getProperty(ACCESS_KEY_PROPERTY);
        String secretKey = properties.getProperty(SECRET_KEY_PROPERTY);
        String endpoint = properties.getProperty(ENDPOINT_PROPERTY);
        String bucketName = properties.getProperty(BUCKET_NAME_PROPERTY);

        uploadClient = new CephUploadClient(accessKey, secretKey, endpoint, bucketName);
    }


    @Test
    public void testUpload() throws MojoExecutionException {

        File file = new File("/Users/admxj/Downloads/mockagent-1.0-SNAPSHOT.jar");
        String upload = uploadClient.upload(file, "agent");
        System.out.println(upload);
    }
}