import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * Created by Naknut on 02/10/14.
 */
public interface Chat extends Remote {
    public void sendMessage(String message) throws RemoteException;
    public void messageCallback(String message) throws RemoteException;
}