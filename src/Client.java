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

    private String nickname;
    private static Boolean running = true;

    public static void main(String[] args) {
        String uri = "rmi://" + args[0] + "/chat";
        try {
            Chat chat = (Chat) Naming.lookup(uri);
            Client client = new Client();
            chat.registerForNotification(client);
            Scanner scanner = new Scanner(System.in);
            while(running) {
                String message = scanner.nextLine();
                chat.sendMessage(message,client);
                if(message.equals("/quit"))
                    running = false;
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
        this.nickname="Unknown-"+java.util.UUID.randomUUID();
    }

    @Override
    public String getNickname(){
        return nickname;
    }

    @Override
    public void setNickname(String nickname){
        this.nickname=nickname;
    }


    @Override
    public void handleMessage(String message) throws RemoteException {
        System.out.println(message);
    }
}
