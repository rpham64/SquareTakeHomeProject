## Build tools & versions used

Gradle = 7.3.3

Libraries:

- Android Core KTX: 1.8.0
- Android Material: 1.6.1
- App Compat: 1.4.2
- Constraint Layout: 2.1.4
- Coroutines: 1.6.4
- Dagger: 2.42
- Espresso: 3.4.0
- Glide: 4.13.2
- Gson: 2.9.0
- JUnit: 1.1.3
- Lifecycle (includes ViewModel, LiveData, Lifecycle): 2.5.0
- LiveData Testing: 2.1.0
- Mockito: 4.6.1
- Mockito (Android): 4.0.0
- Retrofit: 2.9.0
- Swipe Refresh Layout: 1.1.0

## Steps to run the app

1) Build the app.
2) Connect an Android device (either emulator or physical) that's running Android API 26+.
3) Click on the green play button to run the app.

## What areas of the app did you focus on?

- MVVM Architecture based on Google's recommended app
  architecture: https://developer.android.com/topic/architecture
    - Basic idea is to use MVVM and split the app into UI (Activity, ViewModel), Data (Repository,
      Data Sources), and Domain (optional, but not used in this project) layers
- Unit testing via JUnit and Mockito
    - Instructions mentioned that only unit tests were needed, so I didn't add any
      Instrumentation/UI or Integration tests
- Using different UI states: Loading, Content (employee list can be either non-empty or empty),
  Error

## What was the reason for your focus? What problems were you trying to solve?

- Production-ready code
    - Customers should use an app that's built well and doesn't break the user experience
- Easily testable
    - To catch and prevent easily reproducible bugs from making it into the production app
- Separation of Concerns (similarly, Single Responsibility Principle)
    - Scalability -> Easier to scale and test an app if all components are simple and easy to add to

## How long did you spend on this project?

~7-8 hours

## Did you make any trade-offs for this project? What would you have done differently with more time?

- Room for improvement to user experience and UI
    - Since this app is more focused on architecture and being production-ready, I didn't spend much
      time polishing the colors or views
        - Examples
            - Scaling smaller user pictures to be larger. This would make all user pictures the same
              size.
            - Adding dark mode colors
- Accessibility
    - Similarly to the above, no accessibility elements were added to this project.

## What do you think is the weakest part of your project?

- The UI is very barebones since a lot of time was spent on architecture and testing the app.

## Did you copy any code or dependencies? Please make sure to attribute them here!

- Used Google's LiveData.observeForTesting utility function to test LiveData
    -
    Source: https://github.com/android/architecture-components-samples/blob/master/LiveDataSample/app/src/test/java/com/android/example/livedatabuilder/util/LiveDataTestUtil.kt#L61-L68

## Is there any other information you’d like us to know?
- To test and show the loading screen, I added a 2 second delay to show the loading spinner before the employee list is fetched and shown. This is because fetching the employee list from backend is very fast.
