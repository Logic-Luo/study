package com.logic.java.loader;

import java.io.*;

/**
 * @author logic
 * @date 2019-08-08 15:11
 * @since 1.0
 */
public class FileSystemClassLoader  extends ClassLoader{
    private String rootDir;

    public FileSystemClassLoader(ClassLoader parent, String rootDir) {
        super(parent);
        this.rootDir = rootDir;
    }

    @Override
    public Class<?> loadClass(String name) throws ClassNotFoundException {
        return super.loadClass(name);
    }

    @Override
    protected Class<?> findClass(String name) throws ClassNotFoundException {
        byte[] classData = getClassData(name);
        if (classData == null) {
            throw new ClassNotFoundException();
        } else {
            return super.findClass(name);
        }
    }

    private byte[] getClassData(String name) {
        String path = classNameToPath(name);
        try {
            InputStream inputStream = new FileInputStream(path);
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            int bufferSize = 4096;
            byte[] buffer = new byte[bufferSize];

            int byteNumReaad = 0;
            while ((byteNumReaad = inputStream.read(buffer)) != -1) {
                byteArrayOutputStream.write(buffer, 0, byteNumReaad);
            }

            return byteArrayOutputStream.toByteArray();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new byte[0];
    }

    private String classNameToPath(String className) {
        return rootDir + File.separatorChar
                + className.replace('.', File.separatorChar) + ".class";
    }
}
