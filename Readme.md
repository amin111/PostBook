# PostBook
Android App for user to see posts and comments


# Basic Understanding Of MVVM : 
it devided into 3 parts 

1. View : it contain User Interface

2. Model : where actual business logic implemented and it exposes its data to viewmodel

3. ViewModel : it will get the data from Model and generate Observable which is observe by view part  

basically ViewModel have no idea who is observing 



# App Screens : 
it contain 4 screens

1. Splash Screen 

2. Login page : take user id from user

3. PostList page : it will fetch all post based on user it else prompt for 'No post is mapped' and ask for retry with Different user.
once the post list is fetched from server it will diveded into two parts 
3.1. All Post : Show all post 
3.2. Favriote Post : show only favriote one

**It also provide retry functionality if user is not mapped to any post than it will ask for retry with diffrent user, on click it will redireted to the login page**

4. Comment page : on click of post it will refrect to the comment page which have all comment against this post

# Screenshots :

**1** ![Alt text](/screenshot/1.png?raw=true "Splash Screen")  **2**  ![Alt text](/screenshot/2.png?raw=true "Login page")  **3.1**  ![Alt text](/screenshot/3.png?raw=true "All Post")  

===============================================================================

**3.2**  ![Alt text](/screenshot/4.png?raw=true "Favriote Post")  **5**  ![Alt text](/screenshot/5.png?raw=true "Comment page")



