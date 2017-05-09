
Genaral step:
* Obtain token
* Do requests

## Authorization

Request:
```bash
curl -u <your username> -d '{"note":"<any note>"}' 'https://api.github.com/authorizations'
```

You will be promted to enter your password.

Response:
```json
{
  "id": 12345678,
  "url": "https://api.github.com/authorizations/12345678",
  "app": {
    "name": "<any note>",
    "url": "https://developer.github.com/v3/oauth_authorizations/",
    "client_id": "00000000000000000000"
  },
  "token": "this is the token you will use for your requests",
  "hashed_token": "xxx",
  "token_last_eight": "xxx",
  "note": "123",
  "note_url": null,
  "created_at": "2017-05-09T14:05:34Z",
  "updated_at": "2017-05-09T14:05:34Z",
  "scopes": [

  ],
  "fingerprint": null
}

```


[Github docs](https://developer.github.com/v3/oauth_authorizations/#create-a-new-authorization)

## Get users

Request:
```bash
curl -H "Authorization: token <your token>" https://api.github.com/users/benburkert
```



Response:
```json
{
  "login": "benburkert",
  "id": 77,
  "avatar_url": "https://avatars3.githubusercontent.com/u/77?v=3",
  "gravatar_id": "",
  "url": "https://api.github.com/users/benburkert",
  "html_url": "https://github.com/benburkert",
  "followers_url": "https://api.github.com/users/benburkert/followers",
  "following_url": "https://api.github.com/users/benburkert/following{/other_user}",
  "gists_url": "https://api.github.com/users/benburkert/gists{/gist_id}",
  "starred_url": "https://api.github.com/users/benburkert/starred{/owner}{/repo}",
  "subscriptions_url": "https://api.github.com/users/benburkert/subscriptions",
  "organizations_url": "https://api.github.com/users/benburkert/orgs",
  "repos_url": "https://api.github.com/users/benburkert/repos",
  "events_url": "https://api.github.com/users/benburkert/events{/privacy}",
  "received_events_url": "https://api.github.com/users/benburkert/received_events",
  "type": "User",
  "site_admin": false,
  "name": "Ben Burkert",
  "company": null,
  "blog": "http://benburkert.com",
  "location": "SF",
  "email": "ben@benburkert.com",
  "hireable": null,
  "bio": null,
  "public_repos": 123,
  "public_gists": 35,
  "followers": 151,
  "following": 7,
  "created_at": "2008-01-28T23:44:14Z",
  "updated_at": "2017-04-25T21:00:13Z"
}
```
[Github docs](https://developer.github.com/v3/users/)


## Rate limit

Request:
```bash
curl -H "Authorization: token <your token>" https://api.github.com/rate_limit
```

Response:
```json
{
  "resources": {
    "core": {
      "limit": 5000,
      "remaining": 4995,
      "reset": 1494360270
    },
    "search": {
      "limit": 30,
      "remaining": 30,
      "reset": 1494359617
    },
    "graphql": {
      "limit": 200,
      "remaining": 200,
      "reset": 1494363157
    }
  },
  "rate": {
    "limit": 5000,
    "remaining": 4995,
    "reset": 1494360270
  }
}
```


[See too](https://developer.github.com/guides/traversing-with-pagination/)

