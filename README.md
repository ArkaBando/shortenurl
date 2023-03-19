
# Shorten Url

Dummy project for shortening url .

Simple spring boot project to shorten and unshorten url it uses base 62 encoding based on counter to encode an url and then saves it in db (H2 db by default can be replaced by mysql for future use). For extracting the full url we are parsing shorten url and getting shorten string from which again we are fetching from database at initial level.



## Authors

- [@arka](https://github.com/Arka-Bandyopadhyay)


## Appendix


For improving performance have added LFU based inmemory cache of guava through which response time is reduced and throughput is increased . 

As we are not using and redis or zookeeper type of services here for simplitity the counter is java based synchronized which is not distributedly synched which can be made be improved furthr using distributed locking or zookeper based ranged indexes.

There is an future plan to incorporate either redis based bloom filter or kafka or inmemory based bloom filter to reduce db hits and improve performance.

## API Reference

#### Postman api Reference

```http
  http://localhost:8080/api/shorten
```

| Req Body | Type     | Description                |
| :-------- | :------- | :------------------------- |
| `{"fullUrl":"https://readme.so/editor"}` | `json` | contains full url for conversion |

#### Get item

```http
  http://localhost:8080/api/C
```

| Parameter | Type     | Description                       |
| :-------- | :------- | :-------------------------------- |
| `{shortenurl or string}`      | `string` | **Required**. for full url conversion from shorturl |

####  toFullUrl(@PathVariable String shortenString) 

Takes an short url and convert to full url

####  saveUrl(@RequestBody FullUrl fullUrl) 

To convert longurl to shorturl


## Documentation

Curl for apis :

### shortening url -
curl --location --request POST 'http://localhost:8080/api/shorten' \
--header 'Content-Type: application/json' \
--data-raw '{"fullUrl":"https://readme.so/editor"}'

### full url from short url -

curl --location --request GET 'http://localhost:8080/api/C'


## Demo

### convert to short url 

![App Screenshot](https://www.linkpicture.com/q/convert1_1.png)

![App Screenshot](https://www.linkpicture.com/q/convert2_1.png)


### invert from short url 

![App Screenshot](https://www.linkpicture.com/q/invert1.png)

![App Screenshot](https://www.linkpicture.com/q/invert2.png)


## Postman api Screenshots

### converting full to short url
![App Screenshot](https://www.linkpicture.com/q/convert10.png)

### converting from short to full url 

![App Screenshot](https://www.linkpicture.com/q/invert3.png)



## Installation

### Requirements

```
Java 1.8
Mvn 3 and above
```

Install shortenurl using mvn

```bash
 mvn clean package
 mvn spring-boot:run
```
    