package xyz.macromogic.testjudge.security;

public class SystemExitException extends SecurityException {
    private static final long serialVersionUID = 1L;
    public final int status;

    public SystemExitException(int status) {
        super("Ignoring System.exit()");
        this.status = status;
    }

    public void printAttemptStatus() {
        System.err.println("Attempted to exit with value " + status);
    }
}
