# PostBook
Understanding Of MVVM &amp; Retrofit to develop maintainable and testable structure 


# Basic Understanding Of MVVM : 
it devided into 3 parts 

1. View : it contain User Interface

2. Model : where actual business logic implemented and it exposes its data to viewmodel

3. ViewModel : it will get the data from Model and generate Observable which is observe by view part  

basically ViewModel have no idea who is observing 

# Basic Understanding of PostBook : 
it contain 3 screen

1. Login page : take user id from user

2. PostList page : it will fetch all post based on user it else prompt for 'No post is mapped' and ask for retry with Different user.
once the post list is fetched from server it will diveded into two parts 
2.1. All Post : Show all post 
2.2. Favriote Post : show only favriote one

3. Comment page : on click of post it will refrect to the comment page which have all comment against this post
