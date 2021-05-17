# Currency Conversion API

This Spring WebFlux API provides an endpoint to convert a value from one currency to another. Currencies available for conversion: USD, AUD, CAD, PLN, MXN, BRL, EUR, and JPY.
It consumes another [API](http://api.exchangeratesapi.io) to fetch the conversion rates (base currency: EUR).

Spring WebFlux was chosen as main technology because of its non-blocking aspect which is a good feature for modern applications.

The project itself is divided in three layers: Controllers, Services and Repositories.

## Installation

Excute the following command in the project root:

```bash
mvn spring-boot:run
```

This command will run the project but in the first time of its excecution, it will download all maven dependencies including an embedded Mongodb.

Mongodb is setup to listen to on port 54540 and the Netty server on port 8080.

At the application startup 4 users are created: Paulo, Raul, Emanuel and George.

## Usage

As soon as the application is up and running you can go to the API's Swagger Interface available at http://localhost:8080/swagger-ui/index.html where you can find several endpoints to perform the following actions:

- GET list all users
- GET list all transactions by user id
- GET list conversion rates from Euro to the final currency
- POST conversion endpoind

## Heroku

This application is also available on Heroku in this [link](https://currency-conversion-pg.herokuapp.com/swagger-ui/index.html).

## License
[MIT](https://choosealicense.com/licenses/mit/)