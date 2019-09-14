import pymongo
import pandas as pd
client = pymongo.MongoClient('mongodb://172.17.0.2:27017')
tweetCollection = client.test.tweets
filteredCollectionStandDatos = tweetCollection.find({ "hashtags.hashtag": { "$in": ["StandDatos"] } }, { "hashtags": 1, "_id": 0 })
flattenCollectionStandDatos = filteredCollectionStandDatos.distinct("hashtags")
dataStandDatos = pd.DataFrame(flattenCollectionStandDatos)
visitantesStandDatos = dataStandDatos['user'].unique().size

filteredCollectionStandMobile = tweetCollection.find({ "hashtags.hashtag": { "$in": ["StandMobile"] } }, { "hashtags": 1, "_id": 0 })
flattenCollectionStandMobile = filteredCollectionStandMobile.distinct("hashtags")
dataStandMobile = pd.DataFrame(flattenCollectionStandMobile)
visitantesStandMobile = dataStandMobile['user'].unique().size

filteredCollectionStandPlataformas = tweetCollection.find({ "hashtags.hashtag": { "$in": ["StandPlataformas"] } }, { "hashtags": 1, "_id": 0 })
flattenCollectionStandPlataformas = filteredCollectionStandPlataformas.distinct("hashtags")
dataStandPlataformas = pd.DataFrame(flattenCollectionStandPlataformas)
visitantesStandPlataformas = dataStandPlataformas['user'].unique().size


standDatos = [visitantesStandDatos]
standMobile = [visitantesStandMobile]
standPlataformas = [visitantesStandPlataformas]
dataFrame = pd.DataFrame({'Stand Datos': standDatos, 'Stand Mobile': standMobile,'Stand Plataformas': visitantesStandPlataformas}, index=['Tweets'])
graphic = dataFrame.plot.bar(rot=0)