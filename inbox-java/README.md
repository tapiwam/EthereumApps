# Web3J Sample Maven Application

## Prerequistes

- Java version 8
- To compile contracts you need solc
    - Release can be found here - [https://github.com/ethereum/solidity/releases]
    - Tutorial on installing - [https://www.codeooze.com/blockchain/solc-hello-world/]
- To create java contract wrappers
    - [https://github.com/web3j/web3j/releases/tag/v3.4.0]
    
## Running

To run the application make sure maven dependencies are resolved and run the InboxApplication main program.

## Compiling Contracts

To compile a contract you need to have solc installed. Once installed the command to compile teh solidity will be as follows:

```batch
solc <contract>.sol --bin --abi --optimize -o <output-dir>/
```

Once compiled you can then generate Web3J wrapper like:

```batch
web3j solidity generate /path/to/<smart-contract>.bin /path/to/<smart-contract>.abi -o /path/to/src/main/java -p com.your.organisation.name
```

After a wrapper is done to deploy teh contract we can then do this:

```java
Web3j web3 = Web3j.build(new HttpService());  // defaults to http://localhost:8545/
Credentials credentials = WalletUtils.loadCredentials("password", "/path/to/walletfile");

YourSmartContract contract = YourSmartContract.deploy(
        <web3j>, <credentials>,
        GAS_PRICE, GAS_LIMIT,
        <param1>, ..., <paramN>).send();  // constructor params
```

### Demo Compiling Contracts

Please look at ```src\main\resources\contracts\compile.bat```
