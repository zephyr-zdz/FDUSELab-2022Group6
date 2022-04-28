package com.example.selab4;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

@SpringBootTest
class SElab4ApplicationTests {

    @Test
    void makeFile(String filename, String content) {
        try {
            BufferedWriter out = new BufferedWriter(new FileWriter(filename));
            out.write(content);
            out.close();
            System.out.println("文件创建成功！");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    void contextLoads() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("helloworld222");

        makeFile( "测试输出.txt", stringBuilder.toString());
    }

}
