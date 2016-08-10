package com.epam.jmp.classloading;

import org.apache.log4j.Logger;

import java.io.IOException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

public class JarClassLoader extends ClassLoader {

    private static final Logger LOGGER = Logger.getLogger(JarClassLoader.class);

    private JarFile jarFile;
    private URLClassLoader urlClassLoader;

    public JarClassLoader(String path) throws IOException {
        jarFile = new JarFile(path);
        URL[] urls = {new URL("jar:file:" + path + "!/")};
        urlClassLoader = URLClassLoader.newInstance(urls);
    }

    public List<Class> loadClasses() throws ClassNotFoundException {
        LOGGER.info("Loading classes with " + this);
        Enumeration e = jarFile.entries();
        List<Class> classes = new ArrayList<>();
        while (e.hasMoreElements()) {
            JarEntry je = (JarEntry) e.nextElement();
            if(je.isDirectory() || !je.getName().endsWith(".class")){
                continue;
            }
            String className = je.getName().substring(0, je.getName().length() - 6); // -6 to strip .class at the end
            className = className.replace('/', '.');
            Class aClass = urlClassLoader.loadClass(className);
            if (aClass.getFields().length != 0) {

            }
            LOGGER.info("Loaded class: " + className);
            classes.add(aClass);
        }
        return classes;
    }
}
