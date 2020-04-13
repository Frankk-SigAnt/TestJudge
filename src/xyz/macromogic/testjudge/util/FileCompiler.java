package xyz.macromogic.testjudge.util;

import javax.tools.JavaCompiler;
import javax.tools.ToolProvider;
import java.io.ByteArrayOutputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.nio.file.Paths;

import static xyz.macromogic.testjudge.TestJudge.baseDirPath;

public class FileCompiler {
    private JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();

    public JudgeResult compile(String... filePath) {
        JudgeResult result = new JudgeResult();
        if (filePath.length == 0) {
            result.update(JudgeResult.Verdict.COMPILE_ERROR, "No input file\n");
        } else {
            ByteArrayOutputStream compileStdErr = new ByteArrayOutputStream();
            int compileStatus = compiler.run(null, null, compileStdErr, filePath);
            if (compileStatus != 0) {
                result.update(JudgeResult.Verdict.COMPILE_ERROR, compileStdErr.toString());
            }
        }
        return result;
    }

    public Class<?> loadClass(String name) {
        try {
            URL baseURL = Paths.get(baseDirPath).toUri().toURL();
            URLClassLoader classLoader = URLClassLoader.newInstance(new URL[]{baseURL});
            return Class.forName(name, true, classLoader);
        } catch (MalformedURLException | ClassNotFoundException | NoClassDefFoundError e) {
            return null;
        }
    }
}
