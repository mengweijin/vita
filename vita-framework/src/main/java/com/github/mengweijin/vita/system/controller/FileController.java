package com.github.mengweijin.vita.system.controller;

import cn.dev33.satoken.annotation.SaCheckPermission;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.github.mengweijin.vita.framework.domain.R;
import com.github.mengweijin.vita.framework.log.aspect.annotation.Log;
import com.github.mengweijin.vita.framework.log.aspect.enums.EOperationType;
import com.github.mengweijin.vita.framework.util.BeanCopyUtils;
import com.github.mengweijin.vita.framework.util.DownLoadUtils;
import com.github.mengweijin.vita.framework.validator.group.Group;
import com.github.mengweijin.vita.system.domain.entity.FileDO;
import com.github.mengweijin.vita.system.domain.vo.FileVO;
import com.github.mengweijin.vita.system.service.FileService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.dromara.hutool.core.io.file.FileUtil;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;

/**
 * <p>
 *  File Controller
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
        return BeanCopyUtils.copyList(fileList, FileVO.class);
    }

    /**
     * @param id id in table VT_FILE
     */
    @Log(title = LOG_TITLE, operationType = EOperationType.DOWNLOAD)
    @GetMapping("/download/{id}")
    public void download(@PathVariable("id") Long id, HttpServletRequest request, HttpServletResponse response) {
        FileDO fileDO = fileService.getById(id);
        if(fileDO == null) {
            log.warn("No file was found!");
            return;
        }
        DownLoadUtils.simpleDownload(FileUtil.getInputStream(fileDO.getStoragePath()), fileDO.getName(), request, response);
    }

    /**
     * <p>
     * Get File page by File
     * </p>
     * @param page page
     * @param fileEntity {@link FileDO}
     * @return Page<File>
     */
    @SaCheckPermission("system:file:select")
    @GetMapping("/page")
    public IPage<FileVO> page(Page<FileDO> page, FileDO fileEntity) {
        LambdaQueryWrapper<FileDO> wrapper = fileService.getQueryWrapper(fileEntity);
        wrapper.orderByDesc(FileDO::getCreateTime);
        page = fileService.page(page, wrapper);
        return BeanCopyUtils.copyPage(page, FileVO.class);
    }

    /**
     * <p>
     * Get File list by File
     * </p>
     * @param fileEntity {@link FileDO}
     * @return List<File>
     */
    @SaCheckPermission("system:file:select")
    @GetMapping("/list")
    public List<FileVO> list(FileDO fileEntity) {
        List<FileDO> list = fileService.list(new LambdaQueryWrapper<>(fileEntity));
        return BeanCopyUtils.copyList(list, FileVO.class);
    }

    /**
     * <p>
     * Get File by id
     * </p>
     * @param id id
     * @return File
     */
    @SaCheckPermission("system:file:select")
    @GetMapping("/{id}")
    public FileVO getById(@PathVariable("id") Long id) {
        FileDO fileDO = fileService.getById(id);
        return BeanCopyUtils.copyBean(fileDO, FileVO.class);
    }

    /**
     * <p>
     * Add File
     * </p>
     * @param fileEntity {@link FileDO}
     */
    @Log(title = LOG_TITLE, operationType = EOperationType.INSERT)
    @SaCheckPermission("system:file:create")
    @PostMapping("/create")
    public R<Void> create(@Validated({Group.Default.class, Group.Create.class}) @RequestBody FileDO fileEntity) {
        boolean bool = fileService.save(fileEntity);
        return R.result(bool);
    }

    /**
     * <p>
     * Update File
     * </p>
     * @param fileEntity {@link FileDO}
     */
    @Log(title = LOG_TITLE, operationType = EOperationType.UPDATE)
    @SaCheckPermission("system:file:update")
    @PostMapping("/update")
    public R<Void> update(@Validated({Group.Default.class, Group.Update.class}) @RequestBody FileDO fileEntity) {
        boolean bool = fileService.updateById(fileEntity);
        return R.result(bool);
    }

    /**
     * <p>
     * Delete File by id(s), Multiple ids can be separated by commas ",".
     * </p>
     * @param ids id
     */

    @Log(title = LOG_TITLE, operationType = EOperationType.REMOVE)
    @SaCheckPermission("system:file:remove")
    @PostMapping("/remove/{ids}")
    public R<Void> remove(@PathVariable("ids") Long[] ids) {
        return R.result(fileService.removeByIds(Arrays.asList(ids)));
    }

}

