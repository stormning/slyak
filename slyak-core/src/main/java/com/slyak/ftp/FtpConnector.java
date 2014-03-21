/**
 * 
 */
package com.slyak.ftp;


import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.RandomAccessFile;
import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;
import org.apache.commons.net.ftp.FTPReply;

/**
 * The Class FtpConnector.
 *
 * @author stormning@163.com
 */
public class FtpConnector {
	
	/** The ftp client. */
	private FTPClient ftpClient;
	
	/** The Constant DEFAULT_PORT. */
	private static final int DEFAULT_PORT = 21;
	
	/** The Constant DEFAULT_ENCODING. */
	private static final String DEFAULT_ENCODING = "UTF-8";
	
	/** The control encoding. */
	private String controlEncoding = "ISO-8859-1";
	
	private OutputStream outputStream;
	
	private RandomAccessFile randomAccessFile;
	
	/**
	 * Instantiates a new ftp connector.
	 *
	 * @param uname the uname
	 * @param upwd the upwd
	 * @param host the host
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	public FtpConnector(String uname,String upwd,String host) throws IOException {
		this(uname,upwd,host,DEFAULT_PORT);
	}
	
	/**
	 * Instantiates a new ftp connector.
	 *
	 * @param uname the uname
	 * @param upwd the upwd
	 * @param host the host
	 * @param port the port
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	public FtpConnector(String uname,String upwd,String host,int port) throws IOException {
		boolean flag = false;
		try {
			ftpClient = new FTPClient();
			ftpClient.connect(host, port);
			ftpClient.setControlEncoding(controlEncoding);
			int replyCode = ftpClient.getReplyCode();
			flag = FTPReply.isPositiveCompletion(replyCode) && ftpClient.login(uname, upwd);
		} finally {
			if(!flag) {
				destory();
			}
		}
	}
	
	/**
	 * Sets the control encoding.
	 *
	 * @param controlEncoding the new control encoding
	 */
	public void setControlEncoding(String controlEncoding) {
		this.controlEncoding = controlEncoding;
	}

	/**
	 * Upload.
	 *
	 * @param localFile the local file
	 * @param ftpDirectory the ftp directory
	 * @param processor the processor
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	public void upload(File localFile,String ftpDirectory,UploadProcessor processor) throws IOException {
		ftpClient.enterLocalPassiveMode();
		ftpClient.setFileType(FTP.BINARY_FILE_TYPE);
		
		enter(ftpDirectory);
		
		String fileName = getFileName(localFile);
		
		FTPFile exist = findExistFtpFile(fileName);
		
		long total = FileUtils.sizeOf(localFile);
		long offset = exist == null ? 0 : exist.getSize();
		
		//before upload
		processor.beforeUpload(total, offset,fileName);
		
		if(offset < total) {
			outputStream = ftpClient.appendFileStream(encodeIfNecessary(fileName));
			ftpClient.setRestartOffset(offset);
			
			randomAccessFile = new RandomAccessFile(localFile, "r");
			randomAccessFile.seek(offset);
			
			byte[] bytes = new byte[1024];
			int read;
			while ((read = randomAccessFile.read(bytes)) != -1) {
				long t1 = System.currentTimeMillis();
				outputStream.write(bytes);
				processor.onUpload(read, System.currentTimeMillis()-t1);
			}
			
			stopUpload();
		}
		
		processor.onUploadFinished();
	}
	
	/**
	 * Gets the file name.
	 *
	 * @param file the file
	 * @return the file name
	 */
	private String getFileName(File file) {
		String ext = StringUtils.lowerCase(FilenameUtils.getExtension(file.getName()));
		return getMd5ByFile(file)+'.'+ext;
	}
	
	/**
	 * Encode if necessary.
	 *
	 * @param fileName the file name
	 * @return the string
	 */
	private String encodeIfNecessary(String fileName) {
		if(DEFAULT_ENCODING.equalsIgnoreCase(controlEncoding)) {
			return fileName;
		}
		try {
			return new String(fileName.getBytes(DEFAULT_ENCODING),controlEncoding);
		} catch (UnsupportedEncodingException e) {
			throw new RuntimeException(e);
		}
	}
	
	/**
	 * Enter.
	 *
	 * @param ftpDirectory the ftp directory
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	private void enter(String ftpDirectory) throws IOException {
		ftpClient.changeWorkingDirectory("/");
		
		String[] dirs= StringUtils.split(ftpDirectory,'/');
		for (String dir : dirs) {
			String path = encodeIfNecessary(dir);
			boolean flag = ftpClient.changeWorkingDirectory(path);
			if(!flag) {
				ftpClient.makeDirectory(path);
				ftpClient.changeWorkingDirectory(path);
			}
		}
	}
	
	//同名则认为续传
	/**
	 * Find exist ftp file.
	 *
	 * @param fileName the file name
	 * @return the FTP file
	 */
	private FTPFile findExistFtpFile(String fileName) {
		try {
			FTPFile[] files = ftpClient.listFiles(fileName);
			if(files!=null&&files.length>0) {
				return files[0];
			}
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
		return null;
	}

	
	private void stopUpload() throws IOException {
		if(randomAccessFile!=null) {
			IOUtils.closeQuietly(randomAccessFile);
			randomAccessFile = null;
		}
		if(outputStream!=null) {
			outputStream.flush();
			outputStream.close();
			ftpClient.completePendingCommand();
			outputStream = null;
		}
	}
	
	/**
	 * Destory.
	 */
	public void destory() {
		try {
			stopUpload();
			if(ftpClient.isConnected()) {
				ftpClient.disconnect();
			}
		} catch (IOException e) {
			throw new RuntimeException("ftp cliend disconnect error");
		}
	}
	
    /**
     * Gets the md5 by file.
     *
     * @param file the file
     * @return the md5 by file
     */
    public static String getMd5ByFile(File file) {
    	FileInputStream fis = null;
		try {
			fis = new FileInputStream(file);
			MappedByteBuffer byteBuffer = fis.getChannel().map(FileChannel.MapMode.READ_ONLY, 0, file.length());
		    MessageDigest md5 = MessageDigest.getInstance("MD5");
		    md5.update(byteBuffer);
		    BigInteger bi = new BigInteger(1, md5.digest());
		    return bi.toString(16);
		} catch (IOException e) {
			//
		} catch (NoSuchAlgorithmException e) {
			//
		} finally {
			IOUtils.closeQuietly(fis);
		}
		return StringUtils.EMPTY;
    }
}
