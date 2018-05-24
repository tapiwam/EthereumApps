pragma solidity ^0.4.0;

contract Transaction {

    // Event to log sender
    event SenderLogger(address);

    // Event to log value
    event ValueLogger(uint);

    address private owner;

    // Modifier to make sure only owner can interact
    modifier isOwner {
        require(msg.sender == owner);
        _;
    }

    // Modifier to make sure minimum of 100 wei is sent
    modifier validValue {
        assert(msg.value >= 100 wei);
        _;
    }

    // Capture owner when initializing contract
    constructor() public {
        owner = msg.sender;
    }

    // fallback function -> Can interact without decalring funcion
    function() payable public isOwner validValue {

        // Emit the sender and value externally
        emit SenderLogger(msg.sender);
        emit ValueLogger(msg.value);
    }

}

