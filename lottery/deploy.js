const HDWalletProvider = require('truffle-hdwallet-provider');
const Web3 = require('web3')
const { interface, bytecode } = require('./compile');

// Create a provider. Pass the mnuemonic and then teh URL for the network
const provider = new HDWalletProvider(
    '', // mnuemonic
    'https://rinkeby.infura.io/XXXX' // Network url
);

const web3 = new Web3(provider);

const deploy = async () => {
    let accounts = await web3.eth.getAccounts();

    console.log('Attempting to deploy from account.', accounts[0]);

    let constractHash = await new web3.eth.Contract(JSON.parse(interface))
        .deploy({
            data: bytecode
        })
        .send({
            gas: '1000000',
            from: accounts[0]
        });

    console.log('Contract deployed', constractHash.options.address);
};

deploy();