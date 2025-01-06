package data.network;

import java.io.Serializable;

public class Cancel implements Serializable {
    private MessageHeader messageHeader;
    private String playerName;


    public Cancel(MessageHeader messageHeader, String playerName) {
        this.messageHeader = messageHeader;
        this.playerName = playerName;

    }

    public MessageHeader getMessageHeader() {
        return messageHeader;
    }

    public void setMessageHeader(MessageHeader messageHeader) {
        this.messageHeader = messageHeader;
    }

    public String getPlayerName() {
        return playerName;
    }

    public void setPlayerName(String playerName) {
        this.playerName = playerName;
    }


}
