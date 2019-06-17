##5GT CTTC MTP implementation

This is the CTTC implementation for 5GT MTP.

## Installation

First of all, install python 3.6 and virtualenv in your host.

In "main" folder (where is located this README):

* create virtual environment:
```
virtualenv -p python3.6 venv
source venv/bin/activate
pip3 install -r requirements.txt 
```
* rename the "pa.properties" file (changing the value of PA):
```
mv pa.properties.ini pa.properties 
``` 
* create "body_input.dat" and "body_output.dat" files:
```
touch body_input.dat body_output.dat
```

In "db" folder, rename mtp_db.db.ini to mtp_db.db
```
mv mtp_db.db.ini mtp_db.db 
```

## Usage
To run the code in "main" folder:

Activate the virtualenv if not:

```
source venv/bin/activate 
```
Then:
```
export PYTHONPATH=$PYTHONPATH:/current-directory/ (pwd)
python nbi/nbi_server.py
```
Once the code is running, you can enter in the GUI putting in the browser:
http://ip_address:8090

Default User: admin (Password: admin) 