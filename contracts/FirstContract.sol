pragma solidity ^0.4.0;

interface Regulator {
    function checkValue(int amount) returns (bool);
    function loan() returns (bool);
}

contract Bank is Regulator {

    int private value;

    function deposit(int amount) public {
        value += amount;
    }

    function withdraw(int amount) public {
        if(checkValue(amount)){
            value -= amount;
        }
    }

    function balance() public view returns (int) {
        return value;
    }

    function Bank(int initialBalance) public {
        value = initialBalance;
    }

    function loan() returns (bool) {
        return value > 0;
    }

    function checkValue(int amount) returns (bool) {
        return amount <= value;
    }

}

contract FirstContract is Bank(10) {

    string private name;
    uint8 private age;

    function FirstContract(string initialName, uint8 initialAge){
        name = initialName;
        age = initialAge;
    }

    function setName(string newName){
        name = newName;
    }

    function getName() public constant returns (string){
        return name;
    }

    function setAge(uint8 newAge){
        age = newAge;
    }

    function getAge() public constant returns (uint8){
        return age;
    }

}