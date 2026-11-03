# Login Suite

## TC001 - Log in and log out with valid username and valid password
Tags: login, smoke, regression

* Log in with username "alice" and password "mypassword"
* Verify user is logged in as "alice"
* Tap logout button
* Verify user is logged out

## TC002 - Log in with valid username and wrong password
Tags: login, negative, regression

* Log in with username "alice" and password "wrongpass"
* Verify error message "Invalid login credentials, please try again" is displayed

## TC003 - Log in with wrong username and valid password
Tags: login, negative, regression

* Log in with username "bob" and password "mypassword"
* Verify error message "Invalid login credentials, please try again" is displayed

## TC004 - Log in with wrong username and wrong password
Tags: login, negative, regression

* Log in with username "bob" and password "wrongpass"
* Verify error message "Invalid login credentials, please try again" is displayed
