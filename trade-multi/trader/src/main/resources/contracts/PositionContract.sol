pragma solidity ^0.4.0;

library  Util {

    function getLength(uint[] data) public pure returns(uint){
        return data.length;
    }

    function findPositionInList(uint id, uint[] data) public pure returns (uint){
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

    function maxNumber(uint[] data) public pure returns(uint){
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

    function getSubListFromElementPosition(uint id, uint[] data) public pure returns(uint[]){
        uint _pos = findPositionInList(id, data);
        uint _len = getLength(data);
        uint _size = countTransGreaterThan(data, id);

        uint[] memory elements = new uint[](_size);

        for(uint i=_pos; i<_len;i++){
            elements[i-_pos] = data[i];
        }

        return elements;
    }

    function getSubListBySize(uint[] data, uint size) public constant returns(uint[]){

        uint _len = getLength(data);
        uint _start = _len - size;
        uint[] memory elements = new uint[](size);

        for(uint i=_start; i<_len;i++){
            elements[i-_start] = data[i];
        }

        return elements;
    }

}

contract PositionContract {

    address internal owner;

    // event PositionUpdate(string account, string asset, string location);
    event PositionUpdate(uint id);

    modifier isOwner(){
        require(msg.sender == owner);
        _;
    }

    struct Position {
        uint id;
        string account;
        string asset;
        string location;
        int quantity;
        int quantityPending;
        uint timestamp;
        string user;
    }

    mapping(uint => Position) public positions ;
    uint[] public pos;


    function getList() public constant returns(uint[]){
        return pos;
    }

    function getSubListBySize(uint size) public view returns(uint[]) {
        return Util.getSubListBySize(pos, size);
    }

    function getSubListFromElementPosition(uint id) public view returns(uint[]){
        return Util.getSubListFromElementPosition(id, pos);
    }


    function getTransListFromTranId(uint positionid) public constant returns(uint[]){
        uint _pos = Util.findPositionInList(positionid, pos);
        uint _len = pos.length;
        uint _size = Util.countTransGreaterThan(pos, positionid);

        uint[] memory elements = new uint[](_size);

        for(uint i=_pos; i<_len;i++){
            elements[i-_pos] = pos[i];
        }

        return elements;
    }

    function updatePosition(
        string _account,
        string _asset,
        string _loaction,
        int _quantity,
        int _quantityP,
        string _user
    ) public {

        bool exists = checkExists(_account, _asset, _loaction);
        uint _id = 0;
        if(exists){
            _id = getId(_account, _asset, _loaction);
        } else {
            _id = Util.maxNumber(pos) + 1;
        }

        Position memory p = Position(_id, _account, _asset, _loaction, _quantity, _quantityP, block.timestamp, _user);
        positions[_id] = p;

        if(!exists){
            pos.push(_id);
        }

        PositionUpdate(_id);
    }

    function getPosition(string _account, string _asset, string _loaction) public view returns (
        uint id,
        string account, string asset,
        string location, int quantity,
        int quantityPending, uint timestamp,
        string user
    ) {
        uint _id = getId(_account, _asset, _loaction);
        return getPosition(_id);
    }

    function getPosition(uint _id) public constant returns (
        uint id,
        string account, string asset,
        string location, int quantity,
        int quantityPending, uint timestamp,
        string user
    ){
        // require(checkjExists(_tranid));

        Position memory position = positions[id];
        id = position.id;
        account = position.account;
        asset = position.asset;
        location = position.location;
        quantity = position.quantity;
        quantityPending = position.quantityPending;
        timestamp = position.timestamp;
        user = position.user;
    }

    function checkExists(string account, string asset, string location) public constant returns (bool){
        uint _len = pos.length;
        require(_len > 0);


        for(uint i=0; i<_len;i++){
            if(
                keccak256(positions[i].account) == keccak256(account)
                && keccak256(positions[i].asset) == keccak256(asset)
                && keccak256(positions[i].location) == keccak256(location)
            // StringUtils.equal(positions[i].account, account)
            // && positions[i].asset == asset
            //&& positions[i].location == location
            ) {
                return true;
            }
        }

        return false;
    }

    /**
     * Get ID based on key attributes of position
     */
    function getId(string account, string asset, string location) public constant returns (uint){

        uint id = 0;
        uint _len = pos.length;

        for(uint i=0; i<_len;i++){
            if(
                keccak256(positions[i].account) == keccak256(account)
                && keccak256(positions[i].asset) == keccak256(asset)
                && keccak256(positions[i].location) == keccak256(location)

            // positions[i].account == account
            // && positions[i].asset == asset
            // && positions[i].location == location
            ) {
                id = positions[i].id;
                break;
            }
        }

        require(id > 0);
        return uint(id);
    }

}