package xyz.macromogic.testjudge.security;

import java.security.Permission;

public class TestJudgeSecurityManager extends SecurityManager {
    @Override
    public void checkPermission(Permission perm) {
    }

    @Override
    public void checkPermission(Permission perm, Object context) {
    }

    @Override
    public void checkExit(int status) {
        super.checkExit(status);
        throw new SystemExitException(status);
    }
}
