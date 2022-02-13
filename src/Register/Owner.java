package Register;
//Elis Lidberg elli6378
import java.util.Arrays;

public class Owner {
    private String name;
    private Dog[] listOfOwnerDogs = new Dog[0];

    public Owner(String name){
        this.name = name;
    }

    public String getName(){
        return name;
    }

    public String toString(){
        return name + " ";
    }

    //@UnderTest(id="U8.3")
    public boolean checkIfOwnerHasDog(Dog dog){
        if (dog.getOwner() == null && checkIfDogInOwnerArray(dog)){
            dog.setOwner(this);
            addDogToOwner(dog);
            return true;
        }
        return false;
    }


    public Dog[] getDog(){
        Dog[] copyOfOwnerDogs = listOfOwnerDogs.clone();
        return copyOfOwnerDogs;
    }

    public void addDogToOwner(Dog dog){
        //this.dog = dog;
        Dog[] newDogArr = Arrays.copyOf(listOfOwnerDogs, listOfOwnerDogs.length +1);
        listOfOwnerDogs = newDogArr;
        listOfOwnerDogs[listOfOwnerDogs.length-1] = dog;
    }

    public boolean checkIfDogInOwnerArray(Dog dog){
        for (int i = 0; i < listOfOwnerDogs.length; i++){
            if (dog == listOfOwnerDogs[i]){
                return false;
            }
        } return true;
    }

    public void deleteDogFromOwner(Dog dog){
        int index = findIndexOfDogInList(dog);
        if (index >= 0){
            Dog[] copyArray = new Dog[listOfOwnerDogs.length - 1];
            System.arraycopy(listOfOwnerDogs, 0, copyArray, 0, index);
            System.arraycopy(listOfOwnerDogs, index + 1, copyArray, index, listOfOwnerDogs.length - index - 1);
            listOfOwnerDogs = copyArray;
        }
    }

    private int findIndexOfDogInList(Dog dog) {
        for (int i = 0; i < listOfOwnerDogs.length; i++) {
            if (dog == listOfOwnerDogs[i]) {
                return i;
            }
        } return -1;
    }
    //UnderTest(id="U8.5")
    public boolean ownDog(Dog dog){
        if (!checkIfDogInOwnerArray(dog)){
            return true;
        } return false;
    }
}