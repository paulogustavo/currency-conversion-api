package br.com.jaya.currencyconversionapi.controller;

/*@ExtendWith(SpringExtension.class)
@WebFluxTest(controllers = TransactionController.class)
@Import(TransactionService.class)
@ActiveProfiles("test")
class TransactionControllerTest {

    @MockBean
    MongoTransactionRepository transactionRepository;

    @MockBean
    MongoUserRepository userRepository;

    @Autowired
    private WebTestClient webClient;

    @Test
    void testGetTransactionsByUserIdShouldReturnOk() {
        var user = User.builder().id("609ecfbab66b6314c06af684").name("Paulo").build();
        var transaction = Transaction.builder()
                .conversionRate(new BigDecimal("5.4"))
                .finalCurrency("BRL")
                .originValue(BigDecimal.TEN)
                .finalValue(new BigDecimal("54.00"))
                .originCurrency("USD")
                .userId("609ecfbab66b6314c06af684")
                .createdAt(new Date())
                .build();
        List<Transaction> transactions = new ArrayList<>();
        transactions.add(transaction);

        Mockito.when(transactionRepository.findAllByUserId("609ecfbab66b6314c06af684")).thenReturn(Flux.fromIterable(transactions));
        Mockito.when(userRepository.findById("609ecfbab66b6314c06af684")).thenReturn(Mono.just(user));

        webClient.get()
                .uri("/transactions/609ecfbab66b6314c06af684")
                .exchange()
                .expectStatus().isOk()
                .expectBody();

        Mockito.verify(transactionRepository, times(1)).findAllByUserId("609ecfbab66b6314c06af684");
    }

}*/
