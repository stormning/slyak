/*
 * @(#)FileOperation.java 2011
 *
 * Copyright 2011 Slyak SoftWare, Inc. All rights reserved.
 * SLYAK Limited Company/CONFIDENTIAL. Use is subject to license terms.
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
