package br.com.jaya.currencyconversionapi.service;

import br.com.jaya.currencyconversionapi.infrastructure.data.repository.MongoTransactionRepository;
import br.com.jaya.currencyconversionapi.infrastructure.data.repository.MongoUserRepository;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ActiveProfiles("test")
@ExtendWith(SpringExtension.class)
class TransactionServiceTest {
    @Mock
    MongoTransactionRepository transactionRepository;

    @Mock
    MongoUserRepository userRepository;

    /*@InjectMocks
    TransactionService transactionService;

    @Test
    void testFindAllTransactionByUserId_ShouldBeSuccessful(){
        var user = User.builder().id("609ecfbab66b6314c06af684").name("Paulo").build();

        var transaction = Transaction.builder()
                .conversionRate(new BigDecimal("5.27309783152"))
                .originCurrency("USD")
                .finalCurrency("BRL")
                .originValue(BigDecimal.TEN)
                .finalValue(new BigDecimal("52.730978315204"))
                .userId("609ecfbab66b6314c06af684")
                .createdAt(new Date())
                .build();
        List<Transaction> transactions = new ArrayList<>();
        transactions.add(transaction);

        Mockito.when(userRepository.findById((String) Mockito.any())).thenReturn(Mono.just(user));
        Mockito.when(transactionRepository.findAllByUserId(Mockito.any())).thenReturn(Flux.fromIterable(transactions));

        StepVerifier.create(transactionService.getTransactionsByUserId("609ecfbab66b6314c06af684"))
                .expectSubscription()
                .expectNextCount(1)
                .expectComplete()
                .verify();

        Mockito.verify(transactionRepository, times(1)).findAllByUserId("609ecfbab66b6314c06af684");

    }*/
}
