# JPA Column Encryption Demo
![Java CI with Maven](https://github.com/asaikali/jpa-column-encryption/workflows/Java%20CI%20with%20Maven/badge.svg)

**Warning: Security code in this sample comes with NO Gurantees or Warranties  use at your 
own risk. This is an educational sample meant to clearly explain a software architecture / security
pattern. You are responsible for the security of whatever code you derive based on this sample. 
If you find any issues or have feedback please open an issue or send a pull request.**

This sample demonstrates how to encrypt sensitive column values with Spring Data JPA. Sample
features.

* Column encryption using AES-256 in GCM mode which is a very secure operating mode for AES
* Column are encrypted just before the data is saved to the database
* Column values are decryted just before the objects are passed to the  application  
* Each column / table can use a different key for encryption 
* Data encryption keys are stored in the application database use a key encryption key using AES 
  256 in GCM mode 
* Key encryption key is injected into the running application from the platfrom  it is running on.
  For example via kubernetes secret map, or Vault or  other key storage service. The demo app
  is using a config file to make the sample simple do NOT do this in production use a key storage
  service.
* Pattern enables rotation of the key encryption key and data encryption keys. Rotation is possible
  but not fully implemented in the sample. 
  
# Running the Sample Application 

Three steps to run the application 
* run postgres via `docker-compose up`
* import the app in your favourite IDE
* Run Spring Boot app at `com.example.app.DemoApplication`

The application uses docker-compose for the postgres database just type `docker-compose up` to 
get a local postgres that you can use. See [Docker Compose Postgres](https://github.com/asaikali/docker-compose-postgres) 
for details how the docker-compose setup works. Postgres will run on post `25432` and pgAdmin on 
`25433`

# Examine Patient Demo 

In the `app` project inspect the code in the `com.example.app.patient` package pay attention 
to how the `PatientEntity` uses `SensitiveStringValue` to handle storage of the social insurance
number.  

# Test the application 

Once the application is running start make some API requests and  inspect the database to see
what's going on.

```bash
curl --location --request GET 'localhost:8080/'
```

You should get back an empty array with no content in it, so lets do a post to  add patient

```bash
curl --location --request POST 'localhost:8080/patients' \
--header 'Content-Type: application/json' \
--data-raw '{
    "name": "Adib3",
    "sin": "123-123-123"
}'
```

You will  get back a JSON response like the one below an ID set ot a random UUID. 

```json
{
    "id": "4ad3a3e0-bd45-4c9d-93db-d7314d6c1a36",
    "name": "Adib3",
    "sin": "123-123-123"
}
```

Now lets go check what the database has in it. Open a browser and visit pgAdmin at 
`http://localhost:25433` browse to the `patients` table in the `demo` database you 
will be able to see all db rows and notice that the sin column looks something 
the values below

```
id,name,sin
af371d65-fd0c-4c27-8016-2602091c53f7,Adib,99846d4a-1803-44af-aa00-eac07cdf6f17:XYB84AuY9IX98V1j0RUL_pFIE4pGRgiAKHXRc3v8UckFcYYCABehAMII2A==
4ad3a3e0-bd45-4c9d-93db-d7314d6c1a36,Adib3,99846d4a-1803-44af-aa00-eac07cdf6f17:xnV4mq3UpNBtHxvR_OJoRikQ7fV3KDBdmnAeCuR4iMDHgtPUxh5IK2o3RA==
```

notice that the SIN column has a structure `keyId:cipherText` for example
`99846d4a-1803-44af-aa00-eac07cdf6f17:XYB84AuY9IX98V1j0RUL_pFIE4pGRgiAKHXRc3v8UckFcYYCABehAMII2A==`
the KeyId used to encrypt the column is `99846d4a-1803-44af-aa00-eac07cdf6f17`.Inspect the 
`keyservice_keys` table you will find a row that matches the keyId.

```
id,current,name,value
99846d4a-1803-44af-aa00-eac07cdf6f17,true,com.example.app.patient.PatientEntity,44302a6dcb68f8eb26afb772a78cf869d606b343c83023857d47b301f48f0538dcb32f955815033e58017eeb3a56825b3270eb973881627f3b1277c93521a080e0d685751f6d000a2149b3b6aeb0d954f54cb12bc9a6c30d79975e905fe029612b6653a7e8516a55eec10a28e6da3f58d8fc6f637ff28e1848cb23324341cb2e24317bfb8258b3cb35264c9e41
```

Keys in the `keyservice_keys` table are encrypted using AES-256 GCM using a password and salt
that you find in the in the `application.yml`

```yaml
keyservice:
  password: "6416a9ca-4e94-4157-a016-1add5cfdca5c"
  salt: "5c0744940b5c369b"
```

In a production setting the key encryption key should be in a key vault accessible to the application
via the platform it's deployed on such as CloudFoundry or Kubernetes.

# Limits

The code  in this sample is  not optimized for performance, rather it is optimized to make it easy
to understand the pattern. There are much optimization opportunities to improve performance and
ergonomics of using the API.  


