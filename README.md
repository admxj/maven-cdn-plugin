# maven-cdn-plugin


## 安装
```xml
<plugin>
    <groupId>com.admxj.maven.plugin</groupId>
    <artifactId>maven-cdn-plugin</artifactId>
    <version>1.0</version>
    <executions>
        <execution>
            <id>cdn</id>
            <goals>
                <goal>ceph</goal>
            </goals>
        </execution>
    </executions>
    <configuration>
        <bucketName>**</bucketName>
        <endpoint>http://cdn.***.com</endpoint>
        <accessKey>****</accessKey>
        <secretKey>>****</secretKey>
    </configuration>
</plugin>
```