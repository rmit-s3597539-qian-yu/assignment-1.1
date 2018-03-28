import java.util.ArrayList;
import java.util.List;


class Profile {

    String name;       
    int age;            
    String sex;       

    String image = null;
    String status = null;

    Profile(String name ,int age, String sex)
    {
        this.name = name;
        this.sex = sex;
        this.age = age;
    }

    @Override
    public String toString() {
        String result =  "name:"+ name + "\tsex:" +  sex + "\tage:" + age ;
        result += "\nimage:";
        if (image != null)
            result += image;
        result += "\n";
        result += "status:";
        if (status != null)
            result += status;
        return result;
    }
}

class RelationShip {

    List<IPeople> friends = new ArrayList<>();
    List<IPeople> children = new ArrayList<>();

    IPeople father = null;
    IPeople mother = null;

    IPeople mate = null;

    RelationShip()
    {
    }

    RelationShip(IPeople father,IPeople mother)
    {
        this.father = father;
        this.mother = mother;
    }

    @Override
    public String toString() {
        String result = "";
        if (father != null) {
            result += "father:";
            result += father.myNameIs();
            result += "\tmother:";
            result += mother.myNameIs();
            result += "\n";
        }

        result += "friends:";
        for (IPeople people:friends) {
            result += people.myNameIs();
            result += " ";
        }
        result += "\n";
        if (mate != null)
            result += mate.myNameIs();
        return result;
    }
}

 class People implements IPeople{

    private Profile profile = null;
    private RelationShip relationShip = null;

    People(String name,int age,String sex)
    {
        profile = new Profile(name,age,sex);
        relationShip = new RelationShip();
    }

    People(String name,int age,String sex,IPeople father,IPeople mother)
    {
        profile = new Profile(name,age,sex);
        relationShip = new RelationShip(father,mother);
    }

    @Override
    public List<IPeople> myFriends() {
        return relationShip.friends;
    }

    @Override
    public void introduceNewFriend(IPeople people) {
        for (IPeople p : relationShip.friends)
            if (p.myNameIs().equals(people.myNameIs()))
                return;
        relationShip.friends.add(people);
    }

    @Override
    public boolean isHeMyFriend(IPeople people) {
        for (IPeople p : myFriends())
            if (p.myNameIs().equals(people.myNameIs()))
                return true;
        return false;
    }

    @Override
    public IPeople whoIsMyFather() {
        return relationShip.father;
    }

    @Override
    public String myNameIs() {
        return profile.name;
    }

    @Override
    public int myAgeIs() {
        return profile.age;
    }

    @Override
    public String mySex() {
        return profile.sex;
    }

    @Override
    public String toString() {
        return profile.toString() + "\n" + relationShip.toString();
    }

    @Override
    public String getSimpleDescription() {
        return profile.toString();
    }

    @Override
    public void setImage(String path) {
        profile.image = path;
    }

    @Override
    public void setStatus(String status) {
        profile.status = status;
    }

    @Override
    public List<IPeople> getChildren() {
        return relationShip.children;
    }

    @Override
    public void addChild(IPeople people) {
        relationShip.children.add(people);
    }

    @Override
    public void setMate(IPeople people) {
        relationShip.mate = people;
    }
}
