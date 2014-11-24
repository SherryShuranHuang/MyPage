
MyPage
======
This program is to list the movies showing in theaters and calculate the average age of the castfor each movie.<br/>
The web I used to access the data is http://www.imdb.com/. 
And this program can only be used to access the required data with the input of http://www.imdb.com/showtimes/location?ref_=inth_ov_sh_sm.
* GetPageMain.java is the file to run.<br/>
* PageDownLoader.java is the class to download the source code of the given url.<br/>
* HtmlParser.java is the class with the functions to extract the content we want.<br/>
* LinkFilter.java is an interface. (Actually I do not need to use it)<br/>
* LinkDB.java defines the datastructure and functions to visit urls. <br/>
* SqlDB.java has the functions to create and edit database with SQLite.JDBC lib.<br/>

To run this program, open the project with eclipse. Open SqlDB.class and change the two
"E:/eclipseWorkforJava/MyPage/db/movieslist_new.db" in "connection = DriverManager.getConnection(...)" to the path and file name you want to store
the DB file. <br/>
Make sure you add all the lib files in the include folder. Run!<br/>
After the process finish, you can find the movieslist_new.db in the db folder. Open it witn any SQL manager, then you can
check the data.

Or with the SQLite commands:<br/>
.headers on<br/>
.mode csv<br/>
.output result.csv<br/>
.select * from movietbl;<br/>
You can have the result.csv file to easily check all the information you have accessed.


