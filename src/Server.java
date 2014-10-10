import java.net.ConnectException;
import java.net.MalformedURLException;
import java.rmi.ConnectIOException;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;

/**
 * Created by Naknut on 02/10/14.
 */
public class Server extends UnicastRemoteObject implements Chat {

    ArrayList<MessageHandlerWithUsername> mhwuList = new ArrayList<MessageHandlerWithUsername>();
    String welcomeMessage = "Welcome to our cool chat";

    public static void main(String[] args) throws MalformedURLException, RemoteException {
        Server server = new Server();
        Naming.rebind("chat", server);
    }

    protected Server() throws RemoteException {
        super();
        System.out.println("Server started");
    }

    @Override
    public String getUsernames() throws RemoteException {
        synchronized (mhwuList) {
            String message = "";
            for (MessageHandlerWithUsername mhwu : mhwuList) {
                message += mhwu.getUsername() + " ";
            }
            return message;
        }
    }

    @Override
    public String getHelp() throws RemoteException {
        return "The following commands are available:" +
               "\n/quit - disconnect this client"+
               "\n/who - view all connected users"+
               "\n/nick <nickname> - change your nick name"+
               "\n/help - list available commands";
    }

    @Override
    public boolean changeNickname(String newName, String oldName) {
        synchronized (mhwuList) {
            for (MessageHandlerWithUsername mhwu : mhwuList) {
                if (mhwu.getUsername().equals(newName))
                    return false;
            }
            for (MessageHandlerWithUsername mhwu : mhwuList) {
                if (mhwu.getUsername().equals(oldName))
                    mhwu.setUsername(newName);
            }
            sendMessageToAllButSender(oldName + " changed name to " + newName, newName);
        }
        return true;
    }

    @Override
    public void sendMessageToAllButSender(String message, String sender) {
        synchronized (mhwuList) {
            for (MessageHandlerWithUsername mhwu : mhwuList) {
                try {
                    if (!mhwu.getUsername().equals(sender))
                        mhwu.getMessageHandler().handleMessage(sender + ": " + message);
                } catch (RemoteException e) {
                    mhwuList.remove(mhwu);
                }
                catch (Exception e){
                    System.out.println("Exception throwned in snedMessageToAll");
                }
            }
        }
    }

    @Override
    public String registerForNotification(MessageHandler messageHandler, String nickname) {
        synchronized (mhwuList) {
            MessageHandlerWithUsername mhwu = new MessageHandlerWithUsername(messageHandler, nickname);
            mhwuList.add(mhwu);
        }
        return welcomeMessage;
    }

    @Override
    public void deregisterForNotification(String username) {
        synchronized (mhwuList) {
            for (MessageHandlerWithUsername mhwu : mhwuList) {
                if (mhwu.username.equals(username))
                    mhwuList.remove(mhwu);
            }
            sendMessageToAllButSender(username + " has disconnected.", username);
        }
    }
}
