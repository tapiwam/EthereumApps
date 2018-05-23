pragma solidity ^0.4.0;

interface Regulator {
    function checkValue(int amount) external returns (bool);
    function loan() external returns (bool);
}

contract Bank is Regulator {

    int private value;
    address private owner;

    // Owner based functionality only - modfier will interject
    modifier ownerFunc {
        require(owner == msg.sender);
        _;
    }

    function deposit(int amount) ownerFunc public {
        owner = msg.sender;
        value += amount;
    }

    function withdraw(int amount) ownerFunc public {
        if(checkValue(amount)){
            value -= amount;
        }
    }

    function balance() public view returns (int) {
        return value;
    }

    constructor(int initialBalance) public {
        value = initialBalance;
    }

    function loan() public returns (bool) {
        return value > 0;
    }

    function checkValue(int amount) public returns (bool) {
        return amount <= value;
    }

}

contract FirstContract is Bank(10) {

    string private name;
    uint8 private age;

    constructor(string initialName, uint8 initialAge) public {
        name = initialName;
        age = initialAge;
    }

    function setName(string newName) public {
        name = newName;
    }

    function getName() public constant returns (string){
        return name;
    }

    function setAge(uint8 newAge) public {
        age = newAge;
    }

    function getAge() public constant returns (uint8){
        return age;
    }

}