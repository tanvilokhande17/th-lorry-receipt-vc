# Setting up Database

Create RDS using file rds.sql shared in this folder.

Configure RDS in application properties of all projects using below property keys.
database.mysql.url
database.mysql.username
database.mysql.password

## Build JARs

Run each project using below command to build a JAR
mvn clean install

### Setting up Lambda functions in AWS

Create Lambda functions in AWS given in below list.
Upload the JARs created in 2nd Step to the lambda functions as per given list.
Provide appropriate handler path provided in below list to runtime settings of each lambda function.

Lambda Functions				: Project Name (JAR)			: Handler Path
actionOnLorryReceipt			: ActionOnLorryReceipt			: com.amazonaws.lambda.actionOnLorryReceipt.LambdaFunctionHandler::handleRequest
createLorryReceipt				: CreateLorryReceipt			: com.amazonaws.lambda.createlorryreceipt
getLorryReceipts				: GetLorryReceipts				: com.amazonaws.lambda.getlorryreceipts
getLorryReceiptsVerification	: GetLorryReceiptsVerification	: com.amazonaws.lambda.getlorryreceiptverification
shareLorryReceiptVerification	: ShareLorryReceiptVerification	: com.amazonaws.lambda.shareOnLorryReceiptVerification.LambdaFunctionHandler::handleRequest
signIn							: SignIn						: com.amazonaws.lambda.signin
signUp							: SignUp						: com.amazonaws.lambda.signup

### `Setting up API Gateway in AWS

Import API Gateway to AWS using LorryReceipt-dev-oas30-apigateway.json file provided in this folder.
Configure endpoints to route to the Lambda functions created, as given in belowlist.

Endpoints								: Lambda Functions
/user/signup [POST]						: signUp
/user/signin [POST]						: signIn
/lorryreceipt [POST]					: createLorryReceipt
/lorryreceipt [GET]						: getLorryReceipts
/lorryreceipt/action [POST]				: actionOnLorryReceipt
/lorryreceipt/verification [POST]		: shareLorryReceiptVerification
/lorryreceipt/verification/{passkey}	: getLorryReceiptsVerification