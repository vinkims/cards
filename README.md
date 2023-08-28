# Cards 

Allows users to create and manage tasks in form of cards

## Deployment

### Prerequisites
- Java 11
- Maven
- MySQL
- Flyway


### Development(local)
- Create a `cards_db` database on MySQL.
- Clone the repository `https://github.com/vinkims/cards.git`.
- cd into the repository
- Once in the root directory, you can either startup the API directly or build it first
##### Direct
- Update the `database` and `flyway` credentials as stated in the `src/main/resources/application.properties` to match your local settings.
- Run the command: `mvn spring-boot:run` to start up the API.
- Once the API is running, you can access the logs in the `logs` directory.
#### Java Build
- Create a copy of the `src/main/resources/application.properties` file and in it set the database and flyway credentials to match your local ones.
- Build the project using the command: `mvn clean package`
- Once the build is completed, run the resulting jar file as stored in the `target` directory. Use the command: `java -jar $jar_path --spring.config.location=$prop_file` where `prop_file` is the path to the application.properties copy you made earlier.

The API documentation can be accessed at `http://localhost:5010/api/v1/swagger-ui/`

## Usage
All requests/endpoints require an authentication token to be passed in the header except for `/auth/signin`

### Pagination
For the paginated endpoints, the following can be specified as URL query parameters:
- pgNum : The page number to be returned `[default: 0]`
- pgSize : Number of elements per page `[default: 10]`
- sortValue : The column/field to be used for sorting/ordering results `[default: dateCreated]`
- sortDirection : Order of results to be returned; can either be *asc* or *desc* `[default: desc]`

Example: `/card?pgNum=2&pgSize=20`

### Filtering/Searching
To filter a list based on resource fileds/columns, the below can be used as URL query parameters.
The query paramater should be in the format: `keyOperatorValue`. These can be chained via commas. Operator can either be:
- EQ - equals
- GT - greater than or equal to
- LT - less than or equal to
- NEQ - not equal to

*Cards*
  - name
  - color
  - description
  - status.id
  - dateCreated
  - user.id

Example: `/card?q=colorEQ#ffffff,dateCreatedGT2023-08-20` -> returns all cards with the color `#ffffff` which were created after `20th August 2023 at 00:00`.


### Cards
To clear out the contents of the color or description field, send a `PATCH` request with the color and/or description fields missing.