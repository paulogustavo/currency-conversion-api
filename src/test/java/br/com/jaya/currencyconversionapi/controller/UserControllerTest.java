package br.com.jaya.currencyconversionapi.controller;

/*
@ExtendWith(SpringExtension.class)
@WebFluxTest(controllers = UserController.class)
@Import(UserService.class)
@ActiveProfiles("test")
class UserControllerTest {
    @MockBean
    MongoUserRepository repository;

    @Autowired
    private WebTestClient webClient;

    @Test
    void testFindAllUsersShouldReturnOk() {
        var user1 = User.builder().id("609ecfbab66b6314c06af684").name("Paulo").build();
        var user2 = User.builder().id("609ecfbab66b6314c06af685").name("George").build();
        List<User> users = new ArrayList<>();
        users.add(user1);
        users.add(user2);

        Mockito.when(repository.findAll()).thenReturn(Flux.fromIterable(users));

        webClient.get()
                .uri("/users")
                .exchange()
                .expectStatus().isOk()
                .expectBody();

        Mockito.verify(repository, times(1)).findAll();
    }
}*/
