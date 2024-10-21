
# Subscription Tool Auth
Auth feauture using JWT Security Token.
(Google OAuth 2.0 coming soon)


## Environment Variables

To run this project, you will need to add the following environment variables to your .env file:

DATABASE:

`DB_NAME`

`DB_HOST` *localhost*

`DB_PORT` *5432*

`DB_USERNAME`

`DB_PASSWORD`

EMAIL:

`MAILDEV_HOST` *localhost*

`MAILDEV_SMTP_PORT` *1025*

`MAILDEV_WEB_PORT` *1080*

`MAILDEV_USER`

`MAILDEV_PASSWORD`

`MAILDEV_FROM_ADDRESS`

JWT:

`JSON_KEY` *Base64 Secret Key*

`JSON_KEY_EXPIRATION` *Exp time in seconds*

HOST:

`DOMAIN`

`BACKEND_PORT`

`FRONTEND_PORT`




## Deployment

To deploy this project run:

```bash
  docker compose up
```

