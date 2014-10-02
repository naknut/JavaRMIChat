import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;

/**
 * Created by Naknut on 02/10/14.
 */
public class Server extends UnicastRemoteObject implements Chat {

    ArrayList<MessageHandler> messageHandlers = new ArrayList<MessageHandler>();

    public static void main(String[] args) throws MalformedURLException, RemoteException {
        Server server = new Server();
        Naming.rebind("chat", server);
    }

    protected Server() throws RemoteException {
        super();
    }

    public void sendMessage(String message) throws RemoteException {
        System.out.println(message);
        for(MessageHandler messageHandler : messageHandlers) {
            messageHandler.handleMessage(message);
        }
    }

    @Override
    public void registerForNotification(MessageHandler messageHandler) {
        messageHandlers.add(messageHandler);
    }

    @Override
    public void deregisterForNotification(MessageHandler messageHandler) {
        messageHandlers.remove(messageHandler);
    }
}
