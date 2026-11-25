/**
 * Thrown by QuestionClient when the QuestionServer cannot be reached
 * or the connection fails mid-request.
 */
public class ServerUnavailableException extends IllegalStateException {

    private final String host;
    private final int port;

    public ServerUnavailableException(
            String action,
            String host,
            int port,
            Throwable cause) {

        super(
                "Unable to " + action
                + " (" + host + ":" + port + ").",
                cause
        );

        this.host = host;
        this.port = port;
    }

    public String getHost() {

        return host;
    }

    public int getPort() {

        return port;
    }
}
