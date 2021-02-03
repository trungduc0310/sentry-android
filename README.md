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
    implementation 'io.sentry:sentry-android:4.0.0'
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

Or, if you are manually instrumenting Sentry:

```java
 import io.sentry.android.core.SentryAndroid;

SentryAndroid.init(this, options -> {
  options.setDsn("https://examplePublicKey@o0.ingest.sentry.io/0");
});
  
```

## Verify
Great! Now that you’ve completed setting up the SDK, maybe you want to quickly test out how Sentry works. Start by capturing an 
exception:
```java
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import java.lang.Exception;
import io.sentry.Sentry;

public class MyActivity extends AppCompatActivity {
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try {
            throw new Exception("This is a test.");
        } catch (Exception e) {
            Sentry.captureException(e);
        }
    }
}
```
Then, you can see the error in your dashboard:

![](https://docs.sentry.io/static/888979c21d33485325a36fdc104055fb/2e237/android_error.png)

## Usage
### Capturing Errors
In Java you can capture any exception object that you caught:
```java
import io.sentry.Sentry;

try {
  aMethodThatMightFail();
} catch(Exception e) {
  Sentry.captureException(e);
}
```

### Capturing Messages
Another common operation is to capture a bare message. A message is textual information that should be sent to Sentry. Typically
messages are not emitted, but they can be useful for some teams.
```java
import io.sentry.Sentry;

Sentry.captureMessage("Something went wrong");
```

This project is inspired from [Sentry Android Sample](https://github.com/trungduc0310/sentry-android)
