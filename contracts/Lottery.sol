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

        // Reset contract
    }

    function pickWinner() public restricted {

        // Pick the winner using random
        uint index = random() % players.length;
        address winner = players[index];

        // Send balance
        winner.transfer(this.balance);

        // Reset
        reset();
    }

    /**
     * Function to generate a random number
     * */
    function random() private view returns (uint) {
        return uint(keccak256(block.difficulty, now, players));
    }

    function reset() private restricted {
        require(this.balance == 0);
        players = new address[](0);
    }

    // ========================================

    /**
     * Function modifier used to restric access to functions
     */
    modifier restricted() {
        require(manager == msg.sender);
        _;   // Injected code is inserted here
    }
}