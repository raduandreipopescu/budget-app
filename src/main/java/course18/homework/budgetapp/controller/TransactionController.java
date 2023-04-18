package course18.homework.budgetapp.controller;

import course18.homework.budgetapp.model.Transaction;
import course18.homework.budgetapp.model.TransactionType;
import course18.homework.budgetapp.service.TransactionService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("transactions")
@RequiredArgsConstructor
@Slf4j
public class TransactionController {

    private final TransactionService transactionService;

    @GetMapping
    public List<Transaction> getAllTransactions(@RequestParam(required = false) String product,
                                                @RequestParam(required = false) TransactionType type,
                                                @RequestParam(required = false) Double minAmount,
                                                @RequestParam(required = false) Double maxAmount
    ) {
        log.info("GET all transactions call");
        return transactionService.getAllTransactions(product, type, minAmount, maxAmount);
    }

    @GetMapping("{id}")
    public Transaction getTransactionById(@PathVariable String id) {
        log.info("GET transaction by id call");
        return transactionService.getTransactionById(id);
    }

    @GetMapping("reports/type")
    public Map<TransactionType, List<Transaction>> getTransactionsByType() {
        log.info("GET report transactions by type call");
        return transactionService.getTransactionsByType();
    }

    @GetMapping("reports/product")
    public Map<String, List<Transaction>> getTransactionsByProduct() {
        log.info("GET report transactions by product call");
        return transactionService.getTransactionsByProduct();
    }

    @PostMapping
    public Transaction createNewTransaction(@RequestBody Transaction newTransaction) {
        log.info("POST create new transaction call");
        return transactionService.createNewTransaction(newTransaction);
    }

    @PutMapping("{id}")
    public Transaction replaceTransaction(@PathVariable String id, @RequestBody Transaction newTransaction) {
        log.info("PUT replace transaction by id call");
        return transactionService.replaceTransaction(id, newTransaction);
    }

    @DeleteMapping("{id}")
    public Transaction deleteTransactionById(@PathVariable String id) {
        log.info("DELETE transaction by id call");
        return transactionService.deleteTransactionById(id);
    }

}
