package com.admxj.maven.plugin.cdn.upload;

import org.apache.maven.plugin.MojoExecutionException;

import java.io.File;

/**
 * The interface Upload client.
 *
 * @author jin.xiang
 * @version Id : UploadClient, v 0.1 2022/2/11 11:14 AM jin.xiang Exp $
 */
public interface UploadClient {
    /**
     * Upload string.
     *
     * @param file       the file
     * @param targetPath the target path
     * @return the string
     */
    String upload(File file,  String targetPath) throws MojoExecutionException;
}
