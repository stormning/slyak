/**
 * Project name : slyak-core
 * File name : FileOperation.java
 * Package name : com.slyak.core.io
 * Date : 2013-11-27
 * Copyright : 2013 , SLYAK.COM All Rights Reserved
 * Author : stormning@163.com
 */
package com.slyak.core.io;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;

/**
 * @author StormNing
 * 
 */
public interface FileOperation {
    String SCHEME_HTTP = "http";

    String SCHEME_HTTPS = "https";

    void save(String destination) throws IOException;
    
    void save(File file) throws IOException;

    void send(OutputStream outputStream) throws IOException;
}
