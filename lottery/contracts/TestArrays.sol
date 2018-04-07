pragma solidity ^0.4.17;

contract Lottery {

    address public manager;
    address[] public players;

    function Lottery() public {
        manager = msg.sender;
    }

    function getNumberOfPlayers() public view returns (uint) {
        return players.length;
    }

    function getPlayers() public view returns (address[]) {
        return players;
    }

    function enter() public payable {
        // Require that some ether amount is sent
        require(msg.value > 0.01 ether);

        // Add player
        players.push(msg.sender);
    }

    /*function pickWinner() public {

    }*/

    /**
     * Function to generate a random number
     * */
    function random() private view returns (uint) {
        return uint(keccak256(block.difficulty, now, players));
    }

}