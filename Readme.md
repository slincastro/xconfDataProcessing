# xconfDataProcessing

## Install Mongo
Step 1
```
docker run -it -p 27017:27017 mongo
```
Step 2 (In another terminal)
```
docker ps
```
Select your mongo container id
```
docker exec -it <container id> /bin/bash
```
(This opens a terminal inside container)
```
mongo

use test
```

## Install Anaconda environment to use Jupyter

Open another terminal
```
docker run -i -t -p 9292:9292 continuumio/anaconda3 /bin/bash -c "/opt/conda/bin/conda install jupyter -y --quiet && /opt/conda/bin/conda install -c anaconda pandas -y --quiet && /opt/conda/bin/conda install -c anaconda pymongo -y --quiet && mkdir /opt/notebooks && /opt/conda/bin/jupyter notebook --notebook-dir=/opt/notebooks --ip='*' --port=9292 --no-browser --allow-root"
```

Please copy the url to see Jupyter notebooks. Example:
```
http://127.0.0.1:9292/?token=abaca888ea32e1e098e74e7cb306200a8984ed2741ac4c77
```

## Configure Python Notebook

Create a notebook in the Jupyter Tab
Copy the code in script.py file to the notebook

Replace the mongodb host for the ip matching your docker container in your machine. Use this command

```
docker inspect -f '{{range .NetworkSettings.Networks}}{{.IPAddress}}{{end}}' container_name_or_id
```
