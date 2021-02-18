# Sentry for Android


## Overview

Your application will crash whenever a thrown exception goes uncaught. The Sentry SDK catches the exception right before the 
crash and builds a crash report that will persist to the disk. The SDK will try to send the report right after the crash, but 
since the environment may be unstable at the crash time, the report is guaranteed to send once the application is started again.

If there is a fatal error in your native code, the process is similar. The crash report might send before the app crashes,
but will for sure send on restart.

The NDK is not only catching the unhandled exceptions but is also set as a signal handler to be able to react to the signals from
OS. When the application is about to crash, an error report is created and saved to disk. The SDK will try to send the report right
after the crash, but since the environment may be unstable at the crash time, the report is guaranteed to send once the application
is started again..



## Install
To install the Android SDK, add it your build.gradle file:
```groovy
 // Make sure jcenter or mavenCentral is there.
repositories {
    jcenter()
    // Or
    mavenCentral()
}

// Enable Java 1.8 source compatibility if you haven't yet.
android {
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
}

// Add Sentry's SDK as a dependency.
dependencies {
    implementation 'com.tcom.androidsentrysdk:androidsentry:1.0.0'
}
  
```


## Configure
After you’ve completed setting up a project in Sentry, Sentry will give you a value which we call a DSN or Data Source Name. It 
looks a lot like a standard URL, but it’s just a representation of the configuration required by the Sentry SDKs. It consists of 
a few pieces, including the protocol, public key, the server address, and the project identifier.

Configuration is done via AndroidManifest.xml:
```xml
 <application>
  <meta-data android:name="io.sentry.dsn" android:value="https://examplePublicKey@o0.ingest.sentry.io/0" />
</application>
  
```

## Usage

### User
Users consist of a few critical pieces of information that construct a unique identity in Sentry. Each of these is optional, but one 
must be present for the Sentry SDK to capture the user:

id
Your internal identifier for the user.

username
The username. Typically used as a better label than the internal id.

email
An alternative, or addition, to the username. Sentry is aware of email addresses and can display things such as Gravatars and unlock 
messaging capabilities.

ip_address
The user's IP address. If the user is unauthenticated, Sentry uses the IP address as a unique identifier for the user. Sentry will 
attempt to pull this from the HTTP request data, if available. Set to "{{auto}}" to let Sentry infer the IP address from the connection.

Additionally, you can provide arbitrary key/value pairs beyond the reserved names, and the Sentry SDK will store those with the user.

To identify the user:
```java
 userBuilder = new UserBuilder().setId("id1")
                .setUserName("abc1")
                .setEmail("abc123@gmail.com");
 userBuilder.create();
```
You can also clear the currently set user:
```java
 userBuilder.unSetUser();
```
### Capturing Errors
In Java you can capture any exception object that you caught:
```java
 logCapture = new LogCapture(userBuilder);
 try {
           int c = 2 / 0;
     } catch (Exception ex) {
           logCapture.captureExeption(ex);
     }
```

### Capturing Messages
Another common operation is to capture a bare message. A message is textual information that should be sent to Sentry. Typically
messages are not emitted, but they can be useful for some teams.
```java
 logCapture = new LogCapture(userBuilder);
 logCapture.captureMessage("Log message");
```

### FileAttachment
The simplest way to create an attachment is to use a path. The SDK will read the contents of the file each time it prepares an 
event or transaction, then adds the attachment to the same envelope. If the SDK can't read the file, the SDK logs an error message 
and drops the attachment.
```java
 logAttachment = new LogAttachment();
 logAttachment.addAttachment("localPath","fileLog");
```

This project is inspired from [Sentry Android Sample](https://github.com/trungduc0310/sentry-android)
