# 5GT-MTP
This repository contains the code for the Mobile Transport and Computing Platform (MTP) developed in the 5G-Transformer EU project.
It is the second release (see [Changelog](CHANGELOG.md) for detailed supported feature list) and it is the reference code for Proof Of Concept Demo of the project 
## Repository Organization
The repository is divided in three folders:

### 1. MTP 
This folder contains the code of the MTP core. Please refer to the [MTP README](mtp/README.md) for instruction details

### 2. java-client-generated
This folder contains a java REST client library (client-sbi) that MTP core uses to communicate with the plugins. It is generated automatically via [swagger codegen](https://editor.swagger.io/) 

### 3. IFA005
This folder contains the MTP plugins and the json files used for generate the external interfaces for MTP core (both generated via [swagger codegen](https://editor.swagger.io/) ) Please refer to the [IFA005 README](IFA005/README.md) for instruction details.

### 4. MonitoringAPI
This folder contains the Monitoring API that MTP uses for communicate with the 5G-T Monitoring Platform. it includes the json files and the java REST client library used by MTP core (both generated via  [swagger codegen])

### 5. MTP_API_PA_Alg
This folder contains the MTP local placement algorithms, the json file used by algorithms to communicate with MTP core and the java REST client used by MTP core (both generated via [swagger codegen]). Please refer to the [PA README](MTP_API_PA_Alg/README.md) for instruction details

### 6. Postman_collections
This folder contains a json [POSTMAN]() collection to be use for testing the MTP core.