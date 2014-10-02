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

    public void sendMessage(String input, MessageHandler source) throws RemoteException {
        System.out.println(input);
        if(input.charAt(0)=='/'){   // Input is a command

            if(input.startsWith("/quit")){
                deregisterForNotification(source);
            }
            else if(input.startsWith("/who")){
                source.handleMessage("You used /who command");
            }
            else if(input.startsWith("/nick")){
                String[] parameters = input.split(" ");

                if(parameters.length<2){ // No parameters
                    source.handleMessage("Usage: /nick <nickname>");
                    return;
                }
                String newName = parameters[1];

                changeNickname(newName,source);

                source.handleMessage("You used /nick command");
            }
            else if(input.startsWith("/help")){
                String message = "The following commands are available:" +
                        "\n/quit - disconnect this client"+
                        "\n/who - view all connected users"+
                        "\n/nick <nickname> - change your nick name"+
                        "\n/help - list available commands";


                source.handleMessage(message);
            }
            else{
                source.handleMessage("You used an unknown command");
            }

        }
        else{ // Input is an ordinary message
            sendMessageToAllButSender(input,source);
        }
    }

    private void changeNickname(String newName, MessageHandler client){
        try {
            client.setNickname(newName);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    private void sendMessageToAllButSender(String message, MessageHandler sender){
        for(MessageHandler messageHandler : messageHandlers) {
                try {
                if(!messageHandler.getNickname().equals(sender.getNickname()))
                    messageHandler.handleMessage(sender.getNickname()+": "+message);
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
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
