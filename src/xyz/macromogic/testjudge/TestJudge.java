package xyz.macromogic.testjudge;

import xyz.macromogic.testjudge.problem.Lab9Q1;
import xyz.macromogic.testjudge.problem.Lab9Q2;
import xyz.macromogic.testjudge.problem.Problem;
import xyz.macromogic.testjudge.security.TestJudgeSecurityManager;
import xyz.macromogic.testjudge.util.FileLoader;
import xyz.macromogic.testjudge.util.StudentInfo;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;

public class TestJudge {
    public static void main(String[] args) {
        setSecurityManager();
        final String submissionPath = "/Users/macmo/Workspace/SUSTech/CS102B_SA/Exercise for Week9/";
        List<StudentInfo> infoList = null;
        try (BufferedReader csvReader = Files.newBufferedReader(Paths.get(submissionPath + "grades.csv"))) {
            infoList = csvReader.lines()
                    .skip(3)
                    .map(line -> line.substring(1, line.length() - 1).split("\"*,\"*"))
                    .map(StudentInfo::newStudentInfo)
                    .collect(Collectors.toList());
        } catch (IOException e) {
            e.printStackTrace();
        }

        if (infoList != null && infoList.size() > 0) {
            try (PrintWriter csvWriter = new PrintWriter(submissionPath + "grades.csv")) {
                csvWriter.println("\"Exercise for Week9\",\"SCORE_GRADE_TYPE\"\n" +
                        "\"\"\n" +
                        "\"Display ID\",\"ID\",\"Last Name\",\"First Name\",\"grade\",\"Submission date\",\"Late submission\"");
                for (StudentInfo info : infoList) {
                    int score = 100;
                    String basePath = submissionPath + info.getPath();
                    STDOUT.println(basePath);
                    Problem[] problems = {new Lab9Q1(), new Lab9Q2()};
                    StringBuilder msg = new StringBuilder("--------\n");
                    for (Problem prob : problems) {
                        String message = prob.judge(basePath + "Submission attachment(s)/");
                        msg.append(message).append("\n--------\n");
                        if (!prob.isAccepted()) {
                            score -= 10;
                        }
                    }
                    info.setGrade(String.valueOf(score));
                    csvWriter.println(info.toCsvLine());
                    String commentMsg = String.format("Your score is: %d\n", score) + msg;
                    try (PrintWriter commentOut = new PrintWriter(basePath + "comments.txt")) {
                        commentOut.print("<code>");
                        commentOut.print(commentMsg);
                        commentOut.print("</code>");
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }
                }
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }

        FileLoader.clear();
    }

    public static final InputStream STDIN = System.in;
    public static final PrintStream STDOUT = System.out;
    public static final PrintStream STDERR = System.err;
    public static final String baseDirPath = "run/";

    private static void setSecurityManager() {
        System.setSecurityManager(new TestJudgeSecurityManager());
    }
}
