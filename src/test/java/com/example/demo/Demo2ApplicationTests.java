package com.example.demo;

import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.aliyun.oss.common.auth.CredentialsProviderFactory;
import com.aliyun.oss.common.auth.EnvironmentVariableCredentialsProvider;
import com.aliyun.oss.model.PutObjectResult;
import com.example.demo.pojo.Oss;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.UUID;

@SpringBootTest
class Demo2ApplicationTests {
@Autowired
private Oss oss;
    @Test
    void contextLoads() {




                //获取拓展名
               try{
                EnvironmentVariableCredentialsProvider credentialsProvider =
                        CredentialsProviderFactory.newEnvironmentVariableCredentialsProvider();
                OSS ossClient = OSSClientBuilder.create()
                        .endpoint(oss.getEndpoint())
                        .credentialsProvider(credentialsProvider)
                        .region("cn-beijing")
                        .build();

                // 1. 创建存储空间（Bucket）


                // 2. 上传文件
                   String objectName = "exampledir/exampleobject.txt";
                   String content = "Hello OSS";
                   ossClient.putObject(oss.getBucketName(), objectName, new ByteArrayInputStream(content.getBytes()));

            }catch (Exception e)
            {

            }
        }
    }


