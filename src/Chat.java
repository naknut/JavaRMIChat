import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * Created by Naknut on 02/10/14.
 */
public interface Chat extends Remote {
    public void sendMessage(String message, MessageHandler source) throws RemoteException;
    public void registerForNotification(MessageHandler messageHandler) throws RemoteException;
    public void deregisterForNotification(MessageHandler messageHandler) throws RemoteException;
}
