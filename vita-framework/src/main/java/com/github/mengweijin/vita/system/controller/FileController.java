package com.github.mengweijin.vita.system.controller;

import cn.dev33.satoken.annotation.SaCheckPermission;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.github.mengweijin.vita.framework.domain.PageQuery;
import com.github.mengweijin.vita.framework.domain.R;
import com.github.mengweijin.vita.framework.enums.dict.EOperationType;
import com.github.mengweijin.vita.framework.log.operation.Log;
import com.github.mengweijin.vita.framework.util.DownLoadUtils;
import com.github.mengweijin.vita.framework.util.MapstructUtils;
import com.github.mengweijin.vita.framework.validator.group.Group;
import com.github.mengweijin.vita.system.domain.bo.FileBO;
import com.github.mengweijin.vita.system.domain.entity.FileDO;
import com.github.mengweijin.vita.system.domain.vo.FileVO;
import com.github.mengweijin.vita.system.service.FileService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.Arrays;
import java.util.List;
import java.util.function.Supplier;

/**
 * <p>
 * File Controller
 * </p>
 *
 * @author mengweijin
 * @since 2023-06-03
 */
@Slf4j
@AllArgsConstructor
@RestController
@RequestMapping("/system/file")
public class FileController {

    private static final String LOG_TITLE = "文件管理";

    private FileService fileService;

    @Log(title = LOG_TITLE, operationType = EOperationType.UPLOAD)
    @PostMapping("/upload")
    public List<FileVO> upload(HttpServletRequest request) {
        List<FileDO> fileList = fileService.upload(request);
        return MapstructUtils.getConverter().convert(fileList, FileVO.class);
    }

    /**
     * @param id id in table VT_FILE
     */
    @Log(title = LOG_TITLE, operationType = EOperationType.DOWNLOAD)
    @GetMapping("/download/{id}")
    public void download(@PathVariable("id") Long id, HttpServletRequest request, HttpServletResponse response) {
        Supplier<FileDO> supplier = fileService.getFileSupplierById(id);
        DownLoadUtils.download(request, response, supplier);
    }

    @GetMapping("/preview/{id}")
    public void preview(@PathVariable("id") Long id, HttpServletRequest request, HttpServletResponse response) {
        Supplier<FileDO> supplier = fileService.getFileSupplierById(id);
        DownLoadUtils.preview(request, response, supplier);
    }

    /**
     * <p>
     * Get File page by File
     * </p>
     *
     * @param page       page
     * @param fileEntity {@link FileDO}
     * @return Page<File>
     */
    @SaCheckPermission("system:file:select")
    @GetMapping("/page")
    public PageQuery<FileVO> page(PageQuery<FileDO> page, FileDO fileEntity) {
        LambdaQueryWrapper<FileDO> wrapper = fileService.buildQueryWrapper(fileEntity);
        wrapper.orderByDesc(FileDO::getCreateTime);
        return fileService.pageVo(page, wrapper);
    }

    /**
     * <p>
     * Get File list by File
     * </p>
     *
     * @param fileEntity {@link FileDO}
     * @return List<File>
     */
    @SaCheckPermission("system:file:select")
    @GetMapping("/list")
    public List<FileVO> list(FileDO fileEntity) {
        return fileService.listVo(Wrappers.lambdaQuery(fileEntity));
    }

    /**
     * <p>
     * Get File by id
     * </p>
     *
     * @param id id
     * @return File
     */
    @GetMapping("/{id}")
    public FileVO getById(@PathVariable("id") Long id) {
        return fileService.getVoById(id);
    }

    /**
     * <p>
     * Add File
     * </p>
     *
     * @param file {@link MultipartFile}
     */
    @Log(title = LOG_TITLE, operationType = EOperationType.INSERT)
    @SaCheckPermission("system:file:create")
    @PostMapping("/create")
    public R<FileVO> create(@RequestPart("file") MultipartFile file) {
        FileDO fileDO = fileService.upload(file);
        return R.ok(MapstructUtils.getConverter().convert(fileDO, FileVO.class));
    }

    /**
     * <p>
     * Update File
     * </p>
     *
     * @param fileEntity {@link FileDO}
     */
    @Log(title = LOG_TITLE, operationType = EOperationType.UPDATE)
    @SaCheckPermission("system:file:update")
    @PostMapping("/update")
    public R<Void> update(@Validated({Group.Default.class, Group.Update.class}) @RequestBody FileBO fileEntity) {
        boolean bool = fileService.updateByBoById(fileEntity);
        return R.result(bool);
    }

    /**
     * <p>
     * Delete File by id(s), Multiple ids can be separated by commas ",".
     * </p>
     *
     * @param ids id
     */

    @Log(title = LOG_TITLE, operationType = EOperationType.REMOVE)
    @SaCheckPermission("system:file:remove")
    @PostMapping("/remove/{ids}")
    public R<Void> remove(@PathVariable("ids") Long[] ids) {
        return R.result(fileService.removeByIds(Arrays.asList(ids)));
    }

}

