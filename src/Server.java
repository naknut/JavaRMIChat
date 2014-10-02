import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

/**
 * Created by Naknut on 02/10/14.
 */
public class Server extends UnicastRemoteObject implements Chat {

    protected Server() throws RemoteException {
        super();
    }

    public static void main(String[] args) throws MalformedURLException, RemoteException {
        Server server = new Server();
        Naming.rebind("chat", server);
    }

    public void sendMessage(String message) throws RemoteException {
        System.out.println(message);
    }

    public void messageCallback(String message) throws RemoteException {

    }
}
