pragma solidity ^0.4.0;


/**
 * Common utilities for trades
 */
contract TradeUtil {

    address internal owner;

    modifier isOwner(){
        require(msg.sender == owner);
        _;
    }

    function getLength(uint[] data) internal pure returns(uint){
        return data.length;
    }

    function findPositionInList(uint[] data, uint id) internal pure returns (uint){
        uint _len = getLength(data);
        int _pos = -1;

        for(uint i=0; i<_len;i++){
            if(id == data[i]){
                _pos = int(i);
                break;
            }
        }

        require(_pos >= 0);
        return uint(_pos);
    }

    function maxNumber(uint[] data) internal pure returns(uint){
        // require (data.length > 0);

        uint m = 0;

        if(data.length > 0){
            m = data[0];

            for(uint i = 1; i<data.length ; i++ ){
                if(data[i] > m){
                    m = data[i];
                }
            }
        }

        return m;
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

    function getSubListFromElementPosition(uint[] data, uint id) internal pure returns(uint[]){
        uint _pos = findPositionInList(data, id);
        uint _len = getLength(data);
        uint _size = countTransGreaterThan(data, id);

        uint[] memory elements = new uint[](_size);

        for(uint i=_pos; i<_len;i++){
            elements[i-_pos] = data[i];
        }

        return elements;
    }

    function getSubListBySize(uint[] data, uint size) internal pure returns(uint[]){

        uint _len = getLength(data);
        uint _start = _len - size;
        uint[] memory elements = new uint[](size);

        for(uint i=_start; i<_len;i++){
            elements[i-_start] = data[i];
        }

        return elements;
    }

    function strConcat(string _a, string _b, string _c, string _d, string _e) internal pure returns (string){
        bytes memory _ba = bytes(_a);
        bytes memory _bb = bytes(_b);
        bytes memory _bc = bytes(_c);
        bytes memory _bd = bytes(_d);
        bytes memory _be = bytes(_e);
        string memory abcde = new string(_ba.length + _bb.length + _bc.length + _bd.length + _be.length);
        bytes memory babcde = bytes(abcde);
        uint k = 0;
        for (uint i = 0; i < _ba.length; i++) babcde[k++] = _ba[i];
        for (i = 0; i < _bb.length; i++) babcde[k++] = _bb[i];
        for (i = 0; i < _bc.length; i++) babcde[k++] = _bc[i];
        for (i = 0; i < _bd.length; i++) babcde[k++] = _bd[i];
        for (i = 0; i < _be.length; i++) babcde[k++] = _be[i];
        return string(babcde);
    }

    function strConcat(string _a, string _b, string _c, string _d) internal pure returns (string) {
        return strConcat(_a, _b, _c, _d, "");
    }

    function strConcat(string _a, string _b, string _c) internal pure returns (string) {
        return strConcat(_a, _b, _c, "", "");
    }

    function strConcat(string _a, string _b) internal pure returns (string) {
        return strConcat(_a, _b, "", "", "");
    }

    function strToLower(string str) internal pure returns (string) {
        bytes memory bStr = bytes(str);
        bytes memory bLower = new bytes(bStr.length);
        for (uint i = 0; i < bStr.length; i++) {
            // Uppercase character...
            if ((bStr[i] >= 65) && (bStr[i] <= 90)) {
                // So we add 32 to make it lowercase
                bLower[i] = bytes1(int(bStr[i]) + 32);
            } else {
                bLower[i] = bStr[i];
            }
        }
        return string(bLower);
    }
}

library TradeObjects {

    struct Position {
        uint id;
        string pKey;
        uint tranid;
        string account;
        string asset;
        string location;
        int quantity;
        int quantityPending;
        uint timestamp;
        string user;
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
}

contract PositionContract is TradeUtil {

    // event PositionUpdate(string account, string asset, string location);
    event PositionUpdate(uint id);

    // Holds actual trades
    mapping(uint => TradeObjects.Position) public positions;

    // Map of keys to ids
    mapping(string => uint) internal idMap;

    // List of ids
    uint[] public pos;

    // ==============

    function compileKey(string _account, string _asset, string _loaction) public pure returns (string){
        string memory key = strConcat( _account, "_",  _asset, "_", _loaction );
        return strToLower(key);
    }

    // ==============

    /**
     * Check a position exists by key attributes
     */
    function checkKeyExists(string _account, string _asset, string _loaction) public view returns(bool) {
        return checkKeyExists(compileKey(_account, _asset, _loaction));
    }

    /**
     * Check a position exists by key
     */
    function checkKeyExists(string _key) public view returns(bool) {
        return idMap[_key] > 0;
    }

    // ==============

    /**
     * Get ID based on key attributes of position
     */
    function getKeyId(string _account, string _asset, string _loaction) public view returns(uint) {
        return idMap[compileKey(_account, _asset, _loaction)];
    }

    /**
     * Get ID based on key of position
     */
    function getKeyId(string _key) public view returns(uint) {
        return idMap[_key];
    }

    // ==============

    /**
     * Get the position IDs
     */
    function getPositionIds() public view returns (uint[]){
        return pos;
    }

    // ==============

    /**
     * Update a position record
     */
    function updatePosition(
        string _account,
        string _asset,
        string _loaction,
        int _quantity,
        int _quantityP,
        string _user
    ) public {

        string memory _key = compileKey(_account, _asset, _loaction);

        bool itemExists = checkKeyExists(_key);

        uint _id = 0;
        if(itemExists){
            _id = idMap[_key];
        } else {
            _id = maxNumber(pos) + 1;
        }


        TradeObjects.Position memory p = TradeObjects.Position(_id, _key, 0, _account, _asset, _loaction, _quantity, _quantityP, block.timestamp, _user);
        positions[_id] = p;
        idMap[_key] = _id;

        if(!itemExists){
            pos.push(_id);
        }

        PositionUpdate(_id);
    }

    // ==============

    function getPosition(string _account, string _asset, string _loaction) public view returns (
        uint id, string key,
        string account, string asset,
        string location, int quantity,
        int quantityPending, uint timestamp,
        string user
    ) {
        // uint _id = getId(_account, _asset, _loaction);
        uint _id = getKeyId(_account, _asset, _loaction);
        return getPosition(_id);
    }

    function getPosition(uint _id) public constant returns (
        uint id, string key,
        string account, string asset,
        string location, int quantity,
        int quantityPending, uint timestamp,
        string user
    ){
        // require(checkExists(_tranid));

        TradeObjects.Position memory position = positions[_id];
        id = position.id;
        key = position.pKey;
        account = position.account;
        asset = position.asset;
        location = position.location;
        quantity = position.quantity;
        quantityPending = position.quantityPending;
        timestamp = position.timestamp;
        user = position.user;
    }

    // ==========

    /**
     * Get the last few records
     */
    function getSubListBySize(uint size) public view returns(uint[]) {
        return getSubListBySize(pos, size);
    }

    /**
     * Get all sebsequent records from a given position
     */
    function getSubListFromElementPosition(uint id) public view returns(uint[]){
        return getSubListFromElementPosition(pos, id);
    }


    /**
     * Get all subsequent records from a given key
     */
    function getTransListFromKeyPosition(string key) public constant returns(uint[]){

        uint _id = getKeyId(key);
        uint _pos = findPositionInList(pos, _id);
        return getSubListFromElementPosition(pos, _pos);
    }

    // ==========

}

contract TradeContract is TradeUtil {

    mapping(uint => TradeObjects.Tran) public transactions ;
    uint[] public trans;

    event TradeUpdate(uint tranid);
    event TradeStatusUpdate(uint tranid, string status);


    function getTransList() public constant returns(uint[]){
        return trans;
    }

    function getTransListBySize(uint size) public constant returns(uint[]){
        return getSubListBySize(trans, size);
    }

    function findTranPosition(uint tranid) public constant returns (uint){
        return findPositionInList(trans, tranid);
    }

    function getTransListFromTranId(uint tranid) public constant returns(uint[]){
        return getSubListFromElementPosition(trans, tranid);
    }

    function checkTranExists(uint _tranid) public constant returns (bool){
        return (transactions[_tranid].tranid == _tranid);
    }

    function getTransLength() public constant returns(uint){
        return trans.length;
    }

    function getTran(uint _tranid) public constant returns (
        uint tranid, string status,
        string account, string asset,
        string location, int quantity,
        int amount, uint timestamp,
        string user
    ){
        require(checkTranExists(_tranid));

        TradeObjects.Tran memory tran = transactions[_tranid];
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
        TradeObjects.Tran memory tran = TradeObjects.Tran(_tranid, _status, _account, _asset, _loaction, _quantity, _amount, block.timestamp, _user);
        bool itemExists = (transactions[_tranid].tranid == _tranid);
        transactions[_tranid] = tran;

        if(!itemExists){
            trans.push(_tranid);
        }

        TradeUpdate(_tranid);
    }

}