# Virtual power system
The project allows you to add the battery information and list out the batteries according to the postcode

+ Technologies used
+ Spring boot 2.7.2+
+ Java 1.8+
+ Maven

## Running application
You can run the application on you local machine by executing com.pkumal.virtualpower.VirtualPowerApplication from your IDE.

You can also run the application with command mvnw srping-boot:run
from the project root folder from command line

Once you run the project the api is available at url "http://localhost:8080/virtualpower/api/v1". 

## Working with API

You can do it via browser or postman client.
Supported functionalities :

+ Add List of Batteries - POST request
	+ The api allows to save batteries. It takes list of batteries in JSON body.

Request url : "http://localhost:8080/virtualpower/api/v1/addBatteries"

Sample request Body:

```
[
    {
        "name": "batteryName1",
        "postCode": "postcode1",
        "wattCapacity":wattCapacity1
    },
       {
        "name": "batteryName2",
        "postCode": "postcode2",
        "wattCapacity":wattCapacit2
    }
]

```
+ Get List of Batteries for given Post Code Range - GET Request.
	+ The api takes two postcodes and returns the list of batteries falling within those postcodes.

Request url : "http://localhost:8080/virtualpower/api/v1/getBatteriesByPostCodes?from={postCodeFrom}&to={postCodeTo}".

Sample Response Body:

```
{
    "batteryNames": [
        "batteryName1",
        "BatteryName2",
        ...
    ],
    "totalWatts": 1000,
    "averageWatts": 345.784747
}
```

######NOTE : Keep the request content type in application/json.