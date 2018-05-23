
contract TestThrows {

    // Pass a boolean value to assert
    function testAssert() public pure {
        assert(false);
    }

    // Pass a boolean value to require
    function testRequire() public pure {
        require(false);
    }

    function testRevert() public pure {
        revert();
    }

    function testThrow() public pure {
        throw;
    }

}