pragma solidity ^0.4.15;

contract Inbox {
    
    string public message;

    function Inbox(string initialMessage) public {
        message = initialMessage;
    }

    function sendMessage(string newMessage) public {
        message = newMessage;
    }

    function getMessage() public constant returns (string) {
        return message;
    }
}