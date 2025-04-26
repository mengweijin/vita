package com.github.mengweijin.vita.system.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
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
import org.dromara.hutool.core.text.StrValidator;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;

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

    /**
     * Custom paging query
     * @param page page
     * @param fileEntity {@link FileDO}
     * @return IPage
     */
    public IPage<FileDO> page(IPage<FileDO> page, FileDO fileEntity){
        LambdaQueryWrapper<FileDO> query = new LambdaQueryWrapper<>();
        query
                .eq(StrValidator.isNotBlank(fileEntity.getSuffix()), FileDO::getSuffix, fileEntity.getSuffix())
                .eq(StrValidator.isNotBlank(fileEntity.getStoragePath()), FileDO::getStoragePath, fileEntity.getStoragePath())
                .eq(StrValidator.isNotBlank(fileEntity.getMd5()), FileDO::getMd5, fileEntity.getMd5())
                .eq(!Objects.isNull(fileEntity.getId()), FileDO::getId, fileEntity.getId())
                .eq(!Objects.isNull(fileEntity.getCreateBy()), FileDO::getCreateBy, fileEntity.getCreateBy())
                .eq(!Objects.isNull(fileEntity.getCreateTime()), FileDO::getCreateTime, fileEntity.getCreateTime())
                .eq(!Objects.isNull(fileEntity.getUpdateBy()), FileDO::getUpdateBy, fileEntity.getUpdateBy())
                .eq(!Objects.isNull(fileEntity.getUpdateTime()), FileDO::getUpdateTime, fileEntity.getUpdateTime())
                .like(StrValidator.isNotBlank(fileEntity.getName()), FileDO::getName, fileEntity.getName());
        return this.page(page, query);
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
