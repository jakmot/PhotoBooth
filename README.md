# PhotoBooth
Application that allows you to take photos and see previously taken photos.

https://user-images.githubusercontent.com/31387943/115153407-fcede900-a075-11eb-9ed9-d1e13693598d.mp4

[Trello board](https://trello.com/b/RHjW77Xi)

## Setup
Application doesn't require any special setup. To build project simply run in terminal: `./gradlew app:assembleDebug
` or select `Run` in AndroidStudio

## About project
I chose MVVM pattern as it's the most popular choice, but most architectural patterns would work as well in such a simple app. 
You can see that data binding is missing, it can be introduced in the future, but right now it wouldn't give much value.

Saving the photo's creation date was troublesome, because in the Android framework you can't read it from file's properties below API 26.
Initially I was considering using local db, but I was concerned with possible difficulties with data synchronisation 
between db data with files. Using EXIF tags seems to be a much simpler and less bug-prone solution.
 
My approach to testing was slightly different than usual. Inspired by [the article](https://dagger.dev/hilt/testing-philosophy.html)
I decided to use real dependencies and fakes rather than mocking libraries. In the end I'm partly satisfied with the result, as tests look quite simpler and cleaner without all the mocking setup, but at the same time some interactions weren't covered in these tests.

## Furthers improvements
Right now there are no tests covering UI, so in the future I'd definitely add some Espresso tests and also UIAutomator/Appium tests. There are few edge cases not handled, see Trello board. 
From an architectural point of view, it'd be worth considering converting the app to a single activity app. That'd allow using navigation component, easy transition from thumbnail to full screen photo using fragments, clean overview of app's routes in xml file also using one view model to share data between screens.
