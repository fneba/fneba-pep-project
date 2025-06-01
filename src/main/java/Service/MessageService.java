package Service;


import DAO.MessageDAO;
import Model.Message;
import java.util.List;

public class MessageService {
    MessageDAO messageDAO;

    public MessageService(){
        messageDAO = new MessageDAO();
    }

    public MessageService(MessageDAO messageDAO){
        this.messageDAO = messageDAO;
    }

    public Message createNewMessage(Message message){
        return messageDAO.createMessage(message);
    }

    public void updateForMessage(int messageID, String updatedMessage){
        messageDAO.updateMessage(messageID, updatedMessage);
    }

    public List<Message> getMessages(){
        return messageDAO.getAllMessages();
    }

    public Message getMessageByMessageId(int messageID){
        return messageDAO.getMessageByID(messageID);
    }

    public List<Message> getUsersMessages(int postedBYID){
        return messageDAO.getAllMessagesByUser(postedBYID);
    }

    public void deleteMessage(int messageID){
        messageDAO.deleteMessageByID(messageID);
    }
}
