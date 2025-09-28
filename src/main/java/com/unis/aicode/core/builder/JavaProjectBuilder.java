package com.unis.aicode.core.builder;

import cn.hutool.core.util.RuntimeUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.io.File;
import java.util.concurrent.TimeUnit;

/**
 * 构建 Java 项目
 */
@Slf4j
@Component
public class JavaProjectBuilder {

    /**
     * 异步构建 Java 项目
     *
     * @param projectPath
     */
    public void buildProjectAsync(String projectPath) {
        Thread.ofVirtual().name("java-builder-" + System.currentTimeMillis())
                .start(() -> {
                    try {
                        buildProject(projectPath);
                    } catch (Exception e) {
                        log.error("异步构建 Java 项目时发生异常: {}", e.getMessage(), e);
                    }
                });
    }

    /**
     * 构建 Java 项目
     *
     * @param projectPath 项目根目录路径
     * @return 是否构建成功
     */
    public boolean buildProject(String projectPath) {
        File projectDir = new File(projectPath);
        if (!projectDir.exists() || !projectDir.isDirectory()) {
            log.error("项目目录不存在：{}", projectPath);
            return false;
        }
        // 检查是否有 pom.xml 文件（Maven 项目标识）
        File pomFile = new File(projectDir, "pom.xml");
        if (!pomFile.exists()) {
            log.error("项目目录中没有 pom.xml 文件：{}", projectPath);
            return false;
        }
        log.info("开始构建 Java 项目：{}", projectPath);
        // 执行 mvn clean package 命令构建项目
        if (!executeMvnBuild(projectDir)) {
            log.error("mvn clean package 执行失败：{}", projectPath);
            return false;
        }
        // 验证 target 目录是否生成
        File targetDir = new File(projectDir, "target");
        if (!targetDir.exists() || !targetDir.isDirectory()) {
            log.error("构建完成但 target 目录未生成：{}", projectPath);
            return false;
        }
        log.info("Java 项目构建成功，target 目录：{}", projectPath);
        return true;
    }

    /**
     * 执行 mvn clean package 命令
     */
    private boolean executeMvnBuild(File projectDir) {
        log.info("执行 mvn clean package...");
        String command = String.format("%s clean package", buildCommand("mvn"));
        return executeCommand(projectDir, command, 600); // 10分钟超时
    }

    /**
     * 根据操作系统构造命令
     *
     * @param baseCommand 基础命令
     * @return 完整命令
     */
    private String buildCommand(String baseCommand) {
        if (isWindows()) {
            return baseCommand + ".cmd";
        }
        return baseCommand;
    }

    /**
     * 操作系统检测
     *
     * @return 是否为 Windows 系统
     */
    private boolean isWindows() {
        return System.getProperty("os.name").toLowerCase().contains("windows");
    }

    /**
     * 执行命令
     *
     * @param workingDir     工作目录
     * @param command        命令字符串
     * @param timeoutSeconds 超时时间（秒）
     * @return 是否执行成功
     */
    private boolean executeCommand(File workingDir, String command, int timeoutSeconds) {
        try {
            log.info("在目录 {} 中执行命令: {}", workingDir.getAbsolutePath(), command);
            Process process = RuntimeUtil.exec(
                    null,
                    workingDir,
                    command.split("\\s+") // 命令分割为数组
            );
            // 等待进程完成，设置超时
            boolean finished = process.waitFor(timeoutSeconds, TimeUnit.SECONDS);
            if (!finished) {
                log.error("命令执行超时（{}秒），强制终止进程", timeoutSeconds);
                process.destroyForcibly();
                return false;
            }
            int exitCode = process.exitValue();
            if (exitCode == 0) {
                log.info("命令执行成功: {}", command);
                return true;
            } else {
                log.error("命令执行失败，退出码: {}", exitCode);
                return false;
            }
        } catch (Exception e) {
            log.error("执行命令失败: {}, 错误信息: {}", command, e.getMessage());
            return false;
        }
    }

}