import java.util.Scanner;

import Connections.Friendship;
import Connections.Interest;
import NetworkComponents.Network;
import NetworkComponents.NetworkSet;
import NetworkComponents.Person;
import NetworkComponents.Subject;

public class App {
    public static void main(String[] args) throws Exception {
        Scanner scanner = new Scanner(System.in);
        int numberOfNetworks = scanner.nextInt();
        for (int i = 0; i < numberOfNetworks; i++) {
            Network network = new Network();
            NetworkSet.getInstance().addNetwork(network);
            getNetworkData(network, scanner);
        }
        scanner.close();

        checkData();
    }

    private static void getNetworkData(Network network, Scanner scanner){
            int numberOfSubjects = scanner.nextInt();
            for (int j = 0; j < numberOfSubjects; j++) {
                Subject  subject = new Subject(scanner.next());
                NetworkSet.getInstance().addSubject(subject);
                network.addSubject(subject);
            }

            int numberOfPeople = scanner.nextInt();
            for (int j = 0; j < numberOfPeople; j++) {
                Person person = new Person(scanner.next());
                NetworkSet.getInstance().addPerson(person);
                network.addPerson(person);
                
                int numberOfInterests = scanner.nextInt();

                for (int k = 0; k < numberOfInterests; k++) {
                    String interestData = scanner.next();
                    int indexOfSubject = Integer.valueOf(interestData.substring(0, interestData.indexOf(":")));
                    double degreeOfInterest = Double.valueOf(interestData.substring(interestData.indexOf(":") + 1));
                    person.addInterest(network.getSubjectWithIndex(indexOfSubject), degreeOfInterest, network);
                }
            }

            int numberOfFriends = scanner.nextInt();
            for (int k = 0; k < numberOfFriends; k++) {
                Person person = network.getPersonWithIndex(scanner.nextInt());
                int numberOfFriendships = scanner.nextInt();
                for (int j = 0; j < numberOfFriendships;j++) {
                    String friendshipData = scanner.next();
                    int indexOfPerson = Integer.valueOf(friendshipData.substring(0, friendshipData.indexOf(":")));
                    double degreeOfFriendship = Double.valueOf(friendshipData.substring(friendshipData.indexOf(":") + 1));
                    person.addFriend(network.getPersonWithIndex(indexOfPerson), degreeOfFriendship, network);
                }
            }

    }

    private static void checkData(){
        NetworkSet.getInstance().getPeople().iterateOnMap(node -> {
            Person person  = node.getElement().getFirst();
            System.err.println(person.getName() + ":");
            person.getFriendships().iterateOnList(node1 -> {
                Friendship friendship = node1.getElement();
                System.err.print(friendship.getPerson().getName() + "(" + friendship.getDegree() + ") ");
            });
            System.err.println();
            person.getInterests().iterateOnList(node1 -> {
                Interest interest = node1.getElement();
                System.err.print(interest.getSubject().getName() + "(" + interest.getDegree() + ") "); 
            });
            System.err.println("\n//");

        });     
    }

}
