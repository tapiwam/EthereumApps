pragma solidity ^0.4.0;

contract Trade {

    address internal owner;

    event TradeUpdate(uint tranid);
    event TradeStatusUpdate(uint tranid, string status);

    modifier isOwner(){
        require(msg.sender == owner);
        _;
    }

    struct Tran {
        uint tranid;
        string status;
        string account;
        string asset;
        string location;
        int quantity;
        int amount;
        uint timestamp;
        string user;
    }

    mapping(uint => Tran) public transactions ;
    uint[] public trans;


    function getTransList() public constant returns(uint[]){
        return trans;
    }

    function getTransLength() public constant returns(uint){
        return trans.length;
    }

    function getTransListBySize(uint size) public constant returns(uint[]){

        uint _len = getTransLength();
        uint _start = _len - size;
        uint[] memory elements = new uint[](size);

        for(uint i=_start; i<_len;i++){
            elements[i-_start] = trans[i];
        }

        return elements;
    }


    function updateTranStatus (
        uint _tranid,
        string _status,
        string _user
    ) public {
        // Check that it exists
        require(checkTranExists(_tranid));

        // Update
        transactions[_tranid].status = _status;
        transactions[_tranid].timestamp = block.timestamp;
        transactions[_tranid].user = _user;

        TradeStatusUpdate(_tranid, _status);
    }

    function findTranPosition(uint tranid) public constant returns (uint){
        uint _len = getTransLength();
        int _pos = -1;

        for(uint i=0; i<_len;i++){
            if(tranid == trans[i]){
                _pos = int(i);
                break;
            }
        }

        require(_pos >= 0);
        return uint(_pos);
    }

    function countTransGreaterThan(uint[] v, uint tranid) internal pure returns(uint) {
        uint counter = 0;
        bool check = false;

        for (uint i = 0; i < v.length; i++) {
            if ( check == false && v[i] == tranid) {
                check = true;
            }

            if(check){
                counter++;
            }
        }

        return counter;
    }

    function getTransListFromTranId(uint tranid) public constant returns(uint[]){
        uint _pos = findTranPosition(tranid);
        uint _len = getTransLength();
        uint _size = countTransGreaterThan(trans, tranid);

        uint[] memory elements = new uint[](_size);

        for(uint i=_pos; i<_len;i++){
            elements[i-_pos] = trans[i];
        }

        return elements;
    }

    function updateTran(
        uint _tranid,
        string _status,
        string _account,
        string _asset,
        string _loaction,
        int _quantity,
        int _amount,
        string _user
    ) public {
        Tran memory tran = Tran(_tranid, _status, _account, _asset, _loaction, _quantity, _amount, block.timestamp, _user);
        bool itemExists = (transactions[_tranid].tranid == _tranid);
        transactions[_tranid] = tran;

        if(!itemExists){
            trans.push(_tranid);
        }

        TradeUpdate(_tranid);
    }


    function getTran(uint _tranid) public constant returns (
        uint tranid, string status,
        string account, string asset,
        string location, int quantity,
        int amount, uint timestamp,
        string user
    ){
        require(checkTranExists(_tranid));

        Tran memory tran = transactions[_tranid];
        tranid = tran.tranid;
        status = tran.status;
        account = tran.account;
        asset = tran.asset;
        location = tran.location;
        quantity = tran.quantity;
        amount = tran.amount;
        timestamp = tran.timestamp;
        user = tran.user;
    }

    function checkTranExists(uint _tranid) public constant returns (bool){
        return (transactions[_tranid].tranid == _tranid);
    }

}