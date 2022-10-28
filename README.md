# Translation using Oxford dictionaries API

## Features
* Transalate word Oxford dictionaries API
* Validate input parameters
* Error exception handle
* Save search count of word into MongoDB
* Find words searched before order by search count
* One simple unit test for demo

## Technology
* Reactive REST API webflux
* Functional endpoints
* Oxford dictionaries API

## Endpoints
### API translate word
```
http://localhost:8080/translation?source_lang_translate={sourceLang}&target_lang_translate={targetLang}&word={word}

Example: http://localhost:8080/translation?source_lang_translate=en&target_lang_translate=es&word=test
```
### API get searched words order by search count
```
http://localhost:8080/analyst/popularwords
```
## Time logging for creating this app
1. Research new technology - webflux, functional endpoints: 3 hours
1. Create first endpoints: 2 hours
1. Handle error exception: 2 hours
1. Integrate Oxford API: 2 hours
1. Handle validation input: 2 hours
1. Add mongo and write test: 1 hours