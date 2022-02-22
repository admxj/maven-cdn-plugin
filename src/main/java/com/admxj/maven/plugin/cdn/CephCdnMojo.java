package com.admxj.maven.plugin.cdn;

import com.admxj.maven.plugin.cdn.upload.CephUploadClient;
import com.admxj.maven.plugin.cdn.upload.UploadClient;
import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.plugins.annotations.*;
import org.apache.maven.project.MavenProject;

import java.io.File;

/**
 * @author jin.xiang
 * @version Id: CephCdnMojo, v 0.1 2022/2/10 2:06 PM jin.xiang Exp $
 */
@Execute(phase = LifecyclePhase.VERIFY)
@Mojo(name = "ceph",
        requiresDependencyResolution = ResolutionScope.COMPILE_PLUS_RUNTIME,
        requiresDependencyCollection = ResolutionScope.COMPILE_PLUS_RUNTIME,
        threadSafe = true)
public class CephCdnMojo extends AbstractMojo {

    @Parameter(property = "accessKey", required = true)
    private String accessKey;

    @Parameter(property = "secretKey", required = true)
    private String secretKey;

    @Parameter(property = "endpoint", required = true)
    private String endpoint;

    @Parameter(property = "bucketName", required = true)
    private String bucketName;

    private MavenProject project;

    @Override
    public void execute() throws MojoExecutionException, MojoFailureException {
        project = (MavenProject) getPluginContext().get("project");

        String fileName = this.project.getBuild().getFinalName() + ".jar";
        String jarPath = this.project.getBasedir() + "/target/" + fileName;

        UploadClient uploadClient = new CephUploadClient(accessKey, secretKey, endpoint, bucketName);

        getLog().info("CDN上传中.....");
        String target = uploadClient.upload(new File(jarPath), "target");
        getLog().info("CDN上传成功");
        getLog().info("CDN访问路径: " + target);

    }

}
