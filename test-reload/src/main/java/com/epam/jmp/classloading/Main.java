package com.epam.jmp.classloading;

import org.apache.log4j.Logger;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

public class Main {

    private static final Logger LOGGER = Logger.getLogger(Main.class);

    public static void main(String[] args) throws
            ClassNotFoundException,
            IllegalAccessException,
            InstantiationException, IOException {

        Scanner scanner = new Scanner(System.in);

//        D:/IdeaProjects/classloading/reloaded-v1/target/reloaded-v1.jar
        List<Class> loadedClasses = loadClassFromCommandLine(scanner);
        Reloadable loaded = getReloadable(loadedClasses);
        check(loaded);

//        D:/IdeaProjects/classloading/reloaded-v2/target/reloaded-v2.jar
        loadedClasses = loadClassFromCommandLine(scanner);
        loaded = getReloadable(loadedClasses);
        check(loaded);
    }

    private static void check(Reloadable loaded) {
        LOGGER.info("Result of doJob: " + loaded.doJob());
    }

    private static List<Class> loadClassFromCommandLine(Scanner scanner) throws IOException, ClassNotFoundException, InstantiationException, IllegalAccessException {
        System.out.println("Please enter the absolute path to the jar to load");
        String path = scanner.nextLine();
        JarClassLoader jarClassLoader = new JarClassLoader(path);
        return jarClassLoader.loadClasses();
    }

    private static Reloadable getReloadable(List<Class> classes) throws InstantiationException, IllegalAccessException {
        Class classToReload = classes.stream().filter(c -> c.getName().equals("com.epam.jmp.classloading.ClassToReload")).collect(Collectors.toList()).get(0);
        return (Reloadable) classToReload.newInstance();
    }

//    private static void firstCheck() throws ClassNotFoundException, InstantiationException, IllegalAccessException {
//        ClassLoader parentClassLoader = CustomClassLoader.class.getClassLoader();
//        CustomClassLoader classLoader = new CustomClassLoader(parentClassLoader);
//        Class myObjectClass = classLoader.loadClass("com.epam.jmp.classloading.ClassToReload");
//
//        Reloadable reloadable = (Reloadable) myObjectClass.newInstance();
//        reloadable.doJob();
//
//        Scanner scanner = new Scanner(System.in);
//        scanner.nextLine();
//        //create new class loader so classes can be reloaded.
//        classLoader = new CustomClassLoader(parentClassLoader);
//        myObjectClass = classLoader.loadClass("com.epam.jmp.classloading.ClassToReload");
//
//        reloadable = (Reloadable) myObjectClass.newInstance();
//        reloadable.doJob();
//    }
}
