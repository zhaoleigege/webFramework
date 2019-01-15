package com.busekylin.web.ioc;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * 获取运行环境下所有的class
 */
public class BeansList {
    /* 获得class文件的根目录 */
    private static final String ROOT = Objects.requireNonNull(BeansList.class.getClassLoader().getResource("")).getPath();

    private static final int rootLength = ROOT.length();

    private static final int CLASS_SUFFIX_LENGTH = ".class".length();

    /* 获得文件分隔符 */
    private static final String SEPARATOR = File.separator;

    /* 保存获取到的class类 */
    private static List<Class> classes;

    public static List<Class> getAllClasses() throws ClassNotFoundException {
        if (classes != null)
            return classes;

        classes = new ArrayList<>();

        scannerProject(new File(ROOT));

        return classes;
    }

    private static void scannerProject(File folder) throws ClassNotFoundException {
        for (File file : Objects.requireNonNull(folder.listFiles())) {
            if (file.isDirectory()) {
                scannerProject(file);
            } else {
                String classPath = file.getAbsolutePath().substring(rootLength);
                if (classPath.lastIndexOf(".class") == -1)
                    continue;

                classPath = classPath.substring(0, classPath.length() - CLASS_SUFFIX_LENGTH);

                /**
                 * 通过这种方式使得类的静态方法块不会执行
                 */
                classes.add(ClassLoader.getSystemClassLoader().loadClass(classPath.replaceAll(SEPARATOR, ".")));
            }
        }
    }

    public static void main(String[] args) throws ClassNotFoundException {
        BeansList.getAllClasses()
                .forEach(clazz -> System.out.println(clazz.getName()));
    }
}
