# NewsBreeze
Developed using Kotlin, MVVM, Retrofit, Shared Preference, Room, koin, Recyclearview, Collapsing Toolbar

# Features
News List : 
* The list will show show based on internet connectivity. If the internet in available latest news list taking from https://newsapi.org/, after fetching the list it will store in local db
* If internet not there check local data base, if the list contains will show in list
* From the list user can search using title also can able to save the news.
* After save the news, the local db save isSave flag will update in local Db
* User can move to detail screen after clicking the item or Read button
* Top Actionbar have save button. After clicking this user will navigate to Saved News list screen

Detail Screen : 
* The details screen will work offline
* The Selected News item will show here, we are using shared preference for storing selected news item
* The detail screen using Collapsing Toolbar and from here also user can save or unsave the purticular news

Saved News List : 
* The list will work offline.
* The saved list we are showing from local db news list using isSave flag.
* After clicking one news item, user will navigate to detail screen
* User can search purticular saved news here
