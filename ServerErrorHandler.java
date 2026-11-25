import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;

/**
 * Dashboard actions talk to QuestionServer. If it is not running, the
 * clients throw ServerUnavailableException; without this handler that
 * exception dies silently on the Swing thread and the button appears
 * to do nothing. Instead, show the user a dialog explaining why.
 */
public class ServerErrorHandler {

    private static boolean installed = false;

    public static void install() {

        if (installed) {

            return;
        }

        installed = true;

        Thread.UncaughtExceptionHandler previous =
                Thread.getDefaultUncaughtExceptionHandler();

        Thread.setDefaultUncaughtExceptionHandler(
                (thread, error) -> {

                    ServerUnavailableException serverError =
                            findServerError(error);

                    if (serverError == null) {

                        if (previous != null) {

                            previous.uncaughtException(
                                    thread,
                                    error
                            );

                        } else {

                            error.printStackTrace();
                        }

                        return;
                    }

                    SwingUtilities.invokeLater(
                            () -> showServerError(serverError)
                    );
                }
        );
    }

    private static ServerUnavailableException findServerError(
            Throwable error) {

        while (error != null) {

            if (error instanceof ServerUnavailableException) {

                return (ServerUnavailableException) error;
            }

            error = error.getCause();
        }

        return null;
    }

    private static void showServerError(
            ServerUnavailableException error) {

        JOptionPane.showMessageDialog(
                null,
                "Can't connect to the question server at "
                + error.getHost()
                + ":"
                + error.getPort()
                + ".\n\n"
                + "Make sure QuestionServer is running "
                + "(java QuestionServer) and try again.",
                "Server Unavailable",
                JOptionPane.ERROR_MESSAGE
        );
    }
}
