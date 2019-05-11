package com.springmvc.utils.ftp;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;
import org.apache.commons.net.ftp.FTPReply;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import java.io.*;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.List;

public class FtpClientTemple {
    private Logger logger = LoggerFactory.getLogger(getClass());
    private FtpConfig ftpConfig;
    private String controlEncoding = "UTF-8";
    private String localFileEncoding = "UTF-8";
    private String sendFtpFileNameEncoding = "iso-8859-1";

    /**
     * 连接ftp服务器，根据配置创建连接
     */
    private boolean connerServer(FTPClient ftpClient) throws SocketException, IOException{
        boolean isConnected = false;
        String server = ftpConfig.getServer();
        int port = ftpConfig.getPort();
        String user = ftpConfig.getUsername();
        String password = ftpConfig.getPassword();
        String path = ftpConfig.getPath();

        ftpClient.connect(server, port);
        logger.info("Connected to " + server + ".");
        logger.info("FTP server reply code: " + ftpClient.getReplyCode());

        if(FTPReply.isPositiveCompletion(ftpClient.getReplyCode())){
            if(ftpClient.login(user, password)){
                if(FTPReply.isPositiveCompletion(ftpClient.sendCommand("OPTS_UTF8", "ON"))){
                    localFileEncoding = "UTF-8";
                }
                if(StringUtils.isNotEmpty(path)){
                    ftpClient.changeWorkingDirectory(path);
                }
                isConnected = true;
            }
        }
        return isConnected;
    }

    /**
     * 断开与远程服务器的连接
     */
    private void disConnect(FTPClient ftpClient) throws IOException{
        if(ftpClient.isConnected()){
            ftpClient.disconnect();
        }
    }

    /**
     * 改变工作目录
     */
    @SuppressWarnings("unused")
    private boolean changeDirectory(String path, FTPClient ftpClient) throws IOException{
        return ftpClient.changeWorkingDirectory(path);
    }

    /**
     * 创建文件夹
     */
    private boolean createDir(String pathName, FTPClient ftpClient) throws IOException{
        return ftpClient.makeDirectory(pathName);
    }

    /**
     * 删除文件夹
     */
    private boolean removeDirectory(String pathName, FTPClient ftpClient) throws IOException{
        return ftpClient.removeDirectory(pathName);
    }

    /**
     * 递归删除
     */
    @SuppressWarnings("unused")
    private boolean removeDirectory(String path, boolean isAll, FTPClient ftpClient)throws IOException{
        if(!isAll){
            return removeDirectory(path, ftpClient);
        }
        FTPFile[] ftpFileArr = ftpClient.listFiles(path);
        if(ftpFileArr == null || ftpFileArr.length == 0){
            return removeDirectory(path, ftpClient);
        }

        for(FTPFile ftpFile : ftpFileArr){
            String name = ftpFile.getName();
            if(ftpFile.isDirectory()){
                logger.info("* [sD]Delete subPath [" + path + "/" + name + "]");
                if(!ftpFile.getName().equals(".") && (!ftpFile.getName().equals(".."))){
                    removeDirectory(path + "/" + name, true, ftpClient);
                }
            } else if(ftpFile.isFile()){
                logger.info("* [sD]Delete file [" + path + "/" + name + "]");
                deleteFile(path + "/" + name, ftpClient);
            } else if(ftpFile.isSymbolicLink()){
            } else if(ftpFile.isUnknown()){
            }
        }
        return ftpClient.removeDirectory(path);
    }

    /**
     * 删除文件
     */
    private boolean deleteFile(String pathName, FTPClient ftpClient) throws IOException{
        return ftpClient.deleteFile(pathName);
    }

    /**
     * 查看目录是否存在
     */
    @SuppressWarnings("unused")
    private boolean isDirExists(String path, FTPClient ftpClient) throws IOException{
        boolean flag = false;
        FTPFile[] ftpFiles = ftpClient.listFiles();
        for(FTPFile ftpFile : ftpFiles){
            if(ftpFile.isDirectory() && ftpFile.getName().equalsIgnoreCase(path)){
                flag = true;
                break;
            }
        }
        return flag;
    }

    /**
     * 得到某个目录下的文件名列表
     */
    @SuppressWarnings("unused")
    private List<String> getFoleList(String path, FTPClient ftpClient) throws IOException{
        FTPFile[] ftpFiles = ftpClient.listFiles(path);
        List<String> retList = new ArrayList<>();
        if(ftpFiles == null || ftpFiles.length == 0){
            return retList;
        }
        for(FTPFile ftpFile : ftpFiles){
            if(ftpFile.isFile()){
                retList.add(ftpFile.getName());
            }
        }
        return retList;
    }

    /**
     * 下载文件
     */
    @SuppressWarnings("unused")
    private InputStream downFile(String sourceFileName, FTPClient ftpClient) throws IOException{
        return ftpClient.retrieveFileStream(sourceFileName);
    }

    /**
     *  从ftp服务器上下载文件，支持断点续传，下载百分比
     * @param remote  远程文件的路径及名称
     * @param local  本地文件的完整绝对路径
     * @return 下载状态
     * @throws IOException
     */
    public DownLoadStatus downLoad(String remote, String local) throws IOException{
        DownLoadStatus result = DownLoadStatus.serverConntionFail;
        FTPClient ftpClient = new FTPClient();
        FileOutputStream out = null;
        OutputStream outputStream = null;
        InputStream in = null;
        try {
            if(connerServer(ftpClient)){
                // 设置被动模式
                ftpClient.enterLocalPassiveMode();
                // 设置以二进制方式传输
                ftpClient.setFileType(FTP.BINARY_FILE_TYPE);
                FTPFile[] files = ftpClient
                        .listFiles(new String(remote.getBytes(localFileEncoding), sendFtpFileNameEncoding));
                if(files.length != 1){
                    logger.info("远程文件不存在");
                    return DownLoadStatus.RemoteFileNotExist;
                }
                long IRemoteSize = files[0].getSize();
                File f = new File(local);
                // 本地文件存在，进行断点下载
                if(f.exists()){
                    long localSize = f.length();
                    // 判断本地文件大小是否大于远程文件大小
                    if(localSize >= IRemoteSize){
                        logger.info("本地文件大于远程文件，下载终止");
                        return DownLoadStatus.LocalFileBiggerThanRemoteFile;
                    }
                    // 进行断点续传，并记录状态
                    out = new FileOutputStream(f, true);
                    ftpClient.setRestartOffset(localSize);
                    in = ftpClient.retrieveFileStream(
                            new String(remote.getBytes(localFileEncoding), sendFtpFileNameEncoding)
                    );
                    byte[] bytes = new byte[1024];
                    long step = IRemoteSize / 100;
                    step = step == 0 ? 1 : step; // 文件过小，step可能为0
                    long process = localSize / step;
                    int c;
                    while((c = in.read(bytes)) != -1){
                        out.write(bytes, 0, c);
                        localSize += c;
                        long nowProcess = localSize / step;
                        if(nowProcess > process){
                            process = nowProcess;
                            if(process % 10 == 0){
                                logger.info("下载进度：" + process);
                            }
                        }
                    }
                    boolean isDo = ftpClient.completePendingCommand();
                    if(isDo){
                        result = DownLoadStatus.DownloadFromBreakSuccess;
                    } else {
                        result = DownLoadStatus.DownloadFromBreakFailed;
                    }
                } else {
                    outputStream = new FileOutputStream(f);
                    in = ftpClient.retrieveFileStream(
                            new String(remote.getBytes(localFileEncoding), sendFtpFileNameEncoding)
                    );
                    byte[] bytes = new byte[1024];
                    long step = IRemoteSize / 100;
                    step = step == 0 ? 1 : step; // 文件过小，step可能为0
                    long process = 0;
                    long localSize = 0L;
                    int c;
                    while((c = in.read(bytes)) != -1){
                        outputStream.write(bytes, 0, c);
                        localSize += c;
                        long nowProcess = localSize / step;
                        if(nowProcess > process){
                            process = nowProcess;
                            if(process % 10 == 0){
                                logger.info("下载进度：" + process);
                            }
                        }
                    }
                    boolean upNewStatus = ftpClient.completePendingCommand();
                    if(upNewStatus){
                        result = DownLoadStatus.DownloadNewSuccess;
                    } else {
                        result = DownLoadStatus.DownloadNewFailed;
                    }
                }
            } else {
                logger.info("打开ftp服务器失败");
            }
            return result;
        } finally {
            if(null != in){
                in.close();
            }
            if(null != outputStream){
                outputStream.close();
            }
            if(null != out){
                out.close();
            }
            disConnect(ftpClient);
        }
    }

    /**
     *  上传到ftp服务器，支持断点续传
     * @param local  本地文件名，绝对路径
     * @param remote 远程文件路径，按照Linux上的路径
     * @return 上传结果
     * @throws IOException
     */
    public UploadStatus upload(String local, String remote) throws IOException{
        UploadStatus result = UploadStatus.serverConntionFail;
        FTPClient ftpClient = new FTPClient();
        try {
            if (connerServer(ftpClient)) {
                // 设置被动模式
                ftpClient.enterLocalPassiveMode();
                // 设置以二进制方式传输
                ftpClient.setFileType(FTP.BINARY_FILE_TYPE);
                ftpClient.setControlEncoding(controlEncoding);
                ftpClient.setRemoteVerificationEnabled(false);
                String remoterFileName = remote;
                if(remote.contains("/")){
                    remoterFileName = remote.substring(remote.lastIndexOf("/") + 1);
                    // 创建服务器远程目录结构，创建失败直接返回
                    if(createDirecroty(remote, ftpClient) == UploadStatus.CreateDirectortFail){
                        return UploadStatus.CreateDirectortFail;
                    }
                }
                // 检查文件是否存在
                FTPFile[] files = ftpClient
                        .listFiles(new String(remoterFileName.getBytes(localFileEncoding), sendFtpFileNameEncoding));
                if(files.length == 1){
                    long remoteSize = files[0].getSize();
                    File f = new File(local);
                    long localSize = f.length();
                    if(remoteSize == localSize){
                        return UploadStatus.FileExits;
                    } else if(remoteSize > localSize){
                        return UploadStatus.RemoteFileBiggerThanLocalFile;
                    }
                    result = uploadFile(remoterFileName, f, ftpClient, remoteSize);
                    // 如果断点续传没成功，则删除文件，重新上传
                    if(result == UploadStatus.UploadFromBreakFailed){
                        if(!ftpClient.deleteFile(remoterFileName)){
                            return UploadStatus.DeleteRemoteFaild;
                        }
                        result = uploadFile(remoterFileName, f, ftpClient, 0);
                    }
                } else {
                    result = uploadFile(remoterFileName, new File(local), ftpClient, 0);
                }
            }
            return result;
        } finally {
            disConnect(ftpClient);
        }
    }

    /**
     *  递归创建远程服务器目录
     * @param remote 远程服务器文件绝对路径
     * @param _ftpClient FTPClient 对象
     * @return 是否创建成功
     * @throws IOException
     */
    private UploadStatus createDirecroty(String remote, FTPClient _ftpClient) throws IOException{
        UploadStatus status = UploadStatus.CreateDirectorySuccess;
        String directory = remote.substring(0, remote.lastIndexOf("/") + 1);
        if(!directory.equalsIgnoreCase("/") && !_ftpClient
                .changeWorkingDirectory(new String(directory.getBytes(localFileEncoding), sendFtpFileNameEncoding))){
            // 如果目录不存在，则递归创建目录
            int start = 0;
            int end = 0;
            if(directory.startsWith("/")){
                start = 1;
            } else {
                start = 0;
            }

            end = directory.indexOf("/", start);
            while (true){
                String subDirectory = new String(remote.substring(start, end).getBytes(localFileEncoding),
                        sendFtpFileNameEncoding);
                if(_ftpClient.changeWorkingDirectory(subDirectory)){
                    if(_ftpClient.makeDirectory(subDirectory)){
                        _ftpClient.changeWorkingDirectory(subDirectory);
                    }else {
                        logger.info("创建目录失败");
                        return UploadStatus.CreateDirectortFail;
                    }
                }
                start = end + 1;
                end = directory.indexOf("/", start);
                // 检查所有目录是否创建完毕
                if(end <= start){
                    break;
                }
            }
        }
        return status;
    }

    /**
     * 上传文件到服务器，新上传和断点续传
     * @param remoteFile 远程文件名，在上传之前以将目录做了改变
     * @param localFile 本地文件的绝对路径
     * @param _ftpClient
     * @param remoteSize 显示进度
     * @return
     * @throws IOException
     */
    private UploadStatus uploadFile(String remoteFile, File localFile, FTPClient _ftpClient, long remoteSize)
        throws IOException{
        UploadStatus status;
        logger.info("localFile.length() = " + localFile.length());
        long step = localFile.length() / 100;
        step = step == 0 ? 1 :step;
        long process = 0;
        long localreadbytes = 0L;
        RandomAccessFile raf = new RandomAccessFile(localFile, "r");
        OutputStream out = _ftpClient
                .appendFileStream(new String(remoteFile.getBytes(localFileEncoding), sendFtpFileNameEncoding));
        // 断点续传
        if(remoteSize > 0){
            _ftpClient.setRestartOffset(remoteSize);
            process = remoteSize / step;
            raf.seek(remoteSize);
            localreadbytes = remoteSize;
        }
        byte[] bytes = new byte[1024];
        int c;
        while((c = raf.read(bytes)) != -1){
            out.write(bytes, 0, c);
            localreadbytes += c;
            if(localreadbytes / step != process){
                process = localreadbytes / step;
                if(process % 10 == 0){
                    logger.info("长传进度：" + process);
                }
            }
        }
        out.flush();
        raf.close();
        out.close();
        boolean result = _ftpClient.completePendingCommand();
        logger.info("结果：" + result);
        if(remoteSize > 0){
            status = result ?UploadStatus.UploadFromBreakSuccess : UploadStatus.UploadFromBreakFailed;
        } else {
            status = result ? UploadStatus.UploadNewFileSuccess : UploadStatus.UploadNewFileFailed;
        }
        return status;
    }


    public enum UploadStatus{
        serverConntionFail, //服务器连接失败
        CreateDirectortFail, //远程服务器相应目录创建失败
        CreateDirectorySuccess, //远程服务器相应目录创建成功
        UploadNewFileSuccess, //上传新文件成功
        UploadNewFileFailed, //上传新文件失败
        FileExits, //文件已经存在
        RemoteFileBiggerThanLocalFile, //远程文件大于本地文件
        UploadFromBreakSuccess, //断点续传成功
        UploadFromBreakFailed, //断点续传失败
        DeleteRemoteFaild; //删除远程文件失败
    }

    public enum DownLoadStatus{
        serverConntionFail, //服务器连接失败
        RemoteFileNotExist, //远程文件不存在
        DownloadNewSuccess, //下载文件成功
        DownloadNewFailed, //下载文件失败
        LocalFileBiggerThanRemoteFile, // 本地文件大于远程文件
        DownloadFromBreakSuccess, // 断点续传成功
        DownloadFromBreakFailed; // 断点续传失败
    }

}
