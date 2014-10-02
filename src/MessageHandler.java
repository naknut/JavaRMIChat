import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * Created by Naknut on 02/10/14.
 */
public interface MessageHandler extends Remote {
    public void handleMessage(String message) throws RemoteException;
    public String getNickname() throws RemoteException;
    public void setNickname(String nickname) throws RemoteException;
}
