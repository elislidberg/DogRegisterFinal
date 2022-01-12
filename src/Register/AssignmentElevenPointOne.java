package Register;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class AssignmentElevenPointOne {
    private InputReader userInput = new InputReader();
    private List<Dog> listOfDogs = new ArrayList<>();
    private List<Owner> ownerList = new ArrayList();
    private Dog newDog;
    private Owner newOwner;

    public AssignmentElevenPointOne(){
        addDogsAndOwners();
        mainLoop();
    }

//----------------------------------------------------------------------
// Add/create new dog tog to listOfDogs
    private void addDogToList(){
        this.newDog = createDog();
        listOfDogs.add(newDog);
    }

    private Dog createDog() {
        String dogName = userInput.string("Name");
        String dogBreed = userInput.string("Breed");
        int dogAge = userInput.intNr("Age");
        int dogWeight = userInput.intNr("Weight");

        Dog newDog = new Dog(dogName, dogBreed, dogAge, dogWeight);
        System.out.println(newDog.getName() + " added to the register");

        return newDog;
    }

//-----------------------------------------------------------------------------
//create new Owner
//@UnderTest(id="U8.1")
    private void addOwner(){
        this.newOwner = createOwner();
        ownerList.add(newOwner);
        System.out.println(newOwner.getName() + " added to register");
    }
        private Owner createOwner(){
            String ownerName = userInput.string("Name");
            Owner owner = new Owner(ownerName);
            return owner;
        }
//-----------------------------------------------------------------------------
// give dog to owner
    private void giveDogToOwner() {

        String dogInput = userInput.string("Enter the name of the dog");
        Dog dog = findDogName(dogInput);
        if (dog == null) {
            System.out.println("Error: no dog with that name");
        } else if (dog.getOwner() != null) {
            System.out.println("Error: the dog already has an owner");
        } else {
            String ownerInput = userInput.string("Enter the name of the owner");
            Owner owner = findOwner(ownerInput);
            if (owner == null) {
                System.out.println("Error: no owner with that name");
            } else {
                if (dog.checkOwner(owner)){
                    System.out.println(owner.getName() + " now owns " + dog);
                } else {
                    System.out.println("Error: the dog already has an owner");
                }
            }
        }
    }

    private Owner findOwner(String ownerName){
        for (Owner owner: ownerList){
            if(owner.getName().equalsIgnoreCase(ownerName)){
                return owner;
            }
        }return null;
    }



//-----------------------------------------------------------------------------
//removeDog
    private void removeDog(){
        String inputFromUser = userInput.string("Enter the name of the dog");
        Dog deletedDog = findDogName(inputFromUser);
        if (deletedDog == null){
            System.out.println("Error: no such dog");
        } else {
            System.out.println(deletedDog.getName() +  " is removed from the register");
            if (deletedDog.getOwner() == null){
                listOfDogs.remove(deletedDog);
            }else{
                Owner owner = deletedDog.getOwner();
                owner.removeDog(deletedDog);
                listOfDogs.remove(deletedDog);
            }
        }
    }

    private Dog findDogName(String dogName){
        for (Dog dog:listOfDogs){
            if (dog.getName().equalsIgnoreCase(dogName)){
                return dog;
            }
        }return null;
    }

//-----------------------------------------------------------------------------
// remove owned dog
    private void removeDogFromOwner(){
        String dogName = userInput.string("Enter the name of the dog");
        Dog dog = findDogName(dogName);
        Owner owner = dog.getOwner();
        if (dog == null) {
            System.out.println("Error: no dog with that name");
        } else if (owner == null) {
            System.out.println("Error: " + dog.getName() + "has no owner");
        } else {
            dog.removeOwnerFromDog();
        }
    }
//-----------------------------------------------------------------------------
// remove owner
    private void removeOwnerFromRegister(){
        String ownerName = userInput.string("Enter the name of the user");
        Owner owner = findOwner(ownerName);
        if (owner != null) {
            removeDogsFromRegister(owner);
            removeOwnerFromRegister(owner);
            System.out.println(owner.getName() + " is removed from register");
        } else {
            System.out.println("Error: no such owner");
        }
    }

    private void removeOwnerFromRegister(Owner owner){
        ownerList.remove(owner);
    }

    private void removeDogsFromRegister(Owner owner){
        for (Dog dog: owner.getDog()){
            listOfDogs.remove(dog);
        }
    }
//-----------------------------------------------------------------------------
//list dogs
    private void takeUserTailInput(){
        if (listOfDogs.size() != 0){
            double tailLength = userInput.doubleNr("Smallest tail length to display");
            listTailLength(tailLength);
        } else{
            System.out.println("Error: No dogs in register!");
        }
    }

    private void listTailLength(double tailLength){
        ArrayList<Dog>qualifyingDogs = new ArrayList<>();
        selectionSort();
        for(Dog dog:listOfDogs){
            if (dog.getTailLength()>tailLength){
                qualifyingDogs.add(dog);
            }
        }

        if (qualifyingDogs.size() == 0){
            System.out.println("No dogs fulfill the reqs!");
        }else {
            for (Dog qualiDog:qualifyingDogs){
                System.out.println(qualiDog);
            }
        }
    }
//----------------------------------------------------------------------------
//list owners
    private void listOwner(){
        if (ownerList.size() != 0) {
            for (Owner owner : ownerList) {
                System.out.print(owner.getName() + " ");
                Dog[] newDogArr = owner.getDog();
                for (Dog dog : newDogArr) {
                    System.out.print(dog + " ");
                }
                System.out.println();
            }
        }else {
            System.out.print("Error: no owners in register");
        }
    }
//----------------------------------------------------------------------------
// Sort list of dogs

    private void swapTwoDogs(int i, int j){
        Collections.swap(listOfDogs, i,j);
    }

    private int compareTailLength(Dog i, Dog j) {
        if (i.getTailLength() > j.getTailLength()) {
            return 1;
        }
        if (i.getTailLength() < j.getTailLength()) {
            return -1;
        }
        return 0;
    }

    private int compareName(Dog i, Dog j) {
        int comp = i.getName().compareToIgnoreCase(j.getName());
        if (comp > 0) {
            return 1;
        }
        return 0;
    }

    private int compareTwoDogs(Dog i, Dog j) {
        int tailResult = compareTailLength(i, j);
        if (tailResult != 0) {
            return tailResult;
        }
        return compareName(i, j);
    }

    private int findMinDog(int startIndex) {
        int minDogIndex = startIndex;
        for (int i = startIndex + 1; i < listOfDogs.size(); i++) {
            int compResult = compareTwoDogs(listOfDogs.get(minDogIndex), listOfDogs.get(i));
            if (compResult == 1) {
                minDogIndex = i;
            }
        }
        return minDogIndex;
    }

    private int selectionSort() {
        int counter = 0;
        for (int i = 0; i < listOfDogs.size(); i++) {
            int swapIndex = findMinDog(i);
            swapTwoDogs(i, swapIndex);
            if (i != swapIndex) {
                counter++;
            }
        }
        return counter;
    }

//----------------------------------------------------------------------------
// increase age of dog
    private void changeAgeInput(){
        String inptutFromUser = userInput.string("Enter the name of the dog");
        Dog dogToChangeAge = findDogName(inptutFromUser);
        if (dogToChangeAge == null){
            System.out.println("Error: no such dog");
        } else{
            dogToChangeAge.updateAge(1);
            System.out.println(dogToChangeAge.getName() + " is now one year older");
        }
    }
//--------------------------------------------------------------------------------
// menu and other stuff
    private void mainLoop(){
        String menuInput;
        printMenu();
        do {
            menuInput = switchLoop();
        }while (!menuInput.equalsIgnoreCase("exit"));
    }

    private void printMenu(){
        System.out.println("* register new dog");
        System.out.println("* list dogs");
        System.out.println("* increase age");
        System.out.println("* remove dog");
        System.out.println("* register new owner");
        System.out.println("* give dog");
        System.out.println("* list owners");
        System.out.println("* remove owned dog");
        System.out.println("* remove owner");
        System.out.println("* exit");
    }

    private String switchLoop() {
        String menuInput = userInput.string("Command");
        switch (menuInput) {
            case "register new dog":
                addDogToList();
                break;
            case "list dogs":
                takeUserTailInput();
                break;
            case "increase age":
                changeAgeInput();
                break;
            case "remove dog":
                removeDog();
                break;
            case"register new owner":
                addOwner();
                break;
            case "give dog":
                giveDogToOwner();
                break;
            case "list owners":
                listOwner();
                break;
            case "remove owned dog":
                removeDogFromOwner();
                break;
            case "remove owner":
                removeOwnerFromRegister();
                break;
            case "exit":
                return menuInput;
            default:
                System.out.println("Error: Wrong command please try again");
                printMenu();
        }
        return menuInput;
    }
//----------------------------------------------------------------------------------
// tests

    private void addDogsAndOwners(){
        Dog dog1 = new Dog("Elis", "Tax", 6, 7);
        Dog dog2 = new Dog("Gustav", "Gold", 2, 34);
        Dog dog3 = new Dog("Clara", "White", 5, 9);
        Dog dog4 = new Dog("August", "Blue", 3, 5);
        Dog dog5 = new Dog("Klara", "Yellow", 1, 5);

        listOfDogs.add(dog1);
        listOfDogs.add(dog2);
        listOfDogs.add(dog3);
        listOfDogs.add(dog4);
        listOfDogs.add(dog5);

        Owner owner1 = new Owner("H");
        Owner owner2 = new Owner("J");
        Owner owner3 = new Owner("K");
        Owner owner4 = new Owner("L");
        Owner owner5 = new Owner("E");

        ownerList.add(owner1);
        ownerList.add(owner2);
        ownerList.add(owner3);
        ownerList.add(owner4);
        ownerList.add(owner5);
    }

}
