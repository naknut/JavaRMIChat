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

    private static String nickname;
    private static Boolean running = true;

    public static void main(String[] args) {
        String uri = "rmi://" + args[0] + "/chat";
        nickname = args[1];
        try {
            Chat chat = (Chat) Naming.lookup(uri);
            Client client = new Client();
            System.out.println(chat.registerForNotification(client, client.getNickname()));
            Scanner scanner = new Scanner(System.in);
            while(running) {
                String message = scanner.nextLine();
                if(message.equals("/quit")) {
                    running = false;
                    chat.deregisterForNotification(client.getNickname());
                }
                else if(message.equals("/who"))
                    System.out.println(chat.getUsernames());
                else if(message.equals("/help"))
                    System.out.println(chat.getHelp());
                else if(message.startsWith("/nick")) {
                    String[] tokens = message.split(" ");
                    if(chat.changeNickname(tokens[1], nickname)) {
                        nickname = tokens[1];
                        System.out.println("Changed username to " + nickname);
                    } else {
                        System.out.println("Could not change username");
                    }
                }
                else if(message.startsWith("/")) {
                    System.out.println("Unknown command");
                }
                else
                    chat.sendMessageToAllButSender(message, nickname);
            }
        } catch (NotBoundException e) {
            e.printStackTrace();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        System.exit(0);
    }

    protected Client() throws RemoteException {
        super();
    }

    @Override
    public String getNickname(){
        return nickname;
    }

    @Override
    public void setNickname(String nickname) {
        Client.nickname = nickname;
    }


    @Override
    public void handleMessage(String message) throws RemoteException {
        System.out.println(message);
    }
}
