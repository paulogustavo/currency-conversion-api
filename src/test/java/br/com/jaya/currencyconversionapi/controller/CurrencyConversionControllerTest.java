package br.com.jaya.currencyconversionapi.controller;

/*@ExtendWith(SpringExtension.class)
@WebFluxTest(controllers = CurrencyConversionController.class)
@Import(CurrencyConversionService.class)
@ActiveProfiles("test")
class CurrencyConversionControllerTest {

    @MockBean
    MongoTransactionRepository transactionRepository;

    @MockBean
    MongoUserRepository userRepository;

    @MockBean
    CurrencyConversionService currencyConversionService;

    @MockBean
    RatesRepositoryImpl ratesService;

    @Autowired
    private WebTestClient webClient;

    private ConversionRequestDto conversionRequestDTO;
    private RatesResponseDto fetchRatesResponseDto;

    @BeforeEach
    public void init(){
        conversionRequestDTO = new ConversionRequestDto("USD",BigDecimal.TEN, "BRL", "609ecfbab66b6314c06af684");

        fetchRatesResponseDto = new RatesResponseDto();
        Map<String, BigDecimal> rates = new HashMap<>();
        rates.put("BRL", new BigDecimal("6.403258"));
        rates.put("USD", new BigDecimal("1.21459"));
        rates.put("EUR", BigDecimal.ONE);
        rates.put("JPY", new BigDecimal("132.845829"));
        fetchRatesResponseDto.setRates(rates);
    }

    @Test
    void testFetchRatesShouldReturnOk() {
        //Mockito.when(ratesService.fetchRates()).thenReturn(Mono.just(fetchRatesResponseDto));

        webClient.get()
                .uri("/conversion/rates")
                .exchange()
                .expectStatus().isOk()
                .expectBody();

        Mockito.verify(ratesService, times(1)).fetchRates();
    }

    @Test
    void testFetchRatesShouldThrowCustomException() {
       Mockito.when(ratesService.fetchRates()).thenReturn(Mono.error(new CurrencyConversionException("Failed to fetch rates")));

        webClient.get()
                .uri("/conversion/rates")
                .exchange()
                .expectStatus().isBadRequest()
                .expectBody();

        Mockito.verify(ratesService, times(1)).fetchRates();
    }

    //TransactionOk
    @Test
    void testSaveTransactionShouldReturnOk() {

        User user = User.builder().id("609ecfbab66b6314c06af684").name("Paulo").build();
        Transaction transaction = Transaction.builder()
                .conversionRate(new BigDecimal("5.4"))
                .finalCurrency("BRL")
                .originValue(BigDecimal.TEN)
                .finalValue(new BigDecimal("54.00"))
                .originCurrency("USD")
                .userId("609ecfbab66b6314c06af684")
                .createdAt(new Date())
                .build();

        Mockito.when(transactionRepository.save(transaction)).thenReturn(Mono.just(transaction));
        Mockito.when(userRepository.findById("609ecfbab66b6314c06af684")).thenReturn(Mono.just(user));
        //Mockito.when(ratesService.fetchRates()).thenReturn(Mono.just(fetchRatesResponseDto));

        webClient.post()
                .uri("/conversion")
                .bodyValue(conversionRequestDTO)
                .exchange()
                .expectStatus().isOk()
                .expectBody();

    }

}*/
