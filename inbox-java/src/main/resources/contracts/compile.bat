
mkdir output

solc inbox.sol --bin --abi --optimize -o ./output/

web3j solidity generate ./output/inbox.bin ./output/inbox.abi -o ../../java -p inbox.contracts
