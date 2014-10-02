import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * Created by Naknut on 02/10/14.
 */
public interface Chat extends Remote {
    public String getUsernames() throws RemoteException;
    public String getHelp() throws RemoteException;
    public boolean changeNickname(String newName, String oldName) throws RemoteException;
    public void sendMessageToAllButSender(String message, String sender) throws RemoteException;

    public String registerForNotification(MessageHandler messageHandler, String username) throws RemoteException;
    public void deregisterForNotification(String username) throws RemoteException;
}
