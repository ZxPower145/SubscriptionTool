
# Subscription Tool Auth
Auth feauture using JWT Security Token / Google AUTH.


## Environment Variables

To run this project, you will need to add the following environment variables to your .env file:

APPLICATION:

`DOMAIN`

`BACKEND_PORT`

`FRONTEND_PORT`

DATABASE:

`DB_NAME`

`DB_HOST` *postgres*

`DB_PORT` *5432*

`DB_USERNAME`

`DB_PASSWORD`

EMAIL:

`MAILDEV_HOST` *mail-dev*

`MAILDEV_SMTP_PORT` *1025*

`MAILDEV_WEB_PORT` *1080*

`MAILDEV_USER`

`MAILDEV_PASSWORD`

`MAILDEV_FROM_ADDRESS`

JWT:

`JSON_KEY` *Base64 Secret Key*

`JSON_KEY_EXPIRATION` *Exp time in seconds*

OAUTH

`OAUTH_CLIENT_ID`

`OAUTH_CLIENT_SECRET`

`OAUTH_CLIENT_NAME`




## Deployment

To deploy this project run:

```bash
  docker compose up --build
```

