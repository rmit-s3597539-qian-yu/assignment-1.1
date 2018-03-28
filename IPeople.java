import java.util.List;

public interface IPeople {
    List<IPeople> myFriends();
    void introduceNewFriend(IPeople people);
    boolean isHeMyFriend(IPeople people);
    IPeople whoIsMyFather();
    List<IPeople> getChildren();
    void addChild(IPeople people);
    String myNameIs();
    int myAgeIs();
    String mySex();
    String getSimpleDescription();
    void setImage(String path);
    void setStatus(String status);
    void setMate(IPeople people);
}
