package Register;
//Elis Lidberg elli6378
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class DogRegisterMethods {
    private InputReader userInput = new InputReader();
    private List<Dog> listOfDogs = new ArrayList<>(); // skriv om namn på liknande sätt
    private List<Owner> ownerList = new ArrayList();

    public DogRegisterMethods(){
        addDogsAndOwners();
    }

//----------------------------------------------------------------------
// Add/create new dog tog to listOfDogs
    private void addDogToList(){
        Dog newDog = createDog();
        listOfDogs.add(newDog);
    }

    private Dog createDog() {
        String dogName = userInput.inputString("Name");
        String dogBreed = userInput.inputString("Breed");
        int dogAge = userInput.inputIntNr("Age");
        int dogWeight = userInput.inputIntNr("Weight");

        Dog newDog = new Dog(dogName, dogBreed, dogAge, dogWeight);
        System.out.println(newDog.getName() + " added to the register");

        return newDog;
    }

//-----------------------------------------------------------------------------
//create new Owner
//@UnderTest(id="U8.1")
    private void addOwnerToList(){
        Owner newOwner = createOwner();
        ownerList.add(newOwner);
        System.out.println(newOwner.getName() + " added to register");
    }

    private Owner createOwner(){
        String ownerName = userInput.inputString("Name");
        Owner owner = new Owner(ownerName);
        return owner;
    }
//-----------------------------------------------------------------------------
// give dog to owner
    private void giveDogToOwner() {

        String dogInput = userInput.inputString("Enter the name of the dog");
        Dog dog = findDogName(dogInput);
        if (dog == null) {
            System.out.println("Error: no dog with that name");
        } else if (dog.getOwner() != null) {
            System.out.println("Error: the dog already has an owner");
        } else {
            String ownerInput = userInput.inputString("Enter the name of the owner");
            Owner owner = findOwner(ownerInput);
            if (owner == null) {
                System.out.println("Error: no owner with that name");
            } else {
                if (dog.checkIfDogHasOwner(owner)){
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
    private void deleteDog(){
        String inputFromUser = userInput.inputString("Enter the name of the dog");
        Dog deletedDog = findDogName(inputFromUser);
        if (deletedDog == null){
            System.out.println("Error: no such dog");
        } else {
            System.out.println(deletedDog.getName() +  " is removed from the register");
            if (deletedDog.getOwner() == null){
                listOfDogs.remove(deletedDog);
            }else{
                Owner owner = deletedDog.getOwner();
                owner.deleteDogFromOwner(deletedDog);
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
        String dogName = userInput.inputString("Enter the name of the dog");
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
        String ownerName = userInput.inputString("Enter the name of the user");
        Owner owner = findOwner(ownerName);
        if (owner != null) {
            removeDogsFromRegister(owner);
            removeOwner(owner);
            System.out.println(owner.getName() + " is removed from register");
        } else {
            System.out.println("Error: no such owner");
        }
    }

    private void removeOwner(Owner owner){
        ownerList.remove(owner);
    }

    private void removeDogsFromRegister(Owner owner){
        List<Dog> dogsToRemove = new ArrayList<>();
        for (Dog dog: listOfDogs){
            if (owner.checkIfDogInOwnerArray(dog) == false){
                dogsToRemove.add(dog);
            }
        }

        for (Dog dog:dogsToRemove){
            listOfDogs.remove(dog);
        }

        //for (Dog dog: owner.getDog()){
        //    listOfDogs.remove(dog);
        //}
    }
//-----------------------------------------------------------------------------
//list dogs
    private void takeUserTailInput(){
        if (listOfDogs.size() != 0){
            double tailLength = userInput.inputDoubleNr("Smallest tail length to display");
            listTailLength(tailLength);
        } else{
            System.out.println("Error: No dogs in register!");
        }
    }

    private void listTailLength(double tailLength){
        ArrayList<Dog>qualifyingDogs = new ArrayList<>();
        sortDogsInList();
        for(Dog dog:listOfDogs){
            if (dog.getTailLength()>=tailLength){
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
                for (Dog dog:listOfDogs){
                    if (!owner.checkIfDogInOwnerArray(dog)){
                        System.out.print(dog + " ");
                    }
                }
                //------------
                System.out.println();
            }
        }else {
            System.out.println("Error: no owners in register");
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
    } // compareNameOfDogs

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

    private int sortDogsInList() {
        int counter = 0;
        for (int i = 0; i < listOfDogs.size(); i++) {
            int swapIndex = findMinDog(i);
            if (i != swapIndex) {
                swapTwoDogs(i, swapIndex);
                counter++;
            }
        }
        return counter;
    }

//----------------------------------------------------------------------------
// increase age of dog
    private void increaseDogAge(){
        String inptutFromUser = userInput.inputString("Enter the name of the dog");
        Dog dogToChangeAge = findDogName(inptutFromUser);
        if (dogToChangeAge == null){
            System.out.println("Error: no such dog");
        } else{
            dogToChangeAge.updateAge(1);
            System.out.println(dogToChangeAge.getName() + " is now one year older");
        }
    } 
//--------------------------------------------------------------------------------
// main menu
    public void executeMainLoop(){
        String menuInput;
        printMenu();
        do {
            menuInput = handleMainLoopInput();
        }while (!menuInput.equalsIgnoreCase("exit"));
        System.out.println("Goodbye!");
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

    private String handleMainLoopInput() {
        String menuInput = userInput.inputString("Command");
        switch (menuInput) {
            case "register new dog":
                addDogToList();
                break;
            case "list dogs":
                takeUserTailInput();
                break;
            case "increase age":
                increaseDogAge();
                break;
            case "remove dog":
                deleteDog();
                break;
            case"register new owner":
                addOwnerToList();
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
        Dog dog1 = new Dog("Lady", "Grand danois", 6, 6);
        Dog dog2 = new Dog("Gromit", "Dachshund", 11, 9);
        Dog dog3 = new Dog("Rowlf", "Tax", 11, 9);
        Dog dog4 = new Dog("Sparky", "Beagle", 12, 11);
        Dog dog5 = new Dog("Rin Tin Tin", "Beagle", 7, 19);
        Dog dog6 = new Dog("Shiloh", "Golden retriever", 18, 11);
        Dog dog7 = new Dog("Skipper", "Chihuahua", 14, 15);

        listOfDogs.add(dog1);
        listOfDogs.add(dog2);
        listOfDogs.add(dog3);
        listOfDogs.add(dog4);
        listOfDogs.add(dog5);
        listOfDogs.add(dog6);
        listOfDogs.add(dog7);

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
