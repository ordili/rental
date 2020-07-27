# Project description  
This is a rental car booking API service for customers to reserve a car for a period of time.  
# How to start up the project
You can compile the project through maven, or directly execute the compiled jar package.  
java -jar rental-1.0.0-SNAPSHOT.jar  
the jar file in the directory ./final_jar  
# Using swagger to send request    
swagger url is : http://localhost:8080/swagger-ui.html 
# Using postman to send request
import the ./docs/chenbao.postman_collection.json, the files save all requests.  
# How to rental cars  
send a post request as following：  
POST /rent/cars HTTP/1.1  
Host: localhost:8080  
Content-Type: application/json  
{  
  "borrower": {  
    "name": "ordi"  
  },  
  "rentalOrderDetailList": [  
    {  
      "car": {  
        "carModel": "BMW 650"  
      },  
      "dailyRent": 1,  
      "discount": 1,  
      "fromDay": "2020-07-24",  
      "toDay": "2020-07-25",  
      "orderQuantity": 2  
    }  
  ]  
}  
# How to back cars
send a post request as following：  
POST /back/cars HTTP/1.1  
Host: localhost:8080  
Content-Type: application/json  

{  
  "borrower": {  
    "name": "ordi"  
  },  
  "backOrderDetailList": [  
    {  
      "car": {  
        "carModel": "BMW 650"  
      },  
      "orderQuantity": 2  
    }  
  ]  
}  

# Attention
date format is yy-mm-dd, such as 2020-07-25 is right format.