package com.ezreal.ai.code.generator.domain.ai.service.saver;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.StrUtil;
import com.ezreal.ai.code.generator.domain.ai.model.HtmlCodeResult;
import com.ezreal.ai.code.generator.domain.ai.model.MultiFileCodeResult;
import com.ezreal.ai.code.generator.domain.ai.model.valobj.CodeGenTypeEnum;
import org.apache.commons.lang3.StringUtils;

import java.io.File;
import java.nio.charset.StandardCharsets;

/**
 * @author Ezreal
 * @Date 2025/8/9
 */
public class FileCodeSaver {

    public static final String FILE_SAVE_ROOT_DIR = System.getProperty("user.dir") + "/tmp/code_output";

    public static File saveHtmlCodeResult(HtmlCodeResult htmlCodeResult) {
        String fileDir = buildFileDir(CodeGenTypeEnum.HTML);
        write2File(fileDir, "index.html", htmlCodeResult.getHtmlCode());
        return new File(fileDir);
    }


    public static File saveMultiFileCodeResult(MultiFileCodeResult multiFileCodeResult) {
        String fileDir = buildFileDir(CodeGenTypeEnum.MULTI_FILE);
        write2File(fileDir, "index.html", multiFileCodeResult.getHtmlCode());
        write2File(fileDir, "index.css", multiFileCodeResult.getCssCode());
        write2File(fileDir, "index.js", multiFileCodeResult.getJsCode());
        return new File(fileDir);

    }

    private static void write2File(String filePath, String fileName, String content) {
        String fileFullPath = StringUtils.join(filePath, File.separator, fileName);
        FileUtil.writeString(content, fileFullPath, StandardCharsets.UTF_8);
    }


    private static String buildFileDir(CodeGenTypeEnum codeGenTypeEnum) {
        String uniqueName = String.format("%s_%s", codeGenTypeEnum.getValue(), IdUtil.getSnowflakeNextIdStr());
        return StringUtils.join(FILE_SAVE_ROOT_DIR, File.separator, uniqueName);
    }
}
