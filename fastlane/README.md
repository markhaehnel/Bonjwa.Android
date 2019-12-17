fastlane documentation
================
# Installation

Make sure you have the latest version of the Xcode command line tools installed:

```
xcode-select --install
```

Install _fastlane_ using
```
[sudo] gem install fastlane -NV
```
or alternatively using `brew cask install fastlane`

# Available Actions
## Android
### android assemble
```
fastlane android assemble
```
Build app in debug and release
### android unit_test
```
fastlane android unit_test
```
Run unit tests
### android instrumentation_test
```
fastlane android instrumentation_test
```
Run instrumentation tests
### android deploy
```
fastlane android deploy
```
Build & deploy release to Google Play internal track

----

This README.md is auto-generated and will be re-generated every time [fastlane](https://fastlane.tools) is run.
More information about fastlane can be found on [fastlane.tools](https://fastlane.tools).
The documentation of fastlane can be found on [docs.fastlane.tools](https://docs.fastlane.tools).
