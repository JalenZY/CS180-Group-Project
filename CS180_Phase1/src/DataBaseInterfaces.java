public class DataBaseInterfaces {

    // insert
    synchronized public boolean insertDataToUsers(String userid, String username, String password)
    {
        // check if data duplicated
        // insert to file if userid,username is unique
        // do not insert if userid or username duplicated
        return false;
    }
    synchronized public boolean insertDataToMessages(String fromuserid, String touserid, String message)
    {
        // insert to file if data not duplicated
        return false;
    }
    synchronized public boolean insertDataToFriend(String userid, String frienduserid)
    {
        // insert to file if data not duplicated
        return false;
    }
    synchronized public boolean insertDataToBlock(String userid, String blockuserid)
    {
        // insert to file if data not duplicated
        return false;
    }

    // delete
    synchronized public boolean deleteUserByID(String userid)
    {
        // delete data from file if data existed
        return false;
    }
    synchronized public boolean deleteMessage(String fromuserid, String touserid, String message)
    {
        // delete data from file if data existed
        return false;
    }
    synchronized public boolean deletefriend(String userid, String frienduserid)
    {
        // delete data from file if data existed
        return false;
    }
    synchronized public boolean deleteblock(String userid, String blockuserid)
    {
        // delete data from file if data existed
        return false;
    }

    // update
    synchronized public boolean updateDataToUsers(String old_userid, String old_username, String old_password,
                                                  String new_userid, String new_username, String new_password)
    {
        // replace old data to new data
        return false;
    }
    synchronized public boolean updateDataToMessages(String old_fromuserid, String old_touserid, String old_message,
                                                     String new_fromuserid, String new_touserid, String new_message)
    {
        // replace old data to new data
        return false;
    }
    synchronized public boolean updateDataToFriend(String old_userid, String old_frienduserid,
                                                   String new_userid, String new_frienduserid)
    {
        // replace old data to new data
        return false;
    }

    // search/select
    synchronized public String getUserNameById(String userid)
    {
        // search file and return searched data
        return null;
    }
    synchronized public String getUserIDByUsername(String username)
    {
        // search file and return searched data
        return null;
    }
    synchronized public String getMessagesByUsername(String username)
    {
        // search file and return searched data
        return null;
    }
    synchronized public String getUserFriendListById(String userid)
    {
        // search file and return searched data
        return null;
    }
    synchronized public String getUserBlockListById(String userid)
    {
        // search file and return searched data
        return null;
    }

}
