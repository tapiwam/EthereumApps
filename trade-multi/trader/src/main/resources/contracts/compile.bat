
mkdir output
rm output/*

solc trade.sol --bin --abi --optimize -o ./output/

web3j solidity generate ./output/trade.bin ./output/trade.abi -o ../../java -p trader.contracts
