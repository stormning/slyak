/**
 * Project name : slyak-core
 * File name : CommonFile.java
 * Package name : com.slyak.core.io
 * Date : 2013-11-27
 * Copyright : 2013 , SLYAK.COM All Rights Reserved
 * Author : stormning@163.com
 */
package com.slyak.core.io;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URI;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;

/**
 * @author StormNing
 * 
 */
public class CommonFile implements FileOperation {

    private InputStream inputStream;

    @SuppressWarnings("unused")
    private CommonFile() {
    }

    public CommonFile(InputStream inputStream) {
        this.inputStream = inputStream;
    }

    public CommonFile(String fileLocation) throws IOException {
        this(new File(fileLocation));
    }

    public CommonFile(File file) throws IOException {
        this(FileUtils.openInputStream(file));
    }

    public CommonFile(URI uri) throws IOException {
        String scheme = uri.getScheme();
        if (SCHEME_HTTP.equalsIgnoreCase(scheme) || SCHEME_HTTPS.equalsIgnoreCase(scheme)) {
            this.inputStream = uri.toURL().openStream();
        } else {
            File file = new File(uri);
            this.inputStream = FileUtils.openInputStream(file);
        }
    }

    public void save(String destination) throws IOException {
    	save(new File(destination));
    }
    
    @Override
    public void save(File file) throws IOException {
    	 FileOutputStream output = FileUtils.openOutputStream(file);
         send(output);
    }

    public void send(OutputStream outputStream) throws IOException {
        try {
            IOUtils.copyLarge(inputStream, outputStream);
        } finally {
        	close();
        }
    }

    public void close() {
        IOUtils.closeQuietly(inputStream);
    }


    // TODO more methods , like getMimeType ...
}
