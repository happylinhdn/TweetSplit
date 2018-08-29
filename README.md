# TweetSplit
This is example written by kotlin
Screen:
   - History: show all message which you sent
   - New Message: Allow the user to input and send messages
Features:
   - If a user's input is less than or equal to 50 characters, post it as is.
   - If a user's input is greater than 50 characters, split it into chunks that each is less than or equal to 50 characters and post each chunk as a separate message.
   - The functionality that splits messages should be a standalone function. Given the above example, its function call would look like:
```
splitMessage("I can't believe Tweeter now supports chunking my messages, so I don't have to do it myself.")
```
and it would return
```
["1/2 I can't believe Tweeter now supports chunking", "2/2 my messages, so I don't have to do it myself."]
```

Another Skill:
   - MVP architecture
   - Room architecture
   - Recycler, CardView, FragmentActivity, Fragment
   - RxJava

UnitTest:
   - Open terminal at root folder of project, type command: ./gradlew test
   - Open to see the result of unit test: TweetSplit/app/build/reports/tests/testDebugUnitTest/packages/zalora.twitsplit.html
   
   Reference: ![alt text](./unit_test.png)
