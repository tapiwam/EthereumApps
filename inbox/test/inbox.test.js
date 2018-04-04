const assert = require('assert');
const ganache = require('ganache-cli');
const Web3 = require('web3');

const web3 = new Web3(ganache.provider());

let accounts;

beforeEach(async () => {
    // Get a list of all accounts
    accounts = await web3.eth.getAccounts();

    // use one of those accounts to deploy teh contract

});

describe('Inbox', () => {

    it('deploys a contract', () => {
        console.log(accounts);
        // assert.equal(car.park(), 'stopped');
    });


});