pragma solidity ^0.4.0;

contract DataTypes {

    bool myBool = false;

    // ================
    // Can be of sizes incrementing in 8th
    int myInt = -128;
    uint8 myUint = 255;

    // ================
    // Range similar to int -> 1 to 32
    byte myByte;
    bytes30 myByte30;

    // ================
    // Strings are actually arrays on uint8
    string myString;
    uint8[] myStringArr;

    // Cannot pass array or strings as strings are already arrays
    function myStringFn(string s) public {
        myString = s;
    }

    // ================
    // Fixed -> Use for financial systems
    // fixed256x8 myFixed = 128.0;  // ----> Not implemented yet
    // ufixed256x8 myFixed = 128.0;  // ----> Not implemented yet

    // ================
    // Enum
    enum Action {ADD, REMOVE, UPDATE}

    Action myAction = Action.ADD;

    // ================
    // Address

    address myAddress;
    function assignAddress() private {
        myAddress = msg.sender;  // Get address of sender of transaction
        myAddress.balance; // Get balance of address
        myAddress.transfer(10); // Transfer to address
    }

    // ================
    // arrays

    uint[] myIntAr = [1, 2, 3];

    function arrayFunc() public {
        myIntAr.push(1); // Push the value one onto the array
        myIntAr.length; // Get length of array
        myIntAr[0]; // Reference first element
    }

    // ================
    // Structs
    // Similar to an object with no functions

    struct Account {
        uint balance;
        uint dailyLimit;
    }

    // Defining an account
    Account myAccount;

    // Accessing/using the account struct
    function structFunc() public {
        myAccount.balance = 100;
        myAccount.dailyLimit = 30;
    }

    // ================
    // Mapping -> Similar to a hash map

    mapping(address => Account) _accounts;

    function mappingFunc() public payable {
        _accounts[msg.sender].balance += msg.value;
    }

}