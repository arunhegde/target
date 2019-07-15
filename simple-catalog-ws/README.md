# Simple Catalog service

A simple microservice written in springboot that provides RESTFul APIs to manage a simple product catalog.


### Prerequisites

Java1.8
maven

### Design
We have the following entities

##### Category
Represents a product category or a discount category. A category can be a super category (root) or a sub category of a category. 
A category belongs to one parent but can have sub categories in a hierarchical structure.  For super categories parent is null.
		
###### 	Item
Represents a SKU (item). Items are leaf nodes in the category hierarchy. A category can have zero or more items. But an Item can not have any child nodes.
An item can have multiple categories (parent nodes).

###### 	Discount
###### 	Catalog


### Installing

Clone simple-catalog-ws and goto the root folder
Build using ```./mvnw clean package```
Run using ```java -jar target/simple-catalog-ws-0.0.1-SNAPSHOT.jar```

If you want to load sample data while the service starts you can pass loadTestDb arguement as follows
```java -jar target/simple-catalog-ws-0.0.1-SNAPSHOT.jar testMode```

### REST APIs supported
The webservice runs on port ```8083```  (configurable)
```http://localhost:8083/catalogws``` is the root of webservice.
#### List catalog
Lists all catalog items (i.e. scatagories that are marked as supercategory appear here automatically)
*API Path*: ```GET /catalogws/catalog```

*Sample output*: 
```
[{"code":"cat-baby","label":"Baby","description":"Baby products"}]
```

#### Add category
*API Path*: ```POST /catalogws/category```
*Sample Request Body*:
```
{
  "type": "category",
  "label": "Sports",
  "description": "Sports products",
  "superCategory": true
}
```

*Sample output*: 
```
{
  "type": "category",
  "id": "5e4200fa-de59-46ca-85fe-7f3b51eeef1f",
  "code": "cat-sports",
  "label": "Sports",
  "description": "Sports products",
  "discount": null,
  "children": null,
  "superCategory": true,
  "path": "/cat-sports"
}
```

#### Update category
*API Path*: ```PUT /catalogws/category/<id>```
Updates an existing category (To be added)


#### Delete category 
Deletes a category (To be added)
*API Path*: ```DELETE /catalogws/category/<id>```
Deletes an existing category and all its children (To be added)

#### List category
Lists all categories
*API Path*: ```GET /catalogws/category```

#### Search category
Supports text search as well as filtering by category code

#### Search by query
*API Path*: ```GET /catalogws/category?q=Boy```


#### Filter by category code
*API Path*: ```GET /catalogws/category?code=cat-boys```

#### Get category by Id
*API Path*: ```GET /catalogws/category/<id>```


#### Add item
*API Path*: ```POST /catalogws/item```
*Sample Request Body*:
```
{
  "type": "item",
  "label": "Baby Soap",
  "description": "Baby soap product"
}
```


#### Update item
*API Path*: ```PUT /catalogws/item/<id>```
Updates an existing item (To be added)


#### Delete item 
Deletes a item (To be added)
*API Path*: ```DELETE /catalogws/item/<id>```
Deletes an existing item 

#### List item
Lists all items
*API Path*: ```GET /catalogws/item```

#### Search item
Supports text search as well as filtering by item code
#### Search by query
*API Path*: ```GET /catalogws/item?q=car```


#### Filter by item code
*API Path*: ```GET /catalogws/item?code=sku-battery-toy-car```

#### Get item by Id
*API Path*: ```GET /catalogws/item/<id>```

#### Resources
* [Spring Boot](https://spring.io/projects/spring-boot) - The microservcies framework used
* [Maven](https://maven.apache.org/) - Dependency Management
* [Nitrite Database](https://www.dizitart.org/nitrite-database/) - In memory dataabase used

## Authors
* **Arun Kumar D** 


