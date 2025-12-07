## JAVA COMMAND-LINE BANKING APP
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
- **Bank Account System:**
  - Debit card support for mastercard, mastercard titanium, and mastercard platinum.
  - Bank account creation (for registered users only) with an attached debit card.
  - View list of bank accounts and their details (customer can see own only, banker anyone's)
- **Transaction System:**
  - Customer can transact from own accounts only, banker can from anyone's
  - Deposit feature
  - Withdraw feature
  - Transfer feature

## UNRESOLVED ISSUES
- Password reset by user
- Transaction history
- Search transactions
- Overdraft system

## IDEAS FOR IMPROVEMENTS