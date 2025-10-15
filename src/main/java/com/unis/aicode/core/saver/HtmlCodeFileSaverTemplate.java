package com.unis.aicode.core.saver;

import cn.hutool.core.util.StrUtil;
import com.unis.aicode.ai.model.HtmlCodeResult;
import com.unis.aicode.exception.BusinessException;
import com.unis.aicode.exception.ErrorCode;
import com.unis.aicode.model.enums.CodeGenTypeEnum;

/**
 * HTML代码文件保存器
 *
 * @author tanlianwang
 */
public class HtmlCodeFileSaverTemplate extends CodeFileSaverTemplate<HtmlCodeResult> {

    @Override
    protected CodeGenTypeEnum getCodeType() {
        return CodeGenTypeEnum.HTML;
    }

    @Override
    protected void saveFiles(HtmlCodeResult result, String baseDirPath) {
        writeToFile(baseDirPath, "index.html", result.getHtmlCode());
    }

    @Override
    protected void validateInput(HtmlCodeResult result) {
        super.validateInput(result);
        // HTML 代码不能为空
        if (StrUtil.isBlank(result.getHtmlCode())) {
            throw new BusinessException(ErrorCode.SYSTEM_ERROR, "HTML 代码不能为空");
        }
    }
}
