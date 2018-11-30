# 5GT-MTP
This repository contains the code for the Mobile Transport and Computing Platform (MTP) developed in the 5G-Transformer EU project.

## Repository Organization
The repository is divided in three folders:

### 1. MTP 
This folder contains the code of the MTP core. Please refer to the [MTP README](mtp/README.md) for instruction details

### 2. java-client-generated
This folder is a java REST client that MTP core uses to communicate with the plugins. It is generated automatically via [swagger codegen](https://editor.swagger.io/) 

### 3. IFA005
This folder contains the MTP plugins and the json files used for generate the external interfaces for MTP core (both generated via [swagger codegen](https://editor.swagger.io/) ) Please refer to the [IFA005 README](IFA005/README.md) for instruction details.
