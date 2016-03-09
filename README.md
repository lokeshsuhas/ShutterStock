# ShutterStock
Shutter Stock Images API app using MVVM ,RXJava, Dagger2




##External Libraries
  > Retrofit 2 for API 
  ```
  'com.squareup.retrofit2:retrofit:2.0.0-beta4' {
      // exclude Retrofit’s OkHttp peer-dependency module and define your own module import
      exclude module: 'okhttp'
  }
  'com.squareup.retrofit2:converter-gson:2.0.0-beta4'
  'com.squareup.retrofit2:adapter-rxjava:2.0.0-beta4'
  ```
  > Dagger 2 for Dependency Injection
  ```
  'com.google.dagger:dagger:2.0.2'
  'com.google.dagger:dagger-compiler:2.0.2'
  'org.glassfish:javax.annotation:10.0-b28'
  ```
  
  > AppCompact
  ```
  'com.android.support:appcompat-v7:23.1.1'
  ```
  
  > RecyclerView for Images
  ```
  'com.android.support:recyclerview-v7:23.1.1' 
  ```
  >RX
  ```
  'io.reactivex:rxjava:1.1.0'
  'io.reactivex:rxandroid:1.1.0'
  ```
  > GSON conversion library
  ```
  'com.google.code.gson:gson:2.4'
  ```
  > Picasso for loading images
  ```
  'com.squareup.picasso:picasso:2.5.2'
  ```
  > For making the Picasso to use Okhttp3 for downloading the images
  ```
  'com.jakewharton.picasso:picasso2-okhttp3-downloader:1.0.2'
  ```
  > Ohttp3 for API calls
  ```
  'com.squareup.okhttp3:okhttp:3.0.0' 
  ```
  Note: Modified the below RxRecyclerAdapter for our scenario and included in the project directly.
  [RxRecyclerAdapter](https://github.com/ahmedrizwan/RxRecyclerAdapter)
  
##Patterns Used
  1. **MVVM** whole design pattern with Data binding
  2. **Builder Pattern** for Adapter Source
  
##Shutter Stock URLS
```
Base API: https://api.shutterstock.com/v2/
Login API: BaseAPI+oauth/access_token
Image API: BaseAPI+images/search
```
###Shutter stock login for testing
```
Username: lokeshsuhas@gmail.com
Password: l0kesh@1
```

##Scenarios Covered
*	Shutter Stock OAuth 2.0 login support
*	Smooth and no lag in UI
*	Screen orientation
*	State maintenance on orientation change
*	Grid adaption based on orientation.

##ScreenShots


