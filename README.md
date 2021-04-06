# Asset Sentiment Analysis Platform (ASAP)

## Project Description

The Asset Sentiment Analysis Platform, or ASAP, is an application designed to allow users the ability to track and follow various financial assets (stocks, cryptocurrencies, etc.). Assets can be added to a user's watchlist, which will allow for them to receive news and price updates on their dashboard. Additionally, users are able to perform online sentiment analysis on a chosen asset that will query social media APIs (such as Reddit and Twitter) for mentions of an asset or its symbol. The content of online posts about the asset can be given a bullish, bearish, or neutral rating based upon the analyzed sentiment, which is performed by AWS Comprehend. This will allow for users to be able to stay on top of retail investor sentiment for their favorite assets to aid them in anticipating market moves.

Link to front-end repository ASAP-UI: https://github.com/revature-asap/asap-ui

## Technologies Used

Application Tier:
  - Language: Java version 8
  - Framework: Spring version 5
  
Persistence Tier:
  - Database Vendor: PostGreSQL 
  
DevOps Tools:
  - Pipeline: AWS CodePipeline
  - Build Server: AWS CodeBuild
  - Deployment: AWS Elastic Container Service
  - Containerization: Docker

## Features

* Register for a new account.
* Confirm my account by email.
* Login in using existing account credentials.
* View the latest news stories on popular assets.
* View the latest news stories on assets I am following.
* View basic information regarding an asset.
* See various price charts (line and candlestick) at different time scales (e.g. 5m, 15m, 30m, 1h, 4h, 6h, 1d).
* See news sentiment regarding an asset.
* See social media sentiment regarding an asset.
* See technical indicators regarding an asset.
* Perform technical/trend analysis using interactive drawing tools on charts.
* Create and share content with other users regarding an asset.
* Comment on shared content provided by other users.

## Getting Started

First clone the repo to your local machine

- `git clone https://github.com/revature-asap/ASAP.git`

After the repo is cloned, you will need to have the following environment variables on your machine with corresponding values to your accounts across the sites leveraged

- db_url : the url of the database you are connecting to
- db_username : the username used to log into the database you are connecting to
- db_password : the password used for logging into the database you are connecting to
- AWS_ACCESS_KEY_ID : the access key for AWS Comprehend
- AWS_SECRET_ACCESS_KEY : the secret access key for AWS Comprehend
- email_username : the username of the email used for sending confirmation emails to newly registered users
- email_password : the password to the email used for sending confirmation emails to newly registered users
- reddit_public : the public key for hitting the Reddit API
- reddit_private : the private key for hitting the Reddit API
- reddit_username : the username for the reddit account used for accessing the Reddit API
- reddit_password : the password for the reddit account used for accessing the Reddit API
- twitter_bearer_token : the Twitter API token

When all the appropriate environment variables are set, package up the project into a jar

- `mvn package`

You will need to install docker to your machine and have it running in order to create the docker image. The following docker command is run in the root package where the dockerfile is:

```bash
docker build -t asap:test . \
--build-arg DB_URL=$db_url \
--build-arg DB_USERNAME=$db_username \
--build-arg DB_PASSWORD=$db_password \
--build-arg AWS_ACCESS_KEY_ID=$AWS_ACCESS_KEY_ID \
--build-arg AWS_SECRET_ACCESS_KEY=$AWS_SECRET_ACCESS_KEY \
--build-arg EMAIL_USERNAME=$email_username \
--build-arg EMAIL_PASSWORD=$email_password \
--build-arg REDDIT_PUBLIC=$reddit_public \
--build-arg REDDIT_PRIVATE=$reddit_private \
--build-arg REDDIT_USERNAME=$reddit_username \
--build-arg REDDIT_PASSWORD=$reddit_password \
--build-arg TWITTER_BEARER_TOKEN=$twitter_bearer_token
```

Once the docker image has been built, you can run the docker image to have the project running locally on your machine with the following command

- `docker run -d asap:test`

In order to find the IP address that the program is running on your machine with, run the follwoing command

- `docker inspect --format '{{ .NetworkSettings.IPAddress }}' <name of Docker app>`

Go to the ip address printed at port 5000 to see the application running

## Usage

Once you have the project running locally, you can use our API documentation to explore our available endpoints. To access the following endpoints, prepend the endpoint with both the IP address your docker container is running on, and `:5000` to hit the container on the correct port.

- API documentation: `/swagger-ui/index.html` AND `/`
  - Explore our endpoints and get familiar with what they accept and can do.
- Asset endpoint: `/asset`
  - Retrieve an asset from the connected database, or attempt to pull a new asset from Finnhub API or LunarCrush API.
  - Use the stock/cryptocurrency ticker symbol as the value for the query parameter `ticker`
  - Example call: `/asset?ticker=doge`
- Posts endpoint: `/posts`
  - Endpoint for making, modifying, creating and removing post objects in the connected database.
- Reddit endpoint: `/reddit`
  - Retrieve sentiment analysis for up to 45 recent reddit posts from a select number of subreddits (e.g. r/stocks, r/wallstreetbets and r/investing) based on a search term provided
  - Example call: `/reddit?asset=bitcoin`
- Twitter endpoint: `/twitter`
  - Retrieve sentiment analysis for up to 100 recent (English) tweets from verified users based on a search term provided
  - Example call: `/reddit?asset=aapl`
- User endpoint: `/users`
  - Provides information about users already created in the connected database, authenticates existing users and confirms registration of new users.
  - Must be logged in as an Admin to use the endpoint to get all users.
  - Newly created users will have their role set to `Basic`

## Contributors

- Gregory Gertson (Gerts19)
- Alex Googe (darkspearrai)
- Christopher Nichols (return5)
- Cole Space (vanikin3)
- Calvin Zheng (ZGCalvin)
- Tuan Mai (blackiceiceo)
- Gabrielle Luna (GabMoon)
- Eric Newman (enewmanMN)
- Daniel Skwarcha (Daniel-Skwarcha)
- Jonathan Norman (jaynorman1920)
- Kalyb Levesque (kml160030)
- Briant Withrow (Brian-Revature)
- Brandon Johnson (BrandonJohnson777)
- Nathan Gamble (NateGamble)
