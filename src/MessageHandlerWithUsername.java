/**
 * Created by Naknut on 02/10/14.
 */
public class MessageHandlerWithUsername {
    MessageHandler handler;
    String username;

    public MessageHandlerWithUsername(MessageHandler handler, String username) {
        this.handler = handler;
        this.username = username;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public MessageHandler getMessageHandler() {
        return handler;
    }
}
