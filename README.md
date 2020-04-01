# Library-Management-System-Java-
An online Library system with end to end functionalities like Checking In/Out a book, Fine Calculation, Sending Reminders and Inventory Management. The JSP page sends the request to the Servlet Controller which is further passed to the relevant Java Bean and the required data is fetched and provided back to the JSP Page.

I made this application as a tabbed panel application because it present a simple design, no more hazels of moving to other panels and clicking the back button every time to step back. This design presents a smooth and clean view making it easy for the user to understand the functions right at the first view. He can traverse to any function of his choice at any point of time by just a click. A status box has been provided in each panel to help the user out. All the text fields are sub string matched and formatted to take in only the right amount of string that the column in the database can handle. This makes the user’s job easy (as compared to the normal where he gets an error message saying wrong string length or something like that). All the tables are click enabled. Meaning, clicking on the row will populate the data fields attached to it. No need to type again.  I have added an undo button and a reset button just to ease out the librarian. Mistakes are usual in any work we do and in a data entry job like this, mistakes are very common and need to be caught. This functionality will help the librarian. But this will just undo one step.

The link is:    http://localhost:8080/Library/index.html
The main page will have 4 options as listed below.
1.	BOOK SEARCH: 
This panel is for searching for a book.

You can search for a book using any combinations of the 3 fields, Book id, Book title or the book author.
Book title and book author are enabled with substring matching.
Once you click the search button, the table will be populated if it finds the results based on the string provided in the text field.

2.	BOOK CHECK OUT: 
This panel is for checking out a book.
 
Click the check-out button to check the book out.
In this you have to provide all the 3 fields mandatorily and you can click the check out button to check it out.

3.	BOOK CHECK IN:
This panel is for checking in a book.

You have 3 options to choose from that will help you reach the book that you want to check in.
You can either enter the book id, the borrowers first name or last name, or the  card no for searching the book. 
Click on the search button to search for the book you are looking to check in and select the appropriate row. 
Verify the fields and click on the check in button to check in the book.

4.	BORROWERS:
This panel deals with adding new borrower.

Enter the data into all the fields, the name, address and the address. Once you are done entering the data, click on the submit button to generate the card number for this user. If there exists already a user with same credentials (first name, last name, address) then the system will cancel your request to add new user. These must be unique for each user. 


5.	FINES: 
This panel manages all the fines related topics.
Enter the card number in the text field and click the submit button to check for fines if any and all the loan related details. 
The PAID table will show the previous fines charged for the card number and the TO BE PAID table will show the fines the corresponding card number has to pay.
If there are any fines, you can pay it off by clicking the PAY button. But this can be done only after checking in the book.


