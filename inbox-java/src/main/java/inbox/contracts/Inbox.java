package inbox.contracts;

import java.math.BigInteger;
import java.util.Arrays;
import java.util.Collections;
import org.web3j.abi.FunctionEncoder;
import org.web3j.abi.TypeReference;
import org.web3j.abi.datatypes.Function;
import org.web3j.abi.datatypes.Type;
import org.web3j.abi.datatypes.Utf8String;
import org.web3j.crypto.Credentials;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.RemoteCall;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import org.web3j.tx.Contract;
import org.web3j.tx.TransactionManager;

/**
 * <p>Auto generated code.
 * <p><strong>Do not modify!</strong>
 * <p>Please use the <a href="https://docs.web3j.io/command_line.html">web3j command line tools</a>,
 * or the org.web3j.codegen.SolidityFunctionWrapperGenerator in the 
 * <a href="https://github.com/web3j/web3j/tree/master/codegen">codegen module</a> to update.
 *
 * <p>Generated with web3j version 3.4.0.
 */
public class Inbox extends Contract {
    private static final String BINARY = "608060405234801561001057600080fd5b5060405161044038038061044083398101604052805101805161003a906000906020840190610041565b50506100dc565b828054600181600116156101000203166002900490600052602060002090601f016020900481019282601f1061008257805160ff19168380011785556100af565b828001600101855582156100af579182015b828111156100af578251825591602001919060010190610094565b506100bb9291506100bf565b5090565b6100d991905b808211156100bb57600081556001016100c5565b90565b610355806100eb6000396000f3006080604052600436106100565763ffffffff7c0100000000000000000000000000000000000000000000000000000000600035041663469c8110811461005b578063ce6d41de146100b6578063e21f37ce14610140575b600080fd5b34801561006757600080fd5b506040805160206004803580820135601f81018490048402850184019095528484526100b49436949293602493928401919081908401838280828437509497506101559650505050505050565b005b3480156100c257600080fd5b506100cb61016c565b6040805160208082528351818301528351919283929083019185019080838360005b838110156101055781810151838201526020016100ed565b50505050905090810190601f1680156101325780820380516001836020036101000a031916815260200191505b509250505060405180910390f35b34801561014c57600080fd5b506100cb610203565b8051610168906000906020840190610291565b5050565b60008054604080516020601f60026000196101006001881615020190951694909404938401819004810282018101909252828152606093909290918301828280156101f85780601f106101cd576101008083540402835291602001916101f8565b820191906000526020600020905b8154815290600101906020018083116101db57829003601f168201915b505050505090505b90565b6000805460408051602060026001851615610100026000190190941693909304601f810184900484028201840190925281815292918301828280156102895780601f1061025e57610100808354040283529160200191610289565b820191906000526020600020905b81548152906001019060200180831161026c57829003601f168201915b505050505081565b828054600181600116156101000203166002900490600052602060002090601f016020900481019282601f106102d257805160ff19168380011785556102ff565b828001600101855582156102ff579182015b828111156102ff5782518255916020019190600101906102e4565b5061030b92915061030f565b5090565b61020091905b8082111561030b57600081556001016103155600a165627a7a72305820ea40c3a0b5c99ff24fb838f27c81aecb8917953fc1367779e9192a71d33b4d480029";

    public static final String FUNC_SENDMESSAGE = "sendMessage";

    public static final String FUNC_GETMESSAGE = "getMessage";

    public static final String FUNC_MESSAGE = "message";

    protected Inbox(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    protected Inbox(String contractAddress, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    public RemoteCall<TransactionReceipt> sendMessage(String newMessage) {
        final Function function = new Function(
                FUNC_SENDMESSAGE, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Utf8String(newMessage)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteCall<String> getMessage() {
        final Function function = new Function(FUNC_GETMESSAGE, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Utf8String>() {}));
        return executeRemoteCallSingleValueReturn(function, String.class);
    }

    public RemoteCall<String> message() {
        final Function function = new Function(FUNC_MESSAGE, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Utf8String>() {}));
        return executeRemoteCallSingleValueReturn(function, String.class);
    }

    public static RemoteCall<Inbox> deploy(Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit, String initialMessage) {
        String encodedConstructor = FunctionEncoder.encodeConstructor(Arrays.<Type>asList(new org.web3j.abi.datatypes.Utf8String(initialMessage)));
        return deployRemoteCall(Inbox.class, web3j, credentials, gasPrice, gasLimit, BINARY, encodedConstructor);
    }

    public static RemoteCall<Inbox> deploy(Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit, String initialMessage) {
        String encodedConstructor = FunctionEncoder.encodeConstructor(Arrays.<Type>asList(new org.web3j.abi.datatypes.Utf8String(initialMessage)));
        return deployRemoteCall(Inbox.class, web3j, transactionManager, gasPrice, gasLimit, BINARY, encodedConstructor);
    }

    public static Inbox load(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        return new Inbox(contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    public static Inbox load(String contractAddress, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        return new Inbox(contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }
}
