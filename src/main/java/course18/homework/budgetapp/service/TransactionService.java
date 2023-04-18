package course18.homework.budgetapp.service;

import course18.homework.budgetapp.exception.TransactionNotFoundException;
import course18.homework.budgetapp.model.Transaction;
import course18.homework.budgetapp.model.TransactionType;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@Slf4j
public class TransactionService {
    private List<Transaction> transactions;

    public TransactionService() {
        this.transactions = new ArrayList<>();
        log.info("Transaction service created");
    }

    public List<Transaction> getAllTransactions(String product, TransactionType type, Double minAmount, Double maxAmount) {
        return transactions.stream()
                .filter(transaction -> (product == null || product.equals(transaction.product())))
                .filter(transaction -> (type == null || type.equals(transaction.type())))
                .filter(transaction -> (minAmount == null || minAmount < transaction.amount()))
                .filter(transaction -> (maxAmount == null || maxAmount > transaction.amount()))
                .toList();
    }

    public Transaction getTransactionById(String id) {
        return transactions.stream()
                .filter(transaction -> id.equals(transaction.id()))
                .findFirst()
                .orElseThrow(() -> new TransactionNotFoundException("Transaction with id %s was not found".formatted(id)));
    }

    public Transaction createNewTransaction(Transaction newTransaction) {
        String newId = UUID.randomUUID().toString();
        transactions.add(newTransaction.withId(newId));
        return getTransactionById(newId);
    }

    public Transaction replaceTransaction(String requestedId, Transaction newTransaction) {
        Transaction initialTransaction = getTransactionById(requestedId);
        deleteTransactionById(requestedId);
        Transaction updatedTransaction = initialTransaction.toBuilder()
                .id(requestedId)
                .product(newTransaction.product())
                .type(newTransaction.type())
                .amount(newTransaction.amount())
                .build();
        transactions.add(updatedTransaction);
        return updatedTransaction;
    }

    public Transaction deleteTransactionById(String requestedId) {
        Transaction deletedTransaction = getTransactionById(requestedId);
        transactions.remove(deletedTransaction);
        return deletedTransaction;
    }

    public Map<TransactionType, List<Transaction>> getTransactionsByType() {
        return transactions.stream()
                .collect(Collectors.groupingBy(Transaction::type));
    }

    public Map<String, List<Transaction>> getTransactionsByProduct() {
        return transactions.stream()
                .collect(Collectors.groupingBy(Transaction::product));
    }
}
