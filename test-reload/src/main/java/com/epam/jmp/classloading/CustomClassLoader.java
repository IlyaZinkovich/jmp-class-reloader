package com.epam.jmp.classloading;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;

public class CustomClassLoader extends ClassLoader{

    public CustomClassLoader(ClassLoader parent) {
        super(parent);
    }

    public Class loadClass(String name) throws ClassNotFoundException {
        if(!"com.epam.jmp.classloading.ClassToReload".equals(name))
            return super.loadClass(name);

        try {
            String url = "file:D:/IdeaProjects/classloading/target/classes/com/epam/jmp/classloading/ClassToReload.class";
            URL myUrl = new URL(url);
            URLConnection connection = myUrl.openConnection();
            InputStream input = connection.getInputStream();
            ByteArrayOutputStream buffer = new ByteArrayOutputStream();
            int data = input.read();

            while(data != -1){
                buffer.write(data);
                data = input.read();
            }

            input.close();

            byte[] classData = buffer.toByteArray();

            return defineClass("com.epam.jmp.classloading.ClassToReload",
                    classData, 0, classData.length);

        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

}
