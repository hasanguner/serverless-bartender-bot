# Serverless Bartender Bot
 
![](./img/convs.jpg)

`Serverless Bartender Bot` is a chat-bot which is powered by `Amazon Lex` and built on top of a `Serverless Architecture`.

## Build Project
You can build the project and create a fat jar.
```
./gradlew clean build
```

## Installation & Deployment

![](./img/sls-bartenderbot-drw1.svg)

#### Build Infrastructure

[Serverless Framework](https://serverless.com/) is used to describe the entire infrastructure as code.
Under the hood it is converting our `serverless.yml` file to the `CloudFormation` template.
Checkout `serverless.yml` for infrastructure definitions.

Once you have setup your local environment to use `serverless` and `AWS`, you can deploy the entire infrastructure.

```
sls deploy
```

#### Deploy Lex Bot

1 . Give permission to `Lex` to invoke the lambda function.
```
aws lambda add-permission --region eu-west-1 --function-name serverless-bartenderbot-dev-lexevent --statement-id serverless-bartender-bot --action "lambda:InvokeFunction" --principal "lex.amazonaws.com"
```

2 . Update previously exported bot json file with your own aws account id.
```
sed "s/<aws-account-id>/$(aws sts get-caller-identity | jq -r .Account)/g" BartenderBot_Export.json.template > BartenderBot_Export.json
```

3 . Zip bot json file.
```
zip bot.zip BartenderBot_Export.json
```

4 . Start the bot import job.
```
aws lex-models start-import --region eu-west-1 --payload fileb://bot.zip --resource-type BOT --merge-strategy OVERWRITE_LATEST
```

5 . Import process may take a while. You can check the status of the job.
```
aws lex-models get-import --import-id import-id-from-previous-command --region eu-west-1 
```

6 . Once the import operation succeed, you can build the bot.
> I admit that this part is a bit hacky, but there is no other way to put bot into ready state.

```
aws lex-models get-bot --name Bartender_Bot --version-or-alias "\$LATEST" --region eu-west-1 | jq 'with_entries(select([.key] | inside(["name", "description", "intents", "clarificationPrompt", "abortStatement", "idleSessionTTLInSeconds", "voiceId", "checksum", "processBehavior", "locale", "childDirected", "createVersion"])))' | jq -r > BartenderBot.json
aws lex-models put-bot --region eu-west-1 --name Bartender_Bot --cli-input-json file://BartenderBot.json
```

7 . Check whether the status of the bot is `READY`.

```
aws lex-models create-bot-version --region eu-west-1 --name Bartender_Bot
```

8 . Now, you can test the bot.
```
aws lex-runtime post-text --region eu-west-1 --bot-alias "\$LATEST" --bot-name Bartender_Bot --user-id "$(uuidgen)" --input-text "Hi"
```

You will get the following response.
```json
{
    "sessionAttributes": {},
    "message": "Hi. How can i help you ?",
    "messageFormat": "PlainText",
    "dialogState": "ElicitIntent"
}
```
> There is also an user-interface exists on Amazon Lex Console where you can test your bot.

## Tests

You can run all tests.

```
./gradlew test
```
