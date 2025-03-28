package com.github.mengweijin.vita.framework.util;

import com.github.mengweijin.vita.framework.exception.ClientException;
import com.github.mengweijin.vita.framework.exception.ServerException;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.dromara.hutool.crypto.digest.MD5;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.support.StandardServletMultipartResolver;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.function.Function;

/**
 * 文件上传。
 *
 * @author Meng Wei Jin
 **/
@Slf4j
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class UploadUtils {

    /**
     * 上传文件。根据文件后缀名自动分文件夹存放。
     *
     * @param request  MultipartHttpServletRequest
     * @param function function
     * @return FileInfo list in server
     */
    public static <R> List<R> upload(HttpServletRequest request, Function<MultipartFile, R> function) {
        List<R> fileList = new ArrayList<>();
        try {
            StandardServletMultipartResolver multipartResolver = new StandardServletMultipartResolver();
            // 如果是文件上传
            if (multipartResolver.isMultipart(request)) {
                MultipartHttpServletRequest multiRequest = (MultipartHttpServletRequest) request;
                // 获取所有上传的文件名
                Iterator<String> iterator = multiRequest.getFileNames();
                while (iterator.hasNext()) {
                    MultipartFile file = multiRequest.getFile(iterator.next());
                    R r = function.apply(file);
                    fileList.add(r);
                }
            } else {
                String message = "Can't found upload file! The request is not a MultipartHttpServletRequest.";
                throw new ClientException(message);
            }
        } catch (Exception e) {
            throw new ServerException(e);
        }
        return fileList;
    }

    public static String md5(MultipartFile multipartFile) {
        try {
            return MD5.of().digestHex(multipartFile.getBytes());
        } catch (IOException e) {
            throw new ServerException(e);
        }
    }

}
