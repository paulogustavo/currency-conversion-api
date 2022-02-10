# Currency Conversion API

This Spring WebFlux API allows you, among some other things, to convert any amount from one currency into another.

## Getting started
### Prerequisites

- Java 11
- Maven 

### Installation

Execute the following command in the project's root directory:

```bash
mvn spring-boot:run
```

The above command runs the project but in the first execution, it will download all maven dependencies including an embedded Mongodb in order to save the conversion transactions.

Mongodb is setup to listen to on port 54540 and the Netty server on port 8080.

## Usage

As soon as the application is up and running you can go to the API's Swagger Interface available at http://localhost:8080/swagger-ui/index.html where you can find several endpoints to perform the following actions:

- execute currency conversion itself [POST]
- fetch all users (created at startup) [GET]
- fetch all conversion transactions by user id [GET]
- fetch conversion rates from Euro to the final currency (external api) [GET]

Currencies available for conversion: USD, AUD, CAD, PLN, MXN, BRL, EUR, and JPY.

The conversion rates are externally fetched from (http://api.exchangeratesapi.io).

## Heroku

The application is also available on Heroku on this [link](https://currency-conversion-pg.herokuapp.com/swagger-ui/index.html).

## License
[MIT](https://choosealicense.com/licenses/mit/)