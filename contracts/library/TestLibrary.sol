pragma solidity ^0.4.0;

import "./MyLib.sol";

contract TestLibrary is MyLib {

    // Extend functionality of uint
    using IntExtended for uint;

    // Method on uint that can increment by 1
    function testIncrement(uint _base) public pure returns (uint) {
        return _base.increment();
    }

    // Method on uint that can decrement by 1
    function testDecrement(uint _base) public pure returns (uint) {
        return _base.decrement();
    }

    // Method on uint that can increment by some value
    function testIncrementByValue(uint _base, uint _value) public pure returns (uint) {
        return _base.incrementByValue(_value);
    }

    // Method on uint that can decrement by some value
    function testDecrementByValue(uint _base, uint _value) public pure returns (uint) {
        return _base.decrementByValue(_value);
    }
}
