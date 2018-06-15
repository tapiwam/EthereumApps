pragma solidity ^0.4.9;

contract Inbox {

    event NewMessage(string message);

    string public message;

    function Inbox(string initialMessage) public {
        message = initialMessage;
    }

    function sendMessage(string newMessage) public {
        message = newMessage;
        NewMessage(newMessage);
    }

    function getMessage() public constant returns (string) {
        return message;
    }


}