package trader.contracts;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Callable;
import org.web3j.abi.EventEncoder;
import org.web3j.abi.TypeReference;
import org.web3j.abi.datatypes.Bool;
import org.web3j.abi.datatypes.DynamicArray;
import org.web3j.abi.datatypes.Event;
import org.web3j.abi.datatypes.Function;
import org.web3j.abi.datatypes.Type;
import org.web3j.abi.datatypes.Utf8String;
import org.web3j.abi.datatypes.generated.Int256;
import org.web3j.abi.datatypes.generated.Uint256;
import org.web3j.crypto.Credentials;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.DefaultBlockParameter;
import org.web3j.protocol.core.RemoteCall;
import org.web3j.protocol.core.methods.request.EthFilter;
import org.web3j.protocol.core.methods.response.Log;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import org.web3j.tuples.generated.Tuple9;
import org.web3j.tx.Contract;
import org.web3j.tx.TransactionManager;
import rx.Observable;
import rx.functions.Func1;

/**
 * <p>Auto generated code.
 * <p><strong>Do not modify!</strong>
 * <p>Please use the <a href="https://docs.web3j.io/command_line.html">web3j command line tools</a>,
 * or the org.web3j.codegen.SolidityFunctionWrapperGenerator in the 
 * <a href="https://github.com/web3j/web3j/tree/master/codegen">codegen module</a> to update.
 *
 * <p>Generated with web3j version 3.4.0.
 */
public class TradeContract extends Contract {
    private static final String BINARY = "608060405234801561001057600080fd5b5061142d806100206000396000f3006080604052600436106100ae5763ffffffff7c010000000000000000000000000000000000000000000000000000000060003504166305e54f7f81146100b357806312b6eb36146101515780632802d4751461017d5780633733b9b4146101e257806347b47208146101fa57806366c9913914610221578063903b1ac2146102395780639740b1bc146102515780639ace38c214610495578063a50c40f6146104ad578063f605ad7a146104c5575b600080fd5b3480156100bf57600080fd5b5060408051602060046024803582810135601f810185900485028601850190965285855261014f95833595369560449491939091019190819084018382808284375050604080516020601f89358b018035918201839004830284018301909452808352979a9998810197919650918201945092508291508401838280828437509497506106289650505050505050565b005b34801561015d57600080fd5b50610169600435610734565b604080519115158252519081900360200190f35b34801561018957600080fd5b50610192610747565b60408051602080825283518183015283519192839290830191858101910280838360005b838110156101ce5781810151838201526020016101b6565b505050509050019250505060405180910390f35b3480156101ee57600080fd5b506101926004356107a0565b34801561020657600080fd5b5061020f610802565b60408051918252519081900360200190f35b34801561022d57600080fd5b5061020f600435610808565b34801561024557600080fd5b5061020f600435610827565b34801561025d57600080fd5b50610269600435610883565b604051808a8152602001806020018060200180602001806020018981526020018881526020018781526020018060200186810386528e818151815260200191508051906020019080838360005b838110156102ce5781810151838201526020016102b6565b50505050905090810190601f1680156102fb5780820380516001836020036101000a031916815260200191505b5086810385528d5181528d516020918201918f019080838360005b8381101561032e578181015183820152602001610316565b50505050905090810190601f16801561035b5780820380516001836020036101000a031916815260200191505b5086810384528c5181528c516020918201918e019080838360005b8381101561038e578181015183820152602001610376565b50505050905090810190601f1680156103bb5780820380516001836020036101000a031916815260200191505b5086810383528b5181528b516020918201918d019080838360005b838110156103ee5781810151838201526020016103d6565b50505050905090810190601f16801561041b5780820380516001836020036101000a031916815260200191505b50868103825287518152875160209182019189019080838360005b8381101561044e578181015183820152602001610436565b50505050905090810190601f16801561047b5780820380516001836020036101000a031916815260200191505b509e50505050505050505050505050505060405180910390f35b3480156104a157600080fd5b50610269600435610c2b565b3480156104b957600080fd5b50610192600435610f28565b3480156104d157600080fd5b5060408051602060046024803582810135601f810185900485028601850190965285855261014f95833595369560449491939091019190819084018382808284375050604080516020601f89358b018035918201839004830284018301909452808352979a99988101979196509182019450925082915084018382808284375050604080516020601f89358b018035918201839004830284018301909452808352979a99988101979196509182019450925082915084018382808284375050604080516020601f89358b018035918201839004830284018301909452808352979a99988101979196509182019450925082915084018382808284375050604080516020888301358a018035601f8101839004830284018301909452838352979a89359a8a8301359a91999098506060909101965091945090810192508190840183828082843750949750610f849650505050505050565b61063183610734565b151561063c57600080fd5b600083815260016020818152604090922084516106619391909201919085019061131c565b506000838152600160209081526040909120426007820155825161068d9260089092019184019061131c565b507f6f455caddf6da9762940f8da0b25bed37364405b46ad026d52bd0a5f39c57e5f83836040518083815260200180602001828103825283818151815260200191508051906020019080838360005b838110156106f45781810151838201526020016106dc565b50505050905090810190601f1680156107215780820380516001836020036101000a031916815260200191505b50935050505060405180910390a1505050565b6000818152600160205260409020541490565b6060600280548060200260200160405190810160405280929190818152602001828054801561079557602002820191906000526020600020905b815481526020019060010190808311610781575b505050505090505b90565b60606107fc60028054806020026020016040519081016040528092919081815260200182805480156107f157602002820191906000526020600020905b8154815260200190600101908083116107dd575b505050505083611111565b92915050565b60025490565b600280548290811061081657fe5b600091825260209091200154905081565b60006107fc600280548060200260200160405190810160405280929190818152602001828054801561087857602002820191906000526020600020905b815481526020019060010190808311610864575b5050505050836111a7565b60006060806060806000806000606061089a61139a565b6108a38b610734565b15156108ae57600080fd5b60008b8152600160208181526040928390208351610120810185528154815281840180548651600296821615610100026000190190911695909504601f810185900485028601850190965285855290949193858401939092908301828280156109585780601f1061092d57610100808354040283529160200191610958565b820191906000526020600020905b81548152906001019060200180831161093b57829003601f168201915b5050509183525050600282810180546040805160206001841615610100026000190190931694909404601f810183900483028501830190915280845293810193908301828280156109ea5780601f106109bf576101008083540402835291602001916109ea565b820191906000526020600020905b8154815290600101906020018083116109cd57829003601f168201915b505050918352505060038201805460408051602060026001851615610100026000190190941693909304601f8101849004840282018401909252818152938201939291830182828015610a7e5780601f10610a5357610100808354040283529160200191610a7e565b820191906000526020600020905b815481529060010190602001808311610a6157829003601f168201915b505050918352505060048201805460408051602060026001851615610100026000190190941693909304601f8101849004840282018401909252818152938201939291830182828015610b125780601f10610ae757610100808354040283529160200191610b12565b820191906000526020600020905b815481529060010190602001808311610af557829003601f168201915b50505050508152602001600582015481526020016006820154815260200160078201548152602001600882018054600181600116156101000203166002900480601f016020809104026020016040519081016040528092919081815260200182805460018160011615610100020316600290048015610bd25780601f10610ba757610100808354040283529160200191610bd2565b820191906000526020600020905b815481529060010190602001808311610bb557829003601f168201915b505050505081525050905080600001519950806020015198508060400151975080606001519650806080015195508060a0015194508060c0015193508060e0015192508061010001519150509193959799909294969850565b600160208181526000928352604092839020805481840180548651600296821615610100026000190190911695909504601f81018590048502860185019096528585529094919392909190830182828015610cc75780601f10610c9c57610100808354040283529160200191610cc7565b820191906000526020600020905b815481529060010190602001808311610caa57829003601f168201915b50505060028085018054604080516020601f6000196101006001871615020190941695909504928301859004850281018501909152818152959695945090925090830182828015610d595780601f10610d2e57610100808354040283529160200191610d59565b820191906000526020600020905b815481529060010190602001808311610d3c57829003601f168201915b5050505060038301805460408051602060026001851615610100026000190190941693909304601f8101849004840282018401909252818152949594935090830182828015610de95780601f10610dbe57610100808354040283529160200191610de9565b820191906000526020600020905b815481529060010190602001808311610dcc57829003601f168201915b5050505060048301805460408051602060026001851615610100026000190190941693909304601f8101849004840282018401909252818152949594935090830182828015610e795780601f10610e4e57610100808354040283529160200191610e79565b820191906000526020600020905b815481529060010190602001808311610e5c57829003601f168201915b505050600584015460068501546007860154600887018054604080516020601f60026000196101006001881615020190951694909404938401819004810282018101909252828152989995989497509295509091830182828015610f1e5780601f10610ef357610100808354040283529160200191610f1e565b820191906000526020600020905b815481529060010190602001808311610f0157829003601f168201915b5050505050905089565b60606107fc6002805480602002602001604051908101604052809291908181526020018280548015610f7957602002820191906000526020600020905b815481526020019060010190808311610f65575b505050505083611210565b610f8c61139a565b50604080516101208101825289815260208082018a81528284018a9052606083018990526080830188905260a0830187905260c083018690524260e0840152610100830185905260008c81526001808452949020805484518255915180519495928e1494869492936110039390850192019061131c565b506040820151805161101f91600284019160209091019061131c565b506060820151805161103b91600384019160209091019061131c565b506080820151805161105791600484019160209091019061131c565b5060a0820151600582015560c0820151600682015560e08201516007820155610100820151805161109291600884019160209091019061131c565b5050508015156110d257600280546001810182556000919091527f405787fa12a823e0f2b7631cc41b3ba8828b3321ca811111fa75cd3aa3bb5ace018a90555b604080518b815290517f5fdcd8a0e363e81d9b509f4b2b1986f2f60de35609ed33360e83323db8f3aa6c9181900360200190a150505050505050505050565b606060008060606000611123876112bc565b9350858403925085604051908082528060200260200182016040528015611154578160200160208202803883390190505b5091508290505b8381101561119d57868181518110151561117157fe5b906020019060200201518284830381518110151561118b57fe5b6020908102909101015260010161115b565b5095945050505050565b6000806000806111b6866112bc565b92506000199150600090505b828110156111f95785818151811015156111d857fe5b906020019060200201518514156111f1578091506111f9565b6001016111c2565b600082121561120757600080fd5b50949350505050565b606060008060006060600061122588886111a7565b9450611230886112bc565b935061123c88886112c0565b925082604051908082528060200260200182016040528015611268578160200160208202803883390190505b5091508490505b838110156112b157878181518110151561128557fe5b906020019060200201518286830381518110151561129f57fe5b6020908102909101015260010161126f565b509695505050505050565b5190565b60008080805b855181101561131257811580156112f357508486828151811015156112e757fe5b90602001906020020151145b156112fd57600191505b811561130a576001909201915b6001016112c6565b5090949350505050565b828054600181600116156101000203166002900490600052602060002090601f016020900481019282601f1061135d57805160ff191683800117855561138a565b8280016001018555821561138a579182015b8281111561138a57825182559160200191906001019061136f565b506113969291506113e7565b5090565b610120604051908101604052806000815260200160608152602001606081526020016060815260200160608152602001600081526020016000815260200160008152602001606081525090565b61079d91905b8082111561139657600081556001016113ed5600a165627a7a723058206e0c39c36318ced176c9734b1e03d1c73b09c9d88e993f7155f84633a5eb6e790029";

    public static final String FUNC_UPDATETRANSTATUS = "updateTranStatus";

    public static final String FUNC_CHECKTRANEXISTS = "checkTranExists";

    public static final String FUNC_GETTRANSLIST = "getTransList";

    public static final String FUNC_GETTRANSLISTBYSIZE = "getTransListBySize";

    public static final String FUNC_GETTRANSLENGTH = "getTransLength";

    public static final String FUNC_TRANS = "trans";

    public static final String FUNC_FINDTRANPOSITION = "findTranPosition";

    public static final String FUNC_GETTRAN = "getTran";

    public static final String FUNC_TRANSACTIONS = "transactions";

    public static final String FUNC_GETTRANSLISTFROMTRANID = "getTransListFromTranId";

    public static final String FUNC_UPDATETRAN = "updateTran";

    public static final Event TRADEUPDATE_EVENT = new Event("TradeUpdate", 
            Arrays.<TypeReference<?>>asList(),
            Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}));
    ;

    public static final Event TRADESTATUSUPDATE_EVENT = new Event("TradeStatusUpdate", 
            Arrays.<TypeReference<?>>asList(),
            Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}, new TypeReference<Utf8String>() {}));
    ;

    protected TradeContract(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    protected TradeContract(String contractAddress, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    public RemoteCall<TransactionReceipt> updateTranStatus(BigInteger _tranid, String _status, String _user) {
        final Function function = new Function(
                FUNC_UPDATETRANSTATUS, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(_tranid), 
                new org.web3j.abi.datatypes.Utf8String(_status), 
                new org.web3j.abi.datatypes.Utf8String(_user)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteCall<Boolean> checkTranExists(BigInteger _tranid) {
        final Function function = new Function(FUNC_CHECKTRANEXISTS, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(_tranid)), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Bool>() {}));
        return executeRemoteCallSingleValueReturn(function, Boolean.class);
    }

    public RemoteCall<List> getTransList() {
        final Function function = new Function(FUNC_GETTRANSLIST, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<DynamicArray<Uint256>>() {}));
        return new RemoteCall<List>(
                new Callable<List>() {
                    @Override
                    @SuppressWarnings("unchecked")
                    public List call() throws Exception {
                        List<Type> result = (List<Type>) executeCallSingleValueReturn(function, List.class);
                        return convertToNative(result);
                    }
                });
    }

    public RemoteCall<List> getTransListBySize(BigInteger size) {
        final Function function = new Function(FUNC_GETTRANSLISTBYSIZE, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(size)), 
                Arrays.<TypeReference<?>>asList(new TypeReference<DynamicArray<Uint256>>() {}));
        return new RemoteCall<List>(
                new Callable<List>() {
                    @Override
                    @SuppressWarnings("unchecked")
                    public List call() throws Exception {
                        List<Type> result = (List<Type>) executeCallSingleValueReturn(function, List.class);
                        return convertToNative(result);
                    }
                });
    }

    public RemoteCall<BigInteger> getTransLength() {
        final Function function = new Function(FUNC_GETTRANSLENGTH, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}));
        return executeRemoteCallSingleValueReturn(function, BigInteger.class);
    }

    public RemoteCall<BigInteger> trans(BigInteger param0) {
        final Function function = new Function(FUNC_TRANS, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(param0)), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}));
        return executeRemoteCallSingleValueReturn(function, BigInteger.class);
    }

    public RemoteCall<BigInteger> findTranPosition(BigInteger tranid) {
        final Function function = new Function(FUNC_FINDTRANPOSITION, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(tranid)), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}));
        return executeRemoteCallSingleValueReturn(function, BigInteger.class);
    }

    public RemoteCall<Tuple9<BigInteger, String, String, String, String, BigInteger, BigInteger, BigInteger, String>> getTran(BigInteger _tranid) {
        final Function function = new Function(FUNC_GETTRAN, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(_tranid)), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}, new TypeReference<Utf8String>() {}, new TypeReference<Utf8String>() {}, new TypeReference<Utf8String>() {}, new TypeReference<Utf8String>() {}, new TypeReference<Int256>() {}, new TypeReference<Int256>() {}, new TypeReference<Uint256>() {}, new TypeReference<Utf8String>() {}));
        return new RemoteCall<Tuple9<BigInteger, String, String, String, String, BigInteger, BigInteger, BigInteger, String>>(
                new Callable<Tuple9<BigInteger, String, String, String, String, BigInteger, BigInteger, BigInteger, String>>() {
                    @Override
                    public Tuple9<BigInteger, String, String, String, String, BigInteger, BigInteger, BigInteger, String> call() throws Exception {
                        List<Type> results = executeCallMultipleValueReturn(function);
                        return new Tuple9<BigInteger, String, String, String, String, BigInteger, BigInteger, BigInteger, String>(
                                (BigInteger) results.get(0).getValue(), 
                                (String) results.get(1).getValue(), 
                                (String) results.get(2).getValue(), 
                                (String) results.get(3).getValue(), 
                                (String) results.get(4).getValue(), 
                                (BigInteger) results.get(5).getValue(), 
                                (BigInteger) results.get(6).getValue(), 
                                (BigInteger) results.get(7).getValue(), 
                                (String) results.get(8).getValue());
                    }
                });
    }

    public RemoteCall<Tuple9<BigInteger, String, String, String, String, BigInteger, BigInteger, BigInteger, String>> transactions(BigInteger param0) {
        final Function function = new Function(FUNC_TRANSACTIONS, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(param0)), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}, new TypeReference<Utf8String>() {}, new TypeReference<Utf8String>() {}, new TypeReference<Utf8String>() {}, new TypeReference<Utf8String>() {}, new TypeReference<Int256>() {}, new TypeReference<Int256>() {}, new TypeReference<Uint256>() {}, new TypeReference<Utf8String>() {}));
        return new RemoteCall<Tuple9<BigInteger, String, String, String, String, BigInteger, BigInteger, BigInteger, String>>(
                new Callable<Tuple9<BigInteger, String, String, String, String, BigInteger, BigInteger, BigInteger, String>>() {
                    @Override
                    public Tuple9<BigInteger, String, String, String, String, BigInteger, BigInteger, BigInteger, String> call() throws Exception {
                        List<Type> results = executeCallMultipleValueReturn(function);
                        return new Tuple9<BigInteger, String, String, String, String, BigInteger, BigInteger, BigInteger, String>(
                                (BigInteger) results.get(0).getValue(), 
                                (String) results.get(1).getValue(), 
                                (String) results.get(2).getValue(), 
                                (String) results.get(3).getValue(), 
                                (String) results.get(4).getValue(), 
                                (BigInteger) results.get(5).getValue(), 
                                (BigInteger) results.get(6).getValue(), 
                                (BigInteger) results.get(7).getValue(), 
                                (String) results.get(8).getValue());
                    }
                });
    }

    public RemoteCall<List> getTransListFromTranId(BigInteger tranid) {
        final Function function = new Function(FUNC_GETTRANSLISTFROMTRANID, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(tranid)), 
                Arrays.<TypeReference<?>>asList(new TypeReference<DynamicArray<Uint256>>() {}));
        return new RemoteCall<List>(
                new Callable<List>() {
                    @Override
                    @SuppressWarnings("unchecked")
                    public List call() throws Exception {
                        List<Type> result = (List<Type>) executeCallSingleValueReturn(function, List.class);
                        return convertToNative(result);
                    }
                });
    }

    public RemoteCall<TransactionReceipt> updateTran(BigInteger _tranid, String _status, String _account, String _asset, String _loaction, BigInteger _quantity, BigInteger _amount, String _user) {
        final Function function = new Function(
                FUNC_UPDATETRAN, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(_tranid), 
                new org.web3j.abi.datatypes.Utf8String(_status), 
                new org.web3j.abi.datatypes.Utf8String(_account), 
                new org.web3j.abi.datatypes.Utf8String(_asset), 
                new org.web3j.abi.datatypes.Utf8String(_loaction), 
                new org.web3j.abi.datatypes.generated.Int256(_quantity), 
                new org.web3j.abi.datatypes.generated.Int256(_amount), 
                new org.web3j.abi.datatypes.Utf8String(_user)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public List<TradeUpdateEventResponse> getTradeUpdateEvents(TransactionReceipt transactionReceipt) {
        List<Contract.EventValuesWithLog> valueList = extractEventParametersWithLog(TRADEUPDATE_EVENT, transactionReceipt);
        ArrayList<TradeUpdateEventResponse> responses = new ArrayList<TradeUpdateEventResponse>(valueList.size());
        for (Contract.EventValuesWithLog eventValues : valueList) {
            TradeUpdateEventResponse typedResponse = new TradeUpdateEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse.tranid = (BigInteger) eventValues.getNonIndexedValues().get(0).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public Observable<TradeUpdateEventResponse> tradeUpdateEventObservable(EthFilter filter) {
        return web3j.ethLogObservable(filter).map(new Func1<Log, TradeUpdateEventResponse>() {
            @Override
            public TradeUpdateEventResponse call(Log log) {
                Contract.EventValuesWithLog eventValues = extractEventParametersWithLog(TRADEUPDATE_EVENT, log);
                TradeUpdateEventResponse typedResponse = new TradeUpdateEventResponse();
                typedResponse.log = log;
                typedResponse.tranid = (BigInteger) eventValues.getNonIndexedValues().get(0).getValue();
                return typedResponse;
            }
        });
    }

    public Observable<TradeUpdateEventResponse> tradeUpdateEventObservable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(TRADEUPDATE_EVENT));
        return tradeUpdateEventObservable(filter);
    }

    public List<TradeStatusUpdateEventResponse> getTradeStatusUpdateEvents(TransactionReceipt transactionReceipt) {
        List<Contract.EventValuesWithLog> valueList = extractEventParametersWithLog(TRADESTATUSUPDATE_EVENT, transactionReceipt);
        ArrayList<TradeStatusUpdateEventResponse> responses = new ArrayList<TradeStatusUpdateEventResponse>(valueList.size());
        for (Contract.EventValuesWithLog eventValues : valueList) {
            TradeStatusUpdateEventResponse typedResponse = new TradeStatusUpdateEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse.tranid = (BigInteger) eventValues.getNonIndexedValues().get(0).getValue();
            typedResponse.status = (String) eventValues.getNonIndexedValues().get(1).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public Observable<TradeStatusUpdateEventResponse> tradeStatusUpdateEventObservable(EthFilter filter) {
        return web3j.ethLogObservable(filter).map(new Func1<Log, TradeStatusUpdateEventResponse>() {
            @Override
            public TradeStatusUpdateEventResponse call(Log log) {
                Contract.EventValuesWithLog eventValues = extractEventParametersWithLog(TRADESTATUSUPDATE_EVENT, log);
                TradeStatusUpdateEventResponse typedResponse = new TradeStatusUpdateEventResponse();
                typedResponse.log = log;
                typedResponse.tranid = (BigInteger) eventValues.getNonIndexedValues().get(0).getValue();
                typedResponse.status = (String) eventValues.getNonIndexedValues().get(1).getValue();
                return typedResponse;
            }
        });
    }

    public Observable<TradeStatusUpdateEventResponse> tradeStatusUpdateEventObservable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(TRADESTATUSUPDATE_EVENT));
        return tradeStatusUpdateEventObservable(filter);
    }

    public static RemoteCall<TradeContract> deploy(Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        return deployRemoteCall(TradeContract.class, web3j, credentials, gasPrice, gasLimit, BINARY, "");
    }

    public static RemoteCall<TradeContract> deploy(Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        return deployRemoteCall(TradeContract.class, web3j, transactionManager, gasPrice, gasLimit, BINARY, "");
    }

    public static TradeContract load(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        return new TradeContract(contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    public static TradeContract load(String contractAddress, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        return new TradeContract(contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    public static class TradeUpdateEventResponse {
        public Log log;

        public BigInteger tranid;
    }

    public static class TradeStatusUpdateEventResponse {
        public Log log;

        public BigInteger tranid;

        public String status;
    }
}
