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
  
  
