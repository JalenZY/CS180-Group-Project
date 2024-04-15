    // Setters
    void setMessageID(String messageID);
    void setSenderID(String senderID);
    void setRecipientID(String recipientID);
    void setDate(String date);
    void setContent(String content);
    void setRead(boolean read);

    // Getters
    String getMessageID();
    String getConversationID();
    String getSenderID();
    String getRecipientID();
    String getDate();
    boolean isRead();
    String getContent();

    // Method to un-encrypt the given encrypted message string
    static String getConvertedContent(String string);

    // Method to format content for writing to messaging.txt file
    String toString();
