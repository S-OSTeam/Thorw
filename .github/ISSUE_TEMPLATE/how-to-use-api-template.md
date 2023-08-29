---
name: How-To-Use-API-template
about: Explain how to use api
title: ''
labels: ''
assignees: ''

---

###### API Reference

<details markdown="1">
<summary>[summary]</summary>

**[url]**
----
describtion

* **URL**

  [url]

* **Method:**

  [`POST` | 'GET' | `DELETE`]

* **Request Body**

  `id=[String] - 자원 일련번호`  
  `.`  
  `.`  
  `.`  

* **Success Response:**
```
HTTP/1.1 201 Created
Content-type: application/json;charset=UTF-8
{
  "id": "12345678",
   .
   .
   .
}
```
* **error Response:**
```
HTTP/1.1 401 Unauthorized
{
  "errors": [
    {
      "status": "401",
      "message":  "Unauthorized"
    }
  ]
}
```
</details>

<!--
하나 더 추가 한다면 아래처럼 계속 추가
-->
<details markdown="1" style="margin-left:14px">
<summary>[url]</summary>

**[]**
----
describtion

* **URL**

  [url]

* **Method:**

  [`POST` | 'GET' | `DELETE`]

* **Response**

  **Required:**

  `id=[String] - 자원 일련번호`  
  `.`  
  `.`  
  `.`  

* **Success Response:**
```
HTTP/1.1 201 Created
Content-type: application/json;charset=UTF-8
{
  "id": "12345678",
   .
   .
   .
}
```

* **error Response:**
```
HTTP/1.1 401 Unauthorized
{
  "errors": [
    {
      "status": "401",
      "message":  "Unauthorized"
    }
  ]
}
```
</details>
