## APIs

### Authentication

`Authorization: Bearer {api-key}`

### Create a new user

`POST api/v1/users`

Body

```
{
  "username" : "username",          // optional. if not provided a unique username will be generated. if provided it must be unique
  "externalId" : "external-id",     // optional. if provided it must be unique
  "firstName" : "firstname",        // optional
  "lastName" : "lastname",          // optional
  "email" : "email@domain.com",     // required
  "manager" : "manager",            // optional
  "enabled" : true,                 // optional, defaults to true
  "forcePasswordReset" : false,     // optional, defaults to false
  "leaderboards" : true,            // optional, defaults to true
  "admin" : false,                  // optional, defaults to false
  "password" : "password",          // optional, a random password will be generated if not provided.  if you are using sso just leave this blank
  "customFields" : {                // optional
    "key" : "value"
  },
  "activeUntil" : 1646262163        // optional. seconds since epoch that user will automatically be disabled globally
}
```

`201` if successful, returns the user record that was created

```json
{
  "id" : "users_2cd86a01-bf51-4cd2-9331-0e06c06b7910.c8ec112b-4047-49f4-a116-b100a6f350a3",
  "externalId" : "example-external-id461",
  "username" : "jane.doe461",
  "firstName" : "Jane",
  "lastName" : "Doe",
  "email" : "jane.doe461@example.com",
  "photo" : null,
  "enabled" : true,
  "forcePasswordReset" : false,
  "systemCreationDate" : 1644966165,
  "siteLastAccessDate" : null,
  "activeUntil" : null,
  "customFields" : {
    "custom-field-city" : null,
    "custom-field-country" : null,
    "custom-field-state" : null
  },
  "manager" : null
}
```


`409` if there is a conflict, inspect message for details

username conflict example: the returned user is the user that conflict is with.

```json
{
  "message": "username 'jane.doe493' already exists and must be unique",
  "user": {
    "id": "users_9ac3bce5-176f-41e4-aa30-83d2c388842d.c8ec112b-4047-49f4-a116-b100a6f350a3",
    "externalId": "example-external-id493",
    "username": "jane.doe493",
    "firstName": "Jane",
    "lastName": "Doe",
    "email": "jane.doe493@example.com",
    "photo": null,
    "enabled": true,
    "forcePasswordReset": false,
    "systemCreationDate": 1644970805,
    "siteLastAccessDate": null,
    "activeUntil": null,
    "customFields": {
      "custom-field-city": null,
      "custom-field-country": null,
      "custom-field-state": null
    },
    "manager": null
  }
}
```

externalId conflict example: the returned user is the user that the conflict is with.

```json
{
  "message": "externalId 'example-external-id493' already exists and must be unique",
  "user": {
    "id": "users_9ac3bce5-176f-41e4-aa30-83d2c388842d.c8ec112b-4047-49f4-a116-b100a6f350a3",
    "externalId": "example-external-id493",
    "username": "jane.doe493",
    "firstName": "Jane",
    "lastName": "Doe",
    "email": "jane.doe493@example.com",
    "photo": null,
    "enabled": true,
    "forcePasswordReset": false,
    "systemCreationDate": 1644970805,
    "siteLastAccessDate": null,
    "activeUntil": null,
    "customFields": {
      "custom-field-city": null,
      "custom-field-country": null,
      "custom-field-state": null
    },
    "manager": null
  }
}
```

In both the above examples you can check the `enabled` field to see if there is a problem because the user was globally disabled.

`400` for other errors

### Enroll a learner in a class

`PUT api/v1/openapi/classes/{product-code}/users/{external-id}`

`404` `{product-code}` or `{external-id}` not found

`204` the user is already enrolled

`400` other errors