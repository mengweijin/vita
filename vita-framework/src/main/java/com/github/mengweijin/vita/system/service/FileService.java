package com.github.mengweijin.vita.system.service;

import cn.hutool.v7.core.data.id.IdUtil;
import cn.hutool.v7.core.io.file.FileNameUtil;
import cn.hutool.v7.core.io.file.FileUtil;
import cn.hutool.v7.core.text.CharSequenceUtil;
import cn.hutool.v7.core.text.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.github.mengweijin.vita.framework.constant.Const;
import com.github.mengweijin.vita.framework.enums.dict.EYesNo;
import com.github.mengweijin.vita.framework.exception.ServerException;
import com.github.mengweijin.vita.framework.mybatis.BaseVitaService;
import com.github.mengweijin.vita.framework.properties.VitaProperties;
import com.github.mengweijin.vita.framework.util.AopUtils;
import com.github.mengweijin.vita.framework.util.UploadUtils;
import com.github.mengweijin.vita.system.domain.entity.FileDO;
import com.github.mengweijin.vita.system.domain.vo.FileVO;
import com.github.mengweijin.vita.system.mapper.FileMapper;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;
import java.util.function.Supplier;

/**
 * <p>
 * File Service
 * Add @Transactional(rollbackFor = Exception.class) if you need.
 * </p>
 *
 * @author mengweijin
 * @since 2023-06-03
 */
@Slf4j
@Service
@AllArgsConstructor
public class FileService extends BaseVitaService<FileMapper, FileDO, FileVO> {

    private VitaProperties vitaProperties;

    public static void copyFile(MultipartFile multipartFile, String path) {
        try {
            FileUtil.copy(multipartFile.getInputStream(), FileUtil.file(path));
        } catch (IOException e) {
            throw new ServerException(e);
        }
    }

    public static String getStoragePath(String dir, String suffix) {
        LocalDateTime now = LocalDateTime.now(Const.ZONE);
        String year = String.valueOf(now.getYear());
        String month = CharSequenceUtil.padPre(String.valueOf(now.getMonthValue()), 2, "0");
        String day = CharSequenceUtil.padPre(String.valueOf(now.getDayOfMonth()), 2, "0");
        return dir + String.join(File.separator, year, month, day, IdUtil.simpleUUID()) + Const.DOT + suffix;
    }

    @Override
    public LambdaQueryWrapper<FileDO> buildQueryWrapper(FileDO fileDO) {
        LambdaQueryWrapper<FileDO> wrapper = Wrappers.lambdaQuery();
        wrapper.eq(fileDO.getId() != null, FileDO::getId, fileDO.getId());
        wrapper.eq(StrUtil.isNotBlank(fileDO.getMd5()), FileDO::getMd5, fileDO.getMd5());
        wrapper.eq(StrUtil.isNotBlank(fileDO.getSuffix()), FileDO::getSuffix, fileDO.getSuffix());

        wrapper.eq(fileDO.getCreateBy() != null, FileDO::getCreateBy, fileDO.getCreateBy());
        wrapper.eq(fileDO.getUpdateBy() != null, FileDO::getUpdateBy, fileDO.getUpdateBy());
        wrapper.gt(fileDO.getStartCreateTime() != null, FileDO::getCreateTime, fileDO.getStartCreateTime());
        wrapper.le(fileDO.getEndCreateTime() != null, FileDO::getCreateTime, fileDO.getEndCreateTime());
        wrapper.like(StrUtil.isNotBlank(fileDO.getName()), FileDO::getName, fileDO.getName());
        return wrapper;
    }

    public List<FileDO> getByMd5(String md5) {
        return this.lambdaQuery().eq(FileDO::getMd5, md5).list();
    }

    public long countByMd5(String md5) {
        return this.lambdaQuery().eq(FileDO::getMd5, md5).count();
    }

    public List<FileDO> upload(HttpServletRequest request) {
        List<FileDO> list = UploadUtils.upload(request, this::buildFileDO);
        AopUtils.getAopProxy(this).saveBatch(list, Constants.DEFAULT_BATCH_SIZE);
        return list;
    }

    public FileDO upload(MultipartFile multipartFile) {
        FileDO fileDO = this.buildFileDO(multipartFile);
        AopUtils.getAopProxy(this).save(fileDO);
        return fileDO;
    }

    public FileDO buildFileDO(MultipartFile multipartFile) {
        String fileName = multipartFile.getOriginalFilename();
        String md5 = UploadUtils.md5(multipartFile);
        String suffix = FileNameUtil.getSuffix(fileName);

        FileDO fileEntity = new FileDO();
        fileEntity.setMd5(md5);
        fileEntity.setName(fileName);
        fileEntity.setSuffix(suffix);
        fileEntity.setDeleted(EYesNo.N.getValue());

        String storagePath = getStoragePath(vitaProperties.getUploadPath(), suffix);
        copyFile(multipartFile, storagePath);
        fileEntity.setStoragePath(storagePath);
        return fileEntity;
    }

    public Supplier<FileDO> getFileSupplierById(Long id) {
        return () -> {
            FileDO fileDO = this.getById(id);
            if (fileDO == null) {
                throw new ServerException("No file was found By id=" + id);
            }
            return fileDO;
        };
    }

    @Override
    public boolean removeByIds(Collection<?> list) {
        if (CollectionUtils.isEmpty(list)) {
            return false;
        }

        List<FileDO> fileList = this.lambdaQuery().in(FileDO::getId, list).list();

        boolean removed = super.removeByIds(list);
        if (removed) {
            // 从磁盘物理删除文件
            fileList.forEach(f -> FileUtil.del(f.getStoragePath()));
        }

        return removed;
    }
}
