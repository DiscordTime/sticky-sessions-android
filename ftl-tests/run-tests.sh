#!/bin/sh

# Run tests on test lab
gcloud firebase test android run \
    --type instrumentation \
    --app app/build/outputs/apk/prod/debug/app-prod-debug.apk \
    --test app/build/outputs/apk/androidTest/prod/debug/app-prod-debug-androidTest.apk \
    --device model=Nexus6P,version=27,locale=en_US,orientation=portrait \
    --timeout 30m