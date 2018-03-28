import java.util.*;
import java.util.List;

public class MiniNet {

    private static Set<IPeople> peoples = new HashSet<>();

    public static void main(String[] args)
    {
        initialize();
        int choice = -1;
        do {
            String menu = "MiniNet Menu\n======================================================\n"
                    + "1. Add a person.\n"
                    + "2. List everyone.\n"
                    + "3. Find a person ? \n"
                    + "4. Update someone's information.\n"
                    + "5. Delete someone.\n"
                    + "6. Connect two people.\n"
                    + "7. Are these two direct friends?\n"
                    + "8. Who are his/her parents?\n"
                    + "9. Find all his children.\n"
                    + "0. Exit";
            System.out.println(menu);
            System.out.println("Enter an option: ");
            choice = new Scanner(System.in).nextInt();
            switch (choice)
            {
                case 1: addPeople(); break;
                case 2: listAllPleple(); break;
                case 3: selectSomeone(); break;
                case 4: updateInformation(); break;
                case 5: deleteSomeone();break;
                case 6:connectTwo();break;
                case 7:isFriends();break;
                case 8:listFriends();break;
                case 9:getChildren();break;
            }
        }while (choice != 0);
    }

    private static void initialize()
    {
        IPeople f1 = new People("Mary",42,"female");
        IPeople m1 = new People("Kitty",41,"male");
        IPeople c1 = new People("Nike",14,"male",f1,m1);
        IPeople c2 = new People("Tom",15,"male",f1,m1);

        f1.addChild(c1);
        f1.addChild(c2);
        m1.addChild(c1);
        m1.addChild(c2);

        f1.setMate(m1);
        m1.setMate(f1);

        IPeople f2 = new People("Aaron",45,"male");
        IPeople m2 = new People("Cindy",41,"male");
        IPeople c3 = new People("Adam",13,"female",f2,m2);
        IPeople c4 = new People("Alan",1,"male",f2,m2);

        f2.setMate(m2);
        m2.setMate(m1);

        f2.addChild(c3);
        f2.addChild(c4);
        m2.addChild(c3);
        m2.addChild(c4);

        peoples.add(f1);
        peoples.add(m1);
        peoples.add(c1);
        peoples.add(c2);
        peoples.add(f2);
        peoples.add(m2);
        peoples.add(c3);
        peoples.add(c4);

        c1.introduceNewFriend(c3);
        c3.introduceNewFriend(c1);
    }

    private static void addPeople()
    {
        Scanner scanner = new Scanner(System.in);
        String name,sex;
        int age;
        IPeople people;
        System.out.println("To add a new person, please fill in some basic information:");
        System.out.print("name:");
        name = scanner.nextLine();
        System.out.print("sex:");
        sex = scanner.nextLine();
        System.out.print("age:");
        age = scanner.nextInt();
        if (age > 16)
            people = new People(name,age,sex);
        else
        {
            String name1,name2;
            IPeople father = null,mother = null;
            System.out.println("You are a kid! Please tell me who are your parents?");
            System.out.print("your father's name: ");         
            scanner.nextLine();
            name1 = scanner.nextLine();
            for (IPeople people1 : peoples)
                if (people1.myNameIs().equals(name1) && people1.mySex().equals("male")) {
                    System.out.println("find the father");
                    father = people1;
                    break;
                }
            System.out.print("your mother's name: ");
            name2 = scanner.nextLine();
            for (IPeople people1 : peoples) {
                if (people1.myNameIs().equals(name2) && people1.mySex().equals("female")) {
                    System.out.println("find the mather");
                    mother = people1;
                    break;
                }
            }

            if (father == null || mother == null) {
                System.out.println("sorry，your input has some problem!");
                return;
            }

            father.setMate(mother);
            mother.setMate(father);

            people = new People(name,age,sex,father,mother);
            father.addChild(people);
            mother.addChild(people);
        }
        peoples.add(people);
    }

    private static void listAllPleple()
    {
        for (IPeople people:peoples)
            System.out.println(people.getSimpleDescription());
        System.out.println();
    }

    private static void selectSomeone()
    {
        System.out.print("Please enter the name you want to find:");
        String name = new Scanner(System.in).nextLine();
        IPeople people = selectPeopleByName(name);
        if (people != null)
            System.out.println(people);
        else
            System.out.println(name + "Not found! please try again.");
    }

    private static void updateInformation()
    {
        System.out.print("Please enter the people's name you want to update:");
        String name = new Scanner(System.in).nextLine();
        IPeople people = selectPeopleByName(name);
        if (people == null)
            System.out.println("No information about this person!");
        else
        {
            int choice = -1;
            do {
                System.out.println("=======================================================");
                System.out.println(people.getSimpleDescription());
                System.out.println("1. Update image,display more to others.");
                System.out.println("2. Update status.");
                System.out.println("3. Go Back");
                System.out.print("Enter your choice:");
                choice = new Scanner(System.in).nextInt();
                if (choice <= 0 || choice >= 4)
                {
                    System.out.println("There are some problems in your input, please try again!");
                    continue;
                }
                if (choice == 1)
                {
                    System.out.print("Please enter the image's path:");
                    String path = new Scanner(System.in).nextLine();
                    people.setImage(path);
                }
                else if (choice == 2)
                {
                    System.out.print("Please enter the new status:");
                    String status = new Scanner(System.in).nextLine();
                    people.setStatus(status);
                }
            }while (choice != 3);
        }
    }

    private static void deleteSomeone()
    {
        System.out.print("Please enter the people's name you want to delete:");
        String name = new Scanner(System.in).nextLine();
        IPeople people = selectPeopleByName(name);
        if (people == null)
        {
            System.out.println("No information about this person!");
            return;
        }
        if (people.getChildren().size()!=0)
        {
            System.out.println("He has children, you can't just delete him!");
            return;
        }
        System.out.println("Do you really want to remove him?");
        System.out.println("1. YES     2.NO");
        int choice = new Scanner(System.in).nextInt();
        if (choice!=1&&choice!=2)
        {
            System.out.println("Input error! please try again!");
            return;
        }
        if (choice == 1)
            peoples.remove(people);
    }

    private static void connectTwo()
    {
        System.out.println("New friends! enter their names:");
        String name1 = new Scanner(System.in).nextLine();
        String name2 = new Scanner(System.in).nextLine();
        IPeople people1 = selectPeopleByName(name1);
        IPeople people2 = selectPeopleByName(name2);
        if (people1 == null)
        {
            System.out.println("No information about "+ name1);
            return;
        }
        if (people2 == null)
        {
            System.out.println("No information abuot "+name2);
            return;
        }
        if (people1.whoIsMyFather().myNameIs().equals(people2.whoIsMyFather().myNameIs()))
        {
            System.out.println("They are from the same family, can't be friend!");
            return;
        }
        if (people1.myAgeIs() <= 16 || people2.myAgeIs() <= 16)
        {
            if (people1.myAgeIs() > 16 || people2.myAgeIs() > 16 ||
                    (people1.myAgeIs() - people2.myAgeIs() > 2) ||
                    people1.myAgeIs() <= 2 || people2.myAgeIs() <= 2)
            {
                System.out.println("They can't become friends.");
                return;
            }
        }
        people1.introduceNewFriend(people2);
        people2.introduceNewFriend(people1);
    }

    private static void isFriends()
    {
        System.out.println("Please enter their names:");
        String name1 = new Scanner(System.in).nextLine();
        String name2 = new Scanner(System.in).nextLine();
        IPeople people1 = selectPeopleByName(name1);
        IPeople people2 = selectPeopleByName(name2);
        if (people1 == null || people2 == null)
            return;
        if (people1.isHeMyFriend(people2))
            System.out.println("Yes! They are friends");
        else
            System.out.println("They are not friends");
    }

    private static void getChildren()
    {
        System.out.println("Please enter the father's name:");
        String name = new Scanner(System.in).nextLine();
        IPeople people = selectPeopleByName(name);
        if (people == null)
        {
            System.out.println("The people you enter doesn't exist!");
            return;
        }
        List<IPeople> children = people.getChildren();

        if (children == null || children.size() == 0)
        {
            System.out.println("He doesn't have any child.");
        }
        else
        {
            for (IPeople people1: children)
                System.out.print(people1.myNameIs()+" ");
            System.out.println();
        }
    }

    private static void listFriends()
    {
        System.out.println("Please enter name:");
        String name = new Scanner(System.in).nextLine();
        IPeople people = selectPeopleByName(name);
        if (people == null)
        {
            System.out.println("The people you enter doesn't exist!");
            return;
        }
        List<IPeople> friends = people.myFriends();
        if (friends == null || friends.size() == 0)
        {
            System.out.println("He doesn't have any friends.\n");
            return;
        }
        System.out.println("friends of "+ name + ":");
        for (IPeople friend: friends)
            System.out.print(friend.myNameIs() + "\t");
        System.out.println();
    }

    private static IPeople selectPeopleByName(String name)
    {
        for (IPeople people : peoples)
            if (people.myNameIs().equals(name))
                return people;
        return null;
    }
}
