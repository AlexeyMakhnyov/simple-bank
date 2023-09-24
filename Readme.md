Design and implement a RESTful API, backing service and data model to create bank accounts
and transfer money between them. Interaction with API will be using HTTP requests.
Requirements
- Accounts are created by supplying a name and four-digit PIN code. Account number is
automatically created.
- Once account is created one can Deposit, Withdraw or Transfer money between accounts.
- Any operation which deducts funds from the account needs to include the correct PIN
code.
- A specific call will fetch all the accounts: the name and their current balance.
- APIs will use JSON payloads when applicable. Appropriate error codes need to be
returned when operations do not succeed.
- Use in-memory database as a backing store.
- Include unit testing for your service layer.
