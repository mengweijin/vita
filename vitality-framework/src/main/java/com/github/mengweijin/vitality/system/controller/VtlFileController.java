package com.github.mengweijin.vitality.system.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.github.mengweijin.vitality.framework.domain.R;
import com.github.mengweijin.vitality.framework.exception.MinioServiceException;
import com.github.mengweijin.vitality.framework.frontend.tinymce.TinymceImageResponse;
import com.github.mengweijin.vitality.framework.minio.MinioService;
import com.github.mengweijin.vitality.framework.util.DownLoadUtils;
import com.github.mengweijin.vitality.system.dto.VtlFileDTO;
import com.github.mengweijin.vitality.system.entity.VtlFile;
import com.github.mengweijin.vitality.system.service.VtlFileService;
import io.minio.GetObjectResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.List;

/**
 * @author mengweijin
 * @date 2022/10/30
 */
@RestController
@RequestMapping("/vtl-file")
@Validated
public class VtlFileController {

    @Autowired
    private MinioService minioService;

    @Autowired
    private VtlFileService fileService;

    @PostMapping("/upload")
    public List<VtlFile> upload(HttpServletRequest request) {
        return fileService.upload(request);
    }

    @PostMapping("/tinymce/upload")
    public TinymceImageResponse tinymceUpload(HttpServletRequest request) {
        try {
            List<VtlFile> fileList = fileService.upload(request);
            String previewUrl = fileService.getPreviewUrlByFilePath(fileList.get(0).getFilePath());
            return TinymceImageResponse.success(previewUrl);
        } catch (RuntimeException e) {
            return TinymceImageResponse.failed(e.getMessage());
        }

    }


    /**
     * @param fileId uuid in table VTL_FILE
     */
    @PostMapping("/download/{fileId}")
    public R download(@PathVariable("fileId") Long fileId, HttpServletRequest request, HttpServletResponse response) {
        VtlFile vtlFile = fileService.getById(fileId);
        if(vtlFile == null) {
            return R.error("No file was found in database! fileId=" + fileId);
        }
        try(GetObjectResponse getObjectResponse = minioService.download(vtlFile.getFilePath())) {
            DownLoadUtils.download(getObjectResponse, vtlFile.getFileName(), request, response);
            return R.success();
        } catch (IOException e) {
            throw new MinioServiceException(e);
        }
    }

    @GetMapping("/detail/{id}")
    public VtlFileDTO detailById(@PathVariable("id") Long id) {
        return fileService.detailById(id);
    }

    @GetMapping("/page")
    public IPage<VtlFileDTO> page(Page<VtlFileDTO> page, VtlFileDTO dto) {
        return fileService.page(page, dto);
    }

}
