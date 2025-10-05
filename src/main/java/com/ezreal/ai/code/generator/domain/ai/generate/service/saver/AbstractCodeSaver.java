package com.ezreal.ai.code.generator.domain.ai.generate.service.saver;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.StrUtil;
import com.ezreal.ai.code.generator.enums.CodeGenTypeEnum;

import java.io.File;
import java.nio.charset.StandardCharsets;

/**
 * @author Ezreal
 * @Date 2025/8/23
 */
public abstract class AbstractCodeSaver<T> {

    public static final String FILE_SAVE_ROOT_DIR = System.getProperty("user.dir") + "/tmp/code_output";

    public final File saveCode(T result) {

        // 1.检查参数
        validateInput(result);
        // 2.构建唯一文件目录
        String fileDir = buildFileDir(getCodeGenType());
        // 3.保存文件
        saveFile(result, fileDir);
        return new File(fileDir);
    }

    protected abstract void validateInput(T result);

    protected abstract void saveFile(T result, String fileDir);

    protected abstract CodeGenTypeEnum getCodeGenType();

    private String buildFileDir(CodeGenTypeEnum codeGenTypeEnum) {
        String uniqueName = String.format("%s_%s", codeGenTypeEnum.getValue(), IdUtil.getSnowflakeNextIdStr());
        return StrUtil.join(FILE_SAVE_ROOT_DIR, File.separator, uniqueName);
    }

    protected void write2File(String filePath, String fileName, String content) {
        String fileFullPath = StrUtil.join(filePath, File.separator, fileName);
        FileUtil.writeString(content, fileFullPath, StandardCharsets.UTF_8);
    }
}
