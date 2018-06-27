
mkdir output
rm output/*

rem solc trade.sol --bin --abi --optimize -o ./output/
solc PositionContract.sol --bin --abi --optimize -o ./output/

rem web3j solidity generate ./output/trade.bin ./output/trade.abi -o ../../java -p trader.contracts

rem web3j solidity generate ./output/PositionContract.bin ./output/PositionContract.abi -o ../../java -p trader.contracts
web3j solidity generate ./output/TradeContract.bin ./output/TradeContract.abi -o ../../java -p trader.contracts
