## CMD-Bank
Your bank at your fingertips.

## PROJECT BOARD
https://github.com/users/falansari/projects/11/views/1

## USER STORIES

- As a bank employee, I want to create accounts to onboard new customers.
- As a bank employee, I want to retrieve the information of an existing customer to serve them better.
- As a user, I should be able to interact securely with my bank accounts through a command-line interface. 
- As a user, I want to view a history of all transactions to track my spending and deposits.
- As a user, I want to filter my transaction history to only view transactions from certain days. 
- (Extra) As a user, I want to be warned of an overdraft risk before I commit to a withdrawal, so I don't accidentally overdraft my account.
- As a user, I want to be able to set my own account's password to make it secure.
- As a user, I want to withdraw money from my account.
- As a user, I want to deposit money from my account.
- As a user, I want to transfer money between my accounts or to other bank account holders.
- As a user, I want to deposit money into my deactivated account to pay the overdraft fees and gain access back into the account.

## ENTITY RELATIONSHIP DIAGRAM (ERD)
![ERD.jpg](ERD.jpg)

## TECHNOLOGIES

- JAVA LTS v17
- IntelliJ IDEA Community Edition IDE
- Mentorship from Copilot (help get unstuck with bugs and logical errors).
- Pen and paper :)

## IMPLEMENTED FEATURES
- **User Authentication System:**
  - Account Registration (by banker)
  - User accounts data file
  - Password hashing
  - User authentication
  - Password reset
  - Login Fraud Detection: If there are 3 failed login attempts lock the account for 1 min, before trying again with the login information.
- **Bank Account System:**
  - Debit card support for mastercard, mastercard titanium, and mastercard platinum.
  - Bank account creation (for registered users only) with an attached debit card.
  - View list of bank accounts and their details (customer can see own only, banker anyone's)
- **Transaction System:**
  - Customer can transact from own accounts only, banker can from anyone's
  - Deposit feature
  - Withdraw feature
  - Transfer feature
  - Transaction history records
- **Filtering transactions** User can query the program and get transactions with certain conditions (e.g. today, yesterday, last week, last 7 days, last month, last 30 days and filtering on the basics of date and time).
- **Overdraft Protection (requires login)**
    - Charge an ACME overdraft protection fee of $35 when overdrafting.
    - Prevent withdrawing more than $100 if the account balance is negative.
    - Deactivate the account after 2 overdrafts; reactivate if the customer resolves the negative balance and pays the overdraft fees.
- Implemented ability to do as many operations as the user wants before deciding when to exit the system.

## UNRESOLVED ISSUES
- Overdraft count should be reset with a transfer from another account also not only deposit.

## IDEAS FOR IMPROVEMENTS
- PDF statement print out
- Add ability to prematurely end any ongoing operation and go back to the main menu without completing the operation.
- Encrypt all sensitive user and accounts data, not only passwords to prevent data tampering.