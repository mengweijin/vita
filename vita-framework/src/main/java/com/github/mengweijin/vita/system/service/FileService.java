package com.github.mengweijin.vita.system.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.baomidou.mybatisplus.extension.repository.CrudRepository;
import com.github.mengweijin.vita.framework.VitaProperties;
import com.github.mengweijin.vita.framework.constant.Const;
import com.github.mengweijin.vita.framework.exception.ServerException;
import com.github.mengweijin.vita.framework.util.AopUtils;
import com.github.mengweijin.vita.framework.util.UploadUtils;
import com.github.mengweijin.vita.system.domain.entity.FileDO;
import com.github.mengweijin.vita.system.mapper.FileMapper;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.dromara.hutool.core.collection.CollUtil;
import org.dromara.hutool.core.data.id.IdUtil;
import org.dromara.hutool.core.io.file.FileNameUtil;
import org.dromara.hutool.core.io.file.FileUtil;
import org.dromara.hutool.core.text.CharSequenceUtil;
import org.dromara.hutool.core.text.StrUtil;
import org.dromara.hutool.core.text.StrValidator;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * <p>
 *  File Service
 *  Add @Transactional(rollbackFor = Exception.class) if you need.
 * </p>
 *
 * @author mengweijin
 * @since 2023-06-03
 */
@Slf4j
@Service
@AllArgsConstructor
public class FileService extends CrudRepository<FileMapper, FileDO> {

    private VitaProperties vitaProperties;

    @Override
    public boolean removeByIds(Collection<?> list) {
        // 注意顺序，先查出来
        List<FileDO> fileEntityList = this.lambdaQuery().in(FileDO::getId, list).list();

        List<String> shouldBeDeleteFromDistFilePathList = new ArrayList<>();
        fileEntityList.forEach(fileEntity -> {
            if (this.countByMd5(fileEntity.getMd5()) <= 1) {
                shouldBeDeleteFromDistFilePathList.add(fileEntity.getStoragePath());
            }
        });

        boolean removed = super.removeByIds(list);
        if (removed) {
            shouldBeDeleteFromDistFilePathList.forEach(FileUtil::del);
        }
        return removed;
    }

    public LambdaQueryWrapper<FileDO> getQueryWrapper(FileDO fileDO) {
        LambdaQueryWrapper<FileDO> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(fileDO.getId() != null, FileDO::getId, fileDO.getId());
        wrapper.eq(StrUtil.isNotBlank(fileDO.getMd5()), FileDO::getMd5, fileDO.getMd5());
        wrapper.eq(StrUtil.isNotBlank(fileDO.getSuffix()), FileDO::getSuffix, fileDO.getSuffix());

        wrapper.eq(fileDO.getCreateBy() != null, FileDO::getCreateBy, fileDO.getCreateBy());
        wrapper.eq(fileDO.getUpdateBy() != null, FileDO::getUpdateBy, fileDO.getUpdateBy());
        wrapper.gt(fileDO.getSearchStartTime() != null, FileDO::getCreateTime, fileDO.getSearchStartTime());
        wrapper.le(fileDO.getSearchEndTime() != null, FileDO::getCreateTime, fileDO.getSearchEndTime());
        if (StrValidator.isNotBlank(fileDO.getKeywords())) {
            wrapper.or(w -> w.like(FileDO::getName, fileDO.getKeywords()));
        }
        return wrapper;
    }

    public List<FileDO> getByMd5(String md5) {
        return this.lambdaQuery().eq(FileDO::getMd5, md5).list();
    }

    public long countByMd5(String md5) {
        return this.lambdaQuery().eq(FileDO::getMd5, md5).count();
    }

    public List<FileDO> upload(HttpServletRequest request) {
        List<FileDO> list = UploadUtils.upload(request, multipartFile -> {
            String md5 = UploadUtils.md5(multipartFile);
            String fileName = multipartFile.getOriginalFilename();
            String suffix = FileNameUtil.getSuffix(fileName);

            FileDO fileEntity = new FileDO();
            fileEntity.setMd5(md5);
            fileEntity.setName(fileName);
            fileEntity.setSuffix(suffix);

            List<FileDO> fileEntityList = this.getByMd5(md5);
            if (CollUtil.isEmpty(fileEntityList)) {
                String storagePath = getPath(vitaProperties.getFileDir(), suffix);
                copyFile(multipartFile, storagePath);
                fileEntity.setStoragePath(storagePath);
            } else {
                String storagePath = fileEntityList.get(0).getStoragePath();
                if (!FileUtil.exists(storagePath)) {
                    copyFile(multipartFile, storagePath);
                }
                fileEntity.setStoragePath(storagePath);
            }
            return fileEntity;
        });

        AopUtils.getAopProxy(this).saveBatch(list, Constants.DEFAULT_BATCH_SIZE);
        return list;
    }

    public static void copyFile(MultipartFile multipartFile, String path) {
        try {
            FileUtil.copy(multipartFile.getInputStream(), FileUtil.file(path));
        } catch (IOException e) {
            throw new ServerException(e);
        }
    }

    public static String getPath(String dir, String suffix) {
        LocalDateTime now = LocalDateTime.now();
        String year = String.valueOf(now.getYear());
        String month = CharSequenceUtil.padPre(String.valueOf(now.getMonthValue()), 2, "0");
        String day = CharSequenceUtil.padPre(String.valueOf(now.getDayOfMonth()), 2, "0");
        return dir + String.join(File.separator, year, month, day, IdUtil.simpleUUID()) + Const.DOT + suffix;
    }
}
