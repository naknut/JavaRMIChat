import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.Scanner;

/**
 * Created by Naknut on 02/10/14.
 */
public class Client extends UnicastRemoteObject implements MessageHandler {

    public static void main(String[] args) {
        String uri = "rmi://" + args[0] + "/chat";
        try {
            Chat chat = (Chat) Naming.lookup(uri);
            Client client = new Client();
            chat.registerForNotification(client);
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

    protected Client() throws RemoteException {
        super();
    }

    @Override
    public void handleMessage(String message) throws RemoteException {
        System.out.println(message);
    }
}
