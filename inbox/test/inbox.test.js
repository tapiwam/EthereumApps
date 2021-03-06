const assert = require('assert');
const ganache = require('ganache-cli');
const Web3 = require('web3');

const provider = ganache.provider();
const web3 = new Web3(provider);
const { interface, bytecode } = require('../compile');

const INITIAL_STRING = 'Hi there!';
let accounts;
let inbox;

beforeEach(async () => {
    // Get a list of all accounts
    accounts = await web3.eth.getAccounts();

    // use one of those accounts to deploy the contract
    inbox =  await new web3.eth.Contract(JSON.parse(interface))
        .deploy({ data:bytecode, arguments: [INITIAL_STRING]})
        .send({ from: accounts[0], gas: '1000000' });

    inbox.setProvider(provider);
});

describe('Inbox', () => {

    // Truthy test to make sure an address was assigned
    it('deploys a contract', () => {
        assert.ok(inbox.options.address);
    });

    it('has a default message', async () => {
        const message = await inbox.methods.message().call();
        assert.equal(message, INITIAL_STRING);
    });

    it('can change the message', async () => {
        let newMessage = 'Hello';
        let transactionHash = await inbox.methods.sendMessage(newMessage)
            .send({
                from: accounts[1],
                gas: '1000000'
            });

        const message = await inbox.methods.message().call();
        assert.equal(message, newMessage);
    })

});