import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.Scanner;

/**
 * Created by Naknut on 02/10/14.
 */
public class Client extends UnicastRemoteObject {

    protected Client() throws RemoteException {
        super();
    }

    public static void main(String[] args) {
        String uri = "rmi://" + args[0] + "/chat";
        try {
            Chat chat = (Chat) Naming.lookup(uri);
            Scanner scanner = new Scanner(System.in);
            while(true) {
                String message = scanner.nextLine();
                chat.sendMessage(message);
            }
        } catch (NotBoundException e) {
            e.printStackTrace();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }
}
