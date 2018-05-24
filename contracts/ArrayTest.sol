pragma solidity ^0.4.15;

contract ArrayTest {
    uint[] public myArray;

    function ArrayTest() public {
        myArray.push(1);
        myArray.push(10);
        myArray.push(30);
    }

    function getArrayLength() public view returns(uint) {
        return myArray.length;
    }

    function getFirstElement() public view returns(uint) {
        return myArray[0];
    }

    function getMyArray() public view returns(uint[]) {
        return myArray;
    }
}